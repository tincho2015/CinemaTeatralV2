package com.example.darkknight.cinemateatralv2.Clases;

import android.widget.ArrayAdapter;

import com.example.darkknight.cinemateatralv2.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Administradora
{
    private ArrayList<cine>cines;
    private ArrayList<promocion>promociones;
    private ArrayList<teatro>teatros;
    private ArrayList<Usuario>usuarios;
    private ArrayList<cine> cinesNoRepetidos;
    private ArrayList<teatro>teatrosNoRepetidos;
    private ArrayList<pelicula>peliculasNoRepetidas;
    private ArrayList<obraDeTeatro>obrasNoRepetidas;
    private ArrayList<reserva_cine>reservasCines;


    private static Administradora ourInstance = new Administradora();
    public static Administradora getIntance()
    {
        return ourInstance;
    }

    private Administradora()
    {
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();

        //obrasTeatro = new ArrayList<obraDeTeatro>();
        cines = new ArrayList<cine>();
        promociones = new ArrayList<promocion>();
        teatros = new ArrayList<teatro>();
        usuarios = new ArrayList<Usuario>();
        cinesNoRepetidos = new ArrayList<>();
        teatrosNoRepetidos = new ArrayList<>();
        peliculasNoRepetidas = new ArrayList<>();
        obrasNoRepetidas = new ArrayList<>();
        reservasCines = new ArrayList<>();

    }

    public ArrayList darAsientosDisponibles(sala_cine sc){

        if(sc != null)
        {
            sc.darAsientosDisponibles();
        }

        return null;
    }

    public cine darCine(int cineId){

        for(cine c:cines){

            if(c.getID() == cineId){

                return c;
            }
        }
        return null;
    }
    public sala_cine darSalaCine(int cineId, int salaId){

        for(cine c:cines){

            if(c.getID() == cineId){

                for(sala_cine sc:c.getSala_cines()){

                    if(sc.getID() == salaId){

                        return sc;
                    }
                }
            }
        }
        return null;
    }

    public void agregarCine(cine nuevocine)
    {

        if(nuevocine != null && !cines.contains(nuevocine)){
            cines.add(nuevocine);
            boolean repetido = false;
            for(int j=0; j<cinesNoRepetidos.size();j++){
                if(cinesNoRepetidos.get(j).getID()== nuevocine.getID()){
                    //Se encuentra actualmente en ArrayList el elemento.
                    repetido = true;
                    break; //Si encuentra un elemento repetido deja de buscar en el ArrayList.
                }
            }
            if(!repetido){ //Agrega si el elemento no se encuentra repetido en el ArrayList
                cinesNoRepetidos.add(nuevocine); //Agrega en Arraylist de elementos no repetido
            }else{
                cines.remove(nuevocine);
            }

        }
    }
    public void eliminarCine(cine cine){
        if(cine != null && cines.contains(cine)){
            cines.remove(cine);
        }
    }
    public ArrayList darCines(){

            return cines;
    }
    public void agregarTeatro(teatro nuevoteatro)
    {
        if(nuevoteatro!=null && !teatros.contains(nuevoteatro))
        teatros.add(nuevoteatro);
        boolean repetido = false;
        for(int j=0; j<teatrosNoRepetidos.size();j++){
            if(teatrosNoRepetidos.get(j).getID()== nuevoteatro.getID()){
                //Se encuentra actualmente en ArrayList el elemento.
                repetido = true;
                break; //Si encuentra un elemento repetido deja de buscar en el ArrayList.
            }
        }
        if(!repetido){ //Agrega si el elemento no se encuentra repetido en el ArrayList
            teatrosNoRepetidos.add(nuevoteatro); //Agrega en Arraylist de elementos no repetido
        }else{
            teatros.remove(nuevoteatro);
        }
    }
    public void agregarUsuario(Usuario usuario){

        usuarios.add(usuario);
    }

    public void agregarSalaCine(cine c, sala_cine sc)
    {
        if(c != null && sc != null){
            if(cines.contains(c)){

                c.agregarSala(sc);
            }
        }

    }
    public void eliminarSalaCine(cine c,sala_cine sc){

        if(c != null && sc != null){
            if(cines.contains(c)){
                c.eliminarSala(sc);
            }
        }
    }
    public ArrayList darSalas(cine c){

        return c.getSala_cines();

    }
    public cine darCineReserva(int cId){


            for(cine cine:cines){
                if(cine.getID() == cId){

                    return cine;
                }

            }
        return null;
    }
    public pelicula darPeliculaReserva(int peliId){

        for(cine cine:cines){
                for(sala_cine sc:cine.getSala_cines()){

                    for(pelicula peli:sc.getPeliculasSala()){

                        if(peli.getID() == peliId){

                            return peli;
                        }
                    }
                }


        }
        return null;
    }
    public funcion darFuncionReserva(int funcionId){


        for(cine cine:cines){

                for(sala_cine sc:cine.getSala_cines()){

                    for(pelicula peli:sc.getPeliculasSala()){

                        for(funcion func:peli.getFunciones()){

                            if(func.getID() == funcionId){

                                return func;
                            }

                        }
                    }
                }


        }
        return null;

    }
    public horario darHorarioReserva(int horarioId){

        for(cine cine:cines){

                for(sala_cine sc:cine.getSala_cines()){

                    for(pelicula peli:sc.getPeliculasSala()){

                        for(funcion func:peli.getFunciones()){

                            for(horario hora:func.getDiaFuncion().getHorarios()){

                                if(hora.getId()==horarioId)
                                {
                                    return hora;
                                }
                            }

                        }
                    }
                }


        }
        return null;
    }

    public ArrayList darFunciones(cine c,sala_cine sc,pelicula peli){

        if(c != null && sc != null && peli != null){

            if(c.getSala_cines().contains(sc)){

                if(sc.getPeliculasSala().contains(peli)){

                    return peli.getFunciones();
                }
            }
        }
        return null;
    }
    public ArrayList darFuncionesPelicula(pelicula p){

        if(p!= null && !p.getFunciones().isEmpty())
        {
            return p.getFunciones();
        }
        return null;
    }
    public ArrayList darPelis(sala_cine sc){

        return sc.getPeliculasSala();
    }
    public ArrayList darPelisTotal(cine c){

        for(sala_cine sc:c.getSala_cines()) {
            return sc.getPeliculasSala();
        }
        return null;

    }
    public void agregarPeliculasSala(cine c,sala_cine sc, pelicula nuevapeli){

        if(sc != null && nuevapeli != null){

            if(c.getSala_cines().contains(sc)){

                sc.agregarPeliculas(nuevapeli);
            }
        }
    }
    public void agregarFuncion(cine c, sala_cine sc,pelicula p, funcion nuevaFuncion ){

        if(c.getSala_cines().contains(sc)){

            if(sc.getPeliculasSala().contains(p)){

                p.agregarFuncion(nuevaFuncion);
            }
        }

    }
    public void agregarAsientoSala(cine c, sala_cine sc, asiento nuevoAsiento){

        if(c.getSala_cines().contains(sc)){

            if(!sc.getAsientos().contains(nuevoAsiento)){

                sc.agregarAsiento(nuevoAsiento);
            }
        }
    }
    public void eliminarFuncion(cine c, sala_cine sc,pelicula p, funcion nuevaFuncion){

        if(c.getSala_cines().contains(sc)){

            if(sc.getPeliculasSala().contains(p)){

                p.eliminarFuncion(nuevaFuncion);
            }
        }


    }

    public void agregarHorarioFuncion(cine c, sala_cine sc,pelicula p, funcion f, horario nuevoHorario){

        if(c.getSala_cines().contains(sc)){

            if(sc.getPeliculasSala().contains(p)){

                if(p.getFunciones().contains(f)){

                    f.getDiaFuncion().agregarNuevaHora(nuevoHorario);
                }
            }
        }
    }
    public void eliminarHorarioFuncion(cine c, sala_cine sc,pelicula p, funcion f, horario nuevoHorario){

        if(c.getSala_cines().contains(sc)){

            if(sc.getPeliculasSala().contains(p)){

                if(p.getFunciones().contains(f)){

                    f.getDiaFuncion().eliminarHora(nuevoHorario);
                }
            }
        }

    }
    public void eliminarPeliculaSala(sala_cine sc, pelicula peli){

        if(sc != null && peli != null){

            if(cines.contains(sc)){

                sc.eliminarPeliculas(peli);
            }
        }
    }

    public void agregarReservaCine(reserva_cine nuevaReserva ){

        if(nuevaReserva != null){

            reservasCines.add(nuevaReserva);
        }

    }

    public pelicula darNombrePeliPuntuar(int peliId){

        for(cine c: cinesNoRepetidos){

            for(sala_cine sc: c.getSala_cines()){

                for(pelicula p: sc.getPeliculasSala()){

                    if(p.getID() == peliId){

                        return p;
                    }
                }
            }
        }
        return null;
    }


}
