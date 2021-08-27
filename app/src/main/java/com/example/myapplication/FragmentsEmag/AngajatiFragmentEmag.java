package com.example.myapplication.FragmentsEmag;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.utils.Job;
import com.example.myapplication.Adaptere.JobAdapter;
import com.example.myapplication.R;
import com.example.myapplication.firebase.FirebaseServiceAngajati;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class AngajatiFragmentEmag extends Fragment {
    public static final String PRODUSE_KEY = "produse_key";

    private ListView listViewAngajati;
    private RadioGroup radioGroupDepartamente;
    private SearchView searchViewAngajati;
    private ImageView imageViewEmag;

    private List<Job> angajati = new ArrayList<>();
    private FirebaseServiceAngajati firebaseServiceAngajati;
    List<Job> copie =new ArrayList<>();

    DatabaseReference produseRef;

    public AngajatiFragmentEmag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_angajati, container, false);

        LegareComponente(view);

        String[] numeRadioButton ={"Comercial","Finance","Logistics", "Marketing","Marketplace"};

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
        imageViewEmag.setImageResource(R.mipmap.emag);


        firebaseServiceAngajati = FirebaseServiceAngajati.getInstance();
        produseRef = FirebaseDatabase.getInstance().getReference("PosturiDeAngajati");

        Job job = new Job(null,"Manager IT", "Comercial","Bucuresti - Sediul Central");
        Job job1 = new Job(null,"Commercial Analyst", "Comercial","Bucuresti - Sediul Central");
        Job job2 = new Job(null,"Product Manager Accessories&Gadgets", "Comercial","Bucuresti - Sediul Central");

        Job job3 = new Job(null,"Senior Accountant", "Finance","Bucuresti - Sediul Central");
        Job job4 = new Job(null,"Tax Specialist", "Finance","Bucuresti - Sediul Central");

        Job job5 = new Job(null,"Key Account Manager (KAM) BG", "Logistics","Chiajna");
        Job job6 = new Job(null,"Tehnician Mentenanta", "Logistics","Chiajna");
        Job job7 = new Job(null,"Lucrator DepozitT", "Logistics","Bucuresti - Sediul Central");

        Job job8 = new Job(null,"Junior HTML Developer", "Marketing","Bucuresti - Sediul Central");
        Job job9 = new Job(null,"Bucuresti - Sediul Central", "Marketing","Bucuresti - Sediul Central");

        Job job10 = new Job(null,"QA Engineer", "Marketplace","IT Hub Craiova");
        Job job11 = new Job(null,"Senior Reporting Specialist", "Marketplace","Bucuresti - Sediul Central");

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

        addProduseAdapter();

        return view;
    }

    private SearchView.OnQueryTextListener search() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtreaza(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtreaza(newText);
                return false;
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener filterEvent() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == 1){
                    String text = "Comercial";
                    filtreazaDepartament(text);
                }else if(checkedId == 2){
                    String text = "Finance";
                    filtreazaDepartament(text);
                }
                else if(checkedId == 3) {
                    String text = "Logistics";
                    filtreazaDepartament(text);
                } else if(checkedId == 4) {
                    String text = "Marketing";
                    filtreazaDepartament(text);
                }else if(checkedId == 5) {
                    String text = "Marketplace";
                    filtreazaDepartament(text);
                }
                notifyInternalAdapter();
            }
        };
    }

    private void filtreaza(String text) {
        copie.removeAll(copie);
        for (Job job : angajati) {
            if (job.getNumePozitie().toLowerCase().contains(text.toLowerCase())) {
                copie.add(job);
            }
        }
        JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(), R.layout.lv_row_view2, copie, getLayoutInflater());
        listViewAngajati.setAdapter(adapter);
    }

    private void filtreazaDepartament(String text) {
        copie.removeAll(copie);
        for (Job job : angajati) {
            if (job.getDepartament().toLowerCase().contains(text.toLowerCase())) {
                copie.add(job);
            }
        }
        JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(), R.layout.lv_row_view2, copie, getLayoutInflater());
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
        imageViewEmag = view.findViewById(R.id.PozaMagazin);
    }

    private void addProduseAdapter() {
        if(getContext()!=null){
            JobAdapter adapter = new JobAdapter(getContext().getApplicationContext(),R.layout.lv_row_view2,angajati,getLayoutInflater());
            listViewAngajati.setAdapter(adapter);
        }
    }

}