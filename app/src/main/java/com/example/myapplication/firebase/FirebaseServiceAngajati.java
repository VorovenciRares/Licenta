package com.example.myapplication.firebase;

import com.example.myapplication.utils.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseServiceAngajati {


    public static final String TABLE_NAME = "job";
    private static FirebaseServiceAngajati firebaseServiceAngajati;
    private DatabaseReference databaseReference;

    private FirebaseServiceAngajati(){
        databaseReference = FirebaseDatabase.getInstance().getReference(TABLE_NAME);
    }

    public static FirebaseServiceAngajati getInstance(){
        if(firebaseServiceAngajati == null)
            synchronized (FirebaseServiceAngajati.class){
                if(firebaseServiceAngajati == null){
                    firebaseServiceAngajati = new FirebaseServiceAngajati();
                }
            }

        return firebaseServiceAngajati;
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


}
