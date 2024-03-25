package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.semesta_mitra_sejahtera.R;

public class KebijakanActivity extends AppCompatActivity {

    ImageView btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kebijakan_layout);

        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeAkun();
            }

            private void kembaliKeAkun(){
                Intent intent = new Intent(KebijakanActivity.this, MainActivity.class);
                intent.putExtra("tab", "akun"); // Menambahkan ekstra "tab" untuk membuka tab Akun
                startActivity(intent);
                finish();
            }
        });

    }
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(KebijakanActivity.this, MainActivity.class);
            intent.putExtra("tab", "akun");
            startActivity(intent);
            finish();

            super.onBackPressed(); // Kembali ke activity sebelumnya
        }
}