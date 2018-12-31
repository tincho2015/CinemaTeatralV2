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
        if(nuevocine != null && !cines.contains(nuevocine)){
            cines.add(nuevocine);
            pedidos.add(PrePedido);

            boolean repetido = false;
            for(int j=0; j<pedidosNoRepetidos.size();j++){
                if(pedidosNoRepetidos.get(j).getIdproducto() == PrePedido.getIdproducto()){
                    //Se encuentra actualmente en ArrayList el elemento.
                    repetido = true;
                    break; //Si encuentra un elemento repetido deja de buscar en el ArrayList.
                }
            }
            if(!repetido){ //Agrega si el elemento no se encuentra repetido en el ArrayList
                pedidosNoRepetidos.add(PrePedido); //Agrega en Arraylist de elementos no repetidos
            }


        }
    }
    public void eliminarCine(cine cine){
        if(cine != null && cines.contains(cine)){
            cines.remove(cine);
        }
    }
    public ArrayList darCines(){

            return cines;
    }
    public void agregarTeatro(teatro nuevoteatro)
    {
        if(nuevoteatro!=null && !teatros.contains(nuevoteatro))
        teatros.add(nuevoteatro);
    }
    public void agregarUsuario(Usuario usuario){

        usuarios.add(usuario);
    }

    public void agregarSalaCine(cine c, sala_cine sc)
    {
        if(c != null && sc != null){
            if(cines.contains(c)){

                c.agregarSala(sc);
            }
        }

    }
    public void eliminarSalaCine(cine c,sala_cine sc){

        if(c != null && sc != null){
            if(cines.contains(c)){
                c.eliminarSala(sc);
            }
        }
    }
    public ArrayList darSalas(cine c){

        return c.getSala_cines();

    }
    public ArrayList darFunciones(cine c,sala_cine sc,pelicula peli){

        if(c != null && sc != null && peli != null){

            if(c.getSala_cines().contains(sc)){

                if(sc.getPeliculasSala().contains(peli)){

                    return peli.getFunciones();
                }
            }
        }
        return null;
    }
    public ArrayList darPelis(sala_cine sc){

        return sc.getPeliculasSala();
    }
    public void agregarPeliculasSala(cine c,sala_cine sc, pelicula nuevapeli){

        if(sc != null && nuevapeli != null){

            if(c.getSala_cines().contains(sc)){

                sc.agregarPeliculas(nuevapeli);
            }
        }
    }

    public void eliminarPeliculaSala(sala_cine sc, pelicula peli){

        if(sc != null && peli != null){

            if(cines.contains(sc)){

                sc.eliminarPeliculas(peli);
            }
        }
    }


}
