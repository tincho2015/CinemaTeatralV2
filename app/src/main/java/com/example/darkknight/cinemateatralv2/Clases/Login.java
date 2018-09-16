package com.example.darkknight.cinemateatralv2.Clases;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



import com.example.darkknight.cinemateatralv2.R;
import com.example.darkknight.cinemateatralv2.ConexionBD.AppConfig;
import com.example.darkknight.cinemateatralv2.Usuarios.SharedPrefManager;
import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;
import com.example.darkknight.cinemateatralv2.menu_lateral_principal;


public class Login extends Activity {
    EditText editTextUsername, editTextPassword;
    ProgressBar barraProgresoLogin;
    private static final int CODE_GET_REQUEST = 1;
    private static final int CODE_POST_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu);

        editTextUsername = findViewById(R.id.inMail);
        editTextPassword = findViewById(R.id.inPassword);

        //if user presses on login
        //calling the method login
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.link_to_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), activity_registro.class));
            }
        });
    }

    private void usuarioLogin() {
        //first getting the values
        final String nombre_usuario = editTextUsername.getText().toString();
        final String contraseña = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nombre_usuario)) {
            editTextUsername.setError("Ingrese su nombre de usuario, por favor");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            editTextPassword.setError("Ingrese su contraseña, por favor");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine

        //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("nombre_usuario", nombre_usuario);
                params.put("contraseña", contraseña);

                //returing the response
                request request = new request(AppConfig.URL_LOGIN,params,CODE_POST_REQUEST);
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
            barraProgresoLogin = findViewById(R.id.barraProgresoLogin);
            barraProgresoLogin.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barraProgresoLogin.setVisibility(View.GONE);

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
                    finish();
                    startActivity(new Intent(getApplicationContext(),menu_lateral_principal.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña inválidos", Toast.LENGTH_SHORT).show();
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






