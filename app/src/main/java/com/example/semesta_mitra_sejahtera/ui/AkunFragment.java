package com.example.semesta_mitra_sejahtera.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.semesta_mitra_sejahtera.R;

public class AkunFragment extends Fragment {

    Button btnLogout, btnUbahCabang, btnUbahPassword, btnKebijakan, btnFaq;

    private TextView txtNama, txtNotelp, txtNamaCabang;
    private SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.akun_layout, container, false);

        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnFaq = (Button) view.findViewById(R.id.btnFaq);
        btnUbahCabang = (Button) view.findViewById(R.id.btnUbahCabang);
        btnUbahPassword = (Button) view.findViewById(R.id.btnUbahPassword);
        btnKebijakan = (Button) view.findViewById(R.id.btnKebijakan);
        txtNama = view.findViewById(R.id.txtNama);
        txtNotelp = view.findViewById(R.id.txtNoTelp);
        txtNamaCabang = view.findViewById(R.id.txtNamaCabang);


        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE); // Inisialisasi sharedPreferences
        // Ambil data dari SharedPreferences
        String nama = sharedPreferences.getString("nama", ""); // Ganti "nama" sesuai dengan nama yang digunakan saat menyimpan data
        String noTelp = sharedPreferences.getString("no_telp", ""); // Ganti "no_telp" sesuai dengan nama yang digunakan saat menyimpan data
        String namaPeternakan = sharedPreferences.getString("nama_peternakan", "belum pilih peternakan"); // Ganti "nama_peternakan" sesuai dengan nama yang digunakan saat menyimpan data


        // Cek panjang namaPeternakan
        if (namaPeternakan.length() > 20) {
            // Jika lebih dari 12 karakter, potong dan tambahkan titik-titik
            namaPeternakan = namaPeternakan.substring(0, 20) + "...";
        }


        // Set teks pada TextView dengan data dari SharedPreferences
        txtNama.setText(" "+nama);
        txtNotelp.setText(" "+noTelp);
        txtNamaCabang.setText(" "+namaPeternakan);

        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUbahPasswordActivity();
            }
            private void openUbahPasswordActivity() {
                startActivity(new Intent(requireActivity(), UbahPasswordActivity.class));
                requireActivity().finish();
            }
        });
        btnUbahCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUbahCabangActivity();
            }
            private void openUbahCabangActivity() {
                startActivity(new Intent(requireActivity(), CabangActivity.class));
                requireActivity().finish();
            }
        });

        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFaqActivity();
            }
            private void openFaqActivity() {
                startActivity(new Intent(requireActivity(), FaqActivity.class));
                requireActivity().finish();
            }
        });
        btnKebijakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKebijakanActivity();
            }
            private void openKebijakanActivity() {
                startActivity(new Intent(requireActivity(), KebijakanActivity.class));
                requireActivity().finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
            private void logoutUser() {
                // Menghapus nilai Shared Preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Navigasi ke halaman login atau halaman awal aplikasi
                // Gantikan "LoginActivity" dengan nama kelas LoginActivity jika menggunakan aktivitas, atau ganti dengan Fragment lain jika menggunakan Fragment
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();
            }
        });
        return view;
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
