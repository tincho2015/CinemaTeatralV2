package com.example.darkknight.cinemateatralv2;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.reserva;
import com.example.darkknight.cinemateatralv2.Clases.reserva_cine;
import com.example.darkknight.cinemateatralv2.Fragmentos.abmSala;
import com.example.darkknight.cinemateatralv2.Fragmentos.abm_cine_fragment;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link lista_espectaculos_puntuar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link lista_espectaculos_puntuar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lista_espectaculos_puntuar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listViewEspectaculos;
    private ArrayList<reserva>reservasUsuario;

    private ArrayAdapter<reserva>adaptadorReserva;

    private comunicador com;





    private OnFragmentInteractionListener mListener;

    public lista_espectaculos_puntuar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lista_espectaculos_puntuar.
     */
    // TODO: Rename and change types and number of parameters
    public static lista_espectaculos_puntuar newInstance(String param1, String param2) {
        lista_espectaculos_puntuar fragment = new lista_espectaculos_puntuar();
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
        View v = inflater.inflate(R.layout.fragment_lista_espectaculos_puntuar, container, false);

        listViewEspectaculos = v.findViewById(R.id.listViewEspectaculos);

        reservasUsuario = new ArrayList<>();

        reservasUsuario = com.d



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
            barra.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barra.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refrescarLista(object.getJSONArray("reservas"));
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
    class reservasUsuarioAdapter extends ArrayAdapter<reserva_cine> {

        //our hero list
        List<reserva_cine> reservasList;
        Context context = getContext();



        //constructor to get the list
        public reservasUsuarioAdapter(Context context,List<reserva_cine> reservasList) {
            super(context, R.layout.lista_espectaculos, reservasList);
            this.reservasList = reservasList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_cines, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewPuntuar = listViewItem.findViewById(R.id.textViewPuntuar);


            final reserva_cine reserva_c = reservasList.get(position);

            textViewName.setText(reserva_c.getPeliculaId());

            //attaching click listener to update
            textViewPuntuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FragmentManager fragmentm = getActivity().getFragmentManager();
                    ft = fragmentm.beginTransaction();

                    fPuntuar = puntuar_espectaculo.newInstance();
                    tagSala = fPuntuar.getClass().getName();
                    ft.replace(R.id.content_frame, fPuntuar, fPuntuar.getClass().getName());
                    ft.addToBackStack(tagSala);
                    ft.commit();

                }
            });


            return listViewItem;
        }

    }
    private void refrescarLista(JSONArray cines) throws JSONException {
        //clearing previous heroes
        ListaCines.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < cines.length(); i++) {
            //getting each hero object
            JSONObject obj = cines.getJSONObject(i);

            //adding the hero to the list
            ListaCines.add(new cine(
                    obj.getString("direccion"),
                    obj.getInt("ID"),
                    obj.getString("nombre"),
                    obj.getString("telefono"),
                    obj.getString("url")

            ));
        }
        //creating the adapter and setting it to the listview
        abm_cine_fragment.cineAdapter adapter = new abm_cine_fragment.cineAdapter(getActivity().getApplicationContext(),ListaCines);
        this.cines.setAdapter(adapter);
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
