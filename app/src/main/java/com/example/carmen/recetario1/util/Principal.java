package com.example.carmen.recetario1.util;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.carmen.recetario1.R;
import com.example.carmen.recetario1.actividades.Alta;
import com.example.carmen.recetario1.actividades.ActividadRecetas;
import com.example.carmen.recetario1.actividades.Editar;
import com.example.carmen.recetario1.adaptador.Adaptador;
import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.general.RecetaIngrediente;
import com.example.carmen.recetario1.general.Recetario;
import com.example.carmen.recetario1.gestores.GestorCategoria;
import com.example.carmen.recetario1.gestores.GestorIngrediente;
import com.example.carmen.recetario1.gestores.GestorRI;
import com.example.carmen.recetario1.gestores.GestorRecetario;

import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {
    private Ayudante ayudante;
    private List<Recetario> lista = new ArrayList<>();
    private List<RecetaIngrediente> recetaIngrediente = new ArrayList<>();
    private Adaptador adaptador;
    private ListView lv;
    private final int ALTARECETA = 1;
    private final int EDITARRECETA=2;

    //Base de Datos
    private SQLiteDatabase bd;
    private GestorRecetario gr;
    private GestorRI gRI;
    private GestorIngrediente gi;
    //***********************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        init();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {//creamos nuestro menu contextual
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alta, menu);
    }
    //MENU CONTEXTUAL (BORRAR Y EDITAR)
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo vistainfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int posicion = vistainfo.position;//cogemos la posicion del elemento pulsado en la vista
        switch(item.getItemId()){//acciones que hara nuestro menu contextual
            case R.id.action_borrar:
                int id=lista.get(posicion).getIdReceta();
                gr.delete(id);
                actualiza();
                return true;
            case R.id.action_editar:
                editar(posicion);
                actualiza();
                return true;
            default: return super.onContextItemSelected(item);
        }
    }
    //****************************************************************************************

    public void init() {
        lv = (ListView) findViewById(R.id.listView);
        bbdd();
        generarAdaptador();
    }
    public void bbdd() {
        ayudante = new Ayudante(this);
        gr = new GestorRecetario(ayudante);
        gRI=new GestorRI(ayudante);
        gi=new GestorIngrediente(ayudante);
    }

    public void generarAdaptador(){
        lista = gr.select();
        recetaIngrediente=gRI.select();
        adaptador = new Adaptador(this, R.layout.item, lista);
        lv.setAdapter(adaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idReceta=lista.get(position).getIdReceta();
                Intent i = new Intent(Principal.this, ActividadRecetas.class);
                Bundle b = new Bundle();
                b.putInt("ID",idReceta);
                i.putExtras(b);
                startActivity(i);
            }
        });
        registerForContextMenu(lv);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    //Programación del botón que nos lleva a la actividad ALTA
    public void añadir(View v){
        Intent i = new Intent(this,Alta.class);
        Bundle b=new Bundle();
        startActivityForResult(i, ALTARECETA);
    }

    public void editar(int pos){
        int id=lista.get(pos).getIdReceta();
        Intent i = new Intent(this, Editar.class);
        Bundle b = new Bundle();
        b.putInt("ID",id);
        i.putExtras(b);
        startActivityForResult(i, EDITARRECETA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALTARECETA && resultCode == Activity.RESULT_OK) {
            actualiza();//Actualizar adaptador
        }
        if(requestCode == EDITARRECETA && resultCode == Activity.RESULT_OK){
            actualiza();
        }
    }
    public void actualiza(){
       generarAdaptador();
    }
}
