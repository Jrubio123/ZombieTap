package com.jrubio.zombie_tap.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jrubio.zombietap.R;

import java.util.ArrayList;
import java.util.List;

public class Puntajes extends AppCompatActivity {

    LinearLayoutManager mLayoutMangManager;
    RecyclerView recyclerViewUsuarios;
    Adaptador adaptador;
    List<Usuario> usuarioList;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Puntajes");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //inicializamos

        firebaseAuth=FirebaseAuth.getInstance();
        mLayoutMangManager=new LinearLayoutManager(this);
        recyclerViewUsuarios=findViewById(R.id.recyclerViewUsuarios);

        //ordenaremos por tipo de zombies de mas alto a mas bajo
        mLayoutMangManager.setReverseLayout(true);//ordena de Z-A
        mLayoutMangManager.setStackFromEnd(true);//empiza desde arriba sin tener deliz

        recyclerViewUsuarios.setHasFixedSize(true);
        recyclerViewUsuarios.setLayoutManager(mLayoutMangManager);//forma

        usuarioList=new ArrayList<>();

        //obtener la lista

        obtenerTodosLosUsuarios();


    }

    private void obtenerTodosLosUsuarios(){
        FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();//obtiene el usuario actual

        //lamada abase de datos

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Database Jugadores");

        //dependiendo de que dato queremos que se ordene
        ref.orderByChild("Zombies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //escucha cuando surge un cambio (o sea un jugador cambia de puntaje este se actualiza (es un oyente))

                usuarioList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //del modelo
                    Usuario usuario = ds.getValue(Usuario.class);

                    /*if(!usuario.getUid().equals(fUser.getUid())){ //esto es solo si quiero que en la lista no este yo jejeje
                        usuarioList.add(usuario);
                    }*/
                    usuarioList.add(usuario);

                    adaptador =new Adaptador(Puntajes.this,usuarioList);
                    recyclerViewUsuarios.setAdapter(adaptador);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}