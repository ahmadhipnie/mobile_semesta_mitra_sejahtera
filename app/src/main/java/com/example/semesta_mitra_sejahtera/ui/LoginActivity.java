package com.example.semesta_mitra_sejahtera.ui;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.api.apiconfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText et_kodeSignIn, et_passwordSignIn;

    Button btnLogin;

    public boolean PasswordVisible;

    private String url = apiconfig.LOGIN_ENDPOINT; // Ganti dengan URL API login Anda
    public String str_kode;
    public String str_password;
    private boolean passwordVisible;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        et_kodeSignIn = findViewById(R.id.et_kodeSignIn);
        et_passwordSignIn = findViewById(R.id.et_passwordSignIn);
        btnLogin = findViewById(R.id.btnLogin);
        et_passwordSignIn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int inType = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= et_passwordSignIn.getRight() - et_passwordSignIn.getCompoundDrawables()[inType].getBounds().width()) {
                        int selection = et_passwordSignIn.getSelectionEnd();
                        if (passwordVisible) {
                            et_passwordSignIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            et_passwordSignIn.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            et_passwordSignIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_on, 0);
                            et_passwordSignIn.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }
                        et_passwordSignIn.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }

        });

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        checkLoginStatus();
    }


    public void Login(View view) {

        if(et_kodeSignIn.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukkan kode mitra", Toast.LENGTH_SHORT).show();
        }
        else if(et_passwordSignIn.getText().toString().isEmpty()){
            Toast.makeText(this, "Masukkan Password", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Sedang Memuat data..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            str_kode = et_kodeSignIn.getText().toString().trim();
            str_password = et_passwordSignIn.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            JSONObject userObject = jsonObject.getJSONObject("user");
                            int id = userObject.getInt("id");
                            String kode = userObject.getString("kode");
                            String nama = userObject.getString("nama");
                            String password = userObject.getString("password");
                            String no_telp = userObject.getString("no_telp");

                            sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            // Simpan data ke SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("id", id);
                            editor.putString("kode", kode);
                            editor.putString("nama", nama);
                            editor.putString("no_telp", no_telp);
                            editor.putString("password", password);
                            editor.putInt("id_peternakan", 0);
                            editor.putString("nama_peternakan", null);
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            et_kodeSignIn.setText("");
                            et_passwordSignIn.setText("");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: ", error );
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("kode", str_kode);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Pengguna sudah login sebelumnya, arahkan ke halaman beranda
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Menutup aplikasi saat tombol kembali ditekan
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        super.onBackPressed();
    }
}