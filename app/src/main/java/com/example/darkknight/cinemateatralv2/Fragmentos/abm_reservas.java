package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerDeFechas;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerFuncion;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerHorarios;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.Dia;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.reserva_cine;
import com.example.darkknight.cinemateatralv2.Clases.reserva_teatro;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link abm_reservas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link abm_reservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class abm_reservas extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private comunicador com;
    private Spinner spCines;
    private Spinner spPeliculas;
    private Spinner spFunciones;
    private Spinner spHorarios;
    private Button btnSeleccionLugar;
    private cine cineReserva = null;
    private pelicula peliculaReserva = null;
    private funcion funcionReserva = null;
    private Dia diaReserva = null;
    private horario horarioReserva = null;
    private reserva_cine reservaCine = null;
    private reserva_teatro reservaTeatro = null;
    private ArrayList<reserva_cine>reservasHechasCines;
    private ArrayList<reserva_teatro>reservasrHechasTeatros;
    private ArrayList<cine>cinesAdmin;
    private ArrayList<pelicula>peliculasAdmin;
    private ArrayList<funcion>funcionesAdmin;
    private ArrayList<horario>horariosAdmin;
    private ArrayAdapter<cine>adaptadorCine;
    private ArrayAdapter<pelicula>adaptadorPelicula;
    private ArrayAdapter<funcion>adaptadorFuncion;
    private ArrayAdapter<horario>adaptadorHorario;
    private FragmentTransaction ft = null;
    private Fragment fSeleccionLugares = null;

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public abm_reservas() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment abm_reservas.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_reservas newInstance() {
        abm_reservas fragment = new abm_reservas();
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
       View v = inflater.inflate(R.layout.activity_alta_de_reservas_cine,container);

        com = (comunicador)getActivity();

        btnSeleccionLugar = v.findViewById(R.id.btnLugar);


        spCines = v.findViewById(R.id.spCinesReserva);
        spFunciones = v.findViewById(R.id.spFuncionReserva);
        spPeliculas = v.findViewById(R.id.spPeliReserva);
        spHorarios = v.findViewById(R.id.spHorarioReserva);

        reservasHechasCines = new ArrayList<>();
        reservasrHechasTeatros = new ArrayList<>();

        cinesAdmin = new ArrayList<>();
        peliculasAdmin = new ArrayList<>();
        funcionesAdmin = new ArrayList<>();
        horariosAdmin = new ArrayList<>();


        cinesAdmin = com.darCines();

        adaptadorCine = new adaptadorSpinnerSala(getActivity(),cinesAdmin);
        cargarSpinnerCinesReserva(cinesAdmin,adaptadorCine);


        adaptadorPelicula = new adaptadorSpinnerFuncion(getActivity(),peliculasAdmin);

        spCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Selecciono el cine para el cual quiero hacer la reserva.
                cineReserva = adaptadorCine.getItem(position);
                peliculasAdmin = com.darPelisTotal(cineReserva);
                adaptadorPelicula = new adaptadorSpinnerFuncion(getActivity(),peliculasAdmin);
                cargarSpinnePeliculasReserva(peliculasAdmin,adaptadorPelicula);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPeliculas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //una vez seleccionado el cine, selecciono la pelicula de ese cine.
                peliculaReserva = adaptadorPelicula.getItem(position);
                funcionesAdmin = com.darFuncionesPelicula(peliculaReserva);
                adaptadorFuncion = new adaptadorSpinnerDeFechas(getActivity(),funcionesAdmin);
                cargarSpinnerFuncionesReserva(funcionesAdmin,adaptadorFuncion);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFunciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Una vez seleccionado la pelicula, selecciono la funcion de esa pelicula.
                funcionReserva = adaptadorFuncion.getItem(position);
                diaReserva = funcionReserva.getDiaFuncion();
                horariosAdmin = com.darHorariosPorFuncion(funcionReserva,diaReserva);
                adaptadorHorario = new adaptadorSpinnerHorarios(getActivity(),horariosAdmin);
                cargarSpinnerHorariosReserva(horariosAdmin,adaptadorHorario);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spHorarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                horarioReserva = adaptadorHorario.getItem(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSeleccionLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //una vez que tengo todo lo otro selecionado
                //direcciona al fragment de seleccion de lugares
                FragmentManager fragmentm = getActivity().getFragmentManager();
                ft = fragmentm.beginTransaction();
                String tagLugar;
                fSeleccionLugares = seleccion_lugares.newInstance(cineReserva.getID(),peliculaReserva.getID(),funcionReserva.getID(),horarioReserva.getId());
                tagLugar = fSeleccionLugares.getClass().getName();
                ft.replace(R.id.content_frame, fSeleccionLugares, fSeleccionLugares.getClass().getName());
                ft.addToBackStack(tagLugar);
                ft.commit();


            }
        });




        return v;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

    public void cargarSpinnePeliculasReserva(ArrayList<pelicula>peliculasAdmin, ArrayAdapter<pelicula> adaptadorPelisReserva){

        spPeliculas.setAdapter(adaptadorPelisReserva);

    }
    public void cargarSpinnerCinesReserva(ArrayList<cine>cineAdmin, ArrayAdapter<cine>adaptadorCinesReserva){

        spCines.setAdapter(adaptadorCinesReserva);

    }
    public void cargarSpinnerFuncionesReserva(ArrayList<funcion>funcionAdmin, ArrayAdapter<funcion>adaptadorFuncionReserva){

        spFunciones.setAdapter(adaptadorFuncionReserva);
    }
    public void cargarSpinnerHorariosReserva(ArrayList<horario>horariosAdmin, ArrayAdapter<horario>adaptadorHorarioReserva){

        spHorarios.setAdapter(adaptadorHorarioReserva);
    }
}
