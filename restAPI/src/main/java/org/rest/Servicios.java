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
}
