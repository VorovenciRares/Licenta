package com.example.myapplication.Grafic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Graf2Fragment extends Fragment {

    Produs produses;

    public Graf2Fragment(Produs produses) {
       this.produses = produses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_graf2, container, false);

        PieChart pieChart = view.findViewById(R.id.PieChart);

        this.produses = produses;

        ArrayList<PieEntry> produse = new ArrayList<>();
        int nr =0;
            int i=0;
            for(int an: produses.getAn()){
                ArrayList<Float> pret = produses.getPret();
                produse.add(new PieEntry(pret.get(i),String.valueOf(an) ));
                i++;
                nr++;
        }

        PieDataSet pieDataSet = new PieDataSet(produse,"Produse");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend();
        pieChart.setCenterText(this.produses.getNume());
        pieChart.animateY(1750);

        return view;
    }
}