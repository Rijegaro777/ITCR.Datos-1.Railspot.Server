package org.rest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Comparable<Usuario> {
    private int cedula;
    private String pswrd;
    private List<Integer> tiquetes;

    public Usuario(int cedula, String pswrd) throws NoSuchAlgorithmException {
        this.cedula = cedula;
        this.pswrd = Controlador.get_instance().hash_MD5(pswrd);
        this.tiquetes = new ArrayList<>();
    }

    public int get_cedula() {
        return cedula;
    }

    public String get_pswrd() {
        return pswrd;
    }

    public List<Integer> get_tiquetes() {
        return tiquetes;
    }

    @Override
    public int compareTo(Usuario o) {
        return 0;
    }
}
