package com.example.darkknight.cinemateatralv2.Interfaces;

import android.widget.ArrayAdapter;

import com.example.darkknight.cinemateatralv2.Clases.Dia;
import com.example.darkknight.cinemateatralv2.Clases.asiento;
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
    public ArrayList darPelisTotal(cine c);
    public ArrayList darFunciones(cine c,sala_cine sc, pelicula p);
    public ArrayList darFuncionesPelicula(pelicula p);
    public ArrayList darHorariosPorFuncion(funcion f,Dia d);
    public cine darCineReserva(int cineId);
    public pelicula darPeliReserva(int peliId);
    public funcion darFuncionReserva(int funcionId);
    public horario darHorarioReserva(int horarioId);
    cine darCine(int cineId);
    sala_cine darSalaCine(int cineId,int salaId);

    public void mandarSalasCineAdmin(ArrayList<sala_cine>salasCine,cine c);

    public void eliminarCine(ArrayList<cine>cines);

    public void eliminarSalaCine(ArrayList<sala_cine>salasCine,cine c);

    public void eliminarPeliculasSala(ArrayList<sala_cine>salaCines,pelicula p);

    public void mandarPelisSalaAdmin(ArrayList<pelicula>pelisSala,sala_cine sc,cine c);

   // public void mandarSalasTeatroAdmin(ArrayList<sala_teatro>salasTeatros);

    public void mandarFuncionAdmin(cine c, sala_cine sc, pelicula p,ArrayList<funcion>funciones);

    public void mandarFuncionHorarioAdmin(cine c, sala_cine sc, pelicula p,funcion f,ArrayList<horario>horarios);

    void mandarAsientosSalaAdmin(ArrayList<asiento>asientos,sala_cine sc,cine c);



}
