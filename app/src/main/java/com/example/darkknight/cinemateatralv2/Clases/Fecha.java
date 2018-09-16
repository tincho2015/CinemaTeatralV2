package com.example.darkknight.cinemateatralv2.Clases;
import java.util.Calendar;
/**
 * Created by Dark Knight on 07/11/2015.
 */
public class Fecha
{
        private int dia;
        private int mes;
        private int año;

        public static Fecha hoy(){
            Fecha f = new Fecha();
            Calendar c=Calendar.getInstance();
            f.setDia(c.get(Calendar.DAY_OF_MONTH));
            f.setMes(c.get(Calendar.MONTH));//+1);
            f.setAño(c.get(Calendar.YEAR));
            return f;
        }

        public static boolean bisiesto(int a){
            if(a % 4 == 0){
                if( a % 100 == 0)
                    if(a % 1000 == 0)
                        return true;
                    else
                        return false;
                else

                    return true;
            }

            else
                return false;
        }

        public Fecha(){
            dia=mes=año=0;
        }

        public Fecha(int d, int m, int a){
            //si el dia o el mes son incorrectos deberia lanzar un excepcion
            int dias[]= {31,28,31,30,31,30,31,31,30,31,31,31};
            int fin = 0;
            if(m >= 1 && m <= 12){
                fin = dias[m-1];
                if(m == 2 && bisiesto(a)){
                    fin = 29;
                }
                if(d >= 1 && d <= fin){
                    dia = d;
                    mes = m;
                    año = a;
                }
            }
        }
        public boolean equals(Fecha f){
            return año == f.getAño() && mes == f.getMes() && dia == f.getDia();
        }

        public int compareTo(Fecha f){
            //< 0 fecha receptor anterior a f
            //== 0 fecha receptor igual a f
            //>0 fecha receptor posterior a f
            int f1 = año * 10000 + mes * 100 + dia;
            int f2 = f.getAño() * 10000 + f.getMes()* 100 + f.getDia();
            return f1 - f2;
        }
        public int diferenciaDias() // Devuelve la diferencia en dias respecto de hoy
        {
            Calendar hoy = Calendar.getInstance(); // Obtengo hoy
            Calendar fecha = Calendar.getInstance();	// Genero un calendar a partir de la fecha de la instancia
            fecha.set(año, mes, dia);
            int diferencia = (int)(hoy.getTimeInMillis() - fecha.getTimeInMillis())/24/60/60/1000;
            return ( diferencia);
        }
        public int diferenciaDias(Fecha f2) // Devuelve la diferencia en dia entre dos fechas dadas
        {
            Calendar fecha1 = Calendar.getInstance(); // Genero un calendar a partir de la fecha "f1"
            Calendar fecha2 = Calendar.getInstance();	// Genero un calendar a partir de la fecha "f2"
            fecha1.set(año,mes,dia);
            fecha2.set(f2.año, f2.mes, f2.dia);
            int diferencia = (int)(fecha1.getTimeInMillis() - fecha2.getTimeInMillis())/24/60/60/1000;
            return ( diferencia);
        }

        public boolean bisiesto(){
            return Fecha.bisiesto(año);
        }

        public Fecha sumarDias(int d){
            int dias[]= {31,28,31,30,31,30,31,31,30,31,31,31};
            Fecha f = new Fecha();
            int d2,m2,a2;
            d2=dia;
            m2=mes;
            a2=año;
            int fin, restan;
            do{

                fin = dias[m2-1];
                if(m2 == 2 && bisiesto())
                    fin = 29;
                restan = fin - d2;
                if(restan >= d){
                    d2 += d;
                    d = 0;
                }
                else{
                    d -= restan;
                    d2 = 0;
                    if(m2 == 12){
                        m2 = 1;
                        a2++;
                    }
                    else
                        m2++;
                }
            }
            while(d>0);
            f.setDia(d2);
            f.setMes(m2);
            f.setAño(a2);
            return f;
        }

        public Fecha sumarMeses(int m){
            Fecha f = new Fecha();
            int d2,m2,a2;
            d2=dia;
            m2=mes;
            a2=año;
            int restan;
            do{
                restan = 12 - m2;
                if(restan >= m){
                    m2 += m;
                    m = 0;
                }
                else{
                    m -= restan;
                    m2 = 0;
                    a2++;
                }
            }
            while(m>0);
            f.setDia(d2);
            f.setMes(m2);
            f.setAño(a2);
            return f;
        }
        public Fecha sumarAños(int a){
            Fecha f = new Fecha();
            f.setDia(dia);
            f.setMes(mes);
            f.setAño(año + a);
            return f;
        }

        public Fecha restarDias(int d){
            int dias[]= {31,28,31,30,31,30,31,31,30,31,31,31};
            Fecha f = new Fecha();
            int d2,m2,a2;
            d2=dia;
            m2=mes;
            a2=año;
            int fin;
            do{
                if(d2 > d){
                    d2 -= d;
                    d=0;
                }
                else{

                    d-=d2;
                    if(m2 == 1)
                    {
                        fin = 31;
                        m2 = 12;
                        a2--;
                    }
                    else{
                        fin = dias[m2-2];
                        if(m2-1 == 2 && bisiesto())
                            fin = 29;
                        m2--;
                    }
                    d2=fin;
                }
            }while(d>0);
            f.setDia(d2);
            f.setMes(m2);
            f.setAño(a2);
            return f;
        }

        public Fecha restarMeses(int m){
            Fecha f = new Fecha();
            int d2,m2,a2;
            d2=dia;
            m2=mes;
            a2=año;
            do{
                if(m2 > m){
                    m2 -= m;
                    m=0;
                }
                else{
                    m-=m2;
                    a2--;
                    m2=12;
                }
            }while(m>0);
            f.setDia(d2);
            f.setMes(m2);
            f.setAño(a2);
            return f;
        }

        public Fecha restarAños(int a){
            Fecha f = new Fecha();
            f.setDia(dia);
            f.setMes(mes);
            f.setAño(año - a);
            return f;
        }

        public boolean entre(Fecha f1,Fecha f2){
            return this.compareTo(f1)>=0 && this.compareTo(f2)<=0;
        }

        public String toString(){
            return dia+"/"+mes+"/"+año;
        }
        public int getDia(){
            return dia;
        }
        public void setDia(int dia) {
            this.dia = dia;
        }
        public void setMes(int mes) {
            this.mes = mes;
        }
        public int getMes() {
            return mes;
        }
        public void setAño(int año) {
            this.año = año;
        }
        public int getAño() {
            return año;
        }
}

