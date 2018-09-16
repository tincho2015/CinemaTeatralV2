package com.example.darkknight.cinemateatralv2.layouts;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.app.Activity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Clases.cine;
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


//Esto es mi codigo sobre mi layout Alta_de_cine

public class altaDeCine extends Activity
{


    EditText nombre;
    EditText direccion;
    EditText telefono;
    EditText cineid;
    EditText urlCine;
    Button aceptar;
    ProgressBar barra;
    List<cine>ListaCines;
    ListView cines;


    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean seEstaActualizando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abm_cine);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        nombre = findViewById(R.id.editTextTitulo);
        telefono = findViewById(R.id.editTextTelefonoC);
        direccion = findViewById(R.id.editTextGenero);
        barra = findViewById(R.id.progressBar);
        cineid = findViewById(R.id.editTextCineID);
        urlCine = findViewById(R.id.editTextURL);

        ListaCines = new ArrayList<>();

        cines = findViewById(R.id.listViewTeatros);

        aceptar = findViewById(R.id.btnAM);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seEstaActualizando) {

                    actualizarCine();

                }else
                {
                   agregarCine();
                }
            }
        });
        darCines();
    }

    private void agregarCine() {


        String nombre = this.nombre.getText().toString().trim();
        String direccion = this.direccion.getText().toString().trim();
        String telefono = this.telefono.getText().toString().trim();
        String url = this.urlCine.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(nombre)) {
            this.nombre.setError("Por favor, ingrese el nombre");
            this.nombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(direccion)) {
            this.direccion.setError("Por favor, ingrese la dirección");
            this.direccion.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(telefono)){

            this.telefono.setError("Por favor, ingrese nro. de teléfono");
            this.telefono.requestFocus();
        }
        if(!Patterns.WEB_URL.matcher(url).matches()){

            this.urlCine.setError("Por favor, ingrese un sitio web válido");
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("direccion", direccion);
        params.put("telefono", telefono);
        params.put("url",url);



        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_CINE, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void darCines() {

        request request = new request(AppConfig.URL_LISTAR_CINES, null, CODE_GET_REQUEST);
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
                    refrescarLista(object.getJSONArray("cines"));
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
    class cineAdapter extends ArrayAdapter<cine> {

        //our hero list
        List<cine> cineList;

        //constructor to get the list
        public cineAdapter(List<cine> cineList) {
            super(altaDeCine.this, R.layout.activity_listar_cines, cineList);
            this.cineList = cineList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_cines, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final cine cine = cineList.get(position);

            textViewName.setText(cine.getNombre());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    cineid.setText(String.valueOf(cine.getID()));
                    nombre.setText(cine.getNombre());
                    direccion.setText(cine.getDireccion());
                    telefono.setText(cine.getTelefono());
                    urlCine.setText(cine.getUrl());


                    //we will also make the button text to Update
                    aceptar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(altaDeCine.this);

                    builder.setTitle("Eliminar " + cine.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarCine(cine.getID());
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

    private void actualizarCine() {

        String cid = this.cineid.getText().toString();
        String nombre = this.nombre.getText().toString().trim();
        String telefono = this.telefono.getText().toString().trim();
        String direccion = this.direccion.getText().toString().trim();
        String url =  this.urlCine.getText().toString().trim();


        if (TextUtils.isEmpty(nombre)) {
            this.nombre.setError("Por favor, ingrese el nombre");
            this.nombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(telefono)) {
            this.telefono.setError("Por favor, ingrese el telefono");
            this.telefono.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(direccion)){
            this.direccion.setError("Por favor, ingrese la dirección");
            this.direccion.requestFocus();
        }
        if(!Patterns.WEB_URL.matcher(url).matches()){

            this.urlCine.setError("Por favor, ingrese un sitio web válido");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cid",cid);
        params.put("nombre", nombre);
        params.put("telefono", telefono);
        params.put("direccion", direccion);
        params.put("url",url);

        request request = new request(AppConfig.URL_ACTUALIZAR_CINE, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");

        this.nombre.setText("");
        this.telefono.setText("");
        this.direccion.setText("");
        this.urlCine.setText("");


        seEstaActualizando = false;
    }
    private void eliminarCine(int id) {
        request request = new request(AppConfig.URL_ELIMINAR_CINE + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray cines) throws JSONException {
        //clearing previous heroes
        ListaCines.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < cines.length(); i++) {
            //getting each hero object
            JSONObject obj = cines.getJSONObject(i);

            //adding the hero to the list
            ListaCines.add(new cine(
                    obj.getString("direccion"),
                    obj.getInt("cid"),
                    obj.getString("nombre"),
                    obj.getString("telefono"),
                    obj.getString("url")
            ));
        }
        //creating the adapter and setting it to the listview
        cineAdapter adapter = new cineAdapter(ListaCines);
        this.cines.setAdapter(adapter);
    }
}



