package com.example.myapplication.FormularAdaugare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.utils.Job;
import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ComandaActivity extends AppCompatActivity {
    public static final String COMANDA_CHEY = "comanda_key";
    public static final String COMANDA_CHEY2 = "comanda_key2";
    public static final String COMANDA_CHEY3 = "comanda_key3";
    public static final String COMANDA_CHEY4 = "comanda_key4";

    Produs produs;
    String produsUpdate;
    Produs produsItem;
    int ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        produs = getIntent().getParcelableExtra(COMANDA_CHEY);
        produsUpdate = getIntent().getStringExtra(COMANDA_CHEY2);
        produsItem = getIntent().getParcelableExtra(COMANDA_CHEY3);
        ok = getIntent().getIntExtra(COMANDA_CHEY4, 0);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, new ComenziFragment(produs,produsUpdate,produsItem,ok)).commit();

    }


}