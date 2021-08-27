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

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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


public class ProduseFragmentEmag extends Fragment {
    public static final String PRODUSE_KEY = "produse_key";

    private ListView listViewProduse;
    private RadioGroup radioGroupCategorii;
    private SearchView searchViewProduse;
    private ImageView imageViewEmag;
    private ConstraintLayout constraintLayout;
    
    private List<Produs> produse = new ArrayList<>();
    private FirebaseServiceProduse firebaseServiceProduse;
    List<Produs> copie =new ArrayList<>();
    List<Produs> copie2 =new ArrayList<>();
    int ok;
    int okk;

    DatabaseReference produseRef;

    String produsUpdate;
    Produs produsItem;

    public ProduseFragmentEmag(String produsUpdate,  Produs produsItem, int ok) {
        this.produsUpdate = produsUpdate;
        this.produsItem = produsItem;
        this.okk = ok;
    }

    public ProduseFragmentEmag() {

    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produse,container,false);
        LegareComponente(view);

       String[] numeRadioButton ={"Laptop","Telefon","Televizor", "Toate"};

        constraintLayout.setBackgroundColor(getResources().getColor(R.color.lightblue));

        int buttons = 4;
        for (int i = 0; i < buttons ; i++) {
            RadioButton rbn = new RadioButton(getContext());

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8,8,8,8);
            rbn.setLayoutParams(params);

            rbn.setBackgroundResource(R.drawable.radio_selector2);

            rbn.setTextColor(getResources().getColorStateList(R.color.selector_colors));

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

        produseRef = FirebaseDatabase.getInstance().getReference("laba");
        imageViewEmag.setImageResource(R.mipmap.emag);

        ArrayList<Integer> an = new ArrayList<>(Arrays.asList(2017, 2018, 2019,2020,2021));
        ArrayList<Integer> an2 = new ArrayList<>(Arrays.asList(2019,2020,2021));
        ArrayList<Integer> an3 = new ArrayList<>(Arrays.asList(2018,2019,2020,2021));

        ArrayList<Float> pretLaptop1 = new ArrayList<>(Arrays.asList(1569f, 1430f, 1400f, 1360f, 1349f));
        ArrayList<Float> pretLaptop2 = new ArrayList<>(Arrays.asList(3297f, 3350f, 3301f, 3290f,3299f));
        ArrayList<Float> pretLaptop3 = new ArrayList<>(Arrays.asList(4700f, 4500f, 4070f, 4260f,4149f));

        ArrayList<Float> pretTelefon1 = new ArrayList<>(Arrays.asList(330f, 340f, 300f,269f));
        ArrayList<Float> pretTelefon2 = new ArrayList<>(Arrays.asList(320f, 300f,258f));
        ArrayList<Float> pretTelefon3 = new ArrayList<>(Arrays.asList(1060f, 1120f, 1105f, 1170f,1078f));

        ArrayList<Float> pretTelevizor1 = new ArrayList<>(Arrays.asList(871f, 845f, 850f, 860f,759f));
        ArrayList<Float> pretTelevizor2 = new ArrayList<>(Arrays.asList(1103f,1059f, 1060f, 996f));
        ArrayList<Float> pretTelevizor3 = new ArrayList<>(Arrays.asList(1380f, 1306f, 1206f,1199f));

        Produs produs = new Produs(null, "Imagine_Laptop","ASUS X515MA","ASUS",1349,pretLaptop1,an,"1366 x 768",null);
        Produs produs2 = new Produs(null,"Imagine_Laptop","ASUS TUF FX505DT-HN536","ASUS",3299,pretLaptop2,an,"1920 x 1080",null);
        Produs produs3 = new Produs(null,"Imagine_Laptop","ASUS ROG Strix G15 G512LI","ASUS",4149,pretLaptop3,an,"1920 x 1080",null);

        Produs produs4 = new Produs(null,"Imagine_Telefon","Allview A10 Lite","Allview",269,pretTelefon1,an3,"960 x 480",null);
        Produs produs5 = new Produs(null,"Imagine_Telefon","Alcatel 1C","Alcatel",258, pretTelefon2,an2,"960 x 480",null);
        Produs produs6 = new Produs(null,"Imagine_Telefon","Apple iPhone 8","Apple",1078,pretTelefon3,an2,"750 x 1334",null);

        Produs produs7 = new Produs(null,"Imagine_Televizor","LED Orion","Orion",759,pretTelevizor1,an,"1366 x 768",null);
        Produs produs8 = new Produs(null,"Imagine_Televizor","LED Samsung","Samsung",996,pretTelevizor2,an2,"1366 x 768",null);
        Produs produs9 = new Produs(null,"Imagine_Televizor","LED Smart Samsung","Samsung",1199,pretTelevizor3,an3,"1366 x 768",null);

        this.produse.add(produs);
        this.produse.add(produs9);
        this.produse.add(produs6);
        this.produse.add(produs7);
        this.produse.add(produs2);
        this.produse.add(produs4);
        this.produse.add(produs3);
        this.produse.add(produs8);
        this.produse.add(produs5);


          firebaseServiceProduse.upsert(produs);
          firebaseServiceProduse.upsert(produs2);
          firebaseServiceProduse.upsert(produs3);
          firebaseServiceProduse.upsert(produs4);
          firebaseServiceProduse.upsert(produs5);
          firebaseServiceProduse.upsert(produs6);
          firebaseServiceProduse.upsert(produs7);
          firebaseServiceProduse.upsert(produs8);
          firebaseServiceProduse.upsert(produs9);
          firebaseServiceProduse.upsert(produs6);
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
                if(checkedId == 1){
                    String text = "Imagine_Laptop";
                    filtreaza(text);
                    ok=1;
                }else if(checkedId == 2){
                    String text = "Imagine_Telefon";
                    filtreaza(text);
                    ok=1;
                }
                else if(checkedId == 3) {
                    String text = "Imagine_Televizor";
                    filtreaza(text);
                    ok=1;
                }
                else {
                    addProduseAdapter();
                    ok=0;
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
                if (produs.getNume().contains(text)) {
                    copie2.add(produs);
                }
            }
        else {
            for(Produs produs : produse) {
                if (produs.getNume().contains(text)) {
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
         imageViewEmag = view.findViewById(R.id.PozaMagazin);
        constraintLayout = view.findViewById(R.id.general_background);
    }

    private void addProduseAdapter() {
        if(getContext()!=null){
            ProduseAdapter adapter = new ProduseAdapter(getContext().getApplicationContext(),R.layout.lw_row_view, produse,getLayoutInflater(),produsUpdate,produsItem,okk);
        listViewProduse.setAdapter(adapter);
        }
    }
}
