package com.example.carmen.recetario1.actividades;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by Chrno on 23/11/2015.
 */
public class Editar extends Activity {
    private GestorIngrediente gi;
    private GestorRecetario gr;
    private GestorRI gRI;
    private Ayudante ayudante;
    //private List<Ingrediente> listaIngredientes=new ArrayList<>();
    private List<EditText> arrayEditIngred=new ArrayList<>();
    private List<EditText> arrayCantidad=new ArrayList<>();
    private ImageView ivD;
    private LinearLayout lIngrediente,lCantidad;
    private String rutaFoto="fallo";
    private Intent intent;
    private Bundle bundle;
    private Recetario r;
    private int idReceta;
    private EditText nom,instr;
    private ImageView iv;
    private EditText ed, ed2;

    //************************************************************************************
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta);
        nom=(EditText) findViewById(R.id.tvAltaNombre);
        instr=(EditText) findViewById(R.id.tvAltaInstrucciones);
        ivD=(ImageView) findViewById(R.id.ivFoto);
        lIngrediente= (LinearLayout) findViewById(R.id.lIngrediente);
        lCantidad= (LinearLayout) findViewById(R.id.lCantidad);
        iv=(ImageView) findViewById(R.id.ivFoto);
        inicio();
        introducePorDefecto();
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

    //****************************************************************************************
    public void introducePorDefecto(){
        Intent i=getIntent();
        Bundle b=i.getExtras();
        idReceta =b.getInt("ID");
        r=gr.select("_ID="+idReceta,null).get(0);

        nom.setText(r.getNombre());
        instr.setText(r.getInstrucciones());
        File imgFile = new  File(r.getFoto());
        if(imgFile.exists()){
            Bitmap myBitmap =
                    BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivD.setImageBitmap(myBitmap);
        }
    }
    public void inicio(){
        intent = new Intent();
        bundle = new Bundle();
        Toast.makeText(this, "Pulsa (+) para introducir uno o mas ingredientes", Toast.LENGTH_SHORT).show();
        ayudante= new Ayudante(this);
        gr = new GestorRecetario(ayudante);
        gi=new GestorIngrediente(ayudante);
        gRI=new GestorRI(ayudante);
        //listaIngredientes=gi.select();//Seleccion de los ingredientes que tenemos en la base de datos
    }
    public void tostada(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    public void añadir(View v){
        String nombre=nom.getText().toString();
        String intrucciones=instr.getText().toString();

        if(nombre.isEmpty() || intrucciones.isEmpty()) {
            tostada("no dejes el nombre o las instrucciones en blanco");
        }else {
            r.setNombre(nom.getText().toString());
            r.setInstrucciones(instr.getText().toString());
            r.setFoto(rutaFoto);
            gr.update(r);

            GestorRI gRI = new GestorRI(ayudante);
            RecetaIngrediente rI;
            Ingrediente aux;
            int idIngrediente;
            List<Ingrediente> arrayIngrediente;
            int cantidad = -1;
            String nombreIng;
            for (int i = 0; i < arrayEditIngred.size(); i++) {
                nombreIng=arrayEditIngred.get(i).getText().toString();
                if(nombreIng.isEmpty()) {
                    tostada("No dejes el nombre del ingrediente en blanco");
                }else{
                    String condicion = " nombreIngrediente = '" + nombreIng + "'";
                    arrayIngrediente = gi.select(condicion, null);

                    if (arrayIngrediente.size() > 0) {
                        idIngrediente = arrayIngrediente.get(i).getIdIngrediente();
                        String cant=arrayCantidad.get(i).getText().toString();
                        if(cant.isEmpty()) {
                            tostada("No dejes la cantidad enblanco");
                        }else{
                            cantidad = Integer.parseInt(cant);
                            rI = new RecetaIngrediente(r.getIdReceta(), idIngrediente, cantidad);
                            actualizaRecetaIngr(rI);
                            r.setFoto(rutaFoto);

                            gRI.update(rI);
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    } else if (arrayIngrediente.size() == 0) {
                        aux = new Ingrediente();
                        nombreIng=arrayEditIngred.get(i).getText().toString();
                        if(nombreIng.isEmpty()) {
                            tostada("No dejes el nombre de ingrediente vacio");
                        }else{
                            aux.setNombre(nombreIng);
                            idIngrediente=(int)gi.insert(aux);

                            String cadCant=arrayCantidad.get(i).getText().toString();
                            if(cadCant.isEmpty()) {
                                tostada("No dejes la cantidad vacia");
                            }else{
                                cantidad = Integer.parseInt(cadCant);
                                rI = new RecetaIngrediente(r.getIdReceta(), idIngrediente, cantidad);
                                actualizaRecetaIngr(rI);
                                gRI.update(rI);
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        }
                    }
                }
            }
        }
    }

    public void actualizaRecetaIngr(RecetaIngrediente ri){
        gRI.update(ri);
    }

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

    public void foto(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
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
}