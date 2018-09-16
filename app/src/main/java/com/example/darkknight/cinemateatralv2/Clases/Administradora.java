package com.example.darkknight.cinemateatralv2.Clases;

import android.widget.ArrayAdapter;

import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;

public class Administradora
{
    private ArrayList<cine>cines;
    private ArrayList<promocion>promociones;
    private ArrayList<teatro>teatros;
    private ArrayList<Usuario>usuarios;


    private static Administradora ourInstance = new Administradora();
    public static Administradora getIntance()
    {
        return ourInstance;
    }

    private Administradora()
    {
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();

        //obrasTeatro = new ArrayList<obraDeTeatro>();
        cines = new ArrayList<cine>();
        promociones = new ArrayList<promocion>();
        teatros = new ArrayList<teatro>();
        usuarios = new ArrayList<Usuario>();

    }

    public void agregarCine(cine nuevocine)
    {
        cines.add(nuevocine);
    }
    public void agregarPelicula(cine cine,pelicula nuevapelicula)
    {
        if(cine != null && cines.contains(cine))
        cine.agregarPeliculas(nuevapelicula);
    }
    public void agregarTeatro(teatro nuevoteatro)
    {
        teatros.add(nuevoteatro);
    }
    public void agregarObraDeTeatro(obraDeTeatro nuevaobra)
    {
     obrasTeatro.add(nuevaobra);
    }
    public void agregarUsuario(Usuario usuario){

        usuarios.add(usuario);
    }





}
