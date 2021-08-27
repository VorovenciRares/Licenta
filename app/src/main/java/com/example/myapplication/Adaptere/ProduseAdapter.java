package com.example.myapplication.Adaptere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.FormularAdaugare.ComandaActivity;
import com.example.myapplication.FormularAdaugare.ComandaProdusActivity;
import com.example.myapplication.Grafic.GrafPretActivity;
import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;

import java.util.List;

public class ProduseAdapter extends ArrayAdapter<Produs> {

    public static final String MAGAZIN_KEY3 = "magazin_key3";

    private Context context;
    private int resource;
    private List<Produs> produsListCompleta;
    private LayoutInflater layoutInflater;
    String produsUpdate;
    Produs produsItem;
    int ok;

    public ProduseAdapter(@NonNull Context context, int resource, @NonNull List<Produs> objects,
                          LayoutInflater inflater,String produsUpdate, Produs produsItem, int ok) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.produsListCompleta = objects;
        this.layoutInflater = inflater;
        this.produsUpdate = produsUpdate;
        this.produsItem = produsItem;
        this.ok = ok;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource,parent,false);

        final Produs produs = produsListCompleta.get(position);
        if(produs != null){

            TextView textView = view.findViewById(R.id.textViewNumeLatop);
            if(produs.getNume()!=null && !produs.getNume().isEmpty() && produs.getNume().length()<20) {
                textView.setText(produs.getNume());
            }else
            {textView.setTextSize(11.5f);
                textView.setText(produs.getNume());

            }

            addPretProdus(view, produs.getPretActual());
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

            Button buttonEvolutiePret;
            buttonEvolutiePret = view.findViewById(R.id.buttonEvolutiePretProduse);
            buttonEvolutiePret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext().getApplicationContext(), GrafPretActivity.class);

                    TrimiteMaiDeparte(intent, produs);

                    context.startActivity(intent);
                }
            });
        }

        Button buttonComanda;
        buttonComanda = view.findViewById(R.id.buttonComanda);
        buttonComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), ComandaProdusActivity.class);

                TrimiteMaiDeparte(intent, produs);

                context.startActivity(intent);
            }
        });
        return view;
    }

    private void TrimiteMaiDeparte(Intent intent, Produs produs) {
            intent.putExtra(ComandaProdusActivity.PRODUSE_CHEY_COMANDA2, produsUpdate);
            intent.putExtra(ComandaProdusActivity.PRODUSE_CHEY_COMANDA3, produsItem);
            intent.putExtra(GrafPretActivity.PRODUSE_CHEY, produs);
            intent.putExtra(ComandaProdusActivity.PRODUSE_CHEY_COMANDA, produs);
            intent.putExtra(ComandaProdusActivity.PRODUSE_CHEY_COMANDA4, ok);
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

    private void addPretProdus(View view, int pretProdus){
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

