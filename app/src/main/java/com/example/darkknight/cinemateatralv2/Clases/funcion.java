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
        private Dia diaFuncion;

        public funcion(int ID,Dia diaF) {

                this.diaFuncion = diaF;
                this.ID = ID;
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
                return java.text.DateFormat.getDateInstance(android.icu.text.DateFormat.DEFAULT,Locale.forLanguageTag("es-ES")).format(diaFuncion);
        }




}
