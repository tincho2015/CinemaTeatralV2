package com.example.darkknight.cinemateatralv2.Clases;

import com.example.darkknight.cinemateatralv2.ItemReserva;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

public class reserva_teatro extends reserva {


    private teatro teatro;
    private obraDeTeatro obra;
    private sala_teatro st;


    public reserva_teatro(int ID, Usuario usuario, boolean estado, Fecha fechaDesde, Fecha fechaHasta, ItemReserva item) {
        super(ID, usuario, estado, fechaDesde, fechaHasta, item);
    }
}
