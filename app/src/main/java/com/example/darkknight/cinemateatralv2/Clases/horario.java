package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class horario {


        private int hora,minutos;

        public horario(int hora, int minutos) {
                this.hora = hora;
                this.minutos = minutos;
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
