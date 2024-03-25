package com.example.semesta_mitra_sejahtera.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.semesta_mitra_sejahtera.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    DashboardFragment dashboard = new DashboardFragment();
    PerkembanganFragment perkembangan = new PerkembanganFragment();
    JadwalPanenFragment jadwal_panen = new JadwalPanenFragment();
    AkunFragment akun = new AkunFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        String tabToOpen = getIntent().getStringExtra("tab");
        if (tabToOpen != null && tabToOpen.equals("akun")) {
            // Open the Akun tab
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, akun).commit();
            bottomNavigationView.setSelectedItemId(R.id.menu_akun);
        } else if (tabToOpen != null && tabToOpen.equals("jadwal panen")) {
            // Open the Jadwal Panen tab
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, jadwal_panen).commit();
            bottomNavigationView.setSelectedItemId(R.id.jadwal_panen);
        } else if (tabToOpen != null && tabToOpen.equals("perkembangan")) {
            // Open the PerkembanganModel tab
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, perkembangan).commit();
            bottomNavigationView.setSelectedItemId(R.id.menu_perkembangan);
        } else if (tabToOpen != null && tabToOpen.equals("dashboard")) {
            // Open the Dashboard tab
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboard).commit();
            bottomNavigationView.setSelectedItemId(R.id.menu_dashboard);
        } else{
            // Set default fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboard).commit();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId(); // Mendapatkan ID item yang dipilih

        if (itemId == R.id.menu_dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboard).commit();
            return true;
        } else if (itemId == R.id.menu_perkembangan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, perkembangan).commit();
            return true;
        } else if (itemId == R.id.jadwal_panen) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, jadwal_panen).commit();
            return true;
        } else if (itemId == R.id.menu_akun) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, akun).commit();
            return true;
        }



        return false;
    }

    private void exitApp() {
        this.finishAffinity(); // Keluar dari aplikasi
    }

    
}