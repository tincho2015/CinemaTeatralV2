package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
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

import com.example.darkknight.cinemateatralv2.BuildConfig;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Helpers.FragmentNavigationManager;
import com.example.darkknight.cinemateatralv2.Helpers.FragmentUtils;
import com.example.darkknight.cinemateatralv2.Interfaces.NavigationManager;
import com.example.darkknight.cinemateatralv2.Interfaces.comunicador;
import com.example.darkknight.cinemateatralv2.R;

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
 * {@link abm_cine_fragment.OnFragmentInteractionListenerListaCines} interface
 * to handle interaction events.
 * Use the {@link abm_cine_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class abm_cine_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "listaCines";

    private comunicador comunicador;

    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private EditText cineid;
    private EditText urlCine;
    private Button aceptar;
    private Button agregarNuevaSala;
    private ProgressBar barra;
    private List<cine> ListaCines;
    private ListView cines;
    private Fragment fSala = null;
    private Fragment currentFragment = null;
    private List<Fragment>fSalas;
    android.app.FragmentTransaction ft = null;
    private boolean existe = false;
    private String tagSala = "";

    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    boolean seEstaActualizando = false;

    // TODO: Rename and change types of parameters
    private String mParam1;



    public abm_cine_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment abm_cine_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_cine_fragment newInstance() {
        abm_cine_fragment fragment = new abm_cine_fragment();
        return fragment;
    }
    private void darCines() {

        request request = new request(AppConfig.URL_LISTAR_CINES, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void agregarCine() {

        final String nombre = this.nombre.getText().toString().trim();
        final String direccion = this.direccion.getText().toString().trim();
        final String telefono = this.telefono.getText().toString().trim();
        final String url = this.urlCine.getText().toString().trim();



        //validating the inputs
        if (TextUtils.isEmpty(nombre)) {
            this.nombre.setError("Por favor, ingrese el nombre");
            this.nombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(direccion)) {
            this.direccion.setError("Por favor, ingrese la dirección");
            this.direccion.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(telefono)){

            this.telefono.setError("Por favor, ingrese nro. de teléfono");
            this.telefono.requestFocus();
        }
        if(!Patterns.WEB_URL.matcher(url).matches()){

            this.urlCine.setError("Por favor, ingrese un sitio web válido");
        }


        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("direccion", direccion);
        params.put("telefono", telefono);
        params.put("url",url);




        //Calling the create hero API
        request request = new request(AppConfig.URL_CREAR_CINE, params, CODE_POST_REQUEST);
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
                    refrescarLista(object.getJSONArray("cines"));
                    comunicador.mandarCineAdmin((ArrayList<cine>) ListaCines);


                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
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
    class cineAdapter extends ArrayAdapter<cine> {

        //our hero list
        List<cine> cineList;
        Context context = getContext();



        //constructor to get the list
        public cineAdapter(Context context,List<cine> cineList) {
            super(context, R.layout.activity_listar_cines, cineList);
            this.cineList = cineList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_cines, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final cine cine = cineList.get(position);

            textViewName.setText(cine.getNombre());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    cineid.setText(String.valueOf(cine.getID()));
                    nombre.setText(cine.getNombre());
                    direccion.setText(cine.getDireccion());
                    telefono.setText(cine.getTelefono());
                    urlCine.setText(cine.getUrl());


                    //we will also make the button text to Update
                    aceptar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + cine.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarCine(cine.getID());
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
    private void actualizarCine() {

        String cid = this.cineid.getText().toString();
        String nombre = this.nombre.getText().toString().trim();
        String telefono = this.telefono.getText().toString().trim();
        String direccion = this.direccion.getText().toString().trim();
        String url =  this.urlCine.getText().toString().trim();


        if (TextUtils.isEmpty(nombre)) {
            this.nombre.setError("Por favor, ingrese el nombre");
            this.nombre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(telefono)) {
            this.telefono.setError("Por favor, ingrese el telefono");
            this.telefono.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(direccion)){
            this.direccion.setError("Por favor, ingrese la dirección");
            this.direccion.requestFocus();
        }
        if(!Patterns.WEB_URL.matcher(url).matches()){

            this.urlCine.setError("Por favor, ingrese un sitio web válido");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id",cid);
        params.put("nombre", nombre);
        params.put("telefono", telefono);
        params.put("direccion", direccion);
        params.put("url",url);

     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        request request = new request(AppConfig.URL_ACTUALIZAR_CINE, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");

        this.nombre.setText("");
        this.telefono.setText("");
        this.direccion.setText("");
        this.urlCine.setText("");


        seEstaActualizando = false;
    }
    private void eliminarCine(int id) {

     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        request request = new request(AppConfig.URL_ELIMINAR_CINE + id, null, CODE_GET_REQUEST);
        request.execute();
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
        cineAdapter adapter = new cineAdapter(getActivity().getApplicationContext(),ListaCines);
        this.cines.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        getActivity().setTitle("ABM de Cine");

        View view = inflater.inflate(R.layout.abm_cine,container,false);

        nombre = view.findViewById(R.id.editTextNombreC);
        telefono = view.findViewById(R.id.editTextTelefonoC);
        direccion = view.findViewById(R.id.editTextDireccionC);
        barra = view.findViewById(R.id.progressBar);
        cineid = view.findViewById(R.id.editTextCineID);
        urlCine = view.findViewById(R.id.editTextURL);
        comunicador = (comunicador)getActivity();
        agregarNuevaSala = view.findViewById(R.id.btnSala);

        agregarNuevaSala.setVisibility(view.VISIBLE);

        ListaCines = new ArrayList<>();
        fSalas = new ArrayList<>();

        cines = view.findViewById(R.id.listViewCines);

        aceptar = view.findViewById(R.id.btnAM);


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seEstaActualizando) {


                    actualizarCine();

                }else
                {


                    agregarCine();

                }
            }
        });
        agregarNuevaSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentm = getActivity().getFragmentManager();
                ft = fragmentm.beginTransaction();

                fSala = abmSala.newInstance();
                tagSala = fSala.getClass().getName();
                ft.replace(R.id.content_frame, fSala, fSala.getClass().getName());
                ft.addToBackStack(tagSala);
                ft.commit();
            }
        });


        darCines();
        return view;
    }

    public boolean isBackStackExists(FragmentManager fm,String tag) {

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            String backStackTag = fm.getBackStackEntryAt(i).getName();
            if (!backStackTag.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList<cine>listaCines) {
        if (comunicador != null) {
            comunicador.mandarCineAdmin(listaCines);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof comunicador) {
            comunicador = (comunicador) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement comunicador");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        comunicador = null;

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
    public interface OnFragmentInteractionListenerListaCines {
        // TODO: Update argument type and name
        void mandarListaCines(List<cine>listaCines);
    }


}
