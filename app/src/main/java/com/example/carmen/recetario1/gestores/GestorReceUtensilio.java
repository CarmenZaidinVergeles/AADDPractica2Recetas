package com.example.carmen.recetario1.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.basedatos.Contrato;
import com.example.carmen.recetario1.general.RecetarioUtensilio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chrno on 18/11/2015.
 */
public class GestorReceUtensilio {
    private Ayudante abd;
    private SQLiteDatabase bd;



        public GestorReceUtensilio(Ayudante ayudante){
            Log.v("SQLAAD", "constructor del gestor de películas");
            abd = abd;
            this.bd = abd.getWritableDatabase();
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

        public long insert(RecetarioUtensilio recetarioUtensilio) {
            ContentValues valores = new ContentValues();
//            valores.put(Contrato.TablaRecetarioUtensilio._ID, recetarioUtensilio.getIdRU());
            valores.put(Contrato.TablaRecetarioUtensilio.IDRECETARIO,recetarioUtensilio.getIdRecetario());
            valores.put(Contrato.TablaRecetarioUtensilio.IDUTENSILIO,recetarioUtensilio.getIdUtensilio());
            long id = bd.insert(Contrato.TablaRecetarioUtensilio.TABLA, null, valores);
            return id;
        }

        public int delete(RecetarioUtensilio recetarioUtensilio){
            return delete(recetarioUtensilio.getIdRU());
        }

        public int delete(long id){
            String condicion = Contrato.TablaRecetarioUtensilio._ID + " = ?";
            String[] argumentos = { id + "" };
            int cuenta = bd.delete(
                    Contrato.TablaRecetarioUtensilio.TABLA, condicion, argumentos);
            return cuenta;
        }

        public int update(RecetarioUtensilio recetarioUtensilio){
            ContentValues valores = new ContentValues();
            valores.put(Contrato.TablaRecetarioUtensilio._ID, recetarioUtensilio.getIdRU());
            valores.put(Contrato.TablaRecetarioUtensilio.IDRECETARIO, recetarioUtensilio.getIdRecetario());
            String condicion = Contrato.TablaRecetarioUtensilio._ID + " = ?";
            String[] argumentos = { recetarioUtensilio.getIdRU() + "" };
            int cuenta = bd.update(Contrato.TablaRecetarioUtensilio.TABLA, valores,
                    condicion, argumentos);
            return cuenta;
        }

        public RecetarioUtensilio getRow(Cursor c) {
            RecetarioUtensilio recetarioUtensilio = new RecetarioUtensilio();
            recetarioUtensilio.setIdRU(c.getInt(c.getColumnIndex(Contrato.TablaRecetarioUtensilio._ID)));
            recetarioUtensilio.setIdRecetario(c.getInt(c.getColumnIndex(Contrato.TablaRecetarioUtensilio.IDRECETARIO)));
            recetarioUtensilio.setIdUtensilio(c.getInt(c.getColumnIndex(Contrato.TablaRecetarioUtensilio.IDUTENSILIO)));
            return recetarioUtensilio;
        }

        public RecetarioUtensilio getRow(long id) {
            Cursor c = getCursor("_id = ?", new String[]{id+""});
            return getRow(c);
        }

        public Cursor getCursor(){
            return getCursor(null, null);
        }

        public Cursor getCursor(String condicion, String[] parametros) {
            Cursor cursor = bd.query(
                    Contrato.TablaRecetarioUtensilio.TABLA, null, condicion, parametros, null,
                    null, Contrato.TablaRecetarioUtensilio._ID+", "+Contrato.TablaRecetarioUtensilio.IDRECETARIO);
            return cursor;
        }

        public List<RecetarioUtensilio> select(String condicion, String[] parametros) {
            List<RecetarioUtensilio> la = new ArrayList<>();
            Cursor cursor = getCursor(condicion, parametros);
            RecetarioUtensilio RecetarioUtensilio;
            while (cursor.moveToNext()) {
                RecetarioUtensilio = getRow(cursor);
                la.add(RecetarioUtensilio);
            }
            cursor.close();
            return la;
        }

        public List<RecetarioUtensilio> select() {
            return select(null,null);
        }
}