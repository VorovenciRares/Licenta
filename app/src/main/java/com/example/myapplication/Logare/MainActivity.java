package com.example.myapplication.Logare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ActivityMenu;
import com.example.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin, buttonGoogleLogin;
    TextInputEditText Email, Parola;
    TextView SignUp;
    ProgressBar progressBarMain;

    FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1488;
    public final static String SIGN_IN = "1889";
    private FirebaseAuth mAuth;

    boolean esteLogatNormal = true;

    String userID;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeagaComponentele();

        buttonLogin.setOnClickListener(Logheaza());
        SignUp.setOnClickListener(SignUp());

        GoogleRequest();

        buttonGoogleLogin.setOnClickListener(LogheazaGoogle());


    }
    private View.OnClickListener EvenimentDeconectare() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleRequest();
                if (mGoogleSignInClient != null)
                    signOut();
            }
        };
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Autenficarea a dat gres!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GoogleRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        private View.OnClickListener LogheazaGoogle () {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    signIn();
                }
            };
        }


        private View.OnClickListener SignUp () {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), InregistrareActivity.class);
                    startActivity(intent);
                }
            };
        }

        private View.OnClickListener Logheaza () {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EvenimentDeconectare();
                    if (validare())
                        BuildFromWidgets();


                }
            };
        }

        private boolean validare () {
            if (Email.getText().toString() == null || Email.getText().toString().trim().length() == 0) {
                Email.setError("Introduceti adresa de email!");
                return false;
            }

            if (Parola.getText() == null || Parola.getText().toString().trim().length() == 0) {
                Parola.setError("Introduceti o parola!");
                return false;
            }
            return true;
        }

        private void BuildFromWidgets () {
            String email, parola;
            email = Email.getText().toString();
            parola = Parola.getText().toString();
            progressBarMain.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(email, parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                        startActivityForResult(intent,RC_SIGN_IN);

                    } else {
                        Toast.makeText(MainActivity.this, "A aparut o eroare: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBarMain.setVisibility(View.INVISIBLE);
                }
            });


        }

        private void LeagaComponentele () {
            buttonLogin = findViewById(R.id.buttonLogin);
            buttonGoogleLogin = findViewById(R.id.GoogleLogin);
            Email = findViewById(R.id.tie_EmailLog);
            Parola = findViewById(R.id.tie_ParolaLog);
            SignUp = findViewById(R.id.SignUp);
            progressBarMain = findViewById(R.id.progressBarMain);

            firebaseAuth = FirebaseAuth.getInstance();
            mAuth = FirebaseAuth.getInstance();

        }


    }