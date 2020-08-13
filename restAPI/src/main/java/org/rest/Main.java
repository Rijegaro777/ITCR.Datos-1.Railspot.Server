package org.rest;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server.get_instance();
        Controlador.get_instance();
    }
}

