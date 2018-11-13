package com.example.darkknight.cinemateatralv2.layouts;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

//Esto es mi codigo sobre mi layout Alta_de_pelicula
public class altaDePelicula extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    EditText titulo;
    EditText director;
    EditText genero;
    EditText peliculaid;
    EditText sinopsis;
    EditText duracion;
    EditText protagonistas;
    EditText clasificacion;
    Button aceptar;
    ProgressBar barra;
    List<pelicula>ListaPeliculas;
    ListView peliculas;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean seEstaActualizando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abm_pelicula);

        titulo = findViewById(R.id.editTextTitulo);
        sinopsis = findViewById(R.id.editTextSinopsis);
        director = findViewById(R.id.editTextDirector);
        barra = findViewById(R.id.progressBar);
        peliculaid = findViewById(R.id.editTextPID);
        genero =findViewById(R.id.editTextGenero);
        duracion = findViewById(R.id.editTextDuracion);
        protagonistas = findViewById(R.id.editTextProtagonista);
        clasificacion = findViewById(R.id.editTextClasificacion);

        ListaPeliculas = new ArrayList<>();

        peliculas = findViewById(R.id.listViewPeliculas);

        aceptar = findViewById(R.id.btnAM);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seEstaActualizando) {

                    actualizarPelicula();

                }else
                {
                    agregarPelicula();
                }
            }
        });
        darPeliculas();
    }

    private void agregarPelicula() {


        String titulo = this.titulo.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String protagonistas = this.protagonistas.getText().toString().trim();
        String duracion = this.duracion.getText().toString().trim();
        String sinopsis = this.sinopsis.getText().toString().trim();
        String clasificacion = this.clasificacion.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(titulo)) {
            this.titulo.setError("Por favor, ingrese el titulo");
            this.titulo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(director)) {
            this.director.setError("Por favor, ingrese el nombre de director");
            this.director.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(genero)){

            this.genero.setError("Por favor, ingrese el genero");
            this.genero.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(protagonistas)){

            this.protagonistas.setError("Por favor, ingrese el nombre de los protagonistas");
            this.protagonistas.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(duracion)){

            this.duracion.setError("Por favor, ingrese la duración");
            this.duracion.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sinopsis)){

            this.sinopsis.setError("Por favor, ingrese la sinopsis");
            this.sinopsis.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(clasificacion)){

            this.clasificacion.setError("Por favor, ingrese la clasificación de la pelicula");
            this.clasificacion.requestFocus();
            return;
        }



        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonistas",protagonistas);
        params.put("duracion",duracion);
        params.put("clasificacion",clasificacion);



        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_PELICULA, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void darPeliculas() {

        request request = new request(AppConfig.URL_LISTAR_PELICULAS, null, CODE_GET_REQUEST);
        request.execute();
    }

    //inner class to perform network request extending an AsyncTask
    private class request extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        request(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barra.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barra.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("peliculas"));
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            jSonParser requestHandler = new jSonParser();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class peliAdapter extends ArrayAdapter<pelicula> {

        //our peli list
        List<pelicula> peliList;


        //constructor to get the list
        public peliAdapter(List<pelicula> peliList) {
            super(altaDePelicula.this, R.layout.activity_listar_peliculas, peliList);
            this.peliList = peliList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_peliculas, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final pelicula peli = peliList.get(position);

            textViewName.setText(peli.toString());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    peliculaid.setText(String.valueOf(peli.getID()));
                    titulo.setText(peli.getNombre());
                    director.setText(peli.getDirector());
                    sinopsis.setText(peli.getSinopsis());
                    duracion.setText(String.valueOf(peli.getDuracion()));
                    protagonistas.setText(peli.getActoresPrincipales());
                    clasificacion.setText(peli.getClasificacion());


                    //we will also make the button text to Update
                    aceptar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(altaDePelicula.this);

                    builder.setTitle("Eliminar " + peli.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarPelicula(peli.getID());
                                    //if the choice is yes we will delete the hero
                                    //method is commented because it is not yet created
                                    //deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }

    }

    private void actualizarPelicula() {

        String pid = this.peliculaid.getText().toString();
        String titulo = this.titulo.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String protagonistas = this.protagonistas.getText().toString().trim();
        String duracion = this.duracion.getText().toString().trim();
        String sinopsis = this.sinopsis.getText().toString().trim();
        String clasificacion = this.clasificacion.getText().toString().trim();


        if (TextUtils.isEmpty(titulo)) {
            this.titulo.setError("Por favor, ingrese el titulo");
            this.titulo.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(genero)){

            this.genero.setError("Por favor, ingrese el genero");
            this.genero.requestFocus();
        }
        if(TextUtils.isEmpty(director)){

            this.director.setError("Por favor, ingrese el nombre del director");
            this.director.requestFocus();
        }
        if(TextUtils.isEmpty(duracion)){

            this.duracion.setError("Por favor, ingrese la duración");
            this.duracion.requestFocus();
        }
        if(TextUtils.isEmpty(sinopsis)){

            this.sinopsis.setError("Por favor, ingrese la sinopsis");
            this.sinopsis.requestFocus();
        }
        if(TextUtils.isEmpty(protagonistas)){

            this.protagonistas.setError("Por favor, ingrese los nombres de los protagonistas");
            this.protagonistas.requestFocus();
        }
        if(TextUtils.isEmpty(clasificacion)){

            this.clasificacion.setError("Por favor, ingrese la clasificación de la pelicula");
            this.clasificacion.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("pid",pid);
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonistas",protagonistas);
        params.put("duracion",duracion);
        params.put("clasificacion",clasificacion);

        request request = new request(AppConfig.URL_ACTUALIZAR_PELICULAS, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");


        this.titulo.getText().toString().trim();
        this.genero.getText().toString().trim();
        this.director.getText().toString().trim();
        this.protagonistas.getText().toString().trim();
        this.duracion.getText().toString().trim();
        this.sinopsis.getText().toString().trim();
        this.clasificacion.getText().toString().trim();


        seEstaActualizando = false;
    }
    private void eliminarPelicula(int id) {
        request request = new request(AppConfig.URL_ELIMINAR_PELICULAS + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray peliculas) throws JSONException {
        //clearing previous heroes
        ListaPeliculas.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < peliculas.length(); i++) {
            //getting each hero object
            JSONObject obj = peliculas.getJSONObject(i);

            //adding the hero to the list
            ListaPeliculas.add(new pelicula(
                    obj.getInt("pid"),
                    obj.getInt("duracion"),
                    obj.getString("genero"),
                    obj.getString("titulo"),
                    obj.getString("sinopsis"),
                    obj.getString("protagonistas"),
                    obj.getString("clasificacion"),
                    obj.getString("director")
            ));
        }
        //creating the adapter and setting it to the listview
        peliAdapter adapter = new peliAdapter(ListaPeliculas);
        this.peliculas.setAdapter(adapter);
    }
}