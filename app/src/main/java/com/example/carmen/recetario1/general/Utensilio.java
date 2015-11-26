package com.example.carmen.recetario1.general;

/**
 * Created by Chrno on 18/11/2015.
 */
public class Utensilio {
    private int idUtensilio;
    private String nombre;

    public Utensilio(int idUtensilio, String nombre) {
        this.idUtensilio = idUtensilio;
        this.nombre = nombre;
    }

    public Utensilio(){

    }

    public int getIdUtensilio() {
        return idUtensilio;
    }

    public void setIdUtensilio(int idUtensilio) {
        this.idUtensilio = idUtensilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Utensilio{" +
                "idUtensilio=" + idUtensilio +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
