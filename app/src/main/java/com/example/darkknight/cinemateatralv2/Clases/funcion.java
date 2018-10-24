package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class funcion {


        private int dia,mes,año,hora,minutos;
        private int ID;

        public funcion(int dia, int mes, int año, int hora, int minutos, int ID) {
                this.dia = dia;
                this.mes = mes;
                this.año = año;
                this.hora = hora;
                this.minutos = minutos;
                this.ID = ID;
        }

        public int getID() {
                return ID;
        }

        public void setID(int ID) {
                this.ID = ID;
        }

        public String darFecha(){

                return (this.getDia()+"/"+this.getMes()+"/"+this.getAño());
        }

        public int getDia() {
                return dia;
        }

        public void setDia(int dia) {
                this.dia = dia;
        }

        public int getMes() {
                return mes;
        }

        public void setMes(int mes) {
                this.mes = mes;
        }

        public int getAño() {
                return año;
        }

        public void setAño(int año) {
                this.año = año;
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
