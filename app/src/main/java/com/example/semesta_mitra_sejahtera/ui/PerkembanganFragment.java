package com.example.semesta_mitra_sejahtera.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.adapter.CabangAdapter;
import com.example.semesta_mitra_sejahtera.adapter.PerkembanganAdapter;
import com.example.semesta_mitra_sejahtera.model.CabangModel;
import com.example.semesta_mitra_sejahtera.model.PerkembanganModel;
import com.example.semesta_mitra_sejahtera.api.apiconfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PerkembanganFragment extends Fragment {
    private RecyclerView recyclerView;
    private PerkembanganAdapter perkembanganAdapter;
    private SharedPreferences sharedPreferences;
    private List<PerkembanganModel> perkembanganList;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perkembangan_layout, container, false);

        recyclerView = view.findViewById(R.id.rvPerkembangan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        perkembanganList = new ArrayList<>();
        perkembanganAdapter = new PerkembanganAdapter(requireContext(), perkembanganList);
        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String peternakanId = String.valueOf(sharedPreferences.getInt("id_peternakan", 0));

        // Inisialisasi ProgressDialog
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Harap tunggu...");
        progressDialog.setCancelable(false);

        // Tambahkan percabangan berdasarkan nilai id_peternakan
        if (peternakanId.equals("0")) {
            // Tampilkan Toast bahwa peternakan harus dipilih terlebih dahulu
            Toast.makeText(requireContext(), "Pilih peternakan terlebih dahulu di menu akun", Toast.LENGTH_SHORT).show();
        } else {
            // Panggil method untuk mengambil data dari API Laravel
            fetchDataFromApi();
        }

        // Temukan tombol Floating Action Button
        FloatingActionButton fabTambahPerkembangan = view.findViewById(R.id.btnTambahPerkembangan);

        // Atur OnClickListener untuk FAB
        fabTambahPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jalankan intent ke TambahPerkembanganActivity
                Intent intent = new Intent(requireActivity(), TambahPerkembanganActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // PerkembanganFragment
    private void fetchDataFromApi() {
        progressDialog.show();
        String userId = String.valueOf(sharedPreferences.getInt("id", 0));
        String peternakanId = String.valueOf(sharedPreferences.getInt("id_peternakan", 0)); // Perbaiki penamaan atribut

        if (userId.isEmpty()) {
            Log.e("PerkembanganFragment", "UserData ID is Empty");
        }
        String url = apiconfig.TAMPIL_PERKEMBANGAN_ENDPOINT + "?id_user=" + userId + "&id=" + peternakanId; // Perbaiki URL

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderObj = jsonArray.getJSONObject(i);

                        // Parsing data dari JSON response ke objek PerkembanganModel
                        int id = orderObj.getInt("id");
                        String tanggal = orderObj.getString("tanggal");
                        int minggu_ke = orderObj.getInt("minggu_ke");
                        double pakan_pakai = orderObj.getDouble("pakan_pakai");
                        double pakan_sisa = orderObj.getDouble("pakan_sisa");
                        double bobot = orderObj.getDouble("bobot");
                        int afkir = orderObj.getInt("afkir");
                        int kematian = orderObj.getInt("kematian");
                        String nama_peternakan = orderObj.getString("nama_peternakan");
                        String alamat = orderObj.getString("alamat");
                        String created_at = orderObj.getString("created_at");
                        String updated_at = orderObj.getString("updated_at");

                        PerkembanganModel perkembanganModel = new PerkembanganModel(id,tanggal, minggu_ke, pakan_pakai,
                                pakan_sisa, bobot, afkir, kematian, nama_peternakan, alamat, created_at, updated_at);
                        perkembanganList.add(perkembanganModel);
                    }

                    perkembanganAdapter = new PerkembanganAdapter(requireContext(), perkembanganList);
                    recyclerView.setAdapter(perkembanganAdapter);
                    progressDialog.dismiss(); // Menutup ProgressDialog

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(requireContext());
        }
        // Tambahkan request ke antrian Volley
        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tambahkan listener onBackPressed
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Panggil metode untuk keluar dari aplikasi
                exitApp();
            }
        });
    }

    // Metode untuk keluar dari aplikasi
    private void exitApp() {
        requireActivity().finishAffinity(); // Keluar dari aplikasi
    }

}
