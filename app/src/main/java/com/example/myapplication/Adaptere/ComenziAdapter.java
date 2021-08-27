package com.example.myapplication.Adaptere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.FormularAdaugare.ComandaActivity;
import com.example.myapplication.firebase.Callback;
import com.example.myapplication.firebase.FirebaseServiceComezi;
import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ComenziAdapter extends ArrayAdapter<Produs> {

    private Context context;
    private int resource;
    private List<Produs> produsListCompleta;
    private LayoutInflater layoutInflater;

    private List<Produs> comenzi = new ArrayList<>();
    ListView listViewComenzi;
    ComandaActivity comandaActivity;

    public ComenziAdapter(@NonNull Context context, int resource, @NonNull List<Produs> objects,
                          LayoutInflater inflater, ComandaActivity comandaActivity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.produsListCompleta = objects;
        this.layoutInflater = inflater;
        this.comandaActivity = comandaActivity;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        final View view = layoutInflater.inflate(resource,parent,false);


        final Produs produs = produsListCompleta.get(position);
        if(produs != null){

            TextView textView = view.findViewById(R.id.textViewNumeLatop);
            if(produs.getNume()!=null && !produs.getNume().isEmpty() && produs.getNume().length()<20) {
                textView.setText(produs.getNume());
            }else
            {textView.setTextSize(11.5f);
                textView.setText(produs.getNume());

            }

            int nr=0;
            if(produs !=null)
                for (float pret : produs.getPret()) {
                    nr++;
                }

            addPretProdus(view, String.valueOf(produs.getPret().get(nr-1)));
            addMarca(view,produs.getMarca());

            TextView textView2 = view.findViewById(R.id.textViewRezolutie);
            if(produs.getRezolutie()!=null && !produs.getRezolutie().isEmpty() && produs.getRezolutie().length()<20) {
                textView2.setText(produs.getRezolutie());
            }else if(produs.getRezolutie()!=null && !produs.getRezolutie().isEmpty() && produs.getRezolutie().length()>=20)
            {textView2.setTextSize(11.5f);
                textView2.setText(produs.getRezolutie());

            }
            if(produs.getRezolutie()==null){
                if(produs.getPlacaVideo()!=null && !produs.getPlacaVideo().isEmpty() && produs.getPlacaVideo().length()<20) {
                    textView2.setText(produs.getPlacaVideo());
                }else if(produs.getPlacaVideo()!=null && !produs.getPlacaVideo().isEmpty() && produs.getPlacaVideo().length()>=20)
                {
                    textView2.setTextSize(11.5f);
                    textView2.setText(produs.getPlacaVideo());
                }
            }

            addPoza(view, produs.getImagine());

        }
        return view;
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

    private void notifyLvAdapter() {
        ComenziAdapter comenziAdapter = (ComenziAdapter) listViewComenzi.getAdapter();
        comenziAdapter.notifyDataSetChanged();
    }



    private void addPoza(View view, String pozaProdus){
        ImageView imageView = view.findViewById(R.id.laptop_generic_poza);
        switch (pozaProdus){
            case("Imagine_Telefon"):
                imageView.setImageResource(R.mipmap.generic_phone1);
                break;
            case("Imagine_Televizor"):
                imageView.setImageResource(R.mipmap.televizor_generic);
                break;
            case("Imagine_Calculator"):
                imageView.setImageResource(R.mipmap.generic_computer);
                break;
        }

    }

    private void addPretProdus(View view, String pretProdus){
        TextView textView = view.findViewById(R.id.textViewPret);
        populateTextView(String.valueOf(pretProdus),textView);
    }

    private void addMarca(View view, String marca){
        TextView textView = view.findViewById(R.id.textViewMarca);
        populateTextView(marca, textView);
    }

    private void populateTextView(String numeProdus, TextView textView) {
        if(numeProdus!=null && !numeProdus.isEmpty()){
            textView.setText(numeProdus);
        }
        else{
            textView.setText("Necunoscut");
        }
    }
}

