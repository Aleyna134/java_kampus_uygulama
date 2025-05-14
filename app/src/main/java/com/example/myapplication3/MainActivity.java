package com.example.myapplication3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom navigation view'u bulalım
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Renk seçicisini hem ikon hem de metin için uygulayın



        // İlk fragment'ı yükleyelim
        loadFragment(new DuyuruFragment());
        bottomNavigationView.setSelectedItemId(R.id.nav_anasayfa);

        // Bottom Navigation için listener oluşturalım

    }

    // Fragment yükleme fonksiyonu
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.favorilerContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}