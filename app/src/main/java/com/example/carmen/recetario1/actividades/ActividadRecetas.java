package com.example.carmen.recetario1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmen.recetario1.R;
import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.general.RecetaIngrediente;
import com.example.carmen.recetario1.general.Recetario;
import com.example.carmen.recetario1.gestores.GestorIngrediente;
import com.example.carmen.recetario1.gestores.GestorRI;
import com.example.carmen.recetario1.gestores.GestorRecetario;

import java.io.File;
import java.util.List;

public class ActividadRecetas extends Activity {

    private Recetario r;
    private String ingredientes;
    private Ayudante ayudante;
    private GestorIngrediente gi;
    private GestorRecetario gr;
    private GestorRI gri;
    private int id;

    //********************************************************************************************
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recetacompleta);
        inicio();
    }

    static class ViewHolder{
        public TextView tv1, tv2, tv3;
        public ImageView iv;
    }

    public void inicio(){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id=b.getInt("ID");

        ayudante=new Ayudante(this);
        gi=new GestorIngrediente(ayudante);
        gr=new GestorRecetario(ayudante);
        gri=new GestorRI(ayudante);

        r = gr.select(" _ID=" + id,null).get(0);
        ingredientes = gri.selectIngredientes(new String[] {"" + id});

        ViewHolder gv = new ViewHolder();

        TextView tv = (TextView)findViewById(R.id.tvNombreReceta);
        gv.tv1= tv;
        tv= (TextView)findViewById(R.id.tvInstrucciones);
        gv.tv2=tv;
        tv= (TextView)findViewById(R.id.tvIngredientes);
        gv.tv3 = tv;
        ImageView iv = (ImageView)findViewById(R.id.ivReceta);
        gv.iv = iv;

        gv.tv1.setText("Receta: " + r.getNombre());
        gv.tv2.setText("Ingredientes:\n " + ingredientes);
        gv.tv3.setText("Instrucciones:\n " + r.getInstrucciones());


        File imgFile = new File(r.getFoto());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            gv.iv.setImageBitmap(myBitmap);
        }
    }
}
