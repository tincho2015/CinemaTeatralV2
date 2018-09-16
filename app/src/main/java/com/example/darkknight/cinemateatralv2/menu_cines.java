package com.example.darkknight.cinemateatralv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.darkknight.cinemateatralv2.layouts.altaDeCine;

public class menu_cines extends AppCompatActivity {

    Button btnAlta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cines);

        btnAlta = findViewById(R.id.btnAlta);


        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(menu_cines.this,altaDeCine.class);
                startActivity(i);
            }
        });


    }
}
