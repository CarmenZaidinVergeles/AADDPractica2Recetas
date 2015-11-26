package com.example.carmen.recetario1.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.basedatos.Contrato;
import com.example.carmen.recetario1.general.Recetario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carmen on 12/11/2015.
 */
public class GestorRecetario {

    private Ayudante abd;
    private SQLiteDatabase bd;

        public GestorRecetario(Ayudante ayudante){
            Log.v("SQLAAD", "constructor del gestor  recetario");
            abd = ayudante;
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

        public long insert(Recetario recetario) {
            ContentValues valores = new ContentValues();
//          valores.put(Contrato.TablaRecetario._ID, recetario.getIdReceta());
            valores.put(Contrato.TablaRecetario.NOMBRERECETA, recetario.getNombre());
            valores.put(Contrato.TablaRecetario.FOTO, recetario.getFoto());
            valores.put(Contrato.TablaRecetario.INSTRUCCIONES, recetario.getInstrucciones());

            long id = bd.insert(Contrato.TablaRecetario.TABLA, null, valores);
            return id;
        }

        public int delete(Recetario Recetario){
            return delete(Recetario.getIdReceta());
        }

        public int delete(long id){
            String condicion = Contrato.TablaRecetario._ID + " = ?";
            String[] argumentos = { id + "" };
            int cuenta = bd.delete(
                    Contrato.TablaRecetario.TABLA, condicion, argumentos);
            return cuenta;
        }

        public int update(Recetario recetario){
            ContentValues valores = new ContentValues();
            valores.put(Contrato.TablaRecetario._ID, recetario.getIdReceta());
            valores.put(Contrato.TablaRecetario.NOMBRERECETA, recetario.getNombre());
            valores.put(Contrato.TablaRecetario.FOTO, recetario.getFoto());
            valores.put(Contrato.TablaRecetario.INSTRUCCIONES, recetario.getInstrucciones());
            String condicion = Contrato.TablaRecetario._ID + " = ?";
            String[] argumentos = { recetario.getIdReceta() + "" };
            int cuenta = bd.update(Contrato.TablaRecetario.TABLA, valores,
                    condicion, argumentos);
            return cuenta;
        }

        public Recetario getRow(Cursor c) {
            Recetario recetario = new Recetario();
            recetario.setIdReceta(c.getInt(c.getColumnIndex(Contrato.TablaRecetario._ID)));
            recetario.setNombre(c.getString(c.getColumnIndex(Contrato.TablaRecetario.NOMBRERECETA)));
            recetario.setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaRecetario.INSTRUCCIONES)));
            recetario.setFoto(c.getString(c.getColumnIndex(Contrato.TablaRecetario.FOTO)));

            return recetario;
        }

        public Recetario getRow(long id) {
            Cursor c = getCursor("_id = ?", new String[]{id+""});
            return getRow(c);
        }

        public Cursor getCursor(){
            return getCursor(null, null);
        }

        public Cursor getCursor(String condicion, String[] parametros) {
            Cursor cursor = bd.query(
                    Contrato.TablaRecetario.TABLA, null, condicion, parametros, null,
                    null, Contrato.TablaRecetario._ID+", "+Contrato.TablaRecetario.NOMBRERECETA);
            return cursor;
        }

        public List<Recetario> select(String condicion, String[] parametros) {
            List<Recetario> la = new ArrayList<>();
            Cursor cursor = getCursor(condicion, parametros);
            Recetario Recetario;
            while (cursor.moveToNext()) {
                Recetario = getRow(cursor);
                la.add(Recetario);
            }
            cursor.close();
            return la;
        }

        public List<Recetario> select() {
            return select(null,null);
        }
}
