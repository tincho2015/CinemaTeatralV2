package com.example.darkknight.cinemateatralv2.Fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.Toast;

import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerPelicula;
import com.example.darkknight.cinemateatralv2.Adaptadores.adaptadorSpinnerSala;
import com.example.darkknight.cinemateatralv2.Clases.cine;
import com.example.darkknight.cinemateatralv2.Clases.jSonParser;
import com.example.darkknight.cinemateatralv2.Clases.pelicula;
import com.example.darkknight.cinemateatralv2.Clases.sala_cine;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
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
 * {@link comunicador} interface
 * to handle interaction events.
 * Use the {@link abm_pelicula_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class abm_pelicula_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText titulo;
    private EditText director;
    private EditText genero;
    private EditText peliculaid;
    private EditText sinopsis;
    private EditText duracion;
    private EditText protagonistas;
    private EditText clasificacion;
    private Button aceptar;
    private Button agregarFuncion;
    private ProgressBar barra;
    private ArrayList<pelicula> ListaPeliculas;
    private ArrayList<sala_cine>salasAdmin;
    private ArrayList<cine>cinesAdmin;
    private ListView peliculas;
    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;
    private comunicador comunicador;
    private Spinner spSalas;
    private Spinner spCines;
    private ArrayAdapter<sala_cine>adaptadorSalas;
    private ArrayAdapter<cine>adaptadorCinesAdmin;
    private cine c;
    private sala_cine sc;



    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    private boolean seEstaActualizando = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public abm_pelicula_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment abm_pelicula_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static abm_pelicula_fragment newInstance() {
        abm_pelicula_fragment fragment = new abm_pelicula_fragment();
        return fragment;
    }


    private void agregarPelicula(int idsala) {


        String titulo = this.titulo.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String protagonistas = this.protagonistas.getText().toString().trim();
        String duracion = this.duracion.getText().toString().trim();
        String sinopsis = this.sinopsis.getText().toString().trim();
        String clasificacion = this.clasificacion.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(titulo)) {
            this.titulo.setError("Por favor, ingrese el titulo");
            this.titulo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(director)) {
            this.director.setError("Por favor, ingrese el nombre de director");
            this.director.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(genero)){

            this.genero.setError("Por favor, ingrese el genero");
            this.genero.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(protagonistas)){

            this.protagonistas.setError("Por favor, ingrese el nombre de los protagonistas");
            this.protagonistas.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(duracion)){

            this.duracion.setError("Por favor, ingrese la duración");
            this.duracion.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sinopsis)){

            this.sinopsis.setError("Por favor, ingrese la sinopsis");
            this.sinopsis.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(clasificacion)){

            this.clasificacion.setError("Por favor, ingrese la clasificación de la pelicula");
            this.clasificacion.requestFocus();
            return;
        }



        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonistas",protagonistas);
        params.put("duracion",duracion);
        params.put("clasificacion",clasificacion);
        params.put("id_sala",String.valueOf(idsala));


     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //Calling the create hero API

        request request = new request(AppConfig.URL_CREAR_PELICULA + idsala, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void darPeliculas(int idsala) {

        request request = new request(AppConfig.URL_LISTAR_PELICULAS + idsala, null, CODE_GET_REQUEST);
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
                    refrescarLista(object.getJSONArray("peliculas"));
                    comunicador.mandarPelisSalaAdmin(ListaPeliculas,sc,c);
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
    class peliAdapter extends ArrayAdapter<pelicula> {

        //our peli list
        List<pelicula> peliList;
        Context context = getActivity().getApplicationContext();


        //constructor to get the list
        public peliAdapter(Context context,List<pelicula> peliList) {
            super(context, R.layout.activity_listar_peliculas, peliList);
            this.peliList = peliList;
        }


        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_listar_peliculas, null, true);

            //getting the textview for displaying name
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewItem);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final pelicula peli = peliList.get(position);

            textViewName.setText(peli.toString());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    seEstaActualizando = true;

                    //we will set the selected hero to the UI elements
                    peliculaid.setText(String.valueOf(peli.getID()));
                    titulo.setText(peli.getNombre());
                    director.setText(peli.getDirector());
                    sinopsis.setText(peli.getSinopsis());
                    duracion.setText(String.valueOf(peli.getDuracion()));
                    protagonistas.setText(peli.getActoresPrincipales());
                    clasificacion.setText(peli.getClasificacion());


                    //we will also make the button text to Update
                    aceptar.setText("Actualizar");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Eliminar " + peli.getNombre())
                            .setMessage("¿Esta seguro que quiere eliminarlo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    eliminarPelicula(peli.getID());
                                    //if the choice is yes we will delete the hero
                                    //method is commented because it is not yet created
                                    //deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).show();

                }
            });

            return listViewItem;
        }

    }
    private void actualizarPelicula(int salaid) {

        String pid = this.peliculaid.getText().toString();
        String titulo = this.titulo.getText().toString().trim();
        String genero = this.genero.getText().toString().trim();
        String director = this.director.getText().toString().trim();
        String protagonistas = this.protagonistas.getText().toString().trim();
        String duracion = this.duracion.getText().toString().trim();
        String sinopsis = this.sinopsis.getText().toString().trim();
        String clasificacion = this.clasificacion.getText().toString().trim();


        if (TextUtils.isEmpty(titulo)) {
            this.titulo.setError("Por favor, ingrese el titulo");
            this.titulo.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(genero)){

            this.genero.setError("Por favor, ingrese el genero");
            this.genero.requestFocus();
        }
        if(TextUtils.isEmpty(director)){

            this.director.setError("Por favor, ingrese el nombre del director");
            this.director.requestFocus();
        }
        if(TextUtils.isEmpty(duracion)){

            this.duracion.setError("Por favor, ingrese la duración");
            this.duracion.requestFocus();
        }
        if(TextUtils.isEmpty(sinopsis)){

            this.sinopsis.setError("Por favor, ingrese la sinopsis");
            this.sinopsis.requestFocus();
        }
        if(TextUtils.isEmpty(protagonistas)){

            this.protagonistas.setError("Por favor, ingrese los nombres de los protagonistas");
            this.protagonistas.requestFocus();
        }
        if(TextUtils.isEmpty(clasificacion)){

            this.clasificacion.setError("Por favor, ingrese la clasificación de la pelicula");
            this.clasificacion.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("id",pid);
        params.put("titulo", titulo);
        params.put("director", director);
        params.put("genero", genero);
        params.put("sinopsis",sinopsis);
        params.put("protagonistas",protagonistas);
        params.put("duracion",duracion);
        params.put("clasificacion",clasificacion);
        params.put("id_sala", String.valueOf(salaid));

        request request = new request(AppConfig.URL_ACTUALIZAR_PELICULAS + salaid, params, CODE_POST_REQUEST);
        request.execute();

        aceptar.setText("Agregar");


        this.titulo.getText().toString().trim();
        this.genero.getText().toString().trim();
        this.director.getText().toString().trim();
        this.protagonistas.getText().toString().trim();
        this.duracion.getText().toString().trim();
        this.sinopsis.getText().toString().trim();
        this.clasificacion.getText().toString().trim();


        seEstaActualizando = false;
    }
    private void eliminarPelicula(int id) {
        request request = new request(AppConfig.URL_ELIMINAR_PELICULAS + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refrescarLista(JSONArray peliculas) throws JSONException {
        //clearing previous heroes
        ListaPeliculas.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < peliculas.length(); i++) {
            //getting each hero object
            JSONObject obj = peliculas.getJSONObject(i);

            //adding the hero to the list
            ListaPeliculas.add(new pelicula(
                    obj.getInt("id"),
                    obj.getInt("duracion"),
                    obj.getString("genero"),
                    obj.getString("titulo"),
                    obj.getString("sinopsis"),
                    obj.getString("protagonistas"),
                    obj.getString("clasificacion"),
                    obj.getString("director")
            ));
        }
        //creating the adapter and setting it to the listview
        peliAdapter adapter = new peliAdapter(getActivity().getApplicationContext(),ListaPeliculas);
        this.peliculas.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 =getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("ABM de Pelicula");

        View view = inflater.inflate(R.layout.abm_pelicula,container,false);

        titulo = view.findViewById(R.id.editTextTitulo);
        sinopsis = view.findViewById(R.id.editTextSinopsis);
        director = view.findViewById(R.id.editTextDirector);
        barra = view.findViewById(R.id.progressBar);
        peliculaid = view.findViewById(R.id.editTextPID);
        genero = view.findViewById(R.id.editTextGenero);
        duracion = view.findViewById(R.id.editTextDuracion);
        protagonistas = view.findViewById(R.id.editTextProtagonista);
        clasificacion = view.findViewById(R.id.editTextClasificacion);
        spSalas = view.findViewById(R.id.spSalas);
        spCines = view.findViewById(R.id.spCines);
        aceptar = view.findViewById(R.id.btnAM);
        agregarFuncion = view.findViewById(R.id.btnAgregarFuncion);


        ListaPeliculas = new ArrayList<>();
        salasAdmin = new ArrayList<>();
        cinesAdmin = new ArrayList<>();

        cinesAdmin = comunicador.darCines();


        peliculas = view.findViewById(R.id.listViewPeliculas);

        adaptadorCinesAdmin = new adaptadorSpinnerSala(getActivity(),cinesAdmin);

        comunicador = (comunicador)getActivity();

        cargarSpinnerCines(cinesAdmin,adaptadorCinesAdmin);


        //Seleccion de cada uno de los items del spinner

        spCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                c = adaptadorCinesAdmin.getItem(position);

                salasAdmin = comunicador.darSalas(c);
                adaptadorSalas = new adaptadorSpinnerPelicula(getActivity(),salasAdmin);
                cargarSpinnerSalas(salasAdmin,adaptadorSalas);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spSalas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                sc = adaptadorSalas.getItem(position);
                Toast.makeText(getActivity(),"ID:" + sc.getID(),Toast.LENGTH_LONG).show();

                darPeliculas(sc.getID());
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(seEstaActualizando) {

                            actualizarPelicula(sc.getID());
                        }

                        agregarPelicula(sc.getID());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        agregarFuncion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment funcion = null;
                boolean ftx = false;
                crearFragmentFuncion(funcion,ftx);


            }
        });




        return view;
    }

    public void crearFragmentFuncion(Fragment funcion,boolean ftx){



        funcion = new abm_funcion();
        ftx = true;

        if (funcion != null) {

            android.app.FragmentManager fm = getFragmentManager();
            android.app.Fragment currentFragment;
            currentFragment = fm.findFragmentById(R.id.content_frame);

            if (currentFragment == null) {
                //carga del primer fragment justo en la carga inicial de la app
                cambiarFragment(ftx, funcion);
            } else
            if (!currentFragment.getClass().getName().equalsIgnoreCase(funcion.getClass().getName())) {
                //currentFragment no concide con newFragment
                cambiarFragment(ftx,funcion);

            } else {
                //currentFragment es igual a newFragment
            }
        }
    }

    public void cambiarFragment(boolean fragmentTX,Fragment fragment){

        if (fragmentTX) {
            android.app.FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame, fragment,fragment.getClass().getName());
            ft.addToBackStack("funcion_fragment");
            ft.commit();
        }


    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList<pelicula>listaPeliculas,sala_cine sc) {
        if (comunicador != null) {
            //comunicador.mandarPelisSalaAdmin(listaPeliculas,sc);
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


    public void cargarSpinnerSalas(ArrayList<sala_cine>salaCines,ArrayAdapter<sala_cine>adaptadorSalas){

        spSalas.setAdapter(adaptadorSalas);


    }
    public void cargarSpinnerCines(ArrayList<cine>cinesAdmin,ArrayAdapter<cine>adaptadorCines){

        spCines.setAdapter(adaptadorCines);

}
}
