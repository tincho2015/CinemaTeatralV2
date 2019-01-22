package com.example.darkknight.cinemateatralv2;

import android.app.AlertDialog;
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

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerPelicula;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.asiento;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_horario_fragment;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link abm_asientos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link abm_asientos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class abm_asientos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spCinesAsientos;
    private Spinner spSalasAsientos;
    private EditText editTextColumna;
    private EditText editTextFila;
    private EditText editTextAsientoId;
    private Button btnAgregarAsiento;
    private ListView listAsientos;
    private ArrayList<cine>cinesAdmin;
    private ArrayList<sala_cine>sala_cinesAdmin;
    private ArrayList<asiento>asientos;
    private ArrayAdapter<cine>adaptadorCine;
    private ArrayAdapter<sala_cine>adaptadorSalaCine;
    private ProgressBar pbAsiento;
    private comunicador com;
    private cine c = null;
    private sala_cine sc = null;
    private int salaId = 0;

    private int idAsiento;

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    private boolean seEstaActualizando = false;


    private OnFragmentInteractionListener mListener;

    public abm_asientos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment abm_asientos.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_asientos newInstance() {
        abm_asientos fragment = new abm_asientos();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_abm_asientos, container, false);

        com = (comunicador)getActivity();

        editTextColumna = v.findViewById(R.id.editTxtColumna);
        editTextFila = v.findViewById(R.id.editTxtFila);
        btnAgregarAsiento = v.findViewById(R.id.btnAgregarLugar);
        listAsientos = v.findViewById(R.id.listLugares);
        editTextAsientoId = v.findViewById(R.id.editTxtAsientoId);
        pbAsiento = v.findViewById(R.id.pbAsiento);
        spCinesAsientos = v.findViewById(R.id.spCinesAsientos);
        spSalasAsientos = v.findViewById(R.id.spSalasAsientos);


        cinesAdmin = new ArrayList<>();
        sala_cinesAdmin = new ArrayList<>();
        asientos = new ArrayList<>();

        cinesAdmin = com.darCines();
        adaptadorCine = new adaptadorSpinnerSala(getActivity(),cinesAdmin);
        cargarSpinner(cinesAdmin,adaptadorCine);



        spCinesAsientos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                c = adaptadorCine.getItem(position);
                sala_cinesAdmin = com.darSalas(c);
                adaptadorSalaCine = new adaptadorSpinnerPelicula(getActivity(),sala_cinesAdmin);
                cargarSpinnerSala(sala_cinesAdmin,adaptadorSalaCine);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spSalasAsientos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sc = adaptadorSalaCine.getItem(position);
                darAsientos(sc.getID());
                btnAgregarAsiento.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(seEstaActualizando){

                            actualizarAsiento(sc.getID());
                        }else{
                            agregarAsiento(sc.getID());
                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    private void agregarAsiento(int idsala) {

        final String columna = this.editTextColumna.getText().toString().trim();
        final String fila = this.editTextFila.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(columna)) {
            this.editTextColumna.setError("Por favor, ingrese una columna");
            this.editTextColumna.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fila)){

            this.editTextFila.setError("Por favor, ingrese una fila");
            this.editTextFila.requestFocus();
            return;
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("id_sala", String.valueOf(idsala));
        params.put("fila_asiento", columna);
        params.put("columna_asiento",fila);

        //Calling the create hero API
        request request = new request(AppConfig.URL_AGREGAR_ASIENTO + idsala, params, CODE_POST_REQUEST);
        request.execute();


        // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private void eliminarAsiento(int idAsiento,int idSala) {

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        request request = new request(AppConfig.URL_ELIMINAR_ASIENTO + idAsiento + idSala, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void actualizarAsiento(int idsala) {

        String id = this.editTextAsientoId.getText().toString();
        String columna = this.editTextColumna.getText().toString();
        String fila = this.editTextFila.getText().toString().trim();


        //validating the inputs
        if (TextUtils.isEmpty(columna)) {
            this.editTextColumna.setError("Por favor, ingrese una columna");
            this.editTextColumna.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fila)){

            this.editTextFila.setError("Por favor, ingrese una fila");
            this.editTextFila.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("id_sala", String.valueOf(idsala));
        params.put("fila_asiento", columna);
        params.put("columna_asiento",fila);

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        request request = new request(AppConfig.URL_ACTUALIZAR_ASIENTO+ idsala, params, CODE_POST_REQUEST);
        request.execute();

        btnAgregarAsiento.setText("Agregar");

        this.editTextFila.setText("");
        this.editTextColumna.setText("");

        seEstaActualizando = false;
    }
    private void darAsientos(int idsala) {

        request request = new request(AppConfig.URL_LISTAR_ASIENTOS_SALA + idsala, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray asientos) throws JSONException, ParseException {
        //clearing previous heroes
        this.asientos.clear();


        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < asientos.length(); i++) {
            //getting each hero object
            JSONObject obj = asientos.getJSONObject(i);

            //adding the hero to the list
            this.asientos.add(new asiento(
                    obj.getInt("id"),
                    obj.getInt("fila_asiento"),
                    obj.getString("columna_asiento"),
                    obj.getInt("asiento_ocupado")
            ));

        }
        //creating the adapter and setting it to the listview
        asientoAdapter adapter = new asientoAdapter(getActivity().getApplicationContext(),this.asientos);
        listAsientos.setAdapter(adapter);
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
            pbAsiento.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pbAsiento.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("asientos"));
                    com.mandarAsientosSalaAdmin(asientos,sc,c);


                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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
    class asientoAdapter extends ArrayAdapter<asiento> {

        //our hero list
        List<asiento> asientoList;
        Context context = getContext();



        //constructor to get the list
        public asientoAdapter(Context context,List<asiento> asientoList) {
            super(context,R.layout.listar_asiento,asientoList);
            this.asientoList = asientoList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.listar_asiento, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final asiento asiento =asientoList.get(position);

            textViewName.setText(asiento.toString());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    editTextAsientoId.setText(String.valueOf(asiento.getID()));
                    editTextColumna.setText(asiento.getColumna());
                    editTextFila.setText(asiento.getFila());

                    //we will also make the button text to Update
                    btnAgregarAsiento.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + asiento.toString())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarAsiento(asiento.getID(),sc.getID());
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList<asiento>asientos,sala_cine sc,cine c) {
        if (com != null) {
            com.mandarAsientosSalaAdmin(asientos,sc,c);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof comunicador) {
           com = (comunicador) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement comunicador");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void cargarSpinner(ArrayList<cine>cinesAdmin, ArrayAdapter<cine> adaptadorCines){

        spCinesAsientos.setAdapter(adaptadorCines);

    }
    public void cargarSpinnerSala(ArrayList<sala_cine>sala_cinesAdmin, ArrayAdapter<sala_cine> adaptadorSalaCine){

        spSalasAsientos.setAdapter(adaptadorSalaCine);

    }
}
