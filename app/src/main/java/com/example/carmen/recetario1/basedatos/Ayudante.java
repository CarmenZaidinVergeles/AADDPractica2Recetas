package com.example.carmen.recetario1.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Carmen on 12/11/2015.
 */
public class Ayudante extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "recetario.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        Log.v("SQLAAD", "constructor del ayudante");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("SQLAAD","on upgrade");

        String sql="drop table if exists "
                + Contrato.TablaRecetario.TABLA;
        db.execSQL(sql);

        String sql2="drop table if exists "
                + Contrato.TablaIngrediente.TABLA;
        db.execSQL(sql2);

        String sql3="drop table if exists "
                + Contrato.TablaRecetaIngrediente.TABLA;
        db.execSQL(sql3);

        String sql4="drop table if exists "
                + Contrato.TablaCategoria.TABLA;
        db.execSQL(sql4);

        String sql5="drop table if exists "
                + Contrato.TablaUtensilio.TABLA;
        db.execSQL(sql5);

        String sql6="drop table if exists "
                + Contrato.TablaRecetarioUtensilio.TABLA;
        db.execSQL(sql6);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//Cuando se baja la aplicación y se crea por primera vez(no hay versión previa de la aplicación)
        Log.v("SQLAAD", "on create");
        String sql;
        sql="create table "+Contrato.TablaRecetario.TABLA+
                " ("+
                Contrato.TablaRecetario._ID + " integer primary key autoincrement, "+
                Contrato.TablaRecetario.NOMBRERECETA+" text, "+
                Contrato.TablaRecetario.FOTO+" text, "+
                Contrato.TablaRecetario.INSTRUCCIONES +" text " + ")";

        Log.v("SQLAAD", sql);
        db.execSQL(sql);

        String sql2;
        sql2="create table "+Contrato.TablaIngrediente.TABLA+
                " ("+
                Contrato.TablaIngrediente._ID + " integer primary key autoincrement, "+
                Contrato.TablaIngrediente.NOMBREINGREDIENTE +" text " + ")";
        db.execSQL(sql2);
        Log.v("SQLAAD", sql2);

        String sql3;
        sql3 = "create table " +Contrato.TablaRecetaIngrediente.TABLA +
                "(" +
                Contrato.TablaRecetaIngrediente._ID + " integer primary key autoincrement, " +
                Contrato.TablaRecetaIngrediente.IDRECETA + " integer, " +
                Contrato.TablaRecetaIngrediente.IDINGREDIENTE + " integer, " +
                Contrato.TablaRecetaIngrediente.CANTIDAD + " text " + ")";

                /*"foreign key (" + Contrato.TablaRecetaIngrediente.IDRECETA +") references " +
                Contrato.TablaRecetario.TABLA + "(" + Contrato.TablaRecetario.IDRECETA +")" +
                "foreign key (" + Contrato.TablaRecetaIngrediente.IDINGREDIENTE +") references " +
                Contrato.TablaIngrediente.TABLA + "(" + Contrato.TablaIngrediente.IDINGREDIENTE + "))";*/
        db.execSQL(sql3);
        Log.v("SQLAAD", sql3);

        String sql4;
        sql4="create table "+Contrato.TablaCategoria.TABLA +
                " ("+
                Contrato.TablaCategoria._ID + " integer primary key autoincrement, "+
                Contrato.TablaCategoria.NOMBRECATEGORIA +" text " + ")";
        db.execSQL(sql4);
        Log.v("SQLAAD", sql4);

        String sql5;
        sql5="create table "+Contrato.TablaUtensilio.TABLA +
                " ("+
                Contrato.TablaUtensilio._ID + " integer primary key autoincrement, "+
                Contrato.TablaUtensilio.NOMBREUTENSILIO +" text " + ")";
        db.execSQL(sql5);
        Log.v("SQLAAD", sql5);

        String sql6;
        sql6="create table "+Contrato.TablaRecetarioUtensilio.TABLA +
                " ("+
                Contrato.TablaRecetarioUtensilio._ID + " integer primary key autoincrement, "+
                Contrato.TablaRecetarioUtensilio.IDRECETARIO +" integer " +
                Contrato.TablaRecetarioUtensilio.IDUTENSILIO + "integer" + ")";
        db.execSQL(sql6);
        Log.v("SQLAAD", sql6);
    }
}