package com.example.darkknight.cinemateatralv2.ConexionBD;

/**
 * Created by Dark Knight on 25/03/2017.
 */

public class AppConfig {


        // Server user login url
        public static String URL_LOGIN = "http://campobd.esy.es/Campo/Api.php?apicall=login";
        // Server user register url
        public static String URL_REGISTER = "http://campobd.esy.es/Campo/Api.php?apicall=registrarse";

        //Roles

        public static String URL_DAR_ROL_ADMIN = "http://campobd.esy.es/Campo/Api.php?apicall=darRolAdmin";


        //Cine
        public static String URL_CREAR_CINE = "http://campobd.esy.es/Campo/Api.php?apicall=agregarCine";

        public static String URL_LISTAR_CINES = "http://campobd.esy.es/Campo/Api.php?apicall=darCines";

        public static String URL_ACTUALIZAR_CINE = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarCines";

        public static String URL_ELIMINAR_CINE = "http://campobd.esy.es/Campo/Api.php?apicall=borrarCine&cid=";


        //Teatro

        public static String URL_CREAR_TEATRO= "http://campobd.esy.es/Campo/Api.php?apicall=agregarTeatro";

        public static String URL_LISTAR_TEATROS = "http://campobd.esy.es/Campo/Api.php?apicall=darTeatros";

        public static String URL_ACTUALIZAR_TEATROS = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarTeatros";

        public static String URL_ELIMINAR_TEATROS = "http://campobd.esy.es/Campo/Api.php?apicall=borrarTeatro&tid=";

        //Pelicula

        public static String URL_CREAR_PELICULA= "http://campobd.esy.es/Campo/Api.php?apicall=agregarPeliculaSala&id_sala=";

        public static String URL_LISTAR_PELICULAS = "http://campobd.esy.es/Campo/Api.php?apicall=darPeliculasPorSala&id_sala=";

        public static String URL_ACTUALIZAR_PELICULAS = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarPeliculas&id_sala=";

        public static String URL_ELIMINAR_PELICULAS = "http://campobd.esy.es/Campo/Api.php?apicall=borrarPelicula&pid=";

        //Obra de teatro

        public static String URL_CREAR_OBRA_TEATRO= "http://campobd.esy.es/Campo/Api.php?apicall=agregarObraTeatro";

        public static String URL_LISTAR_OBRAS_TEATRO = "http://campobd.esy.es/Campo/Api.php?apicall=darObrasTeatro";

        public static String URL_ACTUALIZAR_OBRAS_TEATROS = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarObrasTeatros";

        public static String URL_ELIMINAR_OBRA_TEATRO = "http://campobd.esy.es/Campo/Api.php?apicall=borrarObraTeatro&oid=";

        //Reserva_cine

        public static String URL_CREAR_RESERVA_CINE = "http://campobd.esy.es/Campo/Api.php?apicall=agregarReservaCine";

        //Reserva_teatro

        public static String URL_CREAR_RESERVA_TEATRO = "http://campobd.esy.es/Campo/Api.php?apicall=agregarReservaTeatro";

        //Salas

        public static String URL_CREAR_SALA= "http://campobd.esy.es/Campo/Api.php?apicall=agregarSalaCine&id_cine=";

        public static String URL_LISTAR_SALAS = "http://campobd.esy.es/Campo/Api.php?apicall=darSalasPorCine&id_cine=";

        public static String URL_ACTUALIZAR_SALA = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarSalaCine";

        public static String URL_ELIMINAR_SALA = "http://campobd.esy.es/Campo/Api.php?apicall=borrarSala&id_sala=&id_cine=";


        //Asientos

        public static String URL_AGREGAR_ASIENTO = "http://campobd.esy.es/Campo/Api.php?apicall=agregarAsientoSala&id_sala=";
        public static String URL_ELIMINAR_ASIENTO = "http://campobd.esy.es/Campo/Api.php?apicall=eliminarAsientoSala&id_asiento=&id_sala=";
        public static String URL_ACTUALIZAR_ASIENTO = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarAsientoSala&id_sala=";
        public static String URL_LISTAR_ASIENTOS_SALA = "http://campobd.esy.es/Campo/Api.php?apicall=darAsientoPorSala&id_sala=";

        //Funciones

        public static String URL_CREAR_FUNCION ="http://campobd.esy.es/Campo/Api.php?apicall=agregarFuncion&pid=";

        public static String URL_ELIMINAR_FUNCION = "http://campobd.esy.es/Campo/Api.php?apicall=borrarFuncion&id_funcion=&id_pelicula=";

        public static String URL_ACTUALIZAR_FUNCION = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarFuncion&id_pelicula=";

        public static String URL_LISTAR_FUNCIONES = "http://campobd.esy.es/Campo/Api.php?apicall=darFuncionPelicula&id_pelicula=";

        public static String URL_AGREGAR_FUNCION_DIA = "http://campobd.esy.es/Campo/Api.php?apicall=agregarFuncionDia&id_funcion=";

        public static String URL_ACTUALIZAR_FUNCION_DIA = "http://campobd.esy.es/Campo/Api.php?apicall=actualizarFuncionDia&id_funcion=";

        public static String URL_BORRAR_FUNCION_DIA = "http://campobd.esy.es/Campo/Api.php?apicall=borrarFuncionDia&id=&id_funcion=";

        public static String URL_LISTAR_FUNCION_DIA = "http://campobd.esy.es/Campo/Api.php?apicall=darFuncionesDia&id_funcion=";



}

