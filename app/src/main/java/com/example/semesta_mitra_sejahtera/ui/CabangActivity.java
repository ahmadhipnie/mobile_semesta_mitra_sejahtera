package com.example.semesta_mitra_sejahtera.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.adapter.CabangAdapter;
import com.example.semesta_mitra_sejahtera.api.apiconfig;
import com.example.semesta_mitra_sejahtera.model.CabangModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CabangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView btnKembali;
    private CabangAdapter CabangAdapter;
    private List<CabangModel> cabangList;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabang_layout);

        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeAkun();
            }

            private void kembaliKeAkun() {
                Intent intent = new Intent(CabangActivity.this, MainActivity.class);
                intent.putExtra("tab", "akun");
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.rvCabang);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cabangList = new ArrayList<>();
        CabangAdapter = new CabangAdapter(this, cabangList);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        // Inisialisasi ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Harap tunggu...");
        progressDialog.setCancelable(false);



        fetchDataFromApi();

        CabangAdapter.setOnItemClickListener(new CabangAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CabangModel cabangModel) {
                int idPeternakan = cabangModel.getId();
                String namaPeternakan = cabangModel.getNamaPeternakan();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id_peternakan", idPeternakan);
                editor.putString("nama_peternakan", namaPeternakan);
                editor.apply();

                Intent intent = new Intent(CabangActivity.this, MainActivity.class);
                intent.putExtra("tab", "akun");
                startActivity(intent);
                finish();

                String message = "Peternakan yang dipilih : " + cabangModel.getNamaPeternakan();
                Toast.makeText(CabangActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataFromApi() {
        progressDialog.show();
        String userId = String.valueOf(sharedPreferences.getInt("id", 0));
        if (userId.isEmpty()) {
            Log.e("CabangActivity", "UserData ID is Empty");
            return;
        }

        String url = apiconfig.TAMPIL_PETERNAKAN_ENDPOINT + "?id_user=" + userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject orderObj = response.getJSONObject(i);

                                // Parsing data dari JSON response ke objek PerkembanganModel
                                int id = orderObj.getInt("id");
                                String nama_peternakan = orderObj.getString("nama_peternakan");
                                int kapasitas = orderObj.getInt("kapasitas");
                                String alamat = orderObj.getString("alamat");
                                String no_telp = orderObj.getString("no_telp");

                                CabangModel cabangModel = new CabangModel(id, nama_peternakan, kapasitas, alamat, no_telp);
                                cabangList.add(cabangModel);
                            }

                            CabangAdapter = new CabangAdapter(CabangActivity.this, cabangList);
                            recyclerView.setAdapter(CabangAdapter);
                            progressDialog.dismiss(); // Menutup ProgressDialog
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("CabangActivity", "Error: " + error.getMessage());
                    }
                });

        requestQueue.add(request);
    }
    public void onBackPressed() {
            Intent intent = new Intent(CabangActivity.this, MainActivity.class);
            intent.putExtra("tab", "akun");
            startActivity(intent);
            finish();

            super.onBackPressed(); // Kembali ke activity sebelumnya
    }
}
