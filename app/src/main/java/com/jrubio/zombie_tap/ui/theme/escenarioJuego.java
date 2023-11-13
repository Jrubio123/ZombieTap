package com.jrubio.zombie_tap.ui.theme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jrubio.zombietap.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class escenarioJuego extends AppCompatActivity {

    String nombreS,uidS, zombieS;

    TextView tvContador, tvNombre,tvTiempo,anchoTv,altoTv;
    ImageView imZombie;

    int contador=0;

    int anchoPantalla, altoPantalla;

    Random aleatorio;

    boolean gameOver=false;
    Dialog miDialog;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference Jugadores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego);

        tvContador=findViewById(R.id.tvContador);
        tvNombre=findViewById(R.id.tvNombre);
        tvTiempo=findViewById(R.id.tvTiempo);
        imZombie=findViewById(R.id.imZombie);
        anchoTv=findViewById(R.id.anchoTv);
        altoTv=findViewById(R.id.altoTv);

        Bundle intent=getIntent().getExtras();

        uidS=intent.getString("UID");
        nombreS=intent.getString("NOMBRE");
        zombieS=intent.getString("ZOMBIES");

        tvContador.setText(zombieS);
        tvNombre.setText(nombreS);

        miDialog=new Dialog(escenarioJuego.this);

        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        Jugadores=firebaseDatabase.getReference("Database Jugadores");

        Pantalla();
        cuentaAtras();


        //al darle clock a la imagen
        imZombie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!gameOver) {

                    contador++;//contador va yendo de 1  en 1 cada que se toque
                    tvContador.setText(String.valueOf(contador));//ya le pone el contador al textview

                    imZombie.setImageResource(R.drawable.zombieaplastado);

                    //Para ejecutar codigo en determinado tiempo
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //que medio segundo despues nos cambie de imagen

                            imZombie.setImageResource(R.drawable.zombie);
                            movimiento();
                        }
                    }, 50);
                }
            }
        });



    }

    //para obtener tamano de pantalla para calcular hasta donde o como se mueven los zombies
    private void Pantalla(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        anchoPantalla= point.x;
        altoPantalla=point.y;

        String anchoS=String.valueOf(anchoPantalla);
        String altoS =String.valueOf(altoPantalla);

        anchoTv.setText(anchoS);
        altoTv.setText(altoS);

        aleatorio=new Random();

    }

    //movimiento aleatorio del zombie
    private void movimiento(){

        int min=0;
        //maximo en x y y
        int maximoX= anchoPantalla-imZombie.getWidth();
        int maximoY= altoPantalla-imZombie.getWidth();

        //movilizamos al zombie

        int randomX= aleatorio.nextInt(((maximoX-min)+1)+min);
        int randomY= aleatorio.nextInt(((maximoY-min)+1)+min);

        imZombie.setX(randomX);
        imZombie.setY(randomY);



    }

    private void cuentaAtras(){

        //dos parametros tiempo de inicio y parametro itnervalo que se va bajando o sea 30000 (30seg) 1000 (disminuye de 1 en 1 )
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                long segundosRestantes=millisUntilFinished/1000;
                tvTiempo.setText(segundosRestantes+" S");

            }
            //Cuando se acaba el tiempo
            public void onFinish() {
                tvTiempo.setText("0 S");//setea cero segundos
                gameOver=true;
                mensajeGameOver();
                guardarResultados("Zombies",contador);


            }
        }.start();


    }

    private void mensajeGameOver() {

        String ubicacion = "fuentes/zombie.TTF";
        Typeface typeface= Typeface.createFromAsset(escenarioJuego.this.getAssets(),ubicacion);

        TextView seAcaboTxt,hasMatadoTxt,numeroTxt;
        Button jugarDeNuevo,irMenu,puntajes;

        miDialog.setContentView(R.layout.gameover);

        seAcaboTxt=miDialog.findViewById(R.id.seAcaboTxt);
        hasMatadoTxt=miDialog.findViewById(R.id.hasMatadoTxt);
        numeroTxt=miDialog.findViewById(R.id.numeroTxt);

        jugarDeNuevo=miDialog.findViewById(R.id.jugarDeNuevo);
        irMenu=miDialog.findViewById(R.id.irMenu);
        puntajes=miDialog.findViewById(R.id.puntajes);

        String zombies=String.valueOf(contador);
        numeroTxt.setText(zombies);//enviamos el valor de cotnador a nuestra pantalla gameover

        seAcaboTxt.setTypeface(typeface);
        hasMatadoTxt.setTypeface(typeface);
        numeroTxt.setTypeface(typeface);

        jugarDeNuevo.setTypeface(typeface);
        irMenu.setTypeface(typeface);
        puntajes.setTypeface(typeface);

        miDialog.show();

        jugarDeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=0;
                miDialog.dismiss();
                tvContador.setText("0");
                gameOver=false;
                cuentaAtras();
                movimiento();

            }
        });

        irMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(escenarioJuego.this,Menu.class));
            }
        });


    }

    //metodo para actualizar puntaje
    private  void guardarResultados(String key, int zombies){//key zombies (el documento en la abse y zombies numero

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put(key,zombies);

        Jugadores.child(user.getUid()).updateChildren(hashMap);//en la referencia jugadores busca al usuario logueado y le pasa el hash

    }


}