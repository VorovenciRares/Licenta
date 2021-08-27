package com.example.myapplication.FormularAdaugare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;

public class ComandaProdusActivity extends AppCompatActivity {

    public static final String PRODUSE_CHEY_COMANDA = "produse_key_comanda";
    public static final String PRODUSE_CHEY_COMANDA2 = "produse_key_comanda2";
    public static final String PRODUSE_CHEY_COMANDA3 = "produse_key_comanda3";
    public static final String PRODUSE_CHEY_COMANDA4 = "produse_key_comanda4";
    Produs produsSelectat;
    Produs produsItem;
    String produsUpdate;

    TextView textViewNume;
    TextView textViewPret;
    TextView textViewMarca;
    TextView textViewRezolutie;
    ImageView imageViewGeneric;
    Button buttonComanda;
    int ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_produs);

        initComponents();

        produsSelectat = getIntent().getParcelableExtra(PRODUSE_CHEY_COMANDA);
        produsUpdate = getIntent().getStringExtra(PRODUSE_CHEY_COMANDA2);
        produsItem = getIntent().getParcelableExtra(PRODUSE_CHEY_COMANDA3);
        ok = getIntent().getIntExtra(PRODUSE_CHEY_COMANDA4, 0);

        int nr=0;
        if(produsSelectat !=null) {
            for (float pret : produsSelectat.getPret()) {
                nr++;
            }

            textViewNume.setText(produsSelectat.getNume());
            textViewPret.setText(String.valueOf(produsSelectat.getPret().get(nr - 1)));
            textViewMarca.setText(produsSelectat.getMarca());
            textViewRezolutie.setText(produsSelectat.getRezolutie());
            switch (produsSelectat.getImagine()) {
                case ("Imagine_Telefon"):
                    imageViewGeneric.setImageResource(R.mipmap.generic_phone1);
                    break;
                case ("Imagine_Televizor"):
                    imageViewGeneric.setImageResource(R.mipmap.televizor_generic);
                    break;
                case ("Imagine_Calculator"):
                    imageViewGeneric.setImageResource(R.mipmap.generic_computer);
                    break;
            }

            buttonComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ComandaActivity.class);
                    intent.putExtra(ComandaActivity.COMANDA_CHEY, produsSelectat);
                    intent.putExtra(ComandaActivity.COMANDA_CHEY2, produsUpdate);
                    intent.putExtra(ComandaActivity.COMANDA_CHEY3, produsItem);
                    intent.putExtra(ComandaActivity.COMANDA_CHEY4, ok);
                    startActivity(intent);
                }
            });
        }
    }

    private void initComponents() {
        textViewNume = findViewById(R.id.textViewNume1);
        textViewPret = findViewById(R.id.textViewPret1);
        textViewMarca = findViewById(R.id.textViewMarca1);
        textViewRezolutie = findViewById(R.id.textViewRezolutie1);
        imageViewGeneric = findViewById(R.id.imageViewGeneric);
        buttonComanda = findViewById(R.id.buttonAdaugaComanda);
    }

}