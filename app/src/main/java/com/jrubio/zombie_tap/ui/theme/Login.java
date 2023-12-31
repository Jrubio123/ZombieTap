package com.jrubio.zombie_tap.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jrubio.zombietap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    //declaramos variables
    EditText correoLogin, passwordLogin;
    Button buttonlogin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conexion con la vista
        correoLogin=findViewById(R.id.correoLogin);
        passwordLogin=findViewById(R.id.passwordLogin);
        buttonlogin=findViewById(R.id.buttonLogin);
        auth=FirebaseAuth.getInstance();

        //ubicacion de la letra
        String ubicacion="fuentes/zombie.TTF";

        Typeface Tf= Typeface.createFromAsset(Login.this.getAssets(),ubicacion);

        buttonlogin.setTypeface(Tf);



        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=correoLogin.getText().toString();
                String password=passwordLogin.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    correoLogin.setError("Correo no valido");
                    correoLogin.setFocusable(true);
                } else if (password.length()<6) {
                    passwordLogin.setError("Contrasena mayor a 6");
                    passwordLogin.setFocusable(true);
                }else {
                    LogueoJuego(email,password);
                }
            }
        });

    }
    //metodo para loguear al jugador
    private void LogueoJuego(String email, String password) {

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user= auth.getCurrentUser();
                            startActivity(new Intent(Login.this, Menu.class));
                            Toast.makeText(Login.this, "BIENVENIDO(A)"+user.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }
}