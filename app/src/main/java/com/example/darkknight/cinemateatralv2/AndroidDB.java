package com.example.darkknight.cinemateatralv2;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.io.InputStream;

public class AndroidDB
{
    String IP = "Sitio de hostinger";
    String GET = IP + ".php";
    String GET_BY_ID = IP +".php";
    String UPDATE = IP + ".php";
    String DELETE = IP + ".php";
    String INSERT = IP + ".php";

    ObtenerWebServices conexionBD;


    /*
    public static final String CREATE_TABLE = " CREATE TABLE Peliculas (codigo INTEGER,nombre TEXT)";

    public static final String INSERT_TABLE = "INSERT INTO Peliculas (codigo,nombre) VALUES (1,'Gladiador')";

    public static final String MODIFICAR_TABLE = "UPDATE Peliculas SET nombre = 'Gladiador 2' WHERE codigo = 1";

    public static final String ELIMINAR_REGISTRO = "DELETE FROM Peliculas WHERE codigo = 1";
    */
    public class ObtenerWebServices extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; //url donde se quiere obtener la informacion.
            String devuelve = "";
            if(params[1] == "1"){ //Consultar todos los cines
            try{
                url = new URL(cadena);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent","Mozilla/5.0" + "(Linux: Android 1.5; es-ES) Ejemplo HTTP");

                int respuesta = connection.getResponseCode();
                StringBuilder result = new StringBuilder();

                if(respuesta == HttpURLConnection.HTTP_OK){

                    InputStream in = new BufferedInputStream(connection.getInputStream()); //Prepara la cadena de entrada
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in)); //introduzco la cadena en un bufferReader

                    // Lo siguiente se hace porque el JSONOBject necesita un String y tengo
                    // que transformar el BufferedReader a String. Esto se hace a traves de un StringBuilder.

                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);  // Se pasa toda la entrada al StringBuilder
                        //Creamos un objeto JSONObject para poder acceder a los atributos(campos) del objeto.

                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        /*
                        JSONArray resultJSON = respuestaJSON.getJSONArray("results");
                        */
                        //Accedemos al vector de resultados.

                       String resultJSON = respuestaJSON.getString("estado"); // estado es el nombre del campo en el JSON

                        if(resultJSON == "1"){
                            JSONArray cinesJSON= respuestaJSON.getJSONArray("Cines");
                            for (int i = 0;i<cinesJSON.length();i++)
                            {
                                devuelve = devuelve + cinesJSON.getJSONObject(i).getString("idCine") + "" +
                                        cinesJSON.getJSONObject(i).getString("nombre") + "" +
                                        cinesJSON.getJSONObject(i).getString("direccion") + "" +
                                        cinesJSON.getJSONObject(i).getString("Telefono") + "\n";
                            }
                        }
                        else{
                            if(resultJSON == "2"){
                                devuelve = "No hay alumnos";
                            }

                        }

                        //Vamos obteniendo todos los campos que nos interesen.


                    }


                }
            } catch (MalformedInputException e){
            e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            return devuelve;
            }
            else if(params[1]=="2"){ //Consultar de un cine por ID

                }
                else if(params[1]=="3") {
                 }
                else if(params[1]=="4"){

                }
                else if (params[1]=="5"){

                }
            return null;

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
