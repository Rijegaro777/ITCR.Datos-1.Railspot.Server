package org.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Path("servicios")
public class Servicios extends Application {
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
}
