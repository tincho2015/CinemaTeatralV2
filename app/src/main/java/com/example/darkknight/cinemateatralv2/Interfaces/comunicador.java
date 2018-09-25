package com.example.darkknight.cinemateatralv2.Interfaces;

import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;

import java.util.ArrayList;
import java.util.List;

public interface comunicador {

    public void mandarCineAdmin(ArrayList<cine>cines);

    public int darCine(int idcine);

    //public void mandarSala();



}
