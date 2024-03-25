package com.example.semesta_mitra_sejahtera.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.semesta_mitra_sejahtera.adapter.JadwalPanenAdapter;
import com.example.semesta_mitra_sejahtera.api.apiconfig;
import com.example.semesta_mitra_sejahtera.model.JadwalPanenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JadwalPanenFragment extends Fragment {

    private RecyclerView recyclerView;
    private JadwalPanenAdapter jadwalPanenAdapter;
    private SharedPreferences sharedPreferences;
    private List<JadwalPanenModel> jadwalPanenList;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jadwal_panen_layout, container, false);

        recyclerView = view.findViewById(R.id.rvJadwalPanen);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        jadwalPanenList = new ArrayList<>();
        jadwalPanenAdapter = new JadwalPanenAdapter(jadwalPanenList, requireContext());
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

        //String toastMessage = "ID Peternakan: " + peternakanId;
        //Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(requireContext());


        jadwalPanenAdapter.setOnItemClickListener(new JadwalPanenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(JadwalPanenModel jadwalPanenModel) {
                int idJadwalPanen = jadwalPanenModel.getId();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id_perkembangan", idJadwalPanen);
                editor.apply();

                Intent intent = new Intent(requireContext(), DetailJadwalPanenActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(jadwalPanenAdapter);



        return view;
    }
    private void fetchDataFromApi() {
        progressDialog.show();
        String userId = String.valueOf(sharedPreferences.getInt("id", 0));
        String peternakanId = String.valueOf(sharedPreferences.getInt("id_peternakan", 0));

        if (userId.isEmpty()) {
            Log.e("JadwalPanenFragment", "UserData ID is Empty");
        }
        String url = apiconfig.TAMPIL_JADWAL_PANEN_ENDPOINT + "?id_user=" + userId + "&id=" + peternakanId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jadwalPanenObject = jsonArray.getJSONObject(i);

                                int id = jadwalPanenObject.getInt("id");
                                String tanggal = jadwalPanenObject.getString("tanggal");
                                int jumlahAyam = jadwalPanenObject.getInt("jumlah_ayam");
                                int bobotAyam = jadwalPanenObject.getInt("bobot_ayam");
                                String namaSopir = jadwalPanenObject.getString("nama_sopir");
                                String nopol = jadwalPanenObject.getString("nopol");
                                String namaPeternakan = jadwalPanenObject.getString("nama_peternakan");



                                JadwalPanenModel jadwalPanen = new JadwalPanenModel(id, tanggal, jumlahAyam, bobotAyam, namaSopir, nopol, namaPeternakan );
                                jadwalPanenList.add(jadwalPanen);
                            }

                            jadwalPanenAdapter.notifyDataSetChanged();
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
