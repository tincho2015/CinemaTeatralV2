package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class asiento {


        private int ID;
        private boolean ocupado;
        private int columna;
        private String fila;

        public int getColumna() {
                return columna;
        }

        public void setColumna(int columna) {
                this.columna = columna;
        }

        public String getFila() {
                return fila;
        }

        public void setFila(String fila) {
                this.fila = fila;
        }

        public asiento(int ID, boolean ocupado,int columna,String fila) {
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

        public boolean isOcupado() {
                return ocupado;
        }

        public void setOcupado(boolean ocupado) {
                this.ocupado = ocupado;
        }
}
