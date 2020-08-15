package org.rest;

public class Estacion implements Comparable<Estacion> {
    private String title;
    private int id;

    public Estacion(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String get_title() {
        return title;
    }

    public int get_id() {
        return id;
    }

    @Override
    public int compareTo(Estacion o) {
        return 0;
    }
}
