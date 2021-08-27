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

public class FirebaseServiceProduse {

    public static final String TABLE_NAME = "produse";
    private static FirebaseServiceProduse firebaseServiceProduse;
    private DatabaseReference databaseReference;

    private FirebaseServiceProduse(){
         databaseReference = FirebaseDatabase.getInstance().getReference(TABLE_NAME);
    }

    public static FirebaseServiceProduse getInstance(){
        if(firebaseServiceProduse == null)
            synchronized (FirebaseServiceProduse.class){
                if(firebaseServiceProduse == null){
                    firebaseServiceProduse = new FirebaseServiceProduse();
                }
        }

        return firebaseServiceProduse;
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

}
