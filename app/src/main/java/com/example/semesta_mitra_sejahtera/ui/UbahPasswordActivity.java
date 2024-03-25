package com.example.semesta_mitra_sejahtera.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class UbahPasswordActivity extends AppCompatActivity {

    ImageView btnKembali;
    EditText txtPasswordLama, txtPasswordBaru, txtKonfirmasiPasswordBaru;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubah_password_layout);

        txtPasswordLama = findViewById(R.id.etPasswordLama);
        txtPasswordBaru = findViewById(R.id.etPasswordBaru);
        txtKonfirmasiPasswordBaru = findViewById(R.id.etKonfirmasiPasswordBaru);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembaliKeAkun();
            }

            private void kembaliKeAkun(){
                Intent intent = new Intent(UbahPasswordActivity.this, MainActivity.class);
                intent.putExtra("tab", "akun"); // Menambahkan ekstra "tab" untuk membuka tab Akun
                startActivity(intent);
                finish();
            }
        });
    }

    public void ubahPassword(View view) {
        int userId = sharedPreferences.getInt("id", 0);
        String passwordId = String.valueOf(sharedPreferences.getString("password", null));
        String oldPassword = txtPasswordLama.getText().toString().trim();
        String newPassword = txtPasswordBaru.getText().toString().trim();
        String confirmPassword = txtKonfirmasiPasswordBaru.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Semua kolom password harus diisi", Toast.LENGTH_SHORT).show();

        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "konfirmasi password tidak cocok", Toast.LENGTH_LONG).show();

        } else if (!oldPassword.equals(passwordId)) {
            Toast.makeText(this, "Password lama tidak cocok", Toast.LENGTH_SHORT).show();

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Apakah Anda yakin ingin mengubah password?");
            alertDialogBuilder.setPositiveButton("Ya", (dialogInterface, i) -> {
                // Proses untuk mengubah password melalui API Laravel

                // Tampilkan ProgressDialog
                ProgressDialog progressDialog = new ProgressDialog(UbahPasswordActivity.this);
                progressDialog.setMessage("Mengubah password...");
                progressDialog.show();

                String url = apiconfig.UBAH_PASSWORD_ENDPOINT;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Tutup ProgressDialog
                                progressDialog.dismiss();

                                // Tanggapan dari API setelah berhasil mengubah password
                                Toast.makeText(UbahPasswordActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();

                                // Hapus SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();

                                // Navigasi kembali ke LoginActivity
                                Intent intent = new Intent(UbahPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity(); // Menutup semua activity sebelumnya
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Tutup ProgressDialog
                                progressDialog.dismiss();

                                // Tanggapan dari API jika terjadi kesalahan
                                Toast.makeText(UbahPasswordActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Parameter yang akan dikirim ke API (ID user, password lama, password baru)
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(userId));
                        params.put("old_password", oldPassword);
                        params.put("new_password", newPassword);
                        return params;
                    }
                };

                // Menambahkan request ke queue
                RequestQueue requestQueue = Volley.newRequestQueue(UbahPasswordActivity.this);
                requestQueue.add(stringRequest);
            });

            alertDialogBuilder.setNegativeButton("Tidak", (dialogInterface, i) -> {
                // Batal mengubah password
                dialogInterface.dismiss();
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(UbahPasswordActivity.this, MainActivity.class);
        intent.putExtra("tab", "akun");
        startActivity(intent);
        finish();

        super.onBackPressed(); // Kembali ke activity sebelumnya
    }
}
