package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.abm_asientos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link comunicador} interface
 * to handle interaction events.
 */
public class abmSala extends Fragment{

    private comunicador comunicador;

    private EditText txtDescripcionSala;
    private EditText txtPrecioSala;
    private EditText txtTipoSala;
    private EditText salaId;
    private Button btnAgregarSala;
    private Button btnAgregarAsiento;
    private ProgressBar barraEspera;
    private Spinner listaDeCines;
    private ArrayList<cine>cinesAdmin;
    private ArrayList<sala_cine>salasDelCine;
    private ListView listaDeSalas;
    private boolean seEstaActualizando = false;
    private ArrayAdapter<cine> adaptadorCines;
    private cine cine_sala = null;
    private Fragment fAsiento = null;
    private int idSala = 0;
    private int idCine = 0;
    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    public abmSala() {
        // Required empty public constructor
    }
    public static abmSala newInstance() {
        abmSala fragment = new abmSala();
        return fragment;
    }

    public void cargarSpinner(ArrayList<cine>cinesAdmin, ArrayAdapter<cine> adaptadorCines){

        listaDeCines.setAdapter(adaptadorCines);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.abm_sala_fragment, container, false);

        getActivity().setTitle("Agregar una sala_cine");


        txtDescripcionSala = view.findViewById(R.id.editTextNombre);
        txtPrecioSala = view.findViewById(R.id.editTextPrecio);
        txtTipoSala = view.findViewById(R.id.editTextTipo);
        btnAgregarSala = view.findViewById(R.id.btnSala);
        barraEspera = view.findViewById(R.id.barraProgreso);
        salaId = view.findViewById(R.id.salaId);
        btnAgregarAsiento = view.findViewById(R.id.btnAgregarAsiento);

        listaDeCines = view.findViewById(R.id.listCines);
        listaDeSalas = view.findViewById(R.id.listaSalas);


        cinesAdmin = new ArrayList<>();

        cinesAdmin = comunicador.darCines();

        adaptadorCines = new adaptadorSpinnerSala(getActivity(),cinesAdmin);

        salasDelCine = new ArrayList<>();

        cargarSpinner(cinesAdmin,adaptadorCines);


        listaDeCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>parent , View view, int position, long id) {



                cine_sala = adaptadorCines.getItem(position);
                Toast.makeText(getActivity(),"ID:" + cine_sala.getID(),Toast.LENGTH_LONG).show();

                darSalas(cine_sala.getID());
                btnAgregarSala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(seEstaActualizando){

                            actualizarSala();
                        }else{

                        agregarSala(cine_sala.getID());
                           }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        btnAgregarAsiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentm = getActivity().getFragmentManager();
                FragmentTransaction ft = fragmentm.beginTransaction();
                String tagAsiento;
                fAsiento = abm_asientos.newInstance();
                tagAsiento = fAsiento.getClass().getName();
                ft.replace(R.id.content_frame, fAsiento, tagAsiento);
                ft.addToBackStack(tagAsiento);
                ft.commit();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList<sala_cine>salaCines,cine c) {
        if (comunicador != null) {
            comunicador.mandarSalasCineAdmin(salaCines,c);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof comunicador) {
            comunicador = (comunicador) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement comunicador");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        comunicador = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void refrescarListaSalas(JSONArray salas) throws JSONException {
        //clearing previous heroes
        salasDelCine.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < salas.length(); i++) {
            //getting each hero object
            JSONObject obj = salas.getJSONObject(i);

            //adding the hero to the list
            salasDelCine.add(new sala_cine(
                    obj.getString("descripcion"),
                    obj.getInt("id_sala"),
                    obj.getString("descripcion_tipo_sala"),
                    obj.getInt("precio_sala")
            ));
        }
        //creating the adapter and setting it to the listview

        //Revisar con respecto a la clase "Adaptador"
        adaptadorSalas adapter = new adaptadorSalas(getActivity().getApplicationContext(),salasDelCine);
        this.listaDeSalas.setAdapter(adapter);
    }
    private void darSalas(int idCine) {

        request request = new request(AppConfig.URL_LISTAR_SALAS + idCine, null, CODE_GET_REQUEST);
        request.execute();
    }
    public void eliminarSala(int idsala){

        request request = new request(AppConfig.URL_ELIMINAR_SALA + idsala,null,CODE_GET_REQUEST);
        request.execute();


    }
    private void actualizarSala() {

            String id = this.salaId.getText().toString().trim();
            String descripcion = this.txtDescripcionSala.getText().toString();
            String precio_sala = this.txtPrecioSala.getText().toString().trim();
            String tipo_sala = this.txtPrecioSala.getText().toString().trim();


            if (TextUtils.isEmpty(descripcion)) {
                this.txtDescripcionSala.setError("Por favor, ingrese el nombre de la sala");
                this.txtDescripcionSala.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(precio_sala)) {
                this.txtPrecioSala.setError("Por favor, ingrese un precio de sala");
                this.txtPrecioSala.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(tipo_sala)){
                this.txtTipoSala.setError("Por favor, ingrese el tipo de sala ");
                this.txtTipoSala.requestFocus();
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("id",id);
            params.put("descripcion_sala", descripcion);
            params.put("precio_sala", precio_sala);
            params.put("descripcion_tipo_sala", tipo_sala);

            //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            request request = new request(AppConfig.URL_ACTUALIZAR_SALA, params, CODE_POST_REQUEST);
            request.execute();

            btnAgregarSala.setText("Agregar");

            this.txtDescripcionSala.setText("");
            this.txtPrecioSala.setText("");
            this.txtTipoSala.setText("");

            seEstaActualizando = false;
        }
    private void agregarSala(int idcine) {


        String nombre_sala = this.txtDescripcionSala.getText().toString().trim();
        String tipo_sala = this.txtTipoSala.getText().toString().trim();
        String precio_sala = this.txtPrecioSala.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(nombre_sala)) {
            txtDescripcionSala.setError("Por favor, ingrese el nombre de la sala_cine");
            txtDescripcionSala.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tipo_sala)) {
            txtTipoSala.setError("Por favor, ingrese un tipo de sala_cine");
            txtTipoSala.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(precio_sala)){

            txtPrecioSala.setError("Por favor, ingrese un precio");
            txtPrecioSala.requestFocus();
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("descripcion", nombre_sala);
        params.put("precio_sala", precio_sala);
        params.put("descripcion_tipo_sala", tipo_sala);
        params.put("id_cine", String.valueOf(idcine));



        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_SALA + idcine, params, CODE_POST_REQUEST);
        request.execute();
    }
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
            barraEspera.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barraEspera.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarListaSalas(object.getJSONArray("salas"));
                    comunicador.mandarSalasCineAdmin(salasDelCine,cine_sala);

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

    public class adaptadorSalas extends ArrayAdapter<sala_cine> {

        ArrayList<sala_cine>listaDeSalas;
        Context context = getContext();


        //constructor to get the list
        public adaptadorSalas(Context context, ArrayList<sala_cine>listaDeSalas) {
            super(context, R.layout.listar_salas,listaDeSalas);
            this.listaDeSalas = listaDeSalas;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.listar_salas, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final sala_cine sala_c = listaDeSalas.get(position);

            textViewName.setText(sala_c.getDescripcion());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    salaId.setText(String.valueOf(sala_c.getID()));
                    txtDescripcionSala.setText(sala_c.getDescripcion());
                    txtPrecioSala.setText(String.valueOf(sala_c.getPrecio_sala()));
                    txtTipoSala.setText(sala_c.getDescripcion_tipo_sala());

                    //we will also make the button text to Update
                    btnAgregarSala.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + sala_c.getDescripcion())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarSala(sala_c.getID());
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







}
