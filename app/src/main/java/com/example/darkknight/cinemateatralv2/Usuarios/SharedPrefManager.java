package com.example.darkknight.cinemateatralv2.Usuarios;

/**
 * Created by Dark Knight on 13/04/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.darkknight.cinemateatralv2.Clases.Login;
import com.example.darkknight.cinemateatralv2.Clases.bienvenida;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "usuarios_preferences";
    private static final String KEY_NOMBRE_USUARIO = "keynombre";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_SEXO = "keysexo";
    private static final String KEY_ID = "keyid";
    private static final String KEY_ROL = "keyrol";
    private static final String KEY_CONTRASEÑA = "keycontraseña";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void usuarioLogin(Usuario usuario) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, usuario.getID());
        editor.putString(KEY_NOMBRE_USUARIO, usuario.getNombre_usuario());
        editor.putString(KEY_EMAIL, usuario.getEmail());
        editor.putString(KEY_SEXO, usuario.getSexo());
        editor.putInt(KEY_ROL,usuario.getRol());
        editor.putString(KEY_CONTRASEÑA,usuario.getContraseña());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean estaLogueado() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NOMBRE_USUARIO, null) != null;
    }

    //this method will give the logged in user
    public Usuario getUsuario() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NOMBRE_USUARIO, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_SEXO, null),
                sharedPreferences.getInt(KEY_ROL,-1),
                sharedPreferences.getString(KEY_CONTRASEÑA,null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }
}
