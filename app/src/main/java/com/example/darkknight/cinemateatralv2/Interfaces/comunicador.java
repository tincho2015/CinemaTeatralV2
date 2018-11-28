package com.example.darkknight.cinemateatralv2.Interfaces;

import android.widget.ArrayAdapter;

import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.Clases.sala_teatro;

import java.util.ArrayList;
import java.util.List;

public interface comunicador {

    public void mandarCineAdmin(ArrayList<cine>cines);

    public ArrayList darCines();
    public ArrayList darSalas(cine c);
    public ArrayList darPelis(sala_cine sc);
    public ArrayList darFunciones(cine c,sala_cine sc, pelicula p);

    public void mandarSalasCineAdmin(ArrayList<sala_cine>salasCine,cine c);

    public void eliminarCine(ArrayList<cine>cines);

    public void eliminarSalaCine(ArrayList<sala_cine>salasCine,cine c);

    public void eliminarPeliculasSala(ArrayList<sala_cine>salaCines,pelicula p);

    public void mandarPelisSalaAdmin(ArrayList<pelicula>pelisSala,sala_cine sc,cine c);

   // public void mandarSalasTeatroAdmin(ArrayList<sala_teatro>salasTeatros);

    public void agregarHorariosAdmin(ArrayList<horario>horariosFecha, funcion f);



}
