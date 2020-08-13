package org.rest;

public class Tiquete implements Comparable<Tiquete> {
    private int id, comprador, partida, destino;
    private String fecha;

    public Tiquete(int id, int comprador, int partida, int destino, String fecha) {
        this.id = id;
        this.comprador = comprador;
        this.partida = partida;
        this.destino = destino;
        this.fecha = fecha;
    }

    public int get_id() {
        return id;
    }

    public int get_comprador() {
        return comprador;
    }

    public int get_partida() {
        return partida;
    }

    public int get_destino() {
        return destino;
    }

    public String get_fecha() {
        return fecha;
    }

    @Override
    public int compareTo(Tiquete o) {
        return 0;
    }
}
