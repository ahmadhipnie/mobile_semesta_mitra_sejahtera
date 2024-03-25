package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semesta_mitra_sejahtera.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailJadwalPanenActivity extends AppCompatActivity {

    ImageView btnKembali;
    private SharedPreferences sharedPreferences;

    TextView txt_jumlah_ayam, txt_bobot_panen, txt_nama_sopir, txt_nopol, txt_nama_peternakan, txt_id_jadwal_panen, txt_tanggal_jadwal_panen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_jadwal_panen_layout);

        // Inisialisasi variabel
        txt_id_jadwal_panen = findViewById(R.id.txt_id_jadwal_panen);
        txt_tanggal_jadwal_panen = findViewById(R.id.txt_tanggal_jadwal_panen);
        txt_jumlah_ayam = findViewById(R.id.txt_jumlah_ayam);
        txt_bobot_panen = findViewById(R.id.txt_bobot_panen);
        txt_nama_sopir = findViewById(R.id.txt_nama_sopir);
        txt_nopol = findViewById(R.id.txt_nopol);
        txt_nama_peternakan = findViewById(R.id.txt_nama_peternakan);
        btnKembali = findViewById(R.id.btnKembali);


        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        int idJadwalPanen = intent.getIntExtra("id", 0);
        String tanggalPanen = intent.getStringExtra("tanggal");
        int jumlahAyam = intent.getIntExtra("jumlah_ayam", 0);
        int bobotAyam = intent.getIntExtra("bobot_ayam", 0);
        String namaSopir = intent.getStringExtra("nama_sopir");
        String nopol = intent.getStringExtra("nopol");
        String namaPeternakan = intent.getStringExtra("nama_peternakan");


        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        //mengatur nilai TextView dengan data yang diterima
        txt_id_jadwal_panen.setText(String.valueOf("ID  " + idJadwalPanen));
        txt_tanggal_jadwal_panen.setText("tanggal Panen : "+ formatTanggal(tanggalPanen));
        txt_jumlah_ayam.setText(String.valueOf(jumlahAyam + " ekor"));
        txt_bobot_panen.setText(String.valueOf(bobotAyam + " Kg"));
        txt_nama_sopir.setText(namaSopir);
        txt_nopol.setText(nopol);
        txt_nama_peternakan.setText(namaPeternakan);


        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailJadwalPanenActivity.this, MainActivity.class);
                intent.putExtra("tab", "jadwal panen");
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(DetailJadwalPanenActivity.this, MainActivity.class);
            intent.putExtra("tab", "jadwal panen");
            startActivity(intent);
            finish();

            super.onBackPressed(); // Kembali ke activity sebelumnya
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


}