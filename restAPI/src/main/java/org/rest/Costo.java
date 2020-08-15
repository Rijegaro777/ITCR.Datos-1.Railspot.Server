package org.rest;

public class Costo {
    private String title;
    private int destino, precio, precio_total, distancia;

    public Costo(String title, int destino, int precio, int distancia) {
        this.title = title;
        this.destino = destino;
        this.precio = precio;
        this.precio_total = precio;
        this.distancia = distancia;
    }

    public int get_precio() {
        return precio;
    }

    public int get_distancia() {
        return distancia;
    }
}
