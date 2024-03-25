package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semesta_mitra_sejahtera.R;

public class DetailArtikelActivity extends AppCompatActivity {

    ImageView btnKembali;
    TextView txt_judul_artikel, txt_deskripsi_artikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_artikel_layout);

        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        int idArtikel = intent.getIntExtra("id", 0);
        String judulArtikel = intent.getStringExtra("judul");
        String kategoriArtikel = intent.getStringExtra("kategori");
        String deskripsiArtikel = intent.getStringExtra("deskripsi");

        // Inisialisasi TextView
        txt_judul_artikel = findViewById(R.id.txt_judul_artikel);
        txt_deskripsi_artikel = findViewById(R.id.txt_deskripsi_artikel);

        //mengatur nilai TextView dengan data yang diterima
        txt_judul_artikel.setText(judulArtikel);
        txt_deskripsi_artikel.setText(deskripsiArtikel);

        btnKembali = findViewById(R.id.btnKembali);
        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailArtikelActivity.this, MainActivity.class);
                intent.putExtra("tab", "dashboard");
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailArtikelActivity.this, MainActivity.class);
        intent.putExtra("tab", "dashboard");
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }

}