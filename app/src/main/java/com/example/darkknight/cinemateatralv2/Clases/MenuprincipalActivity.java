package com.example.darkknight.cinemateatralv2.Clases;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.darkknight.cinemateatralv2.R;

public class MenuprincipalActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnGenerarReserva;
    Button btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);


        btnAdmin = (Button)findViewById(R.id.btnAdmin);
        btnGenerarReserva = (Button)findViewById(R.id.btnReserva);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent menuAdmin = new Intent(MenuprincipalActivity.this,menuAdministracion.class);
                startActivity(menuAdmin);
            }
        });
        /*
        btnGenerarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent menuReserva = new Intent(MenuprincipalActivity.this,)
            }
        });
        */



    }

    @Override
    protected void onStart() {
        super.onStart();


    }



    @Override
    public void onClick(View v) {



    }
}
