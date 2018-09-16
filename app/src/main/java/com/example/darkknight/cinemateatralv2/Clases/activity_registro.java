package com.example.darkknight.cinemateatralv2.Clases;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;


import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;


import com.example.darkknight.cinemateatralv2.layouts.mostrar_usuario;

public class activity_registro extends Activity{


    EditText editTextUsername, editTextEmail, editTextPassword;
    RadioGroup radioGroupGender;
    ProgressBar barraProgreso;

    private static final int CODE_GET_REQUEST = 1;

    private static final int CODE_POST_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).estaLogueado()) {
            finish();
            startActivity(new Intent(this, mostrar_usuario.class));
            return;
        }

        editTextUsername = findViewById(R.id.reg_fullname);
        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_password);
        radioGroupGender = findViewById(R.id.radioGroupSexo);
        barraProgreso = findViewById(R.id.progresoBarra);


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server

                registrarUsuario();

            }
        });

        findViewById(R.id.link_to_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(activity_registro.this, Login.class));
            }
        });

    }
    private void registrarUsuario() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String genero = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();


        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Ingrese un nombre de usuario, por favor");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Ingrese una dirección de correo, por favor ");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Ingrese una dirección válida");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Ingrese una contraseña, por favor");
            editTextPassword.requestFocus();
            return;
        }

        //creating request parameters
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre_usuario", username);
        params.put("email", email);
        params.put("contraseña", password);
        params.put("genero", genero);

        //if it passes all the validations

        //executing the async task
        request request = new request(AppConfig.URL_REGISTER,params,CODE_POST_REQUEST);
        request.execute();
    }

    //Estructura de un request

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
            //displaying the progress bar while user registers on the server
            barraProgreso = findViewById(R.id.progresoBarra);
            barraProgreso.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
             barraProgreso.setVisibility(View.GONE);

            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("usuario");

                    //creating a new user object
                    Usuario usuario = new Usuario(
                            userJson.getInt("id"),
                            userJson.getString("nombre_usuario"),
                            userJson.getString("email"),
                            userJson.getString("genero"),
                            userJson.getInt("tipo_usuario"),
                            userJson.getString("contraseña")
                    );

                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).usuarioLogin(usuario);

                    //starting the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    Toast.makeText(getApplicationContext(), "!Un error ha ocurrido!", Toast.LENGTH_SHORT).show();
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
