package com.example.myapplication.FragmentsAltex;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.Adaptere.ProduseAdapter;
import com.example.myapplication.R;
import com.example.myapplication.firebase.Callback;
import com.example.myapplication.firebase.FirebaseServiceProduse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProduseFragment extends Fragment {
    public static final String PRODUSE_KEY = "produse_key";

    private ListView listViewProduse;
    private RadioGroup radioGroupCategorii;
    private SearchView searchViewProduse;

    
    private List<Produs> produse = new ArrayList<>();
    private FirebaseServiceProduse firebaseServiceProduse;
    private int SelectedProdusIndex = -1;
    List<Produs> copie =new ArrayList<>();
    List<Produs> copie2 =new ArrayList<>();
    int ok;

    String produsUpdate;

    DatabaseReference produseRef;
    Produs produsItem;
    int okk;

    public ProduseFragment(String produsUpdate, Produs produsItem, int ok) {
        this.produsUpdate = produsUpdate;
        this.produsItem = produsItem;
        this.okk = ok;
    }

    public ProduseFragment() {

    }

    public static ProduseFragment newInstance() {

        Bundle args = new Bundle();
        ProduseFragment fragment = new ProduseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produse,container,false);
        LegareComponente(view);



        String[] numeRadioButton ={"Laptop","Telefon","Calculator", "Toate"};


        int buttons = 4;
        for (int i = 0; i < buttons ; i++) {
            RadioButton rbn = new RadioButton(getContext());

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8,8,8,8);
            rbn.setLayoutParams(params);

            rbn.setBackgroundResource(R.drawable.radio_selector5);

            rbn.setTextColor(getResources().getColorStateList(R.color.select_colors4));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rbn.setElevation(4f);
            }
            rbn.setButtonDrawable(R.color.transparent);
            rbn.setPadding(20,20,20,20);
            rbn.setId(i+1);

            rbn.setText(numeRadioButton[i]);
            radioGroupCategorii.addView(rbn);
        }

        radioGroupCategorii.setOnCheckedChangeListener(filterEvent());
        searchViewProduse.setOnQueryTextListener(search());
        firebaseServiceProduse = FirebaseServiceProduse.getInstance();

        produseRef = FirebaseDatabase.getInstance().getReference("muie");

        String id = SelectedProdusIndex != -1 ? produse.get(SelectedProdusIndex).getId() : null;
        ArrayList<Integer> an = new ArrayList<>(Arrays.asList(2017, 2018, 2019,2020,2021));
        ArrayList<Integer> an2 = new ArrayList<>(Arrays.asList(2019,2020,2021));

        ArrayList<Float> pretLenovo1 = new ArrayList<>(Arrays.asList(7031f, 6142f, 5790f, 6200f, 5910f));
        ArrayList<Float> pretLenovo2 = new ArrayList<>(Arrays.asList(3760f, 3350f, 3505f, 3156f,2939f));
        ArrayList<Float> pretLenovo3 = new ArrayList<>(Arrays.asList(6579f, 6300f, 6240f, 6102f,5908f));

        ArrayList<Float> pretTelefon1 = new ArrayList<>(Arrays.asList(230f, 250f, 180f, 155f,119f));
        ArrayList<Float> pretTelefon2 = new ArrayList<>(Arrays.asList(1120f, 1055f, 960f, 1040f,935f));
        ArrayList<Float> pretTelefon3 = new ArrayList<>(Arrays.asList(1060f, 1120f, 1105f, 1170f,1078f));

        ArrayList<Float> pretCalculator1 = new ArrayList<>(Arrays.asList(1320f, 1300f, 1240f, 1150f,1098f));
        ArrayList<Float> pretCalculator2 = new ArrayList<>(Arrays.asList(1900f, 1920f, 1827f));
        ArrayList<Float> pretCalculator3 = new ArrayList<>(Arrays.asList(18500f, 17800f,17000f));

        Produs produs = new Produs(id, "Imagine_Laptop","ThinkPad X1 Yoga","Lenovo",5910,pretLenovo1,an,"2560 x 1440",null);
        Produs produs2 = new Produs(id,"Imagine_Telefon","Samsung Galaxy J5","Samsung",935,pretTelefon2,an,"720 x 1280",null);
        Produs produs3 = new Produs(id,"Imagine_Calculator","PC MYRIA Vision V41WIN","Samsung",17000,pretCalculator3,an2,null,"NVIDIA GeForce RTX 3090");

        Produs produs4 = new Produs(null,"Imagine_Telefon","Telefon NOKIA 130","Nokia",119,pretTelefon1,an,"1920 x 1080",null);
        Produs produs5 = new Produs(null,"Imagine_Laptop","Lenovo Ideapad 310","Lenovo",2939, pretLenovo2,an,"1920 x 1080",null);
        Produs produs6 = new Produs(null,"Imagine_Calculator","ACER Aspire TC","ACER",1827,pretCalculator2,an2,null,"Intel UHD Graphics");

        Produs produs7 = new Produs(null,"Imagine_Calculator","PC MYRIA Manager V47","MYRIA",1098,pretCalculator1,an,null,"Intel UHD Graphics 610");
        Produs produs8 = new Produs(null,"Imagine_Telefon","Samsung Galaxy Core2","Samsung",1078,pretTelefon3,an,"1920 x 1080",null);
        Produs produs9 = new Produs(null,"Imagine_Laptop","Lenovo Yoga 710","Lenovo",5908,pretLenovo3,an,"1920 x 1080",null);

        this.produse.add(produs);
        this.produse.add(produs2);
        this.produse.add(produs3);
        this.produse.add(produs4);
        this.produse.add(produs5);
        this.produse.add(produs6);
        this.produse.add(produs7);
        this.produse.add(produs8);
        this.produse.add(produs9);

          firebaseServiceProduse.upsert(produs);
          firebaseServiceProduse.upsert(produs2);
          firebaseServiceProduse.upsert(produs3);
          firebaseServiceProduse.upsert(produs4);
          firebaseServiceProduse.upsert(produs5);
          firebaseServiceProduse.upsert(produs6);
          firebaseServiceProduse.upsert(produs7);
          firebaseServiceProduse.upsert(produs8);
          firebaseServiceProduse.upsert(produs9);

          addProduseAdapter();
        return view;
    }

    private SearchView.OnQueryTextListener search() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtreazaNume(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtreazaNume(newText);
                return false;
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener filterEvent() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==1){
                    String text = "Imagine_Laptop";
                    filtreaza(text);
                    ok=1;
                }else if(checkedId == 2){
                    String text = "Imagine_Telefon";
                    filtreaza(text);
                    ok =1;
                }
                else if(checkedId ==3) {
                    String text = "Imagine_Calculator";
                    filtreaza(text);
                    ok=1;
                }
                else {
                    addProduseAdapter();
                    ok = 0;
                }
                notifyInternalAdapter();
            }
        };
    }

    private void filtreaza(String text){
            copie.removeAll(copie);

        for(Produs produs : produse) {
            if (produs.getImagine().equals(text)) {
                copie.add(produs);
            }
        }
        ProduseAdapter adapter = new ProduseAdapter(getContext().getApplicationContext(),R.layout.lw_row_view,copie,getLayoutInflater(),produsUpdate,produsItem,okk);
        listViewProduse.setAdapter(adapter);
    }

    private void filtreazaNume(String text) {
        copie2.removeAll(copie2);

        if(ok == 1)
            for(Produs produs : copie) {
                if (produs.getNume().toLowerCase().contains(text.toLowerCase())) {
                    copie2.add(produs);
                }
            }
        else {
        for(Produs produs : produse) {
            if (produs.getNume().toLowerCase().contains(text.toLowerCase())) {
                copie2.add(produs);
            }
        }
        }
        ProduseAdapter adapter = new ProduseAdapter(getContext().getApplicationContext(),R.layout.lw_row_view,copie2,getLayoutInflater(),produsUpdate,produsItem,okk);
        listViewProduse.setAdapter(adapter);
    }



    public void notifyInternalAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) listViewProduse.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void LegareComponente(View view) {
         listViewProduse = view.findViewById(R.id.lista_produse);
         radioGroupCategorii = view.findViewById(R.id.RadioGroupCategorii);
         searchViewProduse = view.findViewById(R.id.searchProduse);

    }

    private void addProduseAdapter() {
        if(getContext()!=null){
            ProduseAdapter adapter = new ProduseAdapter(getContext().getApplicationContext(),R.layout.lw_row_view, produse,getLayoutInflater(),produsUpdate,produsItem,okk);
        listViewProduse.setAdapter(adapter);
        }
    }
}
