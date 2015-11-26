package com.example.carmen.recetario1.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.basedatos.Contrato;
import com.example.carmen.recetario1.general.Ingrediente;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen on 17/11/2015.
 */
public class GestorIngrediente {
    private Ayudante ayudante;
    private SQLiteDatabase bd;

    public GestorIngrediente(Ayudante ayudante){
        this.ayudante = ayudante;
        this.bd = ayudante.getWritableDatabase();
    }
    public void open() {
        bd = ayudante.getWritableDatabase();
    }
    public void openRead() {
        bd = ayudante.getReadableDatabase();
    }
    public void close() {
        ayudante.close();
    }

    public long insert(Ingrediente ingrediente) {
        ContentValues valores = new ContentValues();
//        valores.put(Contrato.TablaIngrediente._ID, ingrediente.getIdIngrediente());
        valores.put(Contrato.TablaIngrediente.NOMBREINGREDIENTE, ingrediente.getNombre());
        long id = bd.insert(Contrato.TablaIngrediente.TABLA, null, valores);
        return id;
    }

    public int delete(Ingrediente ingrediente){
        return delete(ingrediente.getIdIngrediente());
    }

    public int delete(long id){
        String condicion = Contrato.TablaIngrediente._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaIngrediente.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Ingrediente ingrediente){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaIngrediente._ID, ingrediente.getIdIngrediente());
        valores.put(Contrato.TablaIngrediente.NOMBREINGREDIENTE, ingrediente.getNombre());
        String condicion = Contrato.TablaIngrediente._ID + " = ?";
        String[] argumentos = { ingrediente.getIdIngrediente() + "" };
        int cuenta = bd.update(Contrato.TablaIngrediente.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Ingrediente getRow(Cursor c) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setIdIngrediente(c.getInt(c.getColumnIndex(Contrato.TablaIngrediente._ID)));
        ingrediente.setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngrediente.NOMBREINGREDIENTE)));
        return ingrediente;
    }

    public Ingrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaIngrediente.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaIngrediente._ID+", "+Contrato.TablaIngrediente.NOMBREINGREDIENTE);
        return cursor;
    }

    public List<Ingrediente> select(String condicion, String[] parametros) {
        List<Ingrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Ingrediente Ingrediente;
        while (cursor.moveToNext()) {
            Ingrediente = getRow(cursor);
            la.add(Ingrediente);
        }
        cursor.close();
        return la;
    }

    public List<Ingrediente> select() {
        return select(null,null);
    }
}
