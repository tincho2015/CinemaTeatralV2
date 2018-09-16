package com.example.darkknight.cinemateatralv2.Clases;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class tipoComplejo {
    /**
     * Created by Dark Knight on 30/10/2015.
     */
        private String direccion;
        private int ID;
        private String nombre;
        private String telefono;



    private String url;
        /*
        private Map ubicacion;
        */

        public tipoComplejo(String direccion, int ID, String nombre, String telefono,String url) {

            this.direccion = direccion;
            this.ID = ID;
            this.nombre = nombre;
            this.telefono = telefono;
            this.url = url;
            /*
            this.ubicacion = ubicacion;
            */
        }

        public String getDireccion() {
            return direccion;
        }

        public int getID() {
            return ID;
        }

        public String getNombre() {
            return nombre;
        }

        public String getTelefono() {
            return telefono;
        }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
