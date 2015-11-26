package com.example.carmen.recetario1.general;

import java.io.Serializable;

/**
 * Created by Carmen on 12/11/2015.
 */
public class Recetario implements Serializable{
    private int idReceta;
    private String nombre;
    private String foto;
    private String instrucciones;

    public Recetario(/*int idReceta,*/ String nombre, String foto, String instrucciones) {
//        this.idReceta = idReceta;
        this.nombre = nombre;
        this.foto = foto;
        this.instrucciones = instrucciones;
    }

    public Recetario(){

    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    @Override
    public String toString() {
        return "Recetario{" +
                "idReceta=" + idReceta +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                '}';
    }
}
