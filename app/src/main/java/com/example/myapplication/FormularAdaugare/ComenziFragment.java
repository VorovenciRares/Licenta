package com.example.myapplication.FormularAdaugare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.Adaptere.ComenziAdapter;
import com.example.myapplication.Adaptere.ProduseAdapter;
import com.example.myapplication.Grafic.GrafPretActivity;
import com.example.myapplication.Logare.MainActivity;
import com.example.myapplication.MeniuSecundar;
import com.example.myapplication.utils.Job;
import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.example.myapplication.firebase.Callback;
import com.example.myapplication.firebase.FirebaseServiceComezi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ComenziFragment extends Fragment{

    ListView listViewComenzi;
    FloatingActionButton floatingActionButtonAdaugare;
    FloatingActionButton floatingActionButtonUpate;
    FloatingActionButton floatingActionButtonDelete;
    RadioGroup radioGroupAltexSauEmag;
    boolean esteSelectat;
    ComandaActivity comandaActivity;

    Object Item;
    Produs produsPreluat;
    String magazin ="nada";
    String produsUpdate = null;
    private List<Produs> comenzi = new ArrayList<>();
    private FirebaseServiceComezi firebaseServiceComezi;
    int nr = 1;
    Produs produsItem;
    Produs produsItemPreluat;
    int Position;
    int ok;

    public ComenziFragment(Produs produs,String produsUpdate, Produs produsItemPreluat, int ok) {
       produsPreluat = produs;
       this.nr++;
       this.produsUpdate = produsUpdate;
       this.produsItemPreluat = produsItemPreluat;
       this.ok = ok;
    }

    public ComenziFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
             esteSelectat = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comenzi, container, false);

        initComponents(view);

        firebaseServiceComezi = FirebaseServiceComezi.getInstance();

        if(produsUpdate == null) {
            this.comenzi.add(produsPreluat);
            firebaseServiceComezi.upsert(produsPreluat);
            ok = 2;
        }
        if(produsUpdate!=null && produsUpdate.equals("caca")){
            if(produsItemPreluat!=null)
                ok++;
                firebaseServiceComezi.update(produsItemPreluat,produsPreluat);
        }

        addProduseAdapter();

        radioGroupAltexSauEmag.setOnCheckedChangeListener(SelecteazaMagazin());


        listViewComenzi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = (Object) parent.getItemAtPosition(position);
                Item = item;
                produsItem = (Produs) item;
                Position = position;
                nr = 2;
            }
        });

        floatingActionButtonAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magazin != "nada") {
                    Intent intent = new Intent(getContext().getApplicationContext(), MeniuSecundar.class);
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY2, magazin);
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY5, ok);
                    startActivity(intent);
                }
                else Toast.makeText(getContext().getApplicationContext(),"Selecteaza un magazin",Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButtonUpate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (magazin != "nada")
                if(nr == 2 && produsItem !=null){
                    Intent intent = new Intent(getContext().getApplicationContext(), MeniuSecundar.class);
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY2, magazin);
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY3, "caca");
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY4, produsItem);
                    intent.putExtra(MeniuSecundar.MAGAZIN_KEY5, ok);
                    startActivity(intent);
                    nr =1;

                }else {
                    Toast.makeText(getContext().getApplicationContext(), "Selecteaza un produs!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext().getApplicationContext(), "Selecteaza un magazin!", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(nr == 2) {
                            firebaseServiceComezi.delete(comenzi.get(Position));
                            nr =1;
                        }
                        else if(nr == 1){
                            Toast.makeText(getContext().getApplicationContext(), "Selecteaza un produs!", Toast.LENGTH_SHORT).show();
                        }
                }
        });
        firebaseServiceComezi.attachDataChangeEventListener(dataChangeCallback());
        return view;
    }

    private RadioGroup.OnCheckedChangeListener SelecteazaMagazin() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonAltex){
                    magazin = null;
                }else if(checkedId == R.id.radioButtonEmag){
                    magazin = "Emag";
                }
            }
        };
    }

    private void initComponents(View view) {
        listViewComenzi = view.findViewById(R.id.listViewComenzi);
        floatingActionButtonAdaugare = view.findViewById(R.id.floatingActionButtonAdauga);
        floatingActionButtonUpate = view.findViewById(R.id.floatingActionButtonUpdate);
        floatingActionButtonDelete = view.findViewById(R.id.floatingActionButtonDelete);
        radioGroupAltexSauEmag = view.findViewById(R.id.RadioGroupAltexSauEmag);
    }

    private Callback<List<Produs>> dataChangeCallback() {
        return new Callback<List<Produs>>() {
            @Override
            public void runResultOnUiThread(List<Produs> result) {
                if (result != null) {
                    comenzi.clear();
                    comenzi.addAll(result);
                    notifyLvAdapter();
                }
            }
        };
    }

    private void addProduseAdapter() {
        if(getContext()!=null){
            ComenziAdapter adapter = new ComenziAdapter(getContext().getApplicationContext(),R.layout.lv_row_view3, comenzi,getLayoutInflater(),comandaActivity);
            listViewComenzi.setAdapter(adapter);
        }
    }

    private void notifyLvAdapter() {
        ComenziAdapter comenziAdapter = (ComenziAdapter) listViewComenzi.getAdapter();
        comenziAdapter.notifyDataSetChanged();
    }

}