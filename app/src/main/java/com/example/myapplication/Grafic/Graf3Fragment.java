package com.example.myapplication.Grafic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Graf3Fragment extends Fragment {

    Produs produses;

    public Graf3Fragment(Produs produses) {
       this.produses = produses;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graf3, container, false);

        LineChart pieChart = view.findViewById(R.id.LineChart);


        ArrayList<Entry> produse = new ArrayList<>();
        int nr =0;
            int i=0;
            for(int an: produses.getAn()){
                ArrayList<Float> pret = produses.getPret();
                produse.add(new Entry(an,pret.get(i)));
                i++;
                nr++;
            }


        LineDataSet pieDataSet = new LineDataSet(produse,"Pret");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        LineData pieData = new LineData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.fitScreen();
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend();
        pieChart.animateX(1750);

        return view;
    }
}