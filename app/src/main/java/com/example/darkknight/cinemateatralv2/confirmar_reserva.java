package com.example.darkknight.cinemateatralv2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView codReserva;
    private TextView nombreCine;
    private TextView nombrePeli;
    private TextView fechaReserva;
    private TextView horarioReserva;
    private TextView cantEntradas;
    private TextView nombreSala;
    private TextView pagoTotal;



    private OnFragmentInteractionListener mListener;

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
    public static confirmar_reserva newInstance(String param1, String param2) {
        confirmar_reserva fragment = new confirmar_reserva();
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

        View v = inflater.inflate(R.layout.confirmar_reserva_cine, container, false);
        codReserva = v.findViewById(R.id.txtCodRerserva);
        nombreCine = v.findViewById(R.id.txtNombreCine);
        nombrePeli = v.findViewById(R.id.txtNombrePelicula);
        fechaReserva = v.findViewById(R.id.txtFechaReserva);
        horarioReserva = v.findViewById(R.id.txtHoraReserva);
        cantEntradas = v.findViewById(R.id.txtCantEntradas);
        nombreSala = v.findViewById(R.id.txtNombreSala);
        pagoTotal = v.findViewById(R.id.txtPagoTotal);


        //Recibo todos los datos de la reserva que se va a generar

        //si sale todo OK, se genera la nueva instancia de reserva
        // y almaceno la reserva








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
}
