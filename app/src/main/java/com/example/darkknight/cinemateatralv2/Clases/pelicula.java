package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class pelicula extends tipoEspectaculo{


        private String actoresPrincipales;
        private String clasificacion;
        private String director;


        public pelicula(int ID, int duracion,String genero, String nombre, String sinopsis,String actoresPrincipales, String clasificacion, String director) {
            super(ID, duracion,genero, nombre, sinopsis,clasificacion);
            this.actoresPrincipales = actoresPrincipales;
            this.clasificacion = clasificacion;
            this.director = director;
        }

        public String getActoresPrincipales() {
            return actoresPrincipales;
        }

        public void setActoresPrincipales(String actoresPrincipales) {
            this.actoresPrincipales = actoresPrincipales;
        }

        public String getClasificacion() {
            return clasificacion;
        }

        public void setClasificacion(String clasificacion) {
            this.clasificacion = clasificacion;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String toString(){

            return super.getNombre();
        }
        public int getID(){
            return super.getID();
        }

}
