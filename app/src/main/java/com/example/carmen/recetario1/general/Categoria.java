package com.example.carmen.recetario1.general;

/**
 * Created by Chrno on 18/11/2015.
 */
public class Categoria {
    private int idCategoria;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.idCategoria = id;
        this.nombre = nombre;
    }

    public Categoria(){

    }

    public int getId() {
        return idCategoria;
    }

    public void setId(int id) {
        this.idCategoria = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
