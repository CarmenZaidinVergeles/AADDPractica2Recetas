package com.example.carmen.recetario1.general;

/**
 * Created by Carmen on 12/11/2015.
 */
public class Ingrediente {
    private int idIngrediente;
    private String nombre;

    public Ingrediente(int idIngrediente, String nombre) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
    }

    public Ingrediente(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "idIngrediente=" + idIngrediente +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
