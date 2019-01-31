package com.example.darkknight.cinemateatralv2;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Clases.asiento;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.reserva_cine;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_cine_fragment;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link confirmar_reserva.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link confirmar_reserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class confirmar_reserva extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CINEID = "param1";
    private static final String ARG_PELIID = "param2";
    private static final String ARG_FUNCIONID = "param3";
    private static final String ARG_HORARIOID = "param4";
    private static final String ARG_ASIENTOS = "param5";
    private static final String ARG_SALAID = "param6";

    private static final int TIPO_COMPLEJO_CINE = 1;
    private static final int TIPO_COMPLEJO_TEATRO = 2;
    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    private TextView codReserva;
    private TextView nombreCine;
    private TextView nombrePeli;
    private TextView fechaReserva;
    private TextView horarioReserva;
    private TextView cantEntradas;
    private TextView nombreSala;
    private TextView pagoTotal;
    private ProgressBar barraProgreso;
    private Button btnGenerarReserva;


    private cine cineReserva = null;
    private pelicula peliReserva = null;
    private horario hReserva = null;
    private funcion funcionReserva = null;
    private sala_cine salaReserva = null;
    private Usuario user = null;
    private reserva_cine rc = null;

    private int cineId;
    private int peliId;
    private int horarioId;
    private int funcionId;
    private int salaId;



    private ArrayList<asiento>asientosReservados;



    private OnFragmentInteractionListener mListener;

    private comunicador com;

    public confirmar_reserva() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment confirmar_reserva.
     */
    // TODO: Rename and change types and number of parameters
    public static confirmar_reserva newInstance(int param1, int param2, int param3, int param4, ArrayList param5,int param6) {
        confirmar_reserva fragment = new confirmar_reserva();
        Bundle args = new Bundle();
        args.putInt(ARG_CINEID, param1);
        args.putInt(ARG_PELIID, param2);
        args.putInt(ARG_FUNCIONID,param3);
        args.putInt(ARG_HORARIOID,param4);
        args.putParcelableArrayList(ARG_ASIENTOS,param5);
        args.putInt(ARG_SALAID,param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_CINEID);
            mParam2 = getArguments().getString(ARG_PELIID);
            mParam3 = getArguments().getString(ARG_HORARIOID);
            mParam4 = getArguments().getString(ARG_FUNCIONID);
            mParam5 = getArguments().getString(ARG_ASIENTOS);
            mParam6 = getArguments().getString(ARG_SALAID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.confirmar_reserva_cine, container, false);

        codReserva = v.findViewById(R.id.txtCodRerserva);
        nombreCine = v.findViewById(R.id.txtNombreCine);
        nombrePeli = v.findViewById(R.id.txtNombrePelicula);
        fechaReserva = v.findViewById(R.id.txtFechaReserva);
        horarioReserva = v.findViewById(R.id.txtHoraReserva);
        cantEntradas = v.findViewById(R.id.txtCantEntradas);
        nombreSala = v.findViewById(R.id.txtNombreSala);
        pagoTotal = v.findViewById(R.id.txtPagoTotal);
        barraProgreso = v.findViewById(R.id.pbConfirmarReserva);
        btnGenerarReserva = v.findViewById(R.id.btnGenerarReserva);

        com = (comunicador)getActivity();

        //Recibo todos los datos de la reserva que se va a generar

        Bundle b = this.getArguments();
        if(b!= null){

            cineId = b.getInt(ARG_CINEID);
            peliId = b.getInt(ARG_PELIID);
            horarioId = b.getInt(ARG_HORARIOID);
            funcionId= b.getInt(ARG_FUNCIONID);
            salaId = b.getInt(ARG_SALAID);
        }

        cineReserva = com.darCineReserva(cineId);
        peliReserva = com.darPeliReserva(peliId);
        hReserva = com.darHorarioReserva(horarioId);
        funcionReserva = com.darFuncionReserva(funcionId);
        salaReserva = com.darSalaCine(cineId,salaId);
        user = SharedPrefManager.getInstance(getActivity()).getUsuario();



        btnGenerarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                agregarReserva(cineReserva.getID(),peliReserva.getID(),user.getID(),funcionReserva.getID(),salaReserva.getID());
                com.mandarReservaAdmin(rc);

            }
        });

        return v;
    }
    private void agregarReserva(int idCine,int idPelicula,int idUsuario,int idSala,int idFuncion) {


        final String fechaReserva = this.fechaReserva.getText().toString().trim();



        HashMap<String, String> params = new HashMap<>();
        params.put("fecha_reserva",fechaReserva);


        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_RESERVA_CINE+ idCine + idPelicula + idUsuario + idSala + idFuncion, params, CODE_POST_REQUEST);
        request.execute();


        // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
                    generaReserva(object.getJSONArray("reservas"));
                    //comunicador.mandarSalasCineAdmin(salasDelCine,cine_sala);

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
    private reserva_cine generaReserva(JSONArray reservas) throws JSONException, ParseException {

        //traversing through all the items in the json array
        //the json we got from the response


        for (int i = 0; i < reservas.length(); i++) {
            //getting each hero object
            JSONObject obj = reservas.getJSONObject(i);
            JSONObject objDia= new JSONObject();

            String fecha = objDia.getString("fecha_reserva");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date nuevaFechaReserva = sdf.parse(fecha);

            //adding the hero to the list
              rc = new reserva_cine(
                    obj.getInt("id_reserva"),
                      obj.getInt("id_usuario"),
                      obj.getInt("id_funcion"),
                      obj.getInt("id_complejo"),
                      obj.getInt("id_espectaculo"),
                      obj.getInt("id_sala"),
                      nuevaFechaReserva,
                      obj.getInt("nro_reserva")
              );
        }

        return rc;

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
}
