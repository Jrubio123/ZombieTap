package com.example.zombie_tap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.zombie_tap.ui.theme.Menu;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int DURACION_SPLASH=3000;

        /*Handler Ejecutar en un tiempo determinado*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*CODIGO QUE EJECUTA*/
                Intent intent= new Intent(Splash.this, Menu.class);/*ya luego nos tira al main activity*/
                startActivity(intent);
            }
        },DURACION_SPLASH);
    }
}