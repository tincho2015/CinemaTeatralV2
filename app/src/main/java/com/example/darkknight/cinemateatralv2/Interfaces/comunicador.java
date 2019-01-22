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

    void mandarCineAdmin(ArrayList<cine>cines);

   ArrayList darCines();
   ArrayList darSalas(cine c);
   ArrayList darPelis(sala_cine sc);
   ArrayList darPelisTotal(cine c);
   ArrayList darFunciones(cine c,sala_cine sc, pelicula p);
   ArrayList darFuncionesPelicula(pelicula p);
   ArrayList darHorariosPorFuncion(funcion f,Dia d);
   cine darCineReserva(int cineId);
   pelicula darPeliReserva(int peliId);
   funcion darFuncionReserva(int funcionId);
   horario darHorarioReserva(int horarioId);
   cine darCine(int cineId);
   sala_cine darSalaCine(int cineId,int salaId);

   void mandarSalasCineAdmin(ArrayList<sala_cine>salasCine,cine c);

   void eliminarCine(ArrayList<cine>cines);

   void eliminarSalaCine(ArrayList<sala_cine>salasCine,cine c);

   void eliminarPeliculasSala(ArrayList<sala_cine>salaCines,pelicula p);

   void mandarPelisSalaAdmin(ArrayList<pelicula>pelisSala,sala_cine sc,cine c);

   // public void mandarSalasTeatroAdmin(ArrayList<sala_teatro>salasTeatros);
   void mandarFuncionAdmin(cine c, sala_cine sc, pelicula p,ArrayList<funcion>funciones);

   void mandarFuncionHorarioAdmin(cine c, sala_cine sc, pelicula p,funcion f,ArrayList<horario>horarios);

   void mandarAsientosSalaAdmin(ArrayList<asiento>asientos,sala_cine sc,cine c);



}
