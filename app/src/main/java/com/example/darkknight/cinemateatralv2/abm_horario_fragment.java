package com.example.darkknight.cinemateatralv2;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.darkknight.cinemateatralv2.Clases.funcion;
import com.example.darkknight.cinemateatralv2.Clases.horario;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;

import java.util.ArrayList;
import java.util.Calendar;


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
    private Spinner spFechas;
    private ArrayList<funcion>funciones;
    private ArrayList<horario>horarios;
    private int hora,minutos;
    private funcion f;


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
        spFechas = v.findViewById(R.id.spFechas);
        btnHora = v.findViewById(R.id.btnHora);
        in_hora = v.findViewById(R.id.editTextHora);

        com = (comunicador)getActivity();

        horarios = new ArrayList<>();
        funciones = new ArrayList<>();





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

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (com != null) {
            com.agregarHorariosAdmin(this.horarios,f);
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
}
