package com.example.darkknight.cinemateatralv2.Clases;

import android.widget.ArrayAdapter;

import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

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
        if(nuevocine != null && !cines.contains(nuevocine))
        cines.add(nuevocine);
    }
    public int darCine(int idcine){

        int i = 0;
        while(i < this.cines.size() && !this.cines.get(i).sosCine(idcine)){
            i++;

        }
        if(i < this.cines.size()){

            return this.cines.get(i).getID();
        }else{

            return 0;
        }


    }
    public void agregarTeatro(teatro nuevoteatro)
    {
        if(nuevoteatro!=null && !teatros.contains(nuevoteatro))
        teatros.add(nuevoteatro);
    }
    public void agregarUsuario(Usuario usuario){

        usuarios.add(usuario);
    }





}
