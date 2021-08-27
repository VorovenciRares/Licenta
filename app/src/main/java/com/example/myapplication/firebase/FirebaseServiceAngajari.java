package com.example.myapplication.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.utils.Job;
import com.example.myapplication.utils.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseServiceAngajari {


    public static final String TABLE_NAME = "job_aplicari";
    private static FirebaseServiceAngajari firebaseServiceAngajari;
    private DatabaseReference databaseReference;

    private FirebaseServiceAngajari(){
        databaseReference = FirebaseDatabase.getInstance().getReference(TABLE_NAME);
    }

    public static FirebaseServiceAngajari getInstance(){
        if(firebaseServiceAngajari == null)
            synchronized (FirebaseServiceAngajari.class){
                if(firebaseServiceAngajari == null){
                    firebaseServiceAngajari = new FirebaseServiceAngajari();
                }
            }
        return firebaseServiceAngajari;
    }

    public void upsert(Job job)
    {
        if(job ==null)
            return;
        if(job.getId() == null || job.getId().trim().isEmpty()){
            String id = databaseReference.push().getKey();
            job.setId(id);
        }
        databaseReference.child(job.getId()).setValue(job);
    }

    public void update(Job job2, Job job)
    {
        if(job2 ==null)
            return;
        if(job2.getId() == null || job2.getId().trim().isEmpty()){
            String id = databaseReference.push().getKey();
            job.setId(id);
        }
        job.setId(job2.getId());
        databaseReference.child(job2.getId()).setValue(job);

    }

    public void delete(Job job)
    {
        if(job == null || job.getId() == null
                || job.getId().trim().isEmpty())
            return;
        databaseReference.child(job.getId()).removeValue();

        databaseReference.child(job.getId()).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Firebase","Stergerea merge");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Firebase","Stergerea nu merge");
            }
        });
    }

    public void attachDataChangeEventListener(final Callback<List<Job>> listCallback){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Job> produse = new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Job produs = data.getValue(Job.class);
                    if(produs !=null){
                        produse.add(produs);
                    }
                }
                listCallback.runResultOnUiThread(produse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseService","Data nu e dispobivila");
            }
        });
    }
}
