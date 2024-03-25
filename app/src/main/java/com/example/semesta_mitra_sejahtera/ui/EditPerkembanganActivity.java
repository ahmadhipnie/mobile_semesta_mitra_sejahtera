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
import android.widget.ImageView;
import android.widget.TextView;
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

public class EditPerkembanganActivity extends AppCompatActivity {

    ImageView btnKembali;

    TextView txt_minggu_perkembangan, txt_pakan_pakai_perkembangan, txt_pakan_sisa_perkembangan,
            txt_kematian_perkembangan, txt_afkir_perkembangan, txt_bobot_perkembangan;

    Button btn_update;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_perkembangan_layout);

        // Inisialisasi variabel
        txt_minggu_perkembangan = findViewById(R.id.etMingguKe);
        txt_pakan_pakai_perkembangan = findViewById(R.id.etPakanPakai);
        txt_pakan_sisa_perkembangan = findViewById(R.id.etPakanSisa);
        txt_kematian_perkembangan = findViewById(R.id.etKematian);
        txt_afkir_perkembangan = findViewById(R.id.etAfkir);
        txt_bobot_perkembangan = findViewById(R.id.etBobot);
        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKePerkembangan();
            }

            private void kembaliKePerkembangan() {
                Intent intent = new Intent(EditPerkembanganActivity.this, MainActivity.class);
                intent.putExtra("tab", "perkembangan");
                startActivity(intent);
                finish();
            }
        });

        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        int idPerkembangan = intent.getIntExtra("id", 0);
        int mingguPerkembangan = intent.getIntExtra("minggu_ke", 0);
        double pakanPakaiPerkembangan = intent.getDoubleExtra("pakan_pakai", 0);
        double pakanSisaPerkembangan = intent.getDoubleExtra("pakan_sisa", 0);
        int kematianPerkembangan = intent.getIntExtra("kematian", 0);
        int afkirPerkembangan = intent.getIntExtra("afkir", 0);
        double bobotPerkembangan = intent.getDoubleExtra("bobot", 0);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Menampilkan data yang diterima melalui Intent
        txt_minggu_perkembangan.setText(String.valueOf(mingguPerkembangan));
        txt_pakan_pakai_perkembangan.setText(String.valueOf(pakanPakaiPerkembangan));
        txt_pakan_sisa_perkembangan.setText(String.valueOf(pakanSisaPerkembangan));
        txt_kematian_perkembangan.setText(String.valueOf(kematianPerkembangan));
        txt_afkir_perkembangan.setText(String.valueOf(afkirPerkembangan));
        txt_bobot_perkembangan.setText(String.valueOf(bobotPerkembangan));
    }
    public void onBackPressed() {
        Intent intent = new Intent(EditPerkembanganActivity.this, DetailPerkembanganActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }

    public void editPerkembangan(View view) {
        String id_perkembangan = String.valueOf(getIntent().getIntExtra("id", 0));
        String minggu_perkembangan = txt_minggu_perkembangan.getText().toString();
        String pakan_pakai_perkembangan = txt_pakan_pakai_perkembangan.getText().toString();
        String pakan_sisa_perkembangan = txt_pakan_sisa_perkembangan.getText().toString();
        String kematian_perkembangan = txt_kematian_perkembangan.getText().toString();
        String afkir_perkembangan = txt_afkir_perkembangan.getText().toString();
        String bobot_perkembangan = txt_bobot_perkembangan.getText().toString();

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
            // Menampilkan AlertDialog
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

    private void showAlertDialog() {
        ConstraintLayout constraintLayout = findViewById(R.id.alertDialogEdit);
        View view = getLayoutInflater().inflate(R.layout.alert_konfirmasi_edit, constraintLayout, false);
        Button buttonKonfimasi = view.findViewById(R.id.btnKonfirmasi);

        // Inisialisasi AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        buttonKonfimasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_perkembangan = String.valueOf(getIntent().getIntExtra("id", 0));
                String minggu_perkembangan = txt_minggu_perkembangan.getText().toString();
                String pakan_pakai_perkembangan = txt_pakan_pakai_perkembangan.getText().toString();
                String pakan_sisa_perkembangan = txt_pakan_sisa_perkembangan.getText().toString();
                String kematian_perkembangan = txt_kematian_perkembangan.getText().toString();
                String afkir_perkembangan = txt_afkir_perkembangan.getText().toString();
                String bobot_perkembangan = txt_bobot_perkembangan.getText().toString();


                // Panggil method untuk mengirim data ke API
                // Menampilkan ProgressDialog
                ProgressDialog progressDialog = new ProgressDialog(EditPerkembanganActivity.this);
                progressDialog.setMessage("Mengedit perkembangan...");
                progressDialog.show();

                // Melakukan proses edit perkembangan
                String url = apiconfig.EDIT_PERKEMBANGAN_ENDPOINT;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss(); // Menutup ProgressDialog
                                Toast.makeText(EditPerkembanganActivity.this, "Edit perkembangan Berhasil", Toast.LENGTH_SHORT).show();
                                Log.d("DetailorderActivity", "Response: " + response);

                                // Navigasi kembali ke MainActivity
                                Intent intent = new Intent(EditPerkembanganActivity.this, MainActivity.class);
                                intent.putExtra("tab", "perkembangan"); // Menambahkan ekstra "tab" untuk membuka tab Perkembangan
                                startActivity(intent);
                                finish(); // Mengakhiri activity TambahPerkembanganActivity
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss(); // Menutup ProgressDialog
                                Toast.makeText(EditPerkembanganActivity.this, "Edit Perkembangan Gagal", Toast.LENGTH_SHORT).show();
                                Log.d("DetailorderActivity", "Error: " + error);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        // Ambil ID_Peternakan pengguna dari penyimpanan
                        params.put("id", id_perkembangan);
                        params.put("minggu_ke", minggu_perkembangan);
                        params.put("pakan_pakai", pakan_pakai_perkembangan);
                        params.put("pakan_sisa", pakan_sisa_perkembangan);
                        params.put("bobot", bobot_perkembangan);
                        params.put("afkir", afkir_perkembangan);
                        params.put("kematian", kematian_perkembangan);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(EditPerkembanganActivity.this);
                requestQueue.add(stringRequest);
                alertDialog.dismiss();
            }
        });
    }
}
