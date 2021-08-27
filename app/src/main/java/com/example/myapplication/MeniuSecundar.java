package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.FormularAdaugare.ComandaActivity;
import com.example.myapplication.FormularAdaugare.ComenziFragment;
import com.example.myapplication.FragmentsAltex.AngajatiFragment;
import com.example.myapplication.FragmentsAltex.ProduseFragment;
import com.example.myapplication.FragmentsEmag.AngajatiFragmentEmag;
import com.example.myapplication.FragmentsEmag.ProduseFragmentEmag;
import com.example.myapplication.utils.Produs;
import com.google.android.material.navigation.NavigationView;

public class MeniuSecundar extends AppCompatActivity {

    public static final String MAGAZIN_KEY = "magazin_key";
    public static final String MAGAZIN_KEY2 = "magazin_key2";
    public static final String MAGAZIN_KEY3 = "magazin_key3";
    public static final String MAGAZIN_KEY4 = "magazin_key4";
    public static final String MAGAZIN_KEY5 = "magazin_key5";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fragment;

    String magazin;
    String magazin2;
    String produsUpdate;
    Produs produsItem;
    int ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meniu_secundar);


        magazin = (String) getIntent().getSerializableExtra(MAGAZIN_KEY);
        magazin2 = (String) getIntent().getSerializableExtra(MAGAZIN_KEY2);
        produsUpdate = (String) getIntent().getSerializableExtra(MAGAZIN_KEY3);
        produsItem = getIntent().getParcelableExtra(MAGAZIN_KEY4);
        ok = getIntent().getIntExtra(MAGAZIN_KEY5,0);
        LegareComponente();

        configNavigation();

        openDefaultFragment(savedInstanceState);
    }


    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void LegareComponente(){
       navigationView = findViewById(R.id.nav_view);

       SelectareOptiuniMeniu();
   }

    private void SelectareOptiuniMeniu() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.nav_produse):
                        if(magazin == null && magazin2 == null) {
                            fragment = new ProduseFragment(produsUpdate,produsItem,ok);
                        }
                        else {
                            fragment = new ProduseFragmentEmag(produsUpdate,produsItem,ok);
                        }
                        break;

                    case (R.id.nav_angajati):
                        if(magazin == null && magazin2 == null)
                        fragment = new AngajatiFragment();
                        else fragment = new AngajatiFragmentEmag();
                        break;

                    case (R.id.nav_comenzi):
                        fragment = new ComenziFragment();
                        break;

                }
                AfisareFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if(magazin == null && magazin2 == null) {
                fragment = new ProduseFragment(produsUpdate,produsItem,ok);
            }else fragment = new ProduseFragmentEmag(produsUpdate,produsItem,ok);

            AfisareFragment();

            navigationView.setCheckedItem(R.id.nav_produse);
        }
    }

   public void AfisareFragment(){
         getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container,fragment).commit();
   }

}