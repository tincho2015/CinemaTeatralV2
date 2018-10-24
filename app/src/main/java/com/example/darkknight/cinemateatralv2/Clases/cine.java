package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class cine extends tipoComplejo
{



        public cine(String direccion,int id,String nombre,String telefono,String url)
        {
            super(direccion, id, nombre, telefono,url);

        }



    public ArrayList<sala_cine> getSala_cines() {
       return super.getSala_cines();
    }


    public String getDireccion(){

       return super.getDireccion();
    }
    public int id (){

        return super.getID();
    }
    public String toString(){

        return super.getNombre();
    }
    public String getTelefono(){

        return super.getTelefono();
    }
    public void agregarSala(sala_cine s){

            if(s != null && !super.getSala_cines().contains(s)){

                super.getSala_cines().add(s);
            }
    }
    public void eliminarSala(sala_cine s){

            if(s != null && super.getSala_cines().contains(s)){

                super.getSala_cines().remove(s);
            }
    }
    public boolean sosCine(int id){

            if(this.getID() == id){
                return true;
            }
            else{
                return false;
            }
    }

}

