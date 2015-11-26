package com.example.carmen.recetario1.gestores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.basedatos.Contrato;
import com.example.carmen.recetario1.general.Utensilio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chrno on 18/11/2015.
 */
public class GestorUtensilio{
    private Ayudante abd;
    private SQLiteDatabase bd;

        public GestorUtensilio(Context c){
            Log.v("SQLAAD", "constructor del gestor de películas");
            abd = new Ayudante(c);
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

        public long insert( Utensilio utensilio) {
            ContentValues valores = new ContentValues();
//            valores.put(Contrato.TablaUtensilio.IDUTENSILIO, utensilio.getIdUtensilio());
            valores.put(Contrato.TablaUtensilio.NOMBREUTENSILIO, utensilio.getNombre());
            long id = bd.insert(Contrato.TablaUtensilio.TABLA, null, valores);
            return id;
        }

        public int delete(Utensilio Utensilio){
            return delete(Utensilio.getIdUtensilio());
        }

        public int delete(long id){
            String condicion = Contrato.TablaUtensilio.IDUTENSILIO + " = ?";
            String[] argumentos = { id + "" };
            int cuenta = bd.delete(
                    Contrato.TablaUtensilio.TABLA, condicion, argumentos);
            return cuenta;
        }

        public int update(Utensilio utensilio){
            ContentValues valores = new ContentValues();
            valores.put(Contrato.TablaUtensilio.IDUTENSILIO, utensilio.getIdUtensilio());
            valores.put(Contrato.TablaUtensilio.NOMBREUTENSILIO, utensilio.getNombre());
            String condicion = Contrato.TablaUtensilio.IDUTENSILIO + " = ?";
            String[] argumentos = { utensilio.getIdUtensilio() + "" };
            int cuenta = bd.update(Contrato.TablaUtensilio.TABLA, valores,
                    condicion, argumentos);
            return cuenta;
        }

        public Utensilio getRow(Cursor c) {
            Utensilio utensilio = new Utensilio();
            utensilio.setIdUtensilio(c.getInt(c.getColumnIndex(Contrato.TablaUtensilio.IDUTENSILIO)));
            utensilio.setNombre(c.getString(c.getColumnIndex(Contrato.TablaUtensilio.NOMBREUTENSILIO)));
            return utensilio;
        }

        public Utensilio getRow(long id) {
            Cursor c = getCursor("_id = ?", new String[]{id+""});
            return getRow(c);
        }

        public Cursor getCursor(){
            return getCursor(null, null);
        }

        public Cursor getCursor(String condicion, String[] parametros) {
            Cursor cursor = bd.query(
                    Contrato.TablaUtensilio.TABLA, null, condicion, parametros, null,
                    null, Contrato.TablaUtensilio.IDUTENSILIO+", "+Contrato.TablaUtensilio.NOMBREUTENSILIO);
            return cursor;
        }

        public List<Utensilio> select(String condicion, String[] parametros) {
            List<Utensilio> la = new ArrayList<>();
            Cursor cursor = getCursor(condicion, parametros);
            Utensilio Utensilio;
            while (cursor.moveToNext()) {
                Utensilio = getRow(cursor);
                la.add(Utensilio);
            }
            cursor.close();
            return la;
        }

        public List<Utensilio> select() {
            return select(null,null);
        }
}
