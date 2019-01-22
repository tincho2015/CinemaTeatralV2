package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Dark Knight on 30/10/2015.
 */
public class sala_cine extends tipoSalas
{
    private ArrayList<pelicula>peliculasSala;

    public sala_cine(String descripcion, int ID,String des_tipo_sala,float precio_sala) {
        super(descripcion,ID,des_tipo_sala,precio_sala);
        peliculasSala = new ArrayList<>();

    }

    public ArrayList<asiento> getAsientos() {
        return super.getAsientos();
    }

    public void setAsientos(ArrayList<asiento> asientos) {
        super.setAsientos(asientos);
    }

    public ArrayList<pelicula> getPeliculasSala() {
        return peliculasSala;
    }

    public void setPeliculasSala(ArrayList<pelicula> peliculasSala) {
        this.peliculasSala = peliculasSala;
    }
    public void agregarAsiento(asiento a){

        if(a != null && !getAsientos().contains(a)){
            getAsientos().add(a);
        }
    }

    public String toString() {
        return super.getDescripcion();
    }

    public void setDescripcion(String descripcion) {
        super.setDescripcion(descripcion);
    }

    public int getID() {
        return super.getID();
    }

    public void setID(int ID) {
        super.setID(ID);
    }

    public void agregarPeliculas(pelicula p){
         if(p != null && !peliculasSala.contains(p))
        peliculasSala.add(p);
    }
    public void eliminarPeliculas(pelicula p){

        if(p!= null && peliculasSala.contains(p)){

            peliculasSala.remove(p);
        }
    }
    public ArrayList darAsientosDisponibles(){

        ArrayList asientosDisponibles = new ArrayList();

        for(asiento a: this.getAsientos()){

            if(a.isOcupado() == 0){

                asientosDisponibles.add(a);
            }
        }

        return asientosDisponibles;
    }
}
