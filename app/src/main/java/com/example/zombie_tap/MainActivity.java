package com.example.zombie_tap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zombie_tap.ui.theme.Login;
import com.example.zombie_tap.ui.theme.Menu;
import com.example.zombie_tap.ui.theme.Registro;

public class MainActivity extends AppCompatActivity {

    Button BTNLOGIN,BTNREGISTRO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTNLOGIN=findViewById(R.id.BTNLOGIN);
        BTNREGISTRO=findViewById(R.id.BTNREGISTRO);

        //ubicacion de la letra
        String ubicacion="fuentes/zombie.TTF";

        Typeface Tf= Typeface.createFromAsset(MainActivity.this.getAssets(),ubicacion);

        BTNREGISTRO.setTypeface(Tf);
        BTNLOGIN.setTypeface(Tf);

        BTNLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                /*Mensaje si queremos ver algo si se registro, no hay internet etc etc*/
                //Toast.makeText(MainActivity.this, "Has hehco click en el boton login", Toast.LENGTH_SHORT).show();
            }
        });

        BTNREGISTRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Mensaje si queremos ver algo si se registro, no hay internet etc etc*/
               // Toast.makeText(MainActivity.this, "Has hehco click en el boton Registro", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });


    }
}