package com.example.darkknight.cinemateatralv2.Clases;

import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class reserva_cine extends reserva {


    private int cineId;
    private int peliculaId;
    private int scId;


    public reserva_cine(int ID, int idUsuario,int funcionId,int cineId,int peliculaId, int scId,Date fechaHasta,int nroReserva) {
        super(ID,idUsuario,funcionId,fechaHasta,nroReserva);
    }

    public  int getCineId() {
        return cineId;
    }

    public void setCineId(int cineId) {
        this.cineId = cineId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public int getScId() {
        return scId;
    }

    public void setScId(int scId) {
        this.scId = scId;
    }
}
