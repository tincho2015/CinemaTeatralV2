package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class tipoEspectaculo {
  /**
   * Created by Dark Knight on 30/10/2015.
   */
      private int id;
      private int duracion;
    /*
      private String criticas;
      */
      private boolean estado;
      private String genero;
      private String titulo;
      private String sinopsis;
     // private int puntaje;
      private String director;
      private String clasificacion;
      private ArrayList<funcion>funciones;


    public tipoEspectaculo(int id, int duracion,String genero, String nombre, String sinopsis,String clasificacion) {
      this.id = id;
      this.duracion = duracion;
      this.estado = false;
      this.genero = genero;
      this.titulo = nombre;
      this.sinopsis = sinopsis;
      this.clasificacion = clasificacion;
      funciones = new ArrayList<>();
      //this.puntaje = puntaje;

    }

    public int getID() {
      return id;
    }

    public void setID(int ID) {
      this.id = id;
    }

    public int getDuracion() {
      return duracion;
    }

    public void setDuracion(int duracion) {
      this.duracion = duracion;
    }

  public ArrayList<funcion> getFunciones() {
    return funciones;
  }

  public void setFunciones(ArrayList<funcion> funciones) {
    this.funciones = funciones;
  }

  public boolean isEstado() {
      return estado;
    }

    public void setEstado(boolean estado) {
      this.estado = estado;
    }

    public String getGenero() {
      return genero;
    }

    public void setGenero(String genero) {
      this.genero = genero;
    }

    public String getNombre() {
      return titulo;
    }

    public void setNombre(String nombre) {
      this.titulo = nombre;
    }

    public String getSinopsis() {
      return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
      this.sinopsis = sinopsis;
    }

  //  public int getPuntaje() {
    //  return puntaje;
    //}


  /*public void setPuntaje(int puntaje) {
      this.puntaje = puntaje;
    }
*/
}
