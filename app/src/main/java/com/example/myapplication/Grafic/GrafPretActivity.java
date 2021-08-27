package com.example.myapplication.Grafic;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class GrafPretActivity extends AppCompatActivity {

    public static final String PRODUSE_CHEY = "produse_key";
    private Random random;
    Produs produs;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graf_pret);


        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Bundle bundle;
        Fragment selectedFragment = null;
        produs = getIntent().getParcelableExtra(PRODUSE_CHEY);



        selectedFragment = new Graf1Fragment(produs);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_Container, selectedFragment).commit();

        }

    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.BarChart:
                            selectedFragment = new Graf1Fragment(produs);
                            break;
                        case R.id.PieChart:
                            selectedFragment = new Graf2Fragment(produs);
                            break;
                        case R.id.LineGraph:
                            selectedFragment = new Graf3Fragment(produs);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_Container, selectedFragment).commit();
                    return true;
                }
            };

}