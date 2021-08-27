package com.example.myapplication.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.utils.Job;
import com.example.myapplication.utils.Produs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirebaseServiceComezi {


    public static final String TABLE_NAME = "comenzi";
    private static FirebaseServiceComezi firebaseServiceComezi;
    private DatabaseReference databaseReference;
    Random random = new Random();

    public FirebaseServiceComezi(){
        databaseReference = FirebaseDatabase.getInstance().getReference(TABLE_NAME);
    }

    public static FirebaseServiceComezi getInstance(){
        if(firebaseServiceComezi == null)
            synchronized (FirebaseServiceProduse.class){
                if(firebaseServiceComezi == null){
                    firebaseServiceComezi = new FirebaseServiceComezi();
                }
            }

        return firebaseServiceComezi;
    }


    public void upsert(Produs produs)
    {
        if(produs ==null)
            return;
        if(produs.getId() == null || produs.getId().trim().isEmpty()){
            String id = databaseReference.push().getKey();
            produs.setId(id);
        }
        databaseReference.child(produs.getId()).setValue(produs);
    }

    public void update(Produs produs2, Produs produs)
    {
        if(produs2 ==null)
            return;
        if(produs2.getId() == null || produs2.getId().trim().isEmpty()){
            String id = databaseReference.push().getKey();
            produs.setId(id);
        }
        produs.setId(produs2.getId());
        databaseReference.child(produs2.getId()).setValue(produs);

    }


    public void delete(Produs produs)
    {
        if(produs == null || produs.getId() == null
                || produs.getId().trim().isEmpty())
            return;
        databaseReference.child(produs.getId()).removeValue();

        databaseReference.child(produs.getId()).removeEventListener(new ValueEventListener() {
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

    public void attachDataChangeEventListener(final Callback<List<Produs>> listCallback){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Produs> produse = new ArrayList<>();
                //facem vector cu informatiile individuala pt fiecare id
                for(DataSnapshot data:snapshot.getChildren()){
                    //luam valoarea convertita la produse
                    Produs produs = data.getValue(Produs.class);
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
