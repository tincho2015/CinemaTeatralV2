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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String ARG_IDSALA = "param1";
    private static final String ARG_IDCINE = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextColumna;
    private EditText editTextFila;
    private EditText editTextAsientoId;
    private Button btnAgregarAsiento;
    private ListView listAsientos;
    private int salaId = 0;
    private int cineId = 0;
    private ArrayList<asiento>asientos;
    private ProgressBar pbAsiento;
    private comunicador com;
    private cine c = null;
    private sala_cine sc = null;

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
     * @param param1 Parameter 1.
     * @return A new instance of fragment abm_asientos.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_asientos newInstance(int param1,int param2) {
        abm_asientos fragment = new abm_asientos();
        Bundle args = new Bundle();
        args.putInt(ARG_IDSALA, param1);
        args.putInt(ARG_IDCINE,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_IDSALA);
            mParam2 = getArguments().getString(ARG_IDCINE);
        }
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

        c = com.darCine(cineId);
        sc = com.darSalaCine(cineId,salaId);

        Bundle b = this.getArguments();

        if(b !=null){

            salaId = b.getInt(ARG_IDSALA);
            cineId = b.getInt(ARG_IDCINE);

        }




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
    private void eliminarAsiento(int id) {

        //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        request request = new request(AppConfig.URL_ELIMINAR_ASIENTO + id, null, CODE_GET_REQUEST);
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
    private void refrescarLista(JSONArray horarios) throws JSONException, ParseException {
        //clearing previous heroes
        this.asientos.clear();


        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < horarios.length(); i++) {
            //getting each hero object
            JSONObject obj = horarios.getJSONObject(i);

            //adding the hero to the list
            this.asientos.add(new asiento(
                    obj.getInt("id"),
                    obj.getBoolean("asiento_ocupado"),
                    obj.getInt("asiento_columna"),
                    obj.getString("asiento_fila")
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
        public asientoAdapter(Context context,List<asiento> horarioList) {
            super(context,R.layout.listar_asiento,horarioList);
            this.asientoList = asientoList;
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

            final asiento asiento =asientoList.get(position);

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
