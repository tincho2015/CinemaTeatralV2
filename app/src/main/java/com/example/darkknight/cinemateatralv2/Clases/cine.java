package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class cine extends tipoComplejo
{

        private ArrayList<pelicula> peliculas;
        private ArrayList<sala>salas;

        public cine(String direccion,int id,String nombre,String telefono,String url)
        {
            super(direccion, id, nombre, telefono,url);
            peliculas = new ArrayList<pelicula>();
            salas = new ArrayList<sala>();

        }


    public ArrayList<pelicula> getPeliculas() {
        return peliculas;
    }

    public ArrayList<sala> getSalas() {
        return salas;
    }


    public String getDireccion(){

       return super.getDireccion();
    }
    public int id (){

        return super.getID();
    }
    public String nombre(){

        return super.getNombre();
    }
    public String getTelefono(){

        return super.getTelefono();
    }
    public void agregarPeliculas(pelicula nuevaPeli){

            peliculas.add(nuevaPeli);
    }

}

