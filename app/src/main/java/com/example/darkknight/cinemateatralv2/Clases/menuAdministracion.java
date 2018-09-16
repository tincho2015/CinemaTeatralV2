package com.example.darkknight.cinemateatralv2.Clases;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.layouts.altaDeCine;

public class menuAdministracion extends AppCompatActivity
{

    Button BtnCine;
    Button BtnTeatro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_administracion);


        BtnCine = (Button)findViewById(R.id.btnCine);
        BtnTeatro =(Button)findViewById(R.id.btnTeatro);

        BtnCine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent menuCines = new Intent(menuAdministracion.this,altaDeCine.class);
                startActivity(menuCines);
            }
        });
        /*
        BtnTeatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent menuTeatro = new Intent(menuAdministracion.this,alta)
            }
        });

        */





    }

}
