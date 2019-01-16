package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.darkknight.cinemateatralv2.Clases.asiento;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.R;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

import java.sql.BatchUpdateException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link seleccion_lugares.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link seleccion_lugares#newInstance} factory method to
 * create an instance of this fragment.
 */
public class seleccion_lugares extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CINEID = "cineID";
    private static final String ARG_PELIID = "peliID";
    private static final String ARG_FUNCIONID = "funcionID";
    private static final String ARG_HORARIOID = "horarioID";

    private Button lugarA1;
    private Button lugarA2;
    private Button lugarA3;
    private Button lugarA4;
    private Button lugarA5;
    private Button lugarA6;
    private Button lugarA7;

    private Button lugarB1;
    private Button lugarB2;
    private Button lugarB3;
    private Button lugarB4;
    private Button lugarB5;
    private Button lugarB6;
    private Button lugarB7;

    private Button lugarC1;
    private Button lugarC2;
    private Button lugarC3;
    private Button lugarC4;
    private Button lugarC5;
    private Button lugarC6;
    private Button lugarC7;

    private Button lugarD1;
    private Button lugarD2;
    private Button lugarD3;
    private Button lugarD4;
    private Button lugarD5;
    private Button lugarD6;
    private Button lugarD7;

    private Button lugarE1;
    private Button lugarE2;
    private Button lugarE3;
    private Button lugarE4;
    private Button lugarE5;
    private Button lugarE6;
    private Button lugarE7;

    private ArrayList<asiento>asientosSeleccionados;

    private cine cineReservaFinal = null;
    private pelicula peliReservaFinal = null;
    private funcion funcionReservaFinal = null;
    private horario horarioReservaFinal = null;

    private comunicador com;

    private int cineId = 0;
    private int peliculaId = 0;
    private int funcionId = 0;
    private int horarioId = 0;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mparam3;
    private String mparam4;

    private OnFragmentInteractionListener mListener;

    public seleccion_lugares() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment seleccion_lugares.
     */
    // TODO: Rename and change types and number of parameters
    public static seleccion_lugares newInstance(int param1, int param2,int param3,int param4) {
        seleccion_lugares fragment = new seleccion_lugares();
        Bundle args = new Bundle();
        args.putInt(ARG_CINEID,param1);
        args.putInt(ARG_PELIID,param2);
        args.putInt(ARG_FUNCIONID,param3);
        args.putInt(ARG_HORARIOID,param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_CINEID);
            mParam2 = getArguments().getString(ARG_PELIID);
            mparam3 = getArguments().getString(ARG_HORARIOID);
            mparam4 = getArguments().getString(ARG_FUNCIONID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seleccion_lugares, container, false);

        lugarA1 = v.findViewById(R.id.btnA1);
        lugarA2 = v.findViewById(R.id.btnA2);
        lugarA3 = v.findViewById(R.id.btnA3);
        lugarA4 = v.findViewById(R.id.btnA4);
        lugarA5 = v.findViewById(R.id.btnA5);
        lugarA6 = v.findViewById(R.id.btnA6);
        lugarA7 = v.findViewById(R.id.btnA7);

        lugarB1 = v.findViewById(R.id.btnB1);
        lugarB2 = v.findViewById(R.id.btnB2);
        lugarB3 = v.findViewById(R.id.btnB3);
        lugarB4 = v.findViewById(R.id.btnB4);
        lugarB5 = v.findViewById(R.id.btnB5);
        lugarB6 = v.findViewById(R.id.btnB6);
        lugarB7 = v.findViewById(R.id.btnB7);

        lugarC1 = v.findViewById(R.id.btnC1);
        lugarC2 = v.findViewById(R.id.btnC2);
        lugarC3 = v.findViewById(R.id.btnC3);
        lugarC4 = v.findViewById(R.id.btnC4);
        lugarC5 = v.findViewById(R.id.btnC5);
        lugarC6 = v.findViewById(R.id.btnC6);
        lugarC7 = v.findViewById(R.id.btnC7);

        lugarD1 = v.findViewById(R.id.btnD1);
        lugarD2 = v.findViewById(R.id.btnD2);
        lugarD3 = v.findViewById(R.id.btnD3);
        lugarD4 = v.findViewById(R.id.btnD4);
        lugarD5 = v.findViewById(R.id.btnD5);
        lugarD6 = v.findViewById(R.id.btnD6);
        lugarD7 = v.findViewById(R.id.btnD7);

        lugarE1 = v.findViewById(R.id.btnE1);
        lugarE2 = v.findViewById(R.id.btnE2);
        lugarE3 = v.findViewById(R.id.btnE3);
        lugarE4 = v.findViewById(R.id.btnE4);
        lugarE5 = v.findViewById(R.id.btnE5);
        lugarE6 = v.findViewById(R.id.btnE6);
        lugarE7 = v.findViewById(R.id.btnE7);



        Bundle bundle = this.getArguments();
        if(bundle != null){

            cineId = bundle.getInt(ARG_CINEID);
            peliculaId = bundle.getInt(ARG_PELIID);
            funcionId = bundle.getInt(ARG_FUNCIONID);
            horarioId = bundle.getInt(ARG_HORARIOID);
        }

        cineReservaFinal = com.darCineReserva(cineId);
        funcionReservaFinal = com.darFuncionReserva(funcionId);
        peliReservaFinal =com.darPeliReserva(peliculaId);
        horarioReservaFinal = com.darHorarioReserva(horarioId);





    return v;


    }

    private void agregarAsiento(){


    }

    private void agregarReserva(int idCine,int idPelicula,int idFuncion,int idHorario) {

        //A partir de lo seleccionadoo en los spinners, linkear cada parametro con su correspondiente seleccion.


        //Rellenar el hashmap con lo seleccionado
        /*
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("direccion", direccion);
        params.put("telefono", telefono);
        params.put("url",url);
        */



        //Calling the create hero API
        /*
        request request = new request(AppConfig.URL_CREAR_CINE, params, CODE_POST_REQUEST);
        request.execute();
        */

        // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
}
