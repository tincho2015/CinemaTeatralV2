package com.example.darkknight.cinemateatralv2.Usuarios;

/**
 * Created by Dark Knight on 12/08/2016.
 */
public class Usuario {

        private int ID;
        private String nombre_usuario;
        private String email;
        private String sexo;
        private int rol;



    private String contraseña;

    public Usuario(int ID, String nombre_usuario, String email,String sexo,int rol,String contraseña) {
        this.ID = ID;
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.sexo = sexo;
        this.rol = rol;
        this.contraseña = contraseña;
    }
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean esAdmin() {

       return (getRol() == 1);
        }


}
