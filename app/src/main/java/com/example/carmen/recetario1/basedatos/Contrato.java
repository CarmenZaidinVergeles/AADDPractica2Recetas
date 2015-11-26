package com.example.carmen.recetario1.basedatos;

import android.provider.BaseColumns;

/**
 * Created by Carmen on 12/11/2015.
 */
public class Contrato {
    private Contrato() {
    }

    public static abstract class TablaRecetario implements BaseColumns {
        public static final String TABLA = "recetario";
//        public static final String IDRECETA = "idReceta";
        public static final String NOMBRERECETA = "nombreReceta";
        public static final String FOTO = "foto";
        public static final String INSTRUCCIONES = "instrucciones";
    }

    public static abstract class TablaIngrediente implements BaseColumns {
        public static final String TABLA = "ingredientes";
//        public static final String _ID = "idIngrediente";
        public static final String NOMBREINGREDIENTE = "nombreIngrediente";
    }

    public static abstract class TablaRecetaIngrediente implements BaseColumns {
        public static final String TABLA = "recetaIngrediente";
//        public static final String IDRI = "idRI";
        public static final String IDRECETA = "idReceta";
        public static final String IDINGREDIENTE = "idIngrediente";
        public static final String CANTIDAD = "cantidad";
    }

    public static abstract class TablaCategoria implements BaseColumns {
        public static final String TABLA = "categoria";
//        public static final String IDCATEGORIA = "idCategoria";
        public static final String NOMBRECATEGORIA= "nombreCategoria";
    }

    public static abstract class TablaUtensilio implements BaseColumns {
        public static final String TABLA = "utensilio";
        public static final String IDUTENSILIO= "idUtensilio";
        public static final String NOMBREUTENSILIO= "nombreUtensilio";
    }

    public static abstract class TablaRecetarioUtensilio implements BaseColumns {
        public static final String TABLA = "recetarioUtensilio";
//        public static final String IDREUT= "idReUt";
        public static final String IDRECETARIO= "idRecetario";
        public static final String IDUTENSILIO= "idUtensilio";

    }





}
