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
}
