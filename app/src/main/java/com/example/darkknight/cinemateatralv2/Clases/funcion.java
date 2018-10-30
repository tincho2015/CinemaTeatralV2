package com.example.darkknight.cinemateatralv2.Clases;

import android.text.format.DateFormat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class funcion {


        //private int dia,mes,año,hora,minutos;
        private int ID;
        private Date fecha;
        private Date hora;

        public funcion(Date fecha,Date hora,int ID) {

                this.hora = hora;
                this.ID = ID;
                this.fecha = fecha;

        }

        public int getID() {
                return ID;
        }

        public void setID(int ID) {
                this.ID = ID;
        }

        /*
        public String toString(){

                return (this.dia +"/"+this.mes+"/"+this.año);
        }
       */
        public String toString(){
                return java.text.DateFormat.getDateInstance(android.icu.text.DateFormat.DEFAULT,Locale.forLanguageTag("es-ES")).format(fecha);
        }
        public String darHora(){

                return hora.toString();
        }
        /*
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
*/
        public Date getHora() {
                return hora;
        }

        public void setHora(Time hora) {
                this.hora = hora;
        }
}
