package com.example.darkknight.cinemateatralv2.Clases;

import java.util.ArrayList;

public class sala_teatro extends tipoSalas{


    ArrayList<obraDeTeatro>obrasTeatroSala;

    public sala_teatro(String descripcion, int ID,String des_tipo_sala,float precio_sala) {
        super(descripcion, ID,des_tipo_sala,precio_sala);
        this.obrasTeatroSala = new ArrayList<>();
    }

    public ArrayList<obraDeTeatro> getObrasTeatroSala() {
        return obrasTeatroSala;
    }

    public void setObrasTeatroSala(ArrayList<obraDeTeatro> obrasTeatroSala) {
        this.obrasTeatroSala = obrasTeatroSala;
    }

}
