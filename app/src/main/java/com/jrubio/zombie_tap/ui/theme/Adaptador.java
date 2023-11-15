package com.jrubio.zombie_tap.ui.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.jrubio.zombietap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.myHolder> {


    private Context context;
    private List<Usuario> usuarioList;


    public Adaptador(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @androidx.annotation.NonNull
    @Override
    public myHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        //poder inflar el diseno
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull myHolder holder, int i) {

        /*Obtenemos los datos del modelo usuario*/
        String Imagen=usuarioList.get(i).getImagen();
        String Nombre=usuarioList.get(i).getNombre();
        String Edad=usuarioList.get(i).getEdad();
        String Pais=usuarioList.get(i).getPais();
        int Zombies=usuarioList.get(i).getZombies();

        String Z=String.valueOf(Zombies);


        //datos
        holder.nombreJugador.setText(Nombre);
        holder.edadJugador.setText(Edad);
        holder.paisJugador.setText(Pais);
        holder.puntajeJugador.setText(Z);

        //imagen del jugador
        try{
            //si el usuairo tiene foto de perfil la tra
            Picasso.get().load(Imagen).into(holder.imagenJugador);

        }catch (Exception e){
            //si no la normalita de toda la life
            //Picasso.get().load(R.drawable.soldado).into(holder.imagenJugador);

        }
    }
    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder{

        //inicializamos las variables que tenemos en el diseno de puntajes

        CircleImageView imagenJugador;
        TextView nombreJugador,paisJugador,puntajeJugador,edadJugador;


        public myHolder(@NonNull View itemView){
            super  (itemView);
            //inicializamos

            imagenJugador= itemView.findViewById(R.id.imagenJugador);
            nombreJugador= itemView.findViewById(R.id.nombreJugador);
            paisJugador= itemView.findViewById(R.id.paisJugador);
            puntajeJugador= itemView.findViewById(R.id.puntajeJugador);
            edadJugador= itemView.findViewById(R.id.edadJugador);


        }
    }

}
