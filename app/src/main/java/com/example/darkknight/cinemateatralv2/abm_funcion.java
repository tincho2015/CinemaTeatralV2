package com.example.darkknight.cinemateatralv2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerFuncion;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerPelicula;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.Dia;
import com.example.darkknight.cinemateatralv2.Clases.Fecha;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link abm_funcion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link abm_funcion#newInstance} factory method to
 * create an instance of this fragment.
 */

public class abm_funcion extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private EditText editFecha;
    private EditText funcionId;
    private Button btnFuncion;
    private Button btnFecha;
    private ProgressBar barraProgreso;
    private Spinner spPeliculas;
    private Spinner spSalas;
    private Spinner spCines;
    private ListView listaFunciones;
    private ArrayList<funcion>funciones;
    private ArrayList<pelicula>peliculasAdmin;
    private ArrayList<sala_cine>salasAdmin;
    private ArrayList<cine>cinesAdmin;
    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;
    private boolean seEstaActualizando = false;
    private int dia,mes,año;
    private ArrayAdapter<pelicula>adaptadorPelicula;
    private ArrayAdapter<sala_cine>adaptadorSalasCine;
    private ArrayAdapter<cine>adaptadorCine;
    private comunicador com;
    private sala_cine sc;
    private pelicula p;
    private cine c;
    private Dia fecha;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public abm_funcion() {
        // Required empty public constructor
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
            barraProgreso.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barraProgreso.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("funciones"));
                   //com.


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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment abm_funcion.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_funcion newInstance(String param1, String param2) {
        abm_funcion fragment = new abm_funcion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.abm_funcion_fragment, container, false);



        //Vincular los componentes del layout

        editFecha = view.findViewById(R.id.editFecha);
        funcionId = view.findViewById(R.id.editID);
        btnFuncion = view.findViewById(R.id.btnNuevaFuncion);
        btnFecha = view.findViewById(R.id.btnFecha);
        spPeliculas = view.findViewById(R.id.spPeliculas);
        spSalas = view.findViewById(R.id.spSalas);
        spCines = view.findViewById(R.id.spCines);
        listaFunciones = view.findViewById(R.id.listFunciones);
        barraProgreso = view.findViewById(R.id.pbFuncion);

        com = (comunicador)getActivity();

        peliculasAdmin = new ArrayList<>();
        salasAdmin = new ArrayList<>();
        cinesAdmin = new ArrayList<>();
        funciones = new ArrayList<>();

        cinesAdmin = com.darCines();

        adaptadorCine = new adaptadorSpinnerSala(getActivity(),cinesAdmin);

        cargarSpinnerCines(cinesAdmin,adaptadorCine);


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                año = c.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        editFecha.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                    }
                }
                ,año,mes,dia);
                dpd.show();
            }

        });

       //Seleccion de elemento y cargado de spinners
        spCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                c = adaptadorCine.getItem(position);
                salasAdmin = com.darSalas(c);
                adaptadorSalasCine = new adaptadorSpinnerPelicula(getActivity(),salasAdmin);
                cargarSpinnerSalas(salasAdmin,adaptadorSalasCine);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPeliculas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    p = adaptadorPelicula.getItem(position);
                    darFunciones(p.getID());
                    btnFuncion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(seEstaActualizando){

                            actualizarFuncion(p.getID());
                        }else {

                            agregarFuncion(p.getID());

                        }
                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSalas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                sc = adaptadorSalasCine.getItem(position);
                peliculasAdmin = com.darPelis(sc);
                adaptadorPelicula = new adaptadorSpinnerFuncion(getActivity(),peliculasAdmin);
                cargarSpinnerPeliculas(peliculasAdmin,adaptadorPelicula);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    //Agregar nueva funcion

    private void agregarFuncion(int idpeli) {

        final String fecha = this.editFecha.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(fecha)) {
            this.editFecha.setError("Por favor, ingrese la fecha de la función");
            this.editFecha.requestFocus();
            return;
        }


        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("fecha_funcion", fecha);
        params.put("id_tipo_espectaculo", String.valueOf(idpeli));

        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_FUNCION + idpeli, params, CODE_POST_REQUEST);
        request.execute();


        // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private void eliminarFuncion(int id) {

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        request request = new request(AppConfig.URL_ELIMINAR_FUNCION + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void darFunciones(int idPeli) {

        request request = new request(AppConfig.URL_LISTAR_FUNCIONES + idPeli, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void actualizarFuncion(int idpeli) {

        String id = this.funcionId.getText().toString();
        String fecha = this.editFecha.getText().toString().trim();


        if (TextUtils.isEmpty(fecha)) {
            this.editFecha.setError("Por favor, ingrese la fecha");
            this.editFecha.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id_funcion",id);
        params.put("fecha_funcion",fecha);

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        request request = new request(AppConfig.URL_ACTUALIZAR_FUNCION + idpeli, params, CODE_POST_REQUEST);
        request.execute();

        btnFuncion.setText("Agregar");

        this.editFecha.setText("");

        seEstaActualizando = false;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    private void refrescarLista(JSONArray funciones, Dia d) throws JSONException, ParseException {
        //clearing previous heroes
        this.funciones.clear();


        //traversing through all the items in the json array
        //the json we got from the response
      for (int i = 0; i < funciones.length(); i++) {
            //getting each hero object
           JSONObject obj = funciones.getJSONObject(i);
            String fecha = obj.getString("fecha_funcion");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date nuevaFecha = sdf.parse(fecha);
            //sdf.format(fecha);

            String hora = obj.getString("horario_funcion");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:MM");
            //sdf.format(hora);
            Date nuevaHora = sdf2.parse(hora);

            //adding the hero to the list
           this.funciones.add(new funcion(
                    ,
                    obj.getInt("id_funcion")
            ));

        }
        //creating the adapter and setting it to the listview
        funcionAdapter adapter = new funcionAdapter(getActivity().getApplicationContext(),this.funciones);
        listaFunciones.setAdapter(adapter);
    }
    public static String formatearDateFromString(String inputFormat, String outputFormat, String inputDate){
        if(inputFormat.equals("")){ // if inputFormat = "", set a default input format.
            inputFormat = "yyyy-MM-dd";
        }
        if(outputFormat.equals("")){
            outputFormat = "EEEE d 'de' MMMM"; // if inputFormat = "", set a default output format.
        }
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }
    class funcionAdapter extends ArrayAdapter<funcion> {

        //our hero list
        List<funcion> funcionList;
        Context context = getContext();



        //constructor to get the list
        public funcionAdapter(Context context,List<funcion> funcionList) {
            super(context,R.layout.listar_funciones,funcionList);
            this.funcionList = funcionList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.listar_funciones, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final funcion funcion = funcionList.get(position);

            textViewName.setText(funcion.toString());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    funcionId.setText(String.valueOf(funcion.getID()));
                    editFecha.setText(funcion.toString());

                    //we will also make the button text to Update
                    btnFuncion.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + funcion.toString())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarFuncion(funcion.getID());
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
    public void cargarSpinnerSalas(ArrayList<sala_cine>salaAdmin, ArrayAdapter<sala_cine>adaptadorSalaCine){

       spSalas.setAdapter(adaptadorSalaCine);

    }
    public void cargarSpinnerPeliculas(ArrayList<pelicula>peliculasAdmin, ArrayAdapter<pelicula>adaptadorPelicula){

        spPeliculas.setAdapter(adaptadorPelicula);

    }
    public void cargarSpinnerCines(ArrayList<cine>cinesAdmin,ArrayAdapter<cine>adaptadorCine){
        spCines.setAdapter(adaptadorCine);
    }

}
