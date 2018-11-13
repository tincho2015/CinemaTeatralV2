package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.obraDeTeatro;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class abm_obra_teatro_fragment extends Fragment{

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    EditText titulo;
    EditText director;
    EditText genero;
    EditText obrateatroid;
    EditText sinopsis;
    EditText duracion;
    EditText protagonistas;
    EditText obraId;
    Button aceptar;
    ProgressBar barra;
    List<obraDeTeatro> ListaObrasDeTeatro;
    ListView obras_teatro;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean seEstaActualizando = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.abm_obra_teatro, container, false);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("ABM Obra de teatro");


        titulo = view.findViewById(R.id.editTextTituloOT);
        sinopsis = view.findViewById(R.id.editTextSinopsisOT);
        director = view.findViewById(R.id.editTextDirectorOT);
        barra = view.findViewById(R.id.progressBar);
        obraId = view.findViewById(R.id.editTextOID);
        genero = view.findViewById(R.id.editTextGeneroOT);
        duracion = view.findViewById(R.id.editTextDuracionOT);
        protagonistas = view.findViewById(R.id.editTextElencoOT);

        ListaObrasDeTeatro = new ArrayList<>();

        obras_teatro = view.findViewById(R.id.listViewObraTeatro);

        aceptar = view.findViewById(R.id.btnAM);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seEstaActualizando) {


                    actualizarObraDeTeatro();

                }else
                {

                    agregarObraDeTeatro();
                }
            }
        });
        darObrasDeTeatro();
    }
    private void agregarObraDeTeatro() {


        String titulo = this.titulo.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String protagonistas = this.protagonistas.toString().trim();
        String duracion = this.duracion.toString().trim();
        String sinopsis = this.sinopsis.toString().trim();

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

            this.protagonistas.setError("Por favor, ingrese el nombre de los protagonistas");
            this.protagonistas.requestFocus();
        }


        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonitas",protagonistas);
        params.put("duracion",duracion);



        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_OBRA_TEATRO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void darObrasDeTeatro() {

        request request = new request(AppConfig.URL_LISTAR_OBRAS_TEATRO, null, CODE_GET_REQUEST);
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
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("obras_teatro"));
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
    class obraAdapter extends ArrayAdapter<obraDeTeatro> {

        //our peli list
        List<obraDeTeatro> obraList;
        Context context = getActivity().getApplicationContext();


        //constructor to get the list
        public obraAdapter(Context context,List<obraDeTeatro> obraList) {
            super(context, R.layout.activity_listar_obras_teatro, obraList);
            this.obraList = obraList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_obras_teatro, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final obraDeTeatro obra = obraList.get(position);

            textViewName.setText(obra.getNombre());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    obrateatroid.setText(String.valueOf(obra.getID()));
                    titulo.setText(obra.getNombre());
                    director.setText(obra.getDirector());
                    sinopsis.setText(obra.getSinopsis());
                    duracion.setText(String.valueOf(obra.getDuracion()));
                    protagonistas.setText(obra.getElenco());


                    //we will also make the button text to Update
                    aceptar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());

                    builder.setTitle("Eliminar " + obra.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarla?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarObraDeTeatro(obra.getID());
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
    private void actualizarObraDeTeatro() {

        String pid = this.obrateatroid.getText().toString();
        String titulo = this.titulo.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String protagonistas = this.protagonistas.getText().toString().trim();
        String duracion = this.duracion.getText().toString().trim();
        String sinopsis = this.sinopsis.getText().toString().trim();


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


        HashMap<String, String> params = new HashMap<>();
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonitas",protagonistas);
        params.put("duracion",duracion);

        request request = new request(AppConfig.URL_ACTUALIZAR_OBRAS_TEATROS, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");


        this.titulo.getText().toString().trim();
        this.genero.getText().toString().trim();
        this.director.getText().toString().trim();
        this.protagonistas.getText().toString().trim();
        this.duracion.getText().toString().trim();
        this.sinopsis.getText().toString().trim();


        seEstaActualizando = false;
    }
    private void eliminarObraDeTeatro(int id) {
        request request = new request(AppConfig.URL_ELIMINAR_OBRA_TEATRO + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray obras) throws JSONException {
        //clearing previous heroes
        ListaObrasDeTeatro.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < obras.length(); i++) {
            //getting each hero object
            JSONObject obj = obras.getJSONObject(i);

            //adding the hero to the list
            ListaObrasDeTeatro.add(new obraDeTeatro(
                    obj.getInt("id"),
                    obj.getInt("duracion"),
                    obj.getString("genero"),
                    obj.getString("titulo"),
                    obj.getString("sinopsis"),
                    obj.getString("director"),
                    obj.getString("protagonistas"),
                    obj.getString("clasificacion")
            ));
        }
        //creating the adapter and setting it to the listview
        obraAdapter adapter = new obraAdapter(getActivity().getApplicationContext(),ListaObrasDeTeatro);
        this.obras_teatro.setAdapter(adapter);
    }




}
