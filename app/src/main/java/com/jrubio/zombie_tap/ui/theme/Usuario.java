package com.jrubio.zombie_tap.ui.theme;

public class Usuario {

    String Edad, Correo, Fecha, Imagen,Nombre,Pais,Uid;
    int zombies;


    public Usuario() {

    }

    public Usuario(String edad, String correo, String fecha, String imagen, String nombre, String pais, String uid, int zombies) {
        Edad = edad;
        Correo = correo;
        Fecha = fecha;
        Imagen = imagen;
        Nombre = nombre;
        Pais = pais;
        Uid = uid;
        this.zombies = zombies;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getZombies() {
        return zombies;
    }

    public void setZombies(int zombies) {
        this.zombies = zombies;
    }
}
