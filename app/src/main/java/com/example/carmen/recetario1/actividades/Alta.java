package com.example.carmen.recetario1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmen.recetario1.R;
import com.example.carmen.recetario1.basedatos.Ayudante;
import com.example.carmen.recetario1.general.Ingrediente;
import com.example.carmen.recetario1.general.RecetaIngrediente;
import com.example.carmen.recetario1.general.Recetario;
import com.example.carmen.recetario1.gestores.GestorIngrediente;
import com.example.carmen.recetario1.gestores.GestorRI;
import com.example.carmen.recetario1.gestores.GestorRecetario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Alta extends Activity {

    private GestorIngrediente gi;
    private GestorRecetario gr;
    private GestorRI gRI;
    private Ayudante ayudante;
    //private List<Ingrediente> listaIngredientes=new ArrayList<>();
    private List<EditText> arrayEditIngred=new ArrayList<>();
    private List<EditText> arrayCantidad=new ArrayList<>();
    private ImageView iv;
    private LinearLayout lIngrediente,lCantidad;
    private TextView tvNombre, tvInstrucciones;
    private String rutaFoto="fallo";
    private EditText ed, ed2;

    //**********************************************************************************

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);
        inicio();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alta, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }
    //*************************************************************************************
    public void inicio(){ //Declaracion de todos los elementos de la actividad
        Toast.makeText(this,"Pulsa (+) para introducir uno o mas ingredientes",Toast.LENGTH_LONG).show();
        lIngrediente= (LinearLayout) findViewById(R.id.lIngrediente);
        lCantidad= (LinearLayout) findViewById(R.id.lCantidad);
        iv=(ImageView) findViewById(R.id.ivFoto);
        ayudante= new Ayudante(this);
        gr = new GestorRecetario(ayudante);
        gi=new GestorIngrediente(ayudante);
        gRI=new GestorRI(ayudante);
        tvNombre = (EditText)findViewById(R.id.tvAltaNombre);
        tvInstrucciones =(EditText)findViewById(R.id.tvAltaInstrucciones);

    }
    //************************************************************************************************
    //Programación del BOTON AÑADIR
    public void añadir(View v){
        String nombre = tvNombre.getText().toString();
        String intrucciones=tvInstrucciones.getText().toString();
        Recetario r = new Recetario();//Creamos un objeto recetario para posteriormente guardar los datos introducidos en él

        if(nombre.isEmpty() || intrucciones.isEmpty()) {//Se filtran si hay campos vacíos
            tostada("no dejes el nombre o las instrucciones en blanco");
        }else {
            r.setNombre(tvNombre.getText().toString());
            r.setInstrucciones(tvInstrucciones.getText().toString());
            if(rutaFoto.equals("fallo")) {
                rutaFoto = "" + R.mipmap.ic_launcher;
                r.setFoto(rutaFoto);
            }else{
                r.setFoto(rutaFoto);
            }

            int idReceta = (int) gr.insert(r);//Insertamos la receta en la base de datos(nombre, foto, instrucciones), lo q nos devuelve su id

            //A continuación nos ocupamos de los INGREDIENTES (id, nombre)
            //listaIngredientes=gi.select();//Seleccion de los ingredientes que tenemos en la base de datos
            GestorRI gRI = new GestorRI(ayudante);
            RecetaIngrediente rI;
            Ingrediente aux;
            int idIngrediente;

            List<Ingrediente> arrayIngrediente;//Creamos un array de ingredientes
            int cantidad = -1;
            String nombreIng;
            for (int i = 0; i < arrayEditIngred.size(); i++) {
                nombreIng=arrayEditIngred.get(i).getText().toString();
                if(nombreIng.isEmpty())
                    tostada("No dejes el nombre del ingrediente en blanco");
                else{
                    String condicion = " nombreIngrediente = '" + nombreIng + "'";//Objetivo: comprobar si el ingrediente introducido está o no en la BBDD
                    arrayIngrediente = gi.select(condicion, null);

                    if (arrayIngrediente.size() > 0) {// Si el tamaño del array es mayor q cero significa q el ingrediente sí está en la BBDD
                        idIngrediente = arrayIngrediente.get(0).getIdIngrediente();
                        String cant=arrayCantidad.get(i).getText().toString();
                        if(cant.isEmpty()) {
                            tostada("No dejes la cantidad enblanco");
                        }else{
                            cantidad = Integer.parseInt(cant);
                            rI = new RecetaIngrediente(idReceta, idIngrediente, cantidad); //introducimos también su cantidad
                            gRI.insert(rI);
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    } else if (arrayIngrediente.size() == 0) {//Si el tamaño del array es igual a cero significa que el ingrediente  no está en la BBDD, por lo q habrá que introducirlo
                        Ingrediente nuevoIngrediente = new Ingrediente();
                        nombreIng=arrayEditIngred.get(i).getText().toString();
                        if(nombreIng.isEmpty()) {
                            tostada("No dejes el nombre de ingrediente vacio");
                        }else{
                            nuevoIngrediente.setNombre(nombreIng);
                            idIngrediente = (int) gi.insert(nuevoIngrediente);
//
                            String cadCant=arrayCantidad.get(i).getText().toString();
                            if(cadCant.isEmpty()) {
                                tostada("No dejes la cantidad vacia");
                            }else{
                                cantidad = Integer.parseInt(cadCant);
                                rI = new RecetaIngrediente(idReceta, idIngrediente, cantidad); //introducimos también su cantidad
                                gRI.insert(rI);
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        }
                    }
                }
            }
        }
    }

    //Programaciion del boton +
    public void plus(View v){
        ed=new EditText(this);
        ed2=new EditText(this);
        ed2.setInputType(2);
        ed.setHint("Ingrediente");
        ed2.setHint("Cantidad");

        arrayEditIngred.add(ed);
        arrayCantidad.add(ed2);
        ViewGroup.LayoutParams parametros
                =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lIngrediente.addView(ed, parametros);
        lCantidad.addView(ed2, parametros);
    }

    //Progrmación del botón foto
    public void foto(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    rutaFoto=filePath;

                    File imgFile = new  File(filePath);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv.setImageBitmap(myBitmap);
                    }
                }
        }
    }

    //"Tostada"
    public void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
