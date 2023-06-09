package com.pauljordan.cactuapp.models;

import java.io.Serializable;

public class UsuarioModel implements Serializable {
    //Atributos de la clase
    private String uid;
    private String Nombre;
    private String Correo;
    private String Parroquia;
    private String Comunidad;
    private String Telefono;
    private String Rol;

    public UsuarioModel() {
    }

    //get and set Uid
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    //get and set Nombre
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    //get and set Correo
    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    //get and set Parroquia
    public String getParroquia() {
        return Parroquia;
    }

    public void setParroquia(String parroquia) {
        Parroquia = parroquia;
    }

    //get and set Comunidad
    public String getComunidad() {
        return Comunidad;
    }

    public void setComunidad(String comunidad) {
        Comunidad = comunidad;
    }

    //get and set Telefono
    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    //get and set Rol

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

}
