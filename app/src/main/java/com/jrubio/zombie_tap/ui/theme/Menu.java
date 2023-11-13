package com.jrubio.zombie_tap.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jrubio.zombie_tap.MainActivity;
import com.jrubio.zombietap.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Jugadores;


    TextView miPuntuacionTxt,zombies,uid,correo,nombre,menuTxt,fecha;
    Button cerrarSesion,jugarBtn,puntuaciones,acercaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        miPuntuacionTxt = findViewById(R.id.miPuntuacionTxt);
        zombies = findViewById(R.id.zombies);
        uid = findViewById(R.id.uid);
        correo = findViewById(R.id.correo);
        nombre = findViewById(R.id.nombre);
        menuTxt = findViewById(R.id.menuTxt);
        fecha = findViewById(R.id.fecha);


        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();


        firebaseDatabase =FirebaseDatabase.getInstance();

        Jugadores = firebaseDatabase.getReference("Database Jugadores");//nombre de base de datos


        //ubicacion de la letra
        String ubicacion="fuentes/zombie.TTF";

        Typeface Tf= Typeface.createFromAsset(Menu.this.getAssets(),ubicacion);

        cerrarSesion=findViewById(R.id.cerrarSesion);
        jugarBtn=findViewById(R.id.jugatBtn);
        puntuaciones=findViewById(R.id.puntuaciones);
        acercaBtn=findViewById(R.id.acercaBtn);

        miPuntuacionTxt.setTypeface(Tf);
        zombies.setTypeface(Tf);
        uid.setTypeface(Tf);
        correo.setTypeface(Tf);
        nombre.setTypeface(Tf);
        menuTxt.setTypeface(Tf);
        fecha.setTypeface(Tf);

        cerrarSesion.setTypeface(Tf);
        jugarBtn.setTypeface(Tf);
        puntuaciones.setTypeface(Tf);
        acercaBtn.setTypeface(Tf);

        //boton jugar
        jugarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu.this, "Jugar", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Menu.this,escenarioJuego.class);

                String uidS=uid.getText().toString();
                String nombreS=nombre.getText().toString();
                String zombieS=zombies.getText().toString();

                intent.putExtra("UID",uidS);
                intent.putExtra("NOMBRE",nombreS);
                intent.putExtra("ZOMBIES",zombieS);

                startActivity(intent);



            }
        });

        //boton puntuaciones
        puntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu.this, "Puntuaciones", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Menu.this,Puntajes.class);
                startActivity(intent);

            }
        });

        //boton acerca de
        acercaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu.this, "Acerca de", Toast.LENGTH_SHORT).show();


            }
        });


        //cerrar sesion
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion();
            }
        });


    }
    //se ejecuta cuando se abre el juegazo
    protected void onStart(){
        usuariologueado();
        super.onStart();
    }
    //para saber si ya esta en linea
    private void usuariologueado(){
        if(user!=null){
            consulta();
            Toast.makeText(this, "Jugador en linea", Toast.LENGTH_SHORT).show();

        }else {
            startActivity(new Intent(Menu.this, MainActivity.class));
            finish();
        }
    }

    private void CerrarSesion(){
        auth.signOut();
        startActivity(new Intent(Menu.this,MainActivity.class));
        Toast.makeText(this, "Sesion Cerrada", Toast.LENGTH_SHORT).show();
    }

    //consulta para hacer llamado de informacion

    private void consulta(){
        Query query=Jugadores.orderByChild("Correo").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){//comparamos correo electronico con el creado actualmente
                    //obtenemos valores
                    String zombiesString= ""+ds.child("Zombies").getValue();
                    String uidString= ""+ds.child("Uid").getValue();
                    String emailString= ""+ds.child("Correo").getValue();
                    String nombreString= ""+ds.child("Nombre").getValue();
                    String fechaString= ""+ds.child("Fecha").getValue();


                    //datos en las vistas
                    zombies.setText(zombiesString);
                    uid.setText(uidString);
                    correo.setText(emailString);
                    nombre.setText(nombreString);
                    fecha.setText(fechaString);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}