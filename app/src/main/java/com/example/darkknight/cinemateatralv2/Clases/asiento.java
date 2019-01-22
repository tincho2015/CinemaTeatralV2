package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class asiento {


        private int ID;
        private int ocupado;
        private String columna;
        private int fila;

        public String getColumna() {
                return columna;
        }

        public void setColumna(String columna) {
                this.columna = columna;
        }
        public String toString(){

                return getColumna()+getFila();
        }

        public int getFila() {
                return fila;
        }

        public void setFila(int fila) {
                this.fila = fila;
        }

        public asiento(int ID,int fila,String columna,int ocupado) {
                this.ID = ID;
                this.ocupado = ocupado;
                this.fila = fila;
                this.columna = columna;
        }

        public int getID() {
                return ID;
        }

        public void setID(int ID) {
                this.ID = ID;
        }

        public int isOcupado() {
                return ocupado;
        }

        public void setOcupado(int ocupado) {
                this.ocupado = ocupado;
        }
}
