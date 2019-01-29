package com.example.darkknight.cinemateatralv2.Clases;

import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class reserva {


        private int ID;
        private int usuarioid;
        private Date fechaDesde;
        private Date fechaHasta;
        private int nroReserva;
        private int funcionid;
        private ArrayList<asiento>asientos;



    public reserva(int ID, int usuarioId,int funcionid,Date fechaHasta,int nroReserva) {
            this.ID = ID;
            this.usuarioid = usuarioId;
            this.fechaDesde = Fecha.hoy();
            this.fechaHasta = fechaHasta;
            this.asientos = new ArrayList<>();
            this.nroReserva = nroReserva;
        }

    public int getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(int nroReserva) {
        this.nroReserva = nroReserva;
    }

    public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUsuario() {
            return usuarioid;
        }

        public void setUsuario(int usuarioid) {
            this.usuarioid = usuarioid;
        }


        public Date getFechaHasta() {
            return fechaHasta;
        }

        public void setFechaHasta(Date fechaHasta) {
            this.fechaHasta = fechaHasta;
        }

    public ArrayList<asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(ArrayList<asiento> asientos) {
        this.asientos = asientos;
    }



}
