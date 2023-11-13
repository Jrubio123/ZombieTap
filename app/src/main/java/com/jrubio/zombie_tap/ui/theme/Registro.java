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
import android.widget.TextView;
import android.widget.Toast;

import com.jrubio.zombietap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Registro extends AppCompatActivity {

    //variables
    EditText nombreEt,correoEt,passwordEt;
    TextView fechaTxt;
    Button Registrar;
    FirebaseAuth auth; //firebase autenticacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        //conexion con la vista
        nombreEt=findViewById(R.id.nombreEt);
        correoEt=findViewById(R.id.correoEt);
        passwordEt=findViewById(R.id.passwordEt);
        Registrar=findViewById(R.id.Registrar);
        fechaTxt=findViewById(R.id.fechaTxt);

        auth=FirebaseAuth.getInstance();

        //ubicacion de la letra
        String ubicacion="fuentes/zombie.TTF";

        Typeface Tf= Typeface.createFromAsset(Registro.this.getAssets(),ubicacion);

        Registrar.setTypeface(Tf);

        Date date=new Date();
        SimpleDateFormat fecha=new SimpleDateFormat("d 'de' MMMM 'del' YYYY");/*15 de mayo del 2023 como ejemplo  asi sale*/
        String stringFecha= fecha.format(date);
        fechaTxt.setText(stringFecha);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo= correoEt.getText().toString();
                String password= passwordEt.getText().toString();

                /*Validacion de datos*/

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    correoEt.setError("Correo no valido");
                    correoEt.setFocusable(true);
                } else if (password.length()<6) {
                    passwordEt.setError("Contrasena mayor a 6");
                    passwordEt.setFocusable(true);
                }else {
                    registrarJugador(correo,password);
                }
            }
        });
    }
    /*metodo para registrar un usuario*/

    private void registrarJugador(String correo, String password) {

        auth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*Si si registro correctamente*/
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();

                            int contador =0;

                            //firebase usa strings
                            assert user!= null;
                            String uidString= user.getUid();
                            String nombreString = nombreEt.getText().toString();
                            String correoString=correoEt.getText().toString();
                            String passString= passwordEt.getText().toString();
                            String fechaString= fechaTxt.getText().toString();

                            HashMap<Object,Object> datosJugador =new HashMap<>();

                            datosJugador.put("Uid",uidString);
                            datosJugador.put("Nombre",nombreString);
                            datosJugador.put("Correo",correoString );
                            datosJugador.put("Password",passString);
                            datosJugador.put("Fecha",fechaString);
                            datosJugador.put("Zombies",contador);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Database Jugadores");/*Referencia al nombre de la base de datos*/

                            reference.child(uidString).setValue(datosJugador);
                            startActivity(new Intent(Registro.this, Menu.class));

                            Toast.makeText(Registro.this, "Usuario Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }else Toast.makeText(Registro.this, "Ha  ocurrido un error", Toast.LENGTH_SHORT).show();
                    }

                    /*SI falla el registro*/
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}