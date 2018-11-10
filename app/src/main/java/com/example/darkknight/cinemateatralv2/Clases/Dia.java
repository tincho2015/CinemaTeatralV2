package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dark Knight on 30/10/2015.
 */
public class Dia
{
    private Date fecha;
    private ArrayList<horario>horarios;

    public Dia(Date fecha) {
        this.fecha = fecha;
        this.horarios = new ArrayList<>();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<horario> horarios) {
        this.horarios = horarios;
    }
    public void agregarNuevaHora(horario nuevaHora){

        if(nuevaHora != null){

            this.horarios.add(nuevaHora);
        }
    }
    public void eliminarHora(horario hora){

        if(hora != null){

            if(this.horarios.contains(hora)){

                this.horarios.remove(hora);
            }
        }
    }
}
