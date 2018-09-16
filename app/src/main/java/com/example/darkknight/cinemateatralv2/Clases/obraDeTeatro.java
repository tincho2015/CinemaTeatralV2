package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class obraDeTeatro extends tipoEspectaculo{


        private String director;
        private String elenco;

        public obraDeTeatro(int ID, int duracion,String genero, String nombre, String sinopsis,String director, String elenco,String clasificacion) {
            super(ID, duracion,genero, nombre, sinopsis,clasificacion);
            this.director = director;
            this.elenco = elenco;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getElenco() {
            return elenco;
        }

        public void setElenco(String elenco) {
            this.elenco = elenco;
        }

}
