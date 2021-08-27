package com.example.myapplication.Logare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.ActivityMenu;
import com.example.myapplication.R;
import com.example.myapplication.utils.Utilizator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InregistrareActivity extends AppCompatActivity {

    EditText NumeUtilizator, Varsta, Email, Parola;
    Button butonInregistrare;
    ProgressBar progressBarInregistrare;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore firestore;
    String userID;
    FirebaseUser userL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrare);

        initComponents();

        butonInregistrare.setOnClickListener(DateClickEvent());

    }

    private View.OnClickListener DateClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validare()) {
                    BuildFromWidgets();

                }

            }
        };
    }

    private boolean validare() {
        if (NumeUtilizator.getText().toString() == null || NumeUtilizator.getText().toString().trim().length() == 0) {
            NumeUtilizator.setError("Introduceti numele de utilizator!");
            return false;
        }
        if (Varsta.getText().toString() == null || Varsta.getText().toString().trim().length() == 0
                || Integer.parseInt(Varsta.getText().toString().trim()) < 18 || Integer.parseInt(Varsta.getText().toString().trim()) > 90) {
            Varsta.setError("Introduceti o varsta valida!");
            return false;
        }

        if ( Email.getText() == null || Email.getText().toString().trim().length() == 0 ||
                (Email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches())) {
            Email.setError("Introduceti o adresa de email!");
            return false;
        }

        if (Parola.getText() == null || Parola.getText().toString().trim().length() < 5) {
            Parola.setError("Introduceti o parola adecvata!");
            return false;
        }
        return true;
    }

    private void BuildFromWidgets() {

        final String nume_utilizator, email, parola;
        final int varsta;
        nume_utilizator = NumeUtilizator.getText().toString();
        varsta = Integer.parseInt(Varsta.getText().toString());
        email = Email.getText().toString();
        parola = Parola.getText().toString();

        final Utilizator utilizator = new Utilizator(nume_utilizator,varsta,email,parola);

        progressBarInregistrare.setVisibility(View.VISIBLE);


        firebaseAuth.createUserWithEmailAndPassword(email, parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(InregistrareActivity.this, "Utilizator Creat", Toast.LENGTH_SHORT).show();

                    CreazaBD(utilizator);
                    Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                    startActivity(intent);
                    progressBarInregistrare.setVisibility(View.INVISIBLE);

                }else{
                    Toast.makeText(InregistrareActivity.this, "Eroare:" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBarInregistrare.setVisibility(View.INVISIBLE);
                }

            }

        });
    }

    private void CreazaBD(Utilizator utilizator) {
        userID = firebaseAuth.getCurrentUser().getUid();
        Toast.makeText(InregistrareActivity.this,"Caca 1"+userID.toString(),Toast.LENGTH_SHORT).show();
        userL = firebaseAuth.getCurrentUser();
        DocumentReference documentReference = firestore.collection("Utilizatori").document(userID);

        final Map<String, Object> user = new HashMap<>();
        user.put("Nume", utilizator.getNume());
        user.put("Varsta",utilizator.getVarsta());
        user.put("Email", utilizator.getEmail());
        user.put("Parola",utilizator.getParola());
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               userID = firebaseAuth.updateCurrentUser(userL).toString();
                Toast.makeText(InregistrareActivity.this,"Caca 2"+userID.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initComponents() {
        NumeUtilizator = findViewById(R.id.editTextNumeUtilizator1);
        Varsta = findViewById(R.id.editTextVarsta);
        Email = findViewById(R.id.editTextEmail);
        Parola = findViewById(R.id.editTextParola1);
        butonInregistrare = findViewById(R.id.buttonInregistreaza);
        progressBarInregistrare = findViewById(R.id.progressBarInregistrare);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
}