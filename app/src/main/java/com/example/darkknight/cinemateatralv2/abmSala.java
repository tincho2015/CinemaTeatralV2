package com.example.darkknight.cinemateatralv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Clases.bienvenida;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.sala;
import com.example.darkknight.cinemateatralv2.Clases.teatro;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link abmSala.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class abmSala extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText txtDescripcionSala;
    private EditText txtPrecioSala;
    private EditText txtTipoSala;
    private Button btnAgregarSala;
    private int idCine;
    private ProgressBar barraEspera;
    private EditText showIdCine;

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    public abmSala() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Agregar una sala");


        showIdCine = view.findViewById(R.id.showIdCine);
        txtDescripcionSala = view.findViewById(R.id.editTextDesSala);
        txtPrecioSala = view.findViewById(R.id.editTextPrecio);
        txtTipoSala = view.findViewById(R.id.editTextTipoSala);
        btnAgregarSala = view.findViewById(R.id.btnAgregarSala);
        barraEspera = view.findViewById(R.id.progresoBarra);

        Bundle bundle = this.getArguments();
        if(bundle != null){

            idCine = bundle.getInt("id_cine");
            showIdCine.setText(bundle.getInt("id_cine"));
        }

        btnAgregarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregarSala(idCine);
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abm_sala_fragment, container, false);
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
    private void agregarSala(int idcine) {


        String nombre_sala = this.txtDescripcionSala.getText().toString().trim();
        String tipo_sala = this.txtTipoSala.getText().toString().trim();
        String precio_sala = this.txtPrecioSala.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(nombre_sala)) {
            txtDescripcionSala.setError("Por favor, ingrese el nombre de la sala");
            txtDescripcionSala.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tipo_sala)) {
            txtTipoSala.setError("Por favor, ingrese un tipo de sala");
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




}
