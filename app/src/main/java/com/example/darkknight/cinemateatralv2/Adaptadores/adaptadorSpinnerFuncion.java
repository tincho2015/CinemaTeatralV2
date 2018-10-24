package com.example.darkknight.cinemateatralv2.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.R;

import java.util.ArrayList;

public class adaptadorSpinnerFuncion extends ArrayAdapter<pelicula> {


    private Context context;
    ArrayList<pelicula> Peliculas;

    //constructor to get the list
    public adaptadorSpinnerFuncion(Context context, ArrayList<pelicula>Peliculas) {
        super(context, R.layout.support_simple_spinner_dropdown_item,Peliculas);
        this.Peliculas=Peliculas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Peliculas.size();
    }

    @Nullable
    @Override
    public pelicula getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //method returning list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv= view.findViewById(android.R.id.text1);
        if(position==0) { //Primer elemento color Azul #39399F
            txv.setBackgroundColor(Color.parseColor("#39399F"));
            txv.setTextColor(Color.parseColor("#FFFFFF")); //Texto color Blanco
        }else { //Otros elementos ...
            txv.setBackgroundColor(Color.parseColor("#FEF9DC"));
            txv.setTextColor(Color.parseColor("#39399F")); //Texto color Azul
        }
        txv.setText((Peliculas.get(position).toString()));
        return view;
    }

}
