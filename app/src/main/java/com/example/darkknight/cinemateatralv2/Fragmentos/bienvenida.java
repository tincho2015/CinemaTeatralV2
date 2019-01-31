package com.example.darkknight.cinemateatralv2.Fragmentos;

import com.example.darkknight.cinemateatralv2.Clases.Administradora;
import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class bienvenida extends Fragment {

    private TextView txtName;
    private Button btnLogout;
    private Button btnEspectaculosVistos;
    private Button btnReservas;



    public static bienvenida newInstance() {
        bienvenida fragment = new bienvenida();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        View view = inflater.inflate(R.layout.activity_bienvenida, container, false);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        txtName = view.findViewById(R.id.name);
        btnLogout =  view.findViewById(R.id.btnLogout);
        btnEspectaculosVistos = view.findViewById(R.id.btnEspectaculosVistos);
        btnReservas = view.findViewById(R.id.btnListarReservas);


        return view;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Bienvenida");
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Usuario usuario = SharedPrefManager.getInstance(getActivity()).getUsuario();

        txtName.setText(usuario.getNombre_usuario());
        //setting the values to the textviews

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                

            }
        });

        btnEspectaculosVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        txtName = findViewById(R.id.name);
        btnLogout =  findViewById(R.id.btnLogout);
        btnMenu = findViewById(R.id.btnMenu);


        // Displaying the user details on the screen
        //getting the current user
        Usuario usuario = SharedPrefManager.getInstance(this).getUsuario();

        txtName.setText(usuario.getNombre_usuario());
        //setting the values to the textviews

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        // Ir al menu principal
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SharedPrefManager.getInstance(getApplicationContext()).getUsuario().esAdmin()) {
                    //starting the profile activity
                    finish();
                   Fragment fragment = new abm_cine_fragment();

                    if (fragment != null) {
                        android.app.FragmentManager fm = getFragmentManager();
                        android.app.FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.pantalla_fragment, fragment);
                        ft.commit();
                    }
                }

                Intent intent = new Intent(bienvenida.this,MenuprincipalActivity.class);
                startActivity(intent);

            }
        });
    }


    private void logoutUser() {

        SharedPrefManager.getInstance(getApplicationContext()).logout();


    }
    */
}
