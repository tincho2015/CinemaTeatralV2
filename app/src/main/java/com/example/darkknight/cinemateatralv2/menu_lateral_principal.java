package com.example.darkknight.cinemateatralv2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.darkknight.cinemateatralv2.Clases.Administradora;
import com.example.darkknight.cinemateatralv2.Clases.bienvenida;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;


public class menu_lateral_principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        comunicador {

    public Administradora admin = Administradora.getIntance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Usuario usuario = SharedPrefManager.getInstance(this).getUsuario();

        if(usuario.esAdmin()) {

            navigationView.getMenu().findItem(R.id.opc_admin).setVisible(true);

        }


      displaySelectedScreen(R.id.opc_menu_1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        displaySelectedScreen(item.getItemId());
        return true;



    }
    public void setearFragment(boolean fragmentTX,Fragment fragment){

        if (fragmentTX) {
            android.app.FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame,fragment,fragment.getClass().getName());
            ft.addToBackStack("f_main");
            ft.commit();
        }
    }

    private void displaySelectedScreen(int itemId) {


        Fragment fragment = null;
        boolean fragmentTX = false;

        switch (itemId) {
            case R.id.opc_menu_1:
                fragment = new bienvenida();
                fragmentTX = true;
                break;
            case R.id.opc_menu_3:
                fragment = new abm_reservas();
                fragmentTX = true;
                break;
            case R.id.opc_menu_admin_1:
                fragment = new abm_cine_fragment();
                fragmentTX = true;
                break;
            case R.id.opc_menu_admin_2:
                fragment = new abm_teatro_fragment();
                fragmentTX = true;
                break;
            case R.id.opc_menu_admin_3:
                fragment = new abm_pelicula_fragment();
                fragmentTX = true;
                break;
            case R.id.opc_menu_admin_4:
                fragment = new abm_obra_teatro_fragment();
                fragmentTX = true;
                break;

        }

        if (fragment != null) {

            android.app.FragmentManager fm = getFragmentManager();
            android.app.Fragment currentFragment;
            currentFragment = fm.findFragmentById(R.id.content_frame);

            if (currentFragment == null) {
                //carga del primer fragment justo en la carga inicial de la app
                setearFragment(fragmentTX, fragment);
            } else
                if (!currentFragment.getClass().getName().equalsIgnoreCase(fragment.getClass().getName())) {
                //currentFragment no concide con newFragment
                setearFragment(fragmentTX,fragment);

            } else {
                //currentFragment es igual a newFragment
            }
        }


        //replacing the fragment
       //this.setearFragment(fragmentTX,fragment);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

/*
    @Override
    public void pasarCine(List<cine> cines) {


        FragmentManager fragmentManager = getFragmentManager();
        abm_pelicula_fragment abmPeliculaFragment2 = (abm_pelicula_fragment)fragmentManager.findFragmentById(R.id.content_frame);
        abmPeliculaFragment2.recibirCines(cines);

    }
    */

    @Override
    public void mandarCineAdmin(ArrayList<cine> cines) {

        for(cine c:cines) {
            admin.agregarCine(c);
        }
    }

    @Override
    public ArrayList darCines() {

        return admin.darCines();

    }

    @Override
    public ArrayList darSalas(cine c) {

        return admin.darSalas(c);

    }

    @Override
    public ArrayList darPelis(sala_cine sc) {
        return admin.darPelis(sc);
    }

    public void cambiarSala(){

        Fragment f = null;
        boolean ftx = false;

        f = new abmSala();
        ftx = true;

        this.setearFragment(ftx,f);
    }

    @Override
    public void mandarSalasCineAdmin(ArrayList<sala_cine>salasCine, cine c) {

       if(c != null){

           for(sala_cine sc:salasCine){
               admin.agregarSalaCine(c,sc);
           }
       }

    }

    @Override
    public void eliminarCine(ArrayList<cine>cines) {

        for(cine c:cines){

            admin.eliminarCine(c);
        }

    }

    @Override
    public void eliminarSalaCine(ArrayList<sala_cine> salasCine, cine c) {

        if(c != null){

            for(sala_cine sc:salasCine){

                admin.eliminarSalaCine(c,sc);
            }
        }

    }

    @Override
    public void eliminarPeliculasSala(ArrayList<sala_cine> salaCines, pelicula p) {

        if( p != null){

            for(sala_cine sc: salaCines){

                admin.eliminarPeliculaSala(sc,p);
            }
        }

    }

    @Override
    public void mandarPelisSalaAdmin(ArrayList<pelicula> pelisSala, sala_cine sc,cine c) {

        if(sc != null){

            for(pelicula p: pelisSala) {
                admin.agregarPeliculasSala(c,sc,p);

            }
        }

    }
    /*
    @Override
    public void mandarPelisSalaAdmin(ArrayList<pelicula> pelisSala, sala_cine sc) {

        if(sc != null){

            for(pelicula p: pelisSala){

                admin.
            }
        }
    }
    }
    */


}
