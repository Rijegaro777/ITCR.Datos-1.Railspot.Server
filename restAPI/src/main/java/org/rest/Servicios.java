package org.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Path("servicios")
public class Servicios extends Application {
    /**
     * Maneja una solicitud para registrar un usuario.
     * @param usuario El JSON de un usuario.
     * @return Una respuesta "OK" si el resultado es satisfactorio y un error si falla.
     */
    @POST
    @Path("registro")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registrar_usuario(String usuario){
        Response response;
        try{
            Controlador.get_instance().registrar_usuario(usuario);
            response = Response.ok().build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    /**
     * Recibe una solicitud para validar una cedula y una contraseña.
     * @param cedula La cédula del usuario a validar.
     * @param pswrd La contraseña ingresada por el usuario.
     * @return Una respuesta "true" si los datos ingresados coinciden, "false" si no.
     */
    @GET
    @Path("validar_usuario")
    @Produces("application/json")
    public Response validar_usuario(@QueryParam("cedula") String cedula, @QueryParam("pswrd") String pswrd){
        Response response;
        try{
            int num_cedula = Integer.parseInt(cedula);
            String pswrd_encriptada = Controlador.get_instance().hash_MD5(pswrd);
            String valido = Controlador.get_instance().validar_usuario(num_cedula, pswrd_encriptada);
            response = Response.ok(valido).build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    /**
     * Recibe una solicitud para calcular el costo en distancia y dinero entre una parada y todas las demás.
     * @param partida El índice del nodo de partida.
     * @return Un string con el JSON del costo en dinero y distancia del viaje.
     */
    @GET
    @Path("calcular_costo/{partida}")
    @Produces("application/json")
    public Response calcular_costo(@PathParam("partida") String partida){
        Response response;
        try{
            int partida_int = Integer.parseInt(partida);
            String costo = Controlador.get_instance().calcular_costo(partida_int);
            response = Response.ok(costo).build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    /**
     * Recibe una solicitud para comprar un tiquete.
     * @param tiquete El JSON del tiquete a comprar.
     * @return Una respuesta "OK" si el resultado es satisfactorio y un error si falla.
     */
    @POST
    @Path("comprar_tiquete")
    @Consumes("application/json")
    @Produces("application/json")
    public Response comprar_tiquete(String tiquete){
        Response response;
        try{
            Controlador.get_instance().comprar_tiquete(tiquete);
            response = Response.ok().build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    /**
     * Recibe una solicitud para agregar una estación al JSON.
     * @param estacion El JSON de la estación a agregar.
     * @return Una respuesta "OK" si el resultado es satisfactorio y un error si falla.
     */
    @POST
    @Path("agregar_estacion")
    @Consumes("application/json")
    @Produces("application/json")
    public Response agregar_estacion(String estacion){
        Response response;
        try{
            Controlador.get_instance().agregar_estacion(estacion);
            response = Response.ok().build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    /**
     * Recibe una solicitud para agregar una nueva arista al grafo.
     * @param arista El JSON del tiquete a comprar.
     * @return Una respuesta "OK" si el resultado es satisfactorio y un error si falla.
     */
    @POST
    @Path("agregar_ruta")
    @Consumes("application/json")
    @Produces("application/json")
    public Response agregar_ruta(String arista){
        Response response;
        try{
            Controlador.get_instance().agregar_ruta(arista);
            response = Response.ok().build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("obtener_estaciones")
    @Produces("application/json")
    public Response obtener_estaciones(){
        Response response;
        try{
            String lista_estaciones = Controlador.get_instance().obtener_estaciones();
            response = Response.ok(lista_estaciones).build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("obtener_compras_usuario/")
    @Produces("application/json")
    public Response obtener_compras_usuario(){
        Response response;
        try{
            String tiquetes_comprados = Controlador.get_instance().obtener_compras_usuario(Controlador.get_instance().usuario_actual);
            response = Response.ok(tiquetes_comprados).build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }

    @GET
    @Path("obtener_tiquetes_comprados_por_fecha/{fecha}")
    @Produces("application/json")
    public Response obtener_tiquetes_comprados_por_fecha(@PathParam("fecha") String fecha){
        Response response;
        try{
            String[] fecha_por_partes = fecha.split("&");
            String fecha_reformateada = fecha_por_partes[0] + "/" + fecha_por_partes[1] + "/" + fecha_por_partes[2];
            String tiquetes_por_fecha = Controlador.get_instance().obtener_tiquetes_comprados_por_fecha(fecha_reformateada);
            response = Response.ok(tiquetes_por_fecha).build();
        }
        catch (Exception e){
            response = Response.serverError().build();
        }
        return response;
    }
}