package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Dark Knight on 30/10/2015.
 */
public class sala
{
    private ArrayList<asiento>asientos;
    private ArrayList<pelicula>peliculasSala;
    private String descripcion;
    private int ID;

    public sala(ArrayList<asiento> asientos, ArrayList<pelicula> peliculasSala, String descripcion, int ID) {
        this.asientos = asientos;
        this.peliculasSala = peliculasSala;
        this.descripcion = descripcion;
        this.ID = ID;
    }

    public ArrayList<asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(ArrayList<asiento> asientos) {
        this.asientos = asientos;
    }

    public ArrayList<pelicula> getPeliculasSala() {
        return peliculasSala;
    }

    public void setPeliculasSala(ArrayList<pelicula> peliculasSala) {
        this.peliculasSala = peliculasSala;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void agregarPeliculas(pelicula p){
         if(p != null && !peliculasSala.contains(p))
        peliculasSala.add(p);
    }
}
