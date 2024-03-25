package com.example.semesta_mitra_sejahtera.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.semesta_mitra_sejahtera.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.adapter.ArtikelObatAdapter;
import com.example.semesta_mitra_sejahtera.adapter.ArtikelPakanAdapter;
import com.example.semesta_mitra_sejahtera.api.apiconfig;
import com.example.semesta_mitra_sejahtera.model.ArtikelModel;
import com.example.semesta_mitra_sejahtera.model.JadwalPanenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView rvArtikelPakan;
    private RecyclerView rvArtikelObat;
    private ArtikelObatAdapter artikelPakanAdapter;
    private ArtikelObatAdapter artikelObatAdapter;
    private List<ArtikelModel> artikelModelList;
    TextView tvNama;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);

        rvArtikelPakan = view.findViewById(R.id.rv_artikel_pakan);
        rvArtikelObat = view.findViewById(R.id.rv_artikel_obat);
        tvNama = view.findViewById(R.id.txt_nama_user);

        sharedPreferences = requireContext().getSharedPreferences("UserData", 0);
        String nama = sharedPreferences.getString("nama", "");

        // Inisialisasi ProgressDialog
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Harap tunggu...");
        progressDialog.setCancelable(false);

        // Ubah nama menjadi huruf awal kapital
        tvNama.setText(capitalizeFirstLetter(nama));

        progressDialog.show();


        requestQueue = Volley.newRequestQueue(requireContext());

        rvArtikelPakan.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvArtikelObat.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Panggil kedua metode fetchArtikelData secara bersamaan
        fetchArtikelDataObat();
        fetchArtikelDataPakan();


        return view;
    }


    private void fetchArtikelDataObat() {
        String url = apiconfig.ARTIKEL_ENDPOINT + "?kategori=" + "obat";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<ArtikelModel> artikelList = new ArrayList<>();

                        for (int i = 0; i < Math.min(response.length(), 3); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String judul = jsonObject.getString("judul");
                                String kategori = jsonObject.getString("kategori");
                                String deskripsi = jsonObject.getString("deskripsi");

                                ArtikelModel artikel = new ArtikelModel(id, judul, kategori, deskripsi);
                                artikelList.add(artikel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArtikelObatAdapter adapter = new ArtikelObatAdapter(requireContext(), artikelList);
                        rvArtikelObat.setAdapter(adapter);

                        // Tutup ProgressDialog setelah RecyclerView dikonfigurasi
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("DashboardFragment", "Error fetching data for category " + ": " + error.getMessage());

                        // Pastikan juga untuk menutup ProgressDialog dalam kondisi error
                        progressDialog.dismiss();
                    }
                }
        );

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void fetchArtikelDataPakan() {
        String url = apiconfig.ARTIKEL_ENDPOINT + "?kategori=" + "pakan";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<ArtikelModel> artikelList = new ArrayList<>();

                        for (int i = 0; i < Math.min(response.length(), 3); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String judul = jsonObject.getString("judul");
                                String kategori = jsonObject.getString("kategori");
                                String deskripsi = jsonObject.getString("deskripsi");

                                ArtikelModel artikel = new ArtikelModel(id, judul, kategori, deskripsi);
                                artikelList.add(artikel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArtikelPakanAdapter adapter = new ArtikelPakanAdapter(requireContext(), artikelList);
                        rvArtikelPakan.setAdapter(adapter);

                        // Tutup ProgressDialog setelah RecyclerView dikonfigurasi
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("DashboardFragment", "Error fetching data for category " + ": " + error.getMessage());

                        // Pastikan juga untuk menutup ProgressDialog dalam kondisi error
                        progressDialog.dismiss();
                    }
                }
        );

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
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


