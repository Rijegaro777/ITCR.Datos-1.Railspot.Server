package org.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Path("servicios")
public class Servicios extends Application {
    @GET
    @Path("cedula")
    @Consumes("text/plain")
    @Produces("text/plain")
    public Response decir_cedula (@QueryParam("cedula") String cedula) {
        Response response = Response.ok("Desde API: \n Su cédula es: " + cedula).build();
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        return response;
    }
}
