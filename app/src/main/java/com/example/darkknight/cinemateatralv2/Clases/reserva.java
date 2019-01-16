package com.example.darkknight.cinemateatralv2.Clases;

import com.example.darkknight.cinemateatralv2.ItemReserva;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class reserva {


        private int ID;
        private Usuario usuario;
        private boolean estado;
        private Fecha fechaDesde;
        private Fecha fechaHasta;
        private int nroReserva;
        private funcion funcion;
        private asiento asiento;

        public reserva(int ID, Usuario usuario, boolean estado, Fecha fechaDesde, Fecha fechaHasta, ItemReserva item) {
            this.ID = ID;
            this.usuario = usuario;
            this.estado = estado;
            this.fechaDesde = Fecha.hoy();
            this.fechaHasta = fechaHasta;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        public Fecha getFechaHasta() {
            return fechaHasta;
        }

        public void setFechaHasta(Fecha fechaHasta) {
            this.fechaHasta = fechaHasta;
        }

}
