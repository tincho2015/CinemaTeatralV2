package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class teatro extends tipoComplejo {


        private ArrayList<Localidad> localidades;
        private ArrayList<obraDeTeatro>obras;


        public teatro(String direccion, int ID, String nombre,String telefono,String url) {
            super(direccion, ID, nombre, telefono,url);
            localidades = new ArrayList<Localidad>();
            obras = new ArrayList<obraDeTeatro>();


        }


}
