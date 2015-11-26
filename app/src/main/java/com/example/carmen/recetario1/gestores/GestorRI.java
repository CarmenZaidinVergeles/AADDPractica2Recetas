package com.example.carmen.recetario1.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.basedatos.Contrato;
import com.example.carmen.recetario1.general.RecetaIngrediente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen on 17/11/2015.
 */
public class GestorRI {
    private Ayudante abd;
    private SQLiteDatabase bd;
    private String query="SELECT * FROM RECETAINGREDIENTE INNER JOIN " +
            "INGREDIENTES ON RECETAINGREDIENTE.IDINGREDIENTE = " +
            "INGREDIENTES._ID WHERE " + Contrato.TablaRecetaIngrediente.IDRECETA +" = ";

    public GestorRI(Ayudante ayudante){
        Log.v("SQLAAD", "constructor del gestor de películas");
        this.abd=ayudante;
        bd=ayudante.getWritableDatabase();
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(RecetaIngrediente recetaIngrediente) {
        ContentValues valores = new ContentValues();
//        valores.put(Contrato.TablaRecetaIngrediente.IDRI, recetaIngrediente.getIdRI());
        valores.put(Contrato.TablaRecetaIngrediente.IDRECETA, recetaIngrediente.getIdReceta());
        valores.put(Contrato.TablaRecetaIngrediente.IDINGREDIENTE, recetaIngrediente.getIdIngrediente());
        valores.put(Contrato.TablaRecetaIngrediente.CANTIDAD, recetaIngrediente.getCantidad());
        long id = bd.insert(Contrato.TablaRecetaIngrediente.TABLA, null, valores);
        return id;
    }

    public int delete(RecetaIngrediente recetaIngrediente){
        return delete(recetaIngrediente.getIdRI());
    }

    public int delete(long id){
        String condicion = Contrato.TablaRecetaIngrediente._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaRecetaIngrediente.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(RecetaIngrediente recetaIngrediente){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngrediente._ID, recetaIngrediente.getIdRI());
        valores.put(Contrato.TablaRecetaIngrediente.IDRECETA, recetaIngrediente.getIdReceta());
        valores.put(Contrato.TablaRecetaIngrediente.IDINGREDIENTE, recetaIngrediente.getIdIngrediente());
        valores.put(Contrato.TablaRecetaIngrediente.IDRECETA, recetaIngrediente.getIdReceta());
        String condicion = Contrato.TablaRecetaIngrediente._ID + " = ?";
        String[] argumentos = { recetaIngrediente.getIdRI() + "" };
        int cuenta = bd.update(Contrato.TablaRecetaIngrediente.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public RecetaIngrediente getRow(Cursor c) {
        RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
        recetaIngrediente.setIdRI(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente._ID)));
        recetaIngrediente.setIdReceta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDRECETA)));
        recetaIngrediente.setIdIngrediente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDINGREDIENTE)));
        recetaIngrediente.setCantidad(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.CANTIDAD)));
        return recetaIngrediente;
    }

    public RecetaIngrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaRecetaIngrediente.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaRecetaIngrediente._ID+", "+Contrato.TablaRecetaIngrediente.IDINGREDIENTE);
        return cursor;
    }

    public List<RecetaIngrediente> select(String condicion, String[] parametros) {
        List<RecetaIngrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        RecetaIngrediente RecetaIngrediente;
        while (cursor.moveToNext()) {
            RecetaIngrediente = getRow(cursor);
            la.add(RecetaIngrediente);
        }
        cursor.close();
        return la;
    }

    public List<RecetaIngrediente> select() {
        return select(null,null);
    }

    public Cursor getCursorInner(String[] parametros) {
        return bd.rawQuery(query+parametros[0]+";",null);
    }

    public String selectIngredientes(String[] parametros){
        String ingredientes="";
        Cursor cursor=getCursorInner(parametros);
        while (cursor.moveToNext()) {
            ingredientes=ingredientes+""+cursor.getString(cursor.getColumnIndex(Contrato.TablaRecetaIngrediente.CANTIDAD))+
                    " "+cursor.getString(cursor.getColumnIndex(Contrato.TablaIngrediente.NOMBREINGREDIENTE))+"\n";
        }
        return ingredientes;
    }
}