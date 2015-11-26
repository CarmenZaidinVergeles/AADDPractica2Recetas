package com.example.carmen.recetario1.general;

/**
 * Created by Chrno on 18/11/2015.
 */
public class RecetarioUtensilio {
    private int idRU;
    private int idRecetario;
    private int idUtensilio;

    public RecetarioUtensilio(int idRU, int idRecetario, int idUtensilio) {

        this.idRU = idRU;
        this.idRecetario = idRecetario;
        this.idUtensilio = idUtensilio;
    }

    public RecetarioUtensilio(){

    }

    public int getIdRU() {
        return idRU;
    }

    public void setIdRU(int idRU) {
        this.idRU = idRU;
    }

    public int getIdRecetario() {
        return idRecetario;
    }

    public void setIdRecetario(int idRecetario) {
        this.idRecetario = idRecetario;
    }

    public int getIdUtensilio() {
        return idUtensilio;
    }

    public void setIdUtensilio(int idUtensilio) {
        this.idUtensilio = idUtensilio;
    }

    @Override
    public String toString() {
        return "RecetarioUtensilio{" +
                "idRU=" + idRU +
                ", idRecetario=" + idRecetario +
                ", idUtensilio=" + idUtensilio +
                '}';
    }
}
