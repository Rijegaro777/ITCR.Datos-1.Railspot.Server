package org.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Server {
    private static final String BASE_URI = "http://0.0.0.0:8080/rest/";
    private static final ResourceConfig rc = new ResourceConfig().packages("org.rest");
    private static HttpServer instance = null;

    /**
     * Retorna una instancia unica de un HTTP server.
     * @return Un Grizzly HTTP server que escucha en http://0.0.0.0:8080/rest/.
     */
    public static HttpServer get_instance(){
        if (instance == null) {
            instance = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        }
        return instance;
    }
}
