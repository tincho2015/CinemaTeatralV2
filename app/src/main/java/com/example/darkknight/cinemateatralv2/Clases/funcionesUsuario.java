package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 02/10/2016.
 */
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class funcionesUsuario {
    private jSonParser jsonParser;
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://10.0.2.2/ah_login_api/";
    private static String registerURL = "http://10.0.2.2/ah_login_api/";
    private static String login_tag = "login";
    private static String register_tag = "register";
    // constructor
    public funcionesUsuario(){
        jsonParser = new jSonParser();
    }
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    /*
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
       // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params, "POST");
        // return json
        // Log.e("JSON", json.toString());
      //  return json;
    }
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password

    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        // getting JSON Object
     //   JSONObject json = jsonParser.getJSONFromUrl(registerURL, params, "POST");
        // return json
       // return json;
    }
    */

}
