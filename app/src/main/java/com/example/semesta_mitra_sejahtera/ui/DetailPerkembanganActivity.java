package com.example.semesta_mitra_sejahtera.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.api.apiconfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailPerkembanganActivity extends AppCompatActivity {

    ImageView btnKembali;

    Button btnEditPerkembangan, btnHapusPerkembangan;
    TextView txt_id_perkembangan, txt_tanggal_perkembangan, txt_tanggal_diupdate_perkembangan,
            txt_minggu_perkembangan, txt_pakan_pakai_perkembangan, txt_pakan_sisa_perkembangan,
            txt_kematian_perkembangan, txt_nama_peternakan_perkembangan, txt_afkir_perkembangan ,txt_bobot_perkembangan ;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_perkembangan_layout);

        // Inisialisasi variabel
        txt_id_perkembangan = findViewById(R.id.txt_id_perkembangan);
        txt_tanggal_perkembangan = findViewById(R.id.txt_tanggal_perkembangan);
        txt_tanggal_diupdate_perkembangan = findViewById(R.id.txt_tanggal_diupdate_perkembangan);
        txt_minggu_perkembangan = findViewById(R.id.txt_minggu_perkembangan);
        txt_pakan_pakai_perkembangan = findViewById(R.id.txt_pakan_pakai_perkembangan);
        txt_pakan_sisa_perkembangan = findViewById(R.id.txt_pakan_sisa_perkembangan);
        txt_kematian_perkembangan = findViewById(R.id.txt_kematian_perkembangan);
        txt_nama_peternakan_perkembangan = findViewById(R.id.txt_nama_peternakan_perkembangan);
        txt_afkir_perkembangan = findViewById(R.id.txt_afkir_perkembangan);
        txt_bobot_perkembangan = findViewById(R.id.txt_bobot_perkembangan);

        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        int idPerkembangan = intent.getIntExtra("id", 0);
        String tanggalPerkembangan = intent.getStringExtra("tanggal");
        String tanggalDiupdatePerkembangan = intent.getStringExtra("updated_at");
        int mingguPerkembangan = intent.getIntExtra("minggu_ke", 0);
        double pakanPakaiPerkembangan = intent.getDoubleExtra("pakan_pakai", 0);
        double pakanSisaPerkembangan = intent.getDoubleExtra("pakan_sisa", 0);
        int kematianPerkembangan = intent.getIntExtra("kematian", 0);
        String namaPeternakanPerkembangan = intent.getStringExtra("nama_peternakan");
        int afkirPerkembangan = intent.getIntExtra("afkir", 0);
        double bobotPerkembangan = intent.getDoubleExtra("bobot", 0);
        String tanggalCreated_at = intent.getStringExtra("created_at");
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //mengatur nilai TextView dengan data yang diterima
        txt_id_perkembangan.setText(String.valueOf("ID  " + idPerkembangan));
        txt_tanggal_perkembangan.setText("Tanggal : "+ formatTanggal(tanggalPerkembangan));
        txt_tanggal_diupdate_perkembangan.setText("Tanggal Diupdate : "+ formatTanggal(tanggalDiupdatePerkembangan));
        txt_minggu_perkembangan.setText(String.valueOf("Minggu ke - " + mingguPerkembangan));
        txt_pakan_pakai_perkembangan.setText(String.valueOf(pakanPakaiPerkembangan + " Kg"));
        txt_pakan_sisa_perkembangan.setText(String.valueOf(pakanSisaPerkembangan + " Kg"));
        txt_kematian_perkembangan.setText(String.valueOf(kematianPerkembangan + " Ekor"));
        txt_nama_peternakan_perkembangan.setText(namaPeternakanPerkembangan);
        txt_afkir_perkembangan.setText(String.valueOf(afkirPerkembangan + " Ekor"));
        txt_bobot_perkembangan.setText(String.valueOf(bobotPerkembangan + " Kg"));





        btnKembali = findViewById(R.id.btnKembali);
        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPerkembanganActivity.this, MainActivity.class);
                intent.putExtra("tab", "perkembangan");
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });

        btnEditPerkembangan = findViewById(R.id.btn_edit_perkembangan);
        // Menambahkan onClickListener untuk tombol ke edit perkembangan
        btnEditPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPerkembanganActivity.this, EditPerkembanganActivity.class);
                intent.putExtra("id", idPerkembangan);
                intent.putExtra("minggu_ke", mingguPerkembangan);
                intent.putExtra("pakan_pakai", pakanPakaiPerkembangan);
                intent.putExtra("pakan_sisa", pakanSisaPerkembangan);
                intent.putExtra("bobot", bobotPerkembangan);
                intent.putExtra("afkir", afkirPerkembangan);
                intent.putExtra("kematian", kematianPerkembangan);
                intent.putExtra("created_at", tanggalCreated_at);
                intent.putExtra("updated_at", tanggalDiupdatePerkembangan);
                startActivity(intent);
            }
        });

        btnHapusPerkembangan = findViewById(R.id.btn_hapus_perkembangan);
        // Menambahkan onClickListener untuk tombol ke hapus perkembangan
        btnHapusPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();

            }
        });




    }
    private void showAlertDialog() {
        ConstraintLayout constraintLayout = findViewById(R.id.alertDialogHapus);
        View view = getLayoutInflater().inflate(R.layout.alert_konfirmasi_hapus, constraintLayout, false);
        Button buttonBatal = view.findViewById(R.id.btnBatal);
        Button buttonHapus = view.findViewById(R.id.btnHapus);

        // Inisialisasi AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        buttonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idPerkembangan = getIntent().getIntExtra("id", 0);

                // Buat request untuk menghapus data
                StringRequest stringRequest = new StringRequest(Request.Method.POST, apiconfig.HAPUS_PERKEMBANGAN_ENDPOINT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle respons dari server (jika diperlukan)
                        // Misalnya, tampilkan pesan bahwa data telah dihapus
                        // atau refresh tampilan setelah penghapusan data

                        Intent intent = new Intent(DetailPerkembanganActivity.this, MainActivity.class);
                        intent.putExtra("tab", "perkembangan");
                        startActivity(intent);
                        Toast.makeText(DetailPerkembanganActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error (jika diperlukan)
                        // Misalnya, tampilkan pesan error kepada pengguna
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Parameter yang akan dikirim ke server
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(idPerkembangan));
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        return headers;
                    }
                };

                // Tambahkan request ke antrian
                Volley.newRequestQueue(DetailPerkembanganActivity.this).add(stringRequest);
                alertDialog.dismiss();
            }
        });

    }

    private String formatTanggal(String tanggal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(tanggal);
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
            return output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(DetailPerkembanganActivity.this, MainActivity.class);
        intent.putExtra("tab", "perkembangan");
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }


    private void tampilkanKonfirmasiHapus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda ingin menghapus data ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User memilih Ya, panggil fungsi untuk menghapus data
                        ProgressDialog.show(DetailPerkembanganActivity.this, "", "Menghapus...", true);
                        hapusDataPerkembangan();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User memilih Tidak, tutup dialog
                        dialog.dismiss();
                    }
                });
        // Membuat dan menampilkan AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // Fungsi untuk menghapus data perkembangan
    private void hapusDataPerkembangan() {
        int idPerkembangan = getIntent().getIntExtra("id", 0);

        // Buat request untuk menghapus data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiconfig.HAPUS_PERKEMBANGAN_ENDPOINT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Handle respons dari server (jika diperlukan)
                // Misalnya, tampilkan pesan bahwa data telah dihapus
                // atau refresh tampilan setelah penghapusan data

                Intent intent = new Intent(DetailPerkembanganActivity.this, MainActivity.class);
                intent.putExtra("tab", "perkembangan");
                startActivity(intent);
                Toast.makeText(DetailPerkembanganActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error (jika diperlukan)
                // Misalnya, tampilkan pesan error kepada pengguna
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameter yang akan dikirim ke server
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idPerkembangan));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }
        };

        // Tambahkan request ke antrian
        Volley.newRequestQueue(this).add(stringRequest);
    }


}