package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerDeFechas;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerFuncion;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerPelicula;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link abm_horario_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link abm_horario_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class abm_horario_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private comunicador com;
    private ListView listaVHorarios;
    private Button btnAgregar;
    private Button btnHora;
    private EditText in_hora;
    private EditText horarioId;
    private ProgressBar pbHorario;
    private Spinner spFechas;
    private Spinner spCineHorario;
    private Spinner spSalaHorario;
    private Spinner spPeliculasHorario;
    private ArrayList<funcion>funciones;
    private ArrayList<horario>horarios;
    private ArrayList<cine>cinesHorario;
    private ArrayList<sala_cine>salaHorario;
    private ArrayList<pelicula>peliculaHorario;
    private ArrayAdapter<cine>adaptadorCineHorario;
    private ArrayAdapter<sala_cine>adaptadorSalaCHorario;
    private ArrayAdapter<pelicula>adaptadorPeliHorario;
    private ArrayAdapter<funcion>adaptadorFuncion;
    private int hora,minutos;
    private funcion f;
    private cine c;
    private sala_cine sc;
    private pelicula peli;
    private boolean seEstaActualizando = false;

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;


    public abm_horario_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment abm_horario_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_horario_fragment newInstance(String param1, String param2) {
        abm_horario_fragment fragment = new abm_horario_fragment();
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
        View v = inflater.inflate(R.layout.fragment_abm_horario, container, false);


        listaVHorarios = v.findViewById(R.id.listViewHorarios);
        btnAgregar = v.findViewById(R.id.btnAgregarFuncion);
        btnHora = v.findViewById(R.id.btnHora);
        in_hora = v.findViewById(R.id.editTextHora);
        spCineHorario = v.findViewById(R.id.spCinesHorario);
        spPeliculasHorario = v.findViewById(R.id.spPeliculaHorarios);
        spSalaHorario = v.findViewById(R.id.spSalasHorarios);
        spFechas = v.findViewById(R.id.spFechas);
        horarioId = v.findViewById(R.id.editTextHoraId);
        pbHorario = v.findViewById(R.id.pbHorario);


        com = (comunicador)getActivity();

        horarios = new ArrayList<>();
        funciones = new ArrayList<>();

        cinesHorario = new ArrayList<>();
        salaHorario = new ArrayList<>();
        peliculaHorario = new ArrayList<>();

        cinesHorario = com.darCines();
        adaptadorCineHorario = new adaptadorSpinnerSala(getActivity(),cinesHorario);

        cargarSpinnerCineHorario(cinesHorario,adaptadorCineHorario);


        spCineHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                c = adaptadorCineHorario.getItem(position);
                salaHorario = com.darSalas(c);
                adaptadorSalaCHorario = new adaptadorSpinnerPelicula(getActivity(),salaHorario);
                cargarSpinnerSalaHorario(salaHorario,adaptadorSalaCHorario);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSalaHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sc = adaptadorSalaCHorario.getItem(position);
                peliculaHorario = com.darPelis(sc);
                adaptadorPeliHorario = new adaptadorSpinnerFuncion(getActivity(),peliculaHorario);
                cargarSpinnerPeliculaHorario(peliculaHorario,adaptadorPeliHorario);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPeliculasHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                peli = adaptadorPeliHorario.getItem(position);
                funciones = com.darFunciones(c,sc,peli);
                adaptadorFuncion = new adaptadorSpinnerDeFechas(getActivity(),funciones);
                cargarSpinnerFechas(funciones,adaptadorFuncion);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        in_hora.setText(hourOfDay+":"+minute);
                    }
                },hora,minutos,false);
                tpd.show();

            }
        });

        spFechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                f = adaptadorFuncion.getItem(position);

                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(seEstaActualizando){


                            actualizarHorario(f.getID());

                        }else{

                            agregarHorario(f.getID());

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

    private void agregarHorario(int idfuncion) {

        final String hora = this.in_hora.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(hora)) {
            this.in_hora.setError("Por favor, ingrese una hora para la función");
            this.in_hora.requestFocus();
            return;
        }


        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("id_funcion", String.valueOf(idfuncion));
        params.put("horario_funcion", hora);

        //Calling the create hero API
        request request = new request(AppConfig.URL_AGREGAR_FUNCION_DIA + idfuncion, params, CODE_POST_REQUEST);
        request.execute();


        // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private void eliminarHorario(int id) {

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        request request = new request(AppConfig.URL_BORRAR_FUNCION_DIA + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void actualizarHorario(int idfuncion) {

        String id = this.horarioId.getText().toString();
        String hora = this.in_hora.getText().toString().trim();


        if (TextUtils.isEmpty(hora)) {
            this.in_hora.setError("Por favor, ingrese la hora");
            this.in_hora.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("id_funcion",String.valueOf(idfuncion));
        params.put("horario_funcion",hora);

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        request request = new request(AppConfig.URL_ACTUALIZAR_FUNCION_DIA+ idfuncion, params, CODE_POST_REQUEST);
        request.execute();

        btnAgregar.setText("Agregar");

        this.in_hora.setText("");

        seEstaActualizando = false;
    }
    private void darFunciones(int idfuncion) {

        request request = new request(AppConfig.URL_LISTAR_FUNCION_DIA + idfuncion, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray horarios) throws JSONException, ParseException {
        //clearing previous heroes
        this.horarios.clear();


        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < horarios.length(); i++) {
            //getting each hero object
            JSONObject obj = horarios.getJSONObject(i);

            //adding the hero to the list
            this.horarios.add(new horario(
                    obj.getInt("id"),
                    obj.getInt("hora"),
                    obj.getInt("minutos")
            ));

        }
        //creating the adapter and setting it to the listview
        horarioAdapter adapter = new horarioAdapter(getActivity().getApplicationContext(),this.horarios);
        listaVHorarios.setAdapter(adapter);
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
            pbHorario.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pbHorario.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("funciones"));
                    com.mandarFuncionHorarioAdmin(c,sc,peli,f,horarios);


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
    class horarioAdapter extends ArrayAdapter<horario> {

        //our hero list
        List<horario> horarioList;
        Context context = getContext();



        //constructor to get the list
        public horarioAdapter(Context context,List<horario> horarioList) {
            super(context,R.layout.listar_horarios,horarioList);
            this.horarioList = horarioList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.listar_horarios, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final horario horario =horarioList.get(position);

            textViewName.setText(horario.toString());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    horarioId.setText(String.valueOf(horario.getId()));
                    in_hora.setText(horario.toString());

                    //we will also make the button text to Update
                    btnAgregar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + horario.toString())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarHorario(horario.getId());
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
    public void onButtonPressed(cine c,sala_cine sc,pelicula peli,funcion f,ArrayList<horario>horarios) {
        if (com != null) {
            com.mandarFuncionHorarioAdmin(c,sc,peli,f,horarios);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            com = (comunicador) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement comunicador");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        com = null;
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

    public void cargarSpinnerFechas(ArrayList<funcion>funcionAdmin, ArrayAdapter<funcion> adaptadorFuncion){

        spFechas.setAdapter(adaptadorFuncion);

    }
    public void cargarSpinnerCineHorario(ArrayList<cine>cinesHorario, ArrayAdapter<cine> adaptadorCineHorario){

        spCineHorario.setAdapter(adaptadorCineHorario);

    }
    public void cargarSpinnerSalaHorario(ArrayList<sala_cine>salaHorario, ArrayAdapter<sala_cine> adaptadorSalaCHorario){

        spSalaHorario.setAdapter(adaptadorSalaCHorario);

    }
    public void cargarSpinnerPeliculaHorario(ArrayList<pelicula>peliculaHorario, ArrayAdapter<pelicula> adaptadorPeliHorario){

        spPeliculasHorario.setAdapter(adaptadorPeliHorario);

    }
}
