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
import com.example.darkknight.cinemateatralv2.Clases.teatro;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class abm_teatro_fragment extends Fragment{

    EditText nombre;
    EditText direccion;
    EditText telefono;
    EditText teatroid;
    EditText urlTeatro;
    Button aceptar;
    ProgressBar barra;
    List<teatro> ListaTeatros;
    ListView teatros;

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean seEstaActualizando = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.abm_teatro, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 3");


        nombre = view.findViewById(R.id.editTextNombreT);
        telefono = view.findViewById(R.id.editTextTelefonoT);
        direccion = view.findViewById(R.id.editTextDireccionT);
        barra = view.findViewById(R.id.progressBar);
        teatroid = view.findViewById(R.id.editTextTeatroID);
        urlTeatro = view.findViewById(R.id.editTextURL);

        ListaTeatros = new ArrayList<>();

        teatros = view.findViewById(R.id.listViewTeatros);

        aceptar = view.findViewById(R.id.btnAM);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seEstaActualizando) {


                    actualizarTeatro();

                }else
                {

                    agregarTeatro();
                }
            }
        });
        darTeatros();
    }
    private void agregarTeatro() {


        String nombre = this.nombre.getText().toString().trim();
        String direccion = this.direccion.getText().toString().trim();
        String telefono = this.telefono.getText().toString().trim();

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

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("direccion", direccion);
        params.put("telefono", telefono);



        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_TEATRO, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void darTeatros() {

       request request = new request(AppConfig.URL_LISTAR_TEATROS, null, CODE_GET_REQUEST);
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
                    refrescarLista(object.getJSONArray("teatros"));
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
    class teatroAdapter extends ArrayAdapter<teatro> {

        //our hero list
        List<teatro> teatroList;
        Context context = getContext();


        //constructor to get the list
        public teatroAdapter(Context context,List<teatro> teatroList) {
            super(context, R.layout.activity_listar_teatros, teatroList);
            this.teatroList = teatroList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_teatros, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final teatro teatro = teatroList.get(position);

            textViewName.setText(teatro.getNombre());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    teatroid.setText(String.valueOf(teatro.getID()));
                    nombre.setText(teatro.getNombre());
                    direccion.setText(teatro.getDireccion());
                    telefono.setText(teatro.getTelefono());


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

                    builder.setTitle("Eliminar " + teatro.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarTeatro(teatro.getID());
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
    private void actualizarTeatro() {

        String tid = this.teatroid.getText().toString();
        String nombre = this.nombre.getText().toString().trim();
        String telefono = this.telefono.getText().toString().trim();
        String direccion = this.direccion.getText().toString().trim();


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

        HashMap<String, String> params = new HashMap<>();
        params.put("tid",tid);
        params.put("nombre", nombre);
        params.put("telefono", telefono);
        params.put("direccion", direccion);

        request request = new request(AppConfig.URL_ACTUALIZAR_TEATROS, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");

        this.nombre.setText("");
        this.telefono.setText("");
        this.direccion.setText("");


        seEstaActualizando = false;
    }
    private void eliminarTeatro(int id) {
        request request = new request(AppConfig.URL_ELIMINAR_TEATROS + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray teatros) throws JSONException {
        //clearing previous heroes
        ListaTeatros.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < teatros.length(); i++) {
            //getting each hero object
            JSONObject obj = teatros.getJSONObject(i);

            //adding the hero to the list
            ListaTeatros.add(new teatro(
                    obj.getString("direccion"),
                    obj.getInt("id"),
                    obj.getString("nombre"),
                    obj.getString("telefono"),
                    obj.getString("url")
            ));
        }
        //creating the adapter and setting it to the listview
        teatroAdapter adapter = new teatroAdapter(getActivity().getApplicationContext(),ListaTeatros);
        this.teatros.setAdapter(adapter);
    }
}
