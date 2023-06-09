package com.pauljordan.cactuapp.models;

import java.io.Serializable;

public class ReporteEventoModel implements Serializable {
    private String Uid;
    private String Nombre;
    private String Correo;
    private String Rol;
    private String Fecha;
    private String Parroquia;
    private String Comunidad;
    private String Telefono;
    private String Imagen;
    private String Evento;
    private String Descripcion;
    private String Ubicacion;

    public ReporteEventoModel() {
    }

    public ReporteEventoModel(String uid, String nombre, String correo,
                              String rol, String fecha, String parroquia,
                              String comunidad, String telefono, String imagen,
                              String evento, String descripcion, String ubicacion) {
        Uid = uid;
        Nombre = nombre;
        Correo = correo;
        Rol = rol;
        Fecha = fecha;
        Parroquia = parroquia;
        Comunidad = comunidad;
        Telefono = telefono;
        Imagen = imagen;
        Evento = evento;
        Descripcion = descripcion;
        Ubicacion = ubicacion;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getParroquia() {
        return Parroquia;
    }

    public void setParroquia(String parroquia) {
        Parroquia = parroquia;
    }

    public String getComunidad() {
        return Comunidad;
    }

    public void setComunidad(String comunidad) {
        Comunidad = comunidad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getEvento() {
        return Evento;
    }

    public void setEvento(String evento) {
        Evento = evento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }


}
