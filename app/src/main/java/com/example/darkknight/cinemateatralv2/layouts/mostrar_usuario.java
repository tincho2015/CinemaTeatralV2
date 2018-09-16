package com.example.darkknight.cinemateatralv2.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.darkknight.cinemateatralv2.Clases.Login;
import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

public class mostrar_usuario extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    Button editarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).estaLogueado()) {
            finish();
            startActivity(new Intent(this, Login.class));
        }


        textViewId = findViewById(R.id.showID);
        textViewUsername = findViewById(R.id.showNombreUsuario);
        textViewEmail = findViewById(R.id.showEmail);
        textViewGender = findViewById(R.id.showGenero);
        editarInfo = findViewById(R.id.btnEditarInfor);


        //getting the current user
        Usuario usuario = SharedPrefManager.getInstance(this).getUsuario();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(usuario.getID()));
        textViewUsername.setText(usuario.getNombre_usuario());
        textViewEmail.setText(usuario.getEmail());
        textViewGender.setText(usuario.getSexo());

        //when the user presses logout button
        //calling the logout method
        /*
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        */
        /*
        findViewById(R.id.btnEditarInfor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mostrar_usuario.this,);
            }
        });
        */

    }
}
