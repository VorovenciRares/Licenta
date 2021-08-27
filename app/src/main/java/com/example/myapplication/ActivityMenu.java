package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Logare.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityMenu extends AppCompatActivity {
    Button butonAltex;
    Button buttonEmag;
    Button butonDeconectare;
    ImageView imageViewUtilizator;
    TextView Nume, Email;
    private FirebaseAuth mAuth;

    String userId;
    boolean muie = false;

    private GoogleSignInClient mGoogleSignInClient;

    FirebaseFirestore firestore;
    String userID;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;

    private final static int SIGN_IN_INT = 1889;
    boolean esteDeconectat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LegareComponenete();

        butonAltex.setOnClickListener(EvenimentClickAltex());
        buttonEmag.setOnClickListener(EvenimentClickEmag());
        butonDeconectare.setOnClickListener(EvenimentDeconectare());

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Utilizatori").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Nume.setText(doc.get("Nume").toString());
                        Email.setText(doc.get("Email").toString());
                    }
                }
            }
        });

        if (googleSignInAccount != null)
            CreazaBD(googleSignInAccount);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_INT
                && resultCode == RESULT_OK && data != null) {
    ;
        }
    }

    private void CreazaBD(GoogleSignInAccount googleSignInAccount) {
        userID = firebaseAuth.getCurrentUser().getUid();
        if (userID != null) {
            DocumentReference documentReference = firestore.collection("Utilizatori").document(userID);

            final Map<String, Object> user = new HashMap<>();


            user.put("Email", googleSignInAccount.getEmail().toString());
            user.put("Nume", googleSignInAccount.getDisplayName().toString());
            user.put("Parola", "Necunoscuta");
            user.put("Varsta", "Necunoscuta");

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        }
    }

    private View.OnClickListener EvenimentDeconectare() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleRequest();
                if (mGoogleSignInClient != null)
                    signOut();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
    }

    public void GoogleRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private View.OnClickListener EvenimentClickAltex() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeniuSecundar.class);
                startActivity(intent);

            }
        };
    }

    private View.OnClickListener EvenimentClickEmag() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeniuSecundar.class);
                String magazin = "Emag";
                intent.putExtra(MeniuSecundar.MAGAZIN_KEY,magazin);
                startActivity(intent);
            }
        };
    }

    private void LegareComponenete() {
        butonAltex = findViewById(R.id.imageButtonAltex);
        buttonEmag = findViewById(R.id.imageButtonEmag);
        butonDeconectare = findViewById(R.id.buttonDeconectare);
        Nume = findViewById(R.id.textViewNumeMeniu);
        Email = findViewById(R.id.textViewEmailMeniu);
        imageViewUtilizator = findViewById(R.id.imageViewUtilizator);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
}