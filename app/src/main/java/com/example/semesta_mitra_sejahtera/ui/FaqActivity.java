package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.semesta_mitra_sejahtera.R;

public class FaqActivity extends AppCompatActivity {

    ImageView btnKembali;
    EditText etPertanyaanFaq;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_layout);

        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeAkun();
            }

            private void kembaliKeAkun() {
                Intent intent = new Intent(FaqActivity.this, MainActivity.class);
                intent.putExtra("tab", "akun"); // Menambahkan ekstra "tab" untuk membuka tab Akun
                startActivity(intent);
                finish();
            }
        });

        etPertanyaanFaq = findViewById(R.id.etPertanyaanFaq);
        Button btnKirimWhatsApp = findViewById(R.id.btnKirimWhatsApp);
        btnKirimWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimPesanWhatsApp();
            }
        });

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
    }

    private void kirimPesanWhatsApp() {
        String pertanyaan = etPertanyaanFaq.getText().toString().trim();

        if (TextUtils.isEmpty(pertanyaan)) {
            // Validasi jika pertanyaan kosong
            etPertanyaanFaq.setError("Pertanyaan tidak boleh kosong");
            return;
        }

        // Nomor WhatsApp yang ingin dihubungi
        String nomorWhatsApp = "+6285335480929";

        // Data diri dari SharedPreferences
        String namaPengirim = sharedPreferences.getString("nama", "");
        String kodeMitra = sharedPreferences.getString("kode", "");

        // Membuat pesan dengan data diri
        String pesan = "Pertanyaan dari: " + namaPengirim +
                        "\nKode Mitra : " + kodeMitra +
                        "\nPertanyaan : " +
                        "\n\n" + pertanyaan;

        // Membuat format URI untuk intent WhatsApp
        String url = "https://api.whatsapp.com/send?phone=" + nomorWhatsApp + "&text=" + Uri.encode(pesan);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(FaqActivity.this, MainActivity.class);
        intent.putExtra("tab", "akun");
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }
}
