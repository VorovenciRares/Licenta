package com.example.myapplication.Grafic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.utils.Produs;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;


public class Graf1Fragment extends Fragment {

    Random random;
    Produs produses;

    public Graf1Fragment(Produs produses) {
        this.produses = produses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graf1, container, false);


        BarChart barChart = view.findViewById(R.id.BarChart);
        random = new Random();

        ArrayList<BarEntry> produse = new ArrayList<>();

           int i=0;
           for(int an: produses.getAn()){
               ArrayList<Float> pret = produses.getPret();
               produse.add(new BarEntry(an, pret.get(i)));
               i++;
           }


        BarDataSet barDataSet = new BarDataSet(produse, "Pret");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1750);
        return view;
    }
}