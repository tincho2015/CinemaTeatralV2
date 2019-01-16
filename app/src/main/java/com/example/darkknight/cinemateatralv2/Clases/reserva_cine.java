package com.example.darkknight.cinemateatralv2.Clases;

import com.example.darkknight.cinemateatralv2.ItemReserva;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

public class reserva_cine extends reserva {


    private cine cine;
    private pelicula pelicula;
    private sala_cine sc;
    private asiento lugar;


    public reserva_cine(int ID, Usuario usuario, boolean estado, Fecha fechaDesde, Fecha fechaHasta, ItemReserva item) {
        super(ID, usuario, estado, fechaDesde, fechaHasta, item);
    }
}
