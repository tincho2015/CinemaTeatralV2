package com.example.darkknight.cinemateatralv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorListaDesplegable;
import com.example.darkknight.cinemateatralv2.Clases.Administradora;
import com.example.darkknight.cinemateatralv2.Clases.Dia;
import com.example.darkknight.cinemateatralv2.Clases.asiento;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.reserva_cine;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.Helpers.FragmentNavigationManager;
import com.example.darkknight.cinemateatralv2.Interfaces.NavigationManager;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class menu_lateral_principal extends AppCompatActivity
        implements/*NavigationView.OnNavigationItemSelectedListener*/
        comunicador{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTituloActividad;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adaptador;
    private List<String> listaTitulos;
    private Map<String,List<String>>listaChild;
    private NavigationManager navigationManager;


    public Administradora admin = Administradora.getIntance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mTituloActividad = getTitle().toString();
        expandableListView = findViewById(R.id.lvExp);
        navigationManager = FragmentNavigationManager.getmIntance(this);

       inicializarItems();

        View listaHeaderView = getLayoutInflater().inflate(R.layout.nav_header_menu_lateral,null,false);
        expandableListView.addHeaderView(listaHeaderView);

        generarDatos();

        addDrawersItem();
        setupDrawer();

        if(savedInstanceState == null){
            seleccionarPrimeraOpcion();

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Menu principal");

        /*
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
            */
        Usuario usuario = SharedPrefManager.getInstance(this).getUsuario();


        if(usuario.esAdmin()) {



        }


    /*
      displaySelectedScreen(R.id.opc_menu_1);
      */
    }

    private void seleccionarPrimeraOpcion() {

        if(navigationManager != null){

            String primerItem = listaTitulos.get(0);
            navigationManager.showFragmentBienvenida();
            getSupportActionBar().setTitle(primerItem);
        }
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Menu principal");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mTituloActividad);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawersItem() {

        adaptador = new adaptadorListaDesplegable(this,listaTitulos,listaChild);
        expandableListView.setAdapter(adaptador);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(listaTitulos.get(groupPosition).toString()); //Setea titulo para la toolbar cuando se abre el menu
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Menu principal");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String itemSeleccionado = ((listaChild.get(listaTitulos.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(itemSeleccionado);

               // if(items[0].equals(listaTitulos.get(groupPosition))){

                    switch(itemSeleccionado) {
                        case "Bienvenida":
                        navigationManager.showFragmentBienvenida();
                        break;
                        case "ABM de Cine":
                        navigationManager.showFragmentABMCines();
                        break;
                        case "ABM de Teatro":
                        navigationManager.showFragmentABMTeatros();
                        break;
                        case "ABM de Película":
                        navigationManager.showFragmentABMPelicula();
                        break;
                        case "ABM de Obra de Teatro":
                        navigationManager.showFragmentABMObraTeatro();
                        break;
                    }
               // }else
                {
                    try {
                        throw  new IllegalAccessException("Fragment no soportado");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void generarDatos() {

        List<String> tituloAdministracion = Arrays.asList(getResources().getStringArray(R.array.menu_lateral_opciones_admin));
        List<String> tituloReservas = Arrays.asList(getResources().getStringArray(R.array.menu_lateral_opciones_reservas));
        List<String> tituloInicio = Arrays.asList(getResources().getStringArray(R.array.inicio));
        List<String> childItemInicio = Arrays.asList(getResources().getStringArray(R.array.inicio_bienvenida));
        List<String> childItemAdmin = Arrays.asList(getResources().getStringArray(R.array.submenu_opciones_admin));
        List<String> childItemReservas = Arrays.asList(getResources().getStringArray(R.array.submenu_opciones_reservas));

        listaChild = new TreeMap<>();
        listaChild.put(tituloInicio.get(0),childItemInicio);
        listaChild.put(tituloAdministracion.get(0),childItemAdmin);
        listaChild.put(tituloReservas.get(0),childItemReservas);

        listaTitulos = new ArrayList<>(listaChild.keySet());



    }

    private void inicializarItems() {
        items = new String[]{
                ("Inicio")};
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    /*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        displaySelectedScreen(item.getItemId());
        return true;



    }
    */
    public void setearFragment(boolean fragmentTX,Fragment fragment){

        if (fragmentTX) {
            android.app.FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame,fragment,fragment.getClass().getName());
            ft.addToBackStack("f_main");
            ft.commit();
        }
    }
/*
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
            case R.id.opc_menu_admin_1_1:
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
*/
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
            //if(!cines.contains(c))
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

    @Override
    public ArrayList darPelisTotal(cine c) {
        return admin.darPelisTotal(c);
    }

    @Override
    public ArrayList darFunciones(cine c, sala_cine sc, pelicula p) {
        return admin.darFunciones(c,sc,p);
    }

    @Override
    public ArrayList darFuncionesPelicula(pelicula p) {
        return null;
    }

    @Override
    public ArrayList darHorariosPorFuncion(funcion f, Dia d) {
        return null;
    }

    @Override
    public cine darCineReserva(int cineId) {
        return admin.darCineReserva(cineId);
    }

    @Override
    public pelicula darPeliReserva(int peliId) {
        return admin.darPeliculaReserva(peliId);
    }

    @Override
    public funcion darFuncionReserva(int funcionId) {
        return admin.darFuncionReserva(funcionId);
    }

    @Override
    public horario darHorarioReserva(int horarioId) {
        return admin.darHorarioReserva(horarioId);
    }

    @Override
    public cine darCine(int cineId) {
        return admin.darCine(cineId);
    }

    @Override
    public sala_cine darSalaCine(int cineId, int salaId) {
        return admin.darSalaCine(cineId,salaId);
    }

    /*
    public void cambiarSala(){

        Fragment f = null;
        boolean ftx = false;

        f = new abmSala();
        ftx = true;

        this.setearFragment(ftx,f);
    }
*/
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
                if(!pelisSala.contains(p))
                admin.agregarPeliculasSala(c,sc,p);

            }
        }

    }
    @Override
    public void mandarFuncionAdmin(cine c, sala_cine sc, pelicula p,ArrayList<funcion>funciones) {


        for(funcion f:funciones){

            if(!funciones.contains(f))
            {
                admin.agregarFuncion(c,sc,p,f);
            }
        }

    }
    public void mandarFuncionHorarioAdmin(cine c, sala_cine sc,pelicula p, funcion f, ArrayList<horario>horarios){


        for(horario hor: horarios){
            if(!horarios.contains(hor)){

                admin.agregarHorarioFuncion(c,sc,p,f,hor);
            }
        }

    }

    @Override
    public void mandarAsientosSalaAdmin(ArrayList<asiento> asientos, sala_cine sc, cine c) {

    }

    @Override
    public ArrayList darAsientosDisponibles(sala_cine sc) {
        if(sc != null ){

            admin.darAsientosDisponibles(sc);
        }
        return null;
    }

    public void mandarAsientosSalaAdmin(cine c,ArrayList<asiento>asientos, sala_cine sc){

        for(asiento a:asientos){

            if(!asientos.contains(a)){

                admin.agregarAsientoSala(c,sc,a);
            }
        }
    }
    public void mandarReservaAdmin(reserva_cine rc){

        if(rc !=null){

            admin.agregarReservaCine(rc);
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
