package com.example.myapplication.Adaptere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.FormularAdaugare.ComandaActivity;
import com.example.myapplication.FormularAdaugare.ComandaProdusActivity;
import com.example.myapplication.utils.Job;
import com.example.myapplication.R;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {

    private Context context;
    private int resource;
    private List<Job> jobLista;
    private LayoutInflater layoutInflater;


    public JobAdapter(@NonNull Context context, int resource, @NonNull List<Job> objects,
                      LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.jobLista = objects;
        this.layoutInflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource,parent,false);

        final Job job = jobLista.get(position);
        if(job != null){
            TextView textView = view.findViewById(R.id.textViewNumePozitie);
            if(job.getNumePozitie()!=null) {
                textView.setText(job.getNumePozitie());
            }
            TextView textView2 = view.findViewById(R.id.textViewDepartament);
            textView2.setText(job.getDepartament());
            TextView textView3 = view.findViewById(R.id.textViewOras);
            textView3.setText(job.getOras());

        }
        return view;
    }


}

