package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class horario {
        private int id;
        private int hora,minutos;

        public horario(int id,int hora, int minutos) {

                this.id= id;
                this.hora = hora;
                this.minutos = minutos;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public int getHora() {
                return hora;
        }

        public void setHora(int hora) {
                this.hora = hora;
        }

        public int getMinutos() {
                return minutos;
        }

        public void setMinutos(int minutos) {
                this.minutos = minutos;
        }

}
