package com.example.darkknight.cinemateatralv2.Clases;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class tipoSalas {


    private ArrayList<asiento> asientos;
    private String descripcion;
    private int ID;
    private String descripcion_tipo_sala;
    private float precio_sala;


    public float getPrecio_sala() {
        return precio_sala;
    }

    public void setPrecio_sala(float precio_sala) {
        this.precio_sala = precio_sala;
    }

    public tipoSalas(String descripcion, int ID, String des_tipo_sala, float precio_sala) {
        asientos = new ArrayList<>();
        this.descripcion = descripcion;
        this.ID = ID;
        this.descripcion_tipo_sala = des_tipo_sala;
        this.precio_sala = precio_sala;

    }

    public String getDescripcion_tipo_sala() {
        return descripcion_tipo_sala;
    }

    public void setDescripcion_tipo_sala(String descripcion_tipo_sala) {
        this.descripcion_tipo_sala = descripcion_tipo_sala;
    }

    public ArrayList<asiento> getAsientos() {
        return asientos;

    }

    public void setAsientos(ArrayList<asiento> asientos) {
        this.asientos = asientos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
