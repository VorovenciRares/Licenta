package com.example.myapplication.FragmentsAltex;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.myapplication.utils.Job;
import com.example.myapplication.Adaptere.JobAdapter;
import com.example.myapplication.R;
import com.example.myapplication.firebase.FirebaseServiceAngajati;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class AngajatiFragment extends Fragment {
    public static final String PRODUSE_KEY = "produse_key";

    private ListView listViewAngajati;
    private RadioGroup radioGroupDepartamente;
    private SearchView searchViewAngajati;

    private List<Job> angajati = new ArrayList<>();
    private FirebaseServiceAngajati firebaseServiceAngajati;
    List<Job> copie =new ArrayList<>();
    List<Job> copie2 =new ArrayList<>();
    int ok;

    DatabaseReference produseRef;

    public AngajatiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_angajati, container, false);

        LegareComponente(view);

        String[] numeRadioButton ={"Logistica","Magazine","CustomerService", "Sediu"};

        for (int i = 0; i < numeRadioButton.length ; i++) {
            RadioButton rbn = new RadioButton(getContext());

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8,8,8,8);
            rbn.setLayoutParams(params);

            rbn.setBackgroundResource(R.drawable.radio_selector3);

            rbn.setTextColor(getResources().getColorStateList(R.color.selector_colors3));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rbn.setElevation(4f);
            }
            rbn.setButtonDrawable(R.color.transparent);
            rbn.setPadding(20,20,20,20);

            rbn.setId(i+1);
            rbn.setText(numeRadioButton[i]);
            radioGroupDepartamente.addView(rbn);
        }

        radioGroupDepartamente.setOnCheckedChangeListener(filterEvent());
        searchViewAngajati.setOnQueryTextListener(search());

        firebaseServiceAngajati = FirebaseServiceAngajati.getInstance();
        produseRef = FirebaseDatabase.getInstance().getReference("PosturiDeAngajati");


        Job job = new Job(null,"PICKER", "Logistica","Sibiu, Piatra Neamt");
        Job job1 = new Job(null,"MANIPULANT MARFURI", "Logistica","Timisoara, Sibiu, Constanta");
        Job job2 = new Job(null,"MONTATOR IT", "Logistica","Dragomiresti");
        Job job3 = new Job(null,"ANALIST RAPORTARE", "Logistica","Dragomiresti");
        Job job4 = new Job(null,"INSTALATOR", "Logistica","Dragomiresti");

        Job job5 = new Job(null,"CASIER", "Magazine","Bucuresti, Iasi, Cluj Napoca, Timisoara, Vaslui, Bistrita, Botosani, Sibiu");
        Job job6 = new Job(null,"RESPONSABIL SERVICE", "Magazine","Bucuresti, Bacau, Sibiu, Iasi, Sfantu Gheorghe, Craiova");
        Job job7 = new Job(null,"DIRECTOR ADJUNCT", "Magazine","Hunedoara, Ramnicu Valcea, Giurgiu, Campina, Alexandria, Ploiesti");
        Job job8 = new Job(null,"DIRECTOR MAGAZIN", "Magazine","Brasov, Dej, Pascani, Campina, Ploiesti, Satu Mare");
        Job job9 = new Job(null,"MANAGER FINANCIAR", "Magazine","Iasi, Targoviste");

        Job job10 = new Job(null,"CUSTOMER SERVICE CONSULTANT", "Customer Service","Bucuresti");

        Job job11 = new Job(null,"JUNIOR WEB DESIGNER", "Sediu","Bucuresti");
        Job job12 = new Job(null,"AGENT DE TURISM", "Sediu","Bucuresti, Cluj, Timisoara, Iasi, Craiova, Sibiu, Brasov, Constanta");
        Job job13 = new Job(null,"ANALIST CREDITE", "Sediu","Bucuresti");

        angajati.add(job);

        angajati.add(job1);
        angajati.add(job2);
        angajati.add(job3);
        angajati.add(job4);
        angajati.add(job5);
        angajati.add(job6);
        angajati.add(job7);
        angajati.add(job8);
        angajati.add(job9);

        angajati.add(job10);

        angajati.add(job11);
        angajati.add(job12);
        angajati.add(job13);

        firebaseServiceAngajati.upsert(job);

        firebaseServiceAngajati.upsert(job1);
        firebaseServiceAngajati.upsert(job2);
        firebaseServiceAngajati.upsert(job3);
        firebaseServiceAngajati.upsert(job4);
        firebaseServiceAngajati.upsert(job5);
        firebaseServiceAngajati.upsert(job6);
        firebaseServiceAngajati.upsert(job7);
        firebaseServiceAngajati.upsert(job8);
        firebaseServiceAngajati.upsert(job9);
        firebaseServiceAngajati.upsert(job10);

        firebaseServiceAngajati.upsert(job12);
        firebaseServiceAngajati.upsert(job13);

        addProduseAdapter();

        return view;
    }

    private SearchView.OnQueryTextListener search() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtreazaDepartament(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtreazaDepartament(newText);
                return false;
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener filterEvent() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == 1){
                    String text = "Logistica";
                    filtreaza(text);
                    ok = 1;
                }else if(checkedId == 2){
                    String text = "Magazine";
                    filtreaza(text);
                    ok = 1;
                }
                else if(checkedId == 3) {
                    String text = "Customer Service";
                    filtreaza(text);
                    ok = 1;
                } else if(checkedId == 4) {
                    String text = "Sediu";
                    filtreaza(text);
                    ok = 1;
                }
                notifyInternalAdapter();
            }
        };
    }

    private void filtreaza(String text) {
        copie.removeAll(copie);
        for (Job job : angajati) {
            if (job.getDepartament().toLowerCase().contains(text.toLowerCase())) {
                copie.add(job);
            }
        }
        JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(), R.layout.lv_row_view2, copie, getLayoutInflater());
        listViewAngajati.setAdapter(adapter);
    }

    private void filtreazaDepartament(String text) {
        copie2.removeAll(copie2);

        if(ok == 1)
            for(Job job : copie) {
                if (job.getNumePozitie().toLowerCase().contains(text.toLowerCase())) {
                    copie2.add(job);
                }
            }
        else {
            for (Job job : angajati) {
                if (job.getNumePozitie().toLowerCase().contains(text.toLowerCase())) {
                    copie2.add(job);
                }
            }
        }
        JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(),R.layout.lv_row_view2,copie2,getLayoutInflater());
        listViewAngajati.setAdapter(adapter);
    }


    public void notifyInternalAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) listViewAngajati.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void LegareComponente(View view) {
        listViewAngajati = view.findViewById(R.id.lista_angajati);
        radioGroupDepartamente = view.findViewById(R.id.RadioGroupCategoriiDepartamente);
        searchViewAngajati = view.findViewById(R.id.searchAngajati);
    }

    private void addProduseAdapter() {
        if(getContext()!=null){
            JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(),R.layout.lv_row_view2,angajati,getLayoutInflater());
            listViewAngajati.setAdapter(adapter);
        }
    }

}