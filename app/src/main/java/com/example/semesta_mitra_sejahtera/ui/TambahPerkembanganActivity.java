package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.api.apiconfig;

import java.util.HashMap;
import java.util.Map;

public class TambahPerkembanganActivity extends AppCompatActivity {

    ImageView btnKembali;

    EditText et_minggu_perkembangan, et_pakan_pakai_perkembangan,
            et_pakan_sisa_perkembangan, et_kematian_perkembangan,
            et_afkir_perkembangan, et_bobot_perkembangan;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_perkembangan_layout);

        // Inisialisasi variabel
        et_minggu_perkembangan = findViewById(R.id.etMingguKe);
        et_pakan_pakai_perkembangan = findViewById(R.id.etPakanPakai);
        et_pakan_sisa_perkembangan = findViewById(R.id.etPakanSisa);
        et_kematian_perkembangan = findViewById(R.id.etKematian);
        et_afkir_perkembangan = findViewById(R.id.etAfkir);
        et_bobot_perkembangan = findViewById(R.id.etBobot);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeAkun();
            }

            private void kembaliKeAkun(){
                Intent intent = new Intent(TambahPerkembanganActivity.this, MainActivity.class);
                intent.putExtra("tab", "perkembangan"); // Menambahkan ekstra "tab" untuk membuka tab Akun
                startActivity(intent);
                finish();
            }
        });
    }

    public void tambahPerkembangan(View view) {
        String minggu_perkembangan = et_minggu_perkembangan.getText().toString();
        String pakan_pakai_perkembangan = et_pakan_pakai_perkembangan.getText().toString();
        String pakan_sisa_perkembangan = et_pakan_sisa_perkembangan.getText().toString();
        String kematian_perkembangan = et_kematian_perkembangan.getText().toString();
        String afkir_perkembangan = et_afkir_perkembangan.getText().toString();
        String bobot_perkembangan = et_bobot_perkembangan.getText().toString();

        if (minggu_perkembangan.isEmpty()) {
            Toast.makeText(this, "Minggu Perkembangan tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (!isMingguValid(minggu_perkembangan)) {
            Toast.makeText(this, "Minggu perkembangan harus diisi antara 1-4", Toast.LENGTH_SHORT).show();
        } else if (pakan_pakai_perkembangan.isEmpty()) {
            Toast.makeText(this, "Pakan pakai tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (pakan_sisa_perkembangan.isEmpty()) {
            Toast.makeText(this, "Pakan sisa tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (kematian_perkembangan.isEmpty()) {
            Toast.makeText(this, "Kematian tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (afkir_perkembangan.isEmpty()) {
            Toast.makeText(this, "Afkir tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (bobot_perkembangan.isEmpty()) {
            Toast.makeText(this, "Bobot ayam tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {

            showAlertDialog();


        }


    }

    private boolean isMingguValid(String minggu) {
        try {
            int mingguInt = Integer.parseInt(minggu);
            return mingguInt >= 1 && mingguInt <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(TambahPerkembanganActivity.this, MainActivity.class);
        intent.putExtra("tab", "perkembangan");
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }
    private void showAlertDialog() {
            ConstraintLayout constraintLayout = findViewById(R.id.alertDialog);
            View view = getLayoutInflater().inflate(R.layout.alert_konfirmasi_tambah, constraintLayout, false);
            Button buttonTidak = view.findViewById(R.id.btnTidak);
            Button buttonYa = view.findViewById(R.id.btnIya);

            ProgressDialog progressDialog;

            // Inisialisasi ProgressDialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Harap tunggu...");
            progressDialog.setCancelable(false);

            // Inisialisasi AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            buttonYa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    String minggu_perkembangan = et_minggu_perkembangan.getText().toString();
                    String pakan_pakai_perkembangan = et_pakan_pakai_perkembangan.getText().toString();
                    String pakan_sisa_perkembangan = et_pakan_sisa_perkembangan.getText().toString();
                    String kematian_perkembangan = et_kematian_perkembangan.getText().toString();
                    String afkir_perkembangan = et_afkir_perkembangan.getText().toString();
                    String bobot_perkembangan = et_bobot_perkembangan.getText().toString();
                    // Melakukan proses tambah perkembangan
                    String url = apiconfig.TAMBAH_PERKEMBANGAN_ENDPOINT;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Show your custom AlertDialog for success
                                    showAlertDialog();

                                    Toast.makeText(TambahPerkembanganActivity.this, "Tambah perkembangan Berhasil", Toast.LENGTH_SHORT).show();
                                    Log.d("DetailorderActivity", "Response: " + response);


                                    // Navigasi kembali ke MainActivity
                                    Intent intent = new Intent(TambahPerkembanganActivity.this, MainActivity.class);
                                    intent.putExtra("tab", "perkembangan"); // Menambahkan ekstra "tab" untuk membuka tab Perkembangan
                                    startActivity(intent);
                                    finish(); // Mengakhiri activity TambahPerkembanganActivity


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(TambahPerkembanganActivity.this, "Tambah Perkembangan Gagal", Toast.LENGTH_SHORT).show();
                                    Log.d("DetailorderActivity", "Error: " + error);

                                    // Menghilangkan ProgressDialog jika terjadi kesalahan
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();

                            // Ambil ID_Peternakan pengguna dari penyimpanan
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            String peternakanId = String.valueOf(sharedPreferences.getInt("id_peternakan", 0));

                            params.put("minggu_ke", minggu_perkembangan);
                            params.put("pakan_pakai", pakan_pakai_perkembangan);
                            params.put("pakan_sisa", pakan_sisa_perkembangan);
                            params.put("bobot", bobot_perkembangan);
                            params.put("afkir", afkir_perkembangan);
                            params.put("kematian", kematian_perkembangan);
                            params.put("id_peternakan", peternakanId);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(TambahPerkembanganActivity.this);
                    requestQueue.add(stringRequest);
                    progressDialog.dismiss(); // Menutup ProgressDialog

                }
            });

            buttonTidak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        // Show the AlertDialog
        alertDialog.show();

    }



}
