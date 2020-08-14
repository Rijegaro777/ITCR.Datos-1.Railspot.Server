package org.rest;

import AlgoritmosOrdenamiento.Sorting;
import EstructurasDatos.AristaGrafo;
import EstructurasDatos.BST;
import EstructurasDatos.Grafo;
import EstructurasDatos.NodoGrafo;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controlador {
    private int san_jose, san_pedro, zapote, sabanilla, curridabat,
            tres_rios, cartago, paraiso, guadalupe, moravia, tibas,
            santo_domingo, heredia, usuario_actual;
    private String json_usuarios, json_tiquetes;
    private Gson gson;
    private ArrayList<AristaGrafo> aristas;
    private BST<Usuario> arbol_usuarios;
    private BST<Tiquete> arbol_tiquetes;
    private Grafo rutas;
    private static Controlador instance = null;

    /**
     * Clase singleton de Controlador.
     */
    private Controlador() {
        this.gson = new Gson();
        this.arbol_usuarios = new BST<>();
        this.arbol_tiquetes = new BST<>();

        this.json_usuarios = "src/main/resources/usuarios.json";
        this.json_tiquetes = "src/main/resources/tiquetes.json";

        try {
            cargar_usuarios();
            cargar_tiquetes();
            cargar_rutas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea y devuelve una instancia única de Controlador.
     *
     * @return Una instancia de Controlador.
     */
    public static Controlador get_instance() {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }

    /**
     * Agrega un usuario nuevo al JSON de usuarios.
     *
     * @param usuario Un string con el JSON del usuario.
     * @throws Exception
     */
    public void registrar_usuario(String usuario) throws Exception {
        Usuario usuario_aux = gson.fromJson(usuario, Usuario.class);
        Usuario usuario_final = new Usuario(usuario_aux.get_cedula(), usuario_aux.get_pswrd());
        arbol_usuarios.add(usuario_final, usuario_final.get_cedula());
        agregar_usuario_json_usuarios(usuario_final);
    }

    /**
     * Valida que exista un usuario con una cédula y verifica si su contraseña corresponde con el pswrd.
     * @param cedula La cédula que se va a buscar en el árbol.
     * @param pswrd  La contraseña que se va a comparar con la del usuario.
     * @return El string de un boolean que indica si la cedula y la contraseña son correctas.
     */
    public String validar_usuario(int cedula, String pswrd) {
        if (arbol_usuarios.contains(cedula)) {
            if (arbol_usuarios.get_nodo(cedula).get_valor().get_pswrd().equals(pswrd)) {
                usuario_actual = cedula;
                return String.valueOf(true);
            }
        }
        return String.valueOf(false);
    }

    /**
     * Agrega un tiquete al JSON de tiquetes.
     *
     * @param tiquete Un string con el JSON del tiquete.
     * @throws Exception
     */
    public void comprar_tiquete(String tiquete) throws Exception {
        Tiquete tiquete_aux = gson.fromJson(tiquete, Tiquete.class);
        int id;
        if (arbol_tiquetes.is_empty()) {
            id = 0;
        } else {
            id = arbol_tiquetes.get_max() + 1;
        }
        Tiquete tiquete_final = new Tiquete(id, usuario_actual, tiquete_aux.get_partida(), tiquete_aux.get_destino(), tiquete_aux.get_fecha());
        arbol_tiquetes.add(tiquete_final, tiquete_final.get_id());
        arbol_usuarios.get_nodo(tiquete_final.get_comprador()).get_valor().get_tiquetes().add(tiquete_final.get_id());
        agregar_tiquete_json_tiquetes(tiquete_final);
        actualizar_usuarios_json_usuarios();
    }

    /**
     * Lee el archivo JSON de los usuarios.
     *
     * @return Una lista con todos los usuarios del JSON.
     */
    private List<Usuario> get_lista_usuarios_json_usuarios() throws IOException {
        FileReader file_reader = new FileReader(json_usuarios);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        JsonReader json_reader = new JsonReader(buffered_reader);

        List<Usuario> result = new ArrayList<>();

        try {
            Usuario[] lista_json;

            lista_json = gson.fromJson(json_reader, Usuario[].class);

            Collections.addAll(result, lista_json);
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            json_reader.close();
            buffered_reader.close();
            file_reader.close();
        }
        return result;
    }

    /**
     * Lee el archivo JSON de los tiquete.
     *
     * @return Una lista con todos los tiquetes del JSON.
     */
    private List<Tiquete> get_lista_tiquetes_json_tiquetes() throws IOException {
        FileReader file_reader = new FileReader(json_tiquetes);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        JsonReader json_reader = new JsonReader(buffered_reader);

        List<Tiquete> result = new ArrayList<>();

        try {
            Tiquete[] lista_json;

            lista_json = gson.fromJson(json_reader, Tiquete[].class);

            Collections.addAll(result, lista_json);
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            json_reader.close();
            buffered_reader.close();
            file_reader.close();
        }
        return result;
    }

    /**
     * Agrega un Usuario al JSON.
     *
     * @param usuario El usuario a agregar.
     */
    private void agregar_usuario_json_usuarios(Usuario usuario) throws Exception {
        List<Usuario> contenido = get_lista_usuarios_json_usuarios();

        FileWriter fileWriter = new FileWriter(json_usuarios);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            contenido.add(usuario);
            bufferedWriter.write(gson.toJson(contenido));
        } catch (Exception e) {
            throw new Exception("Error");
        } finally {
            bufferedWriter.close();
            fileWriter.close();
        }
    }

    /**
     * Agrega un tiquete al JSON.
     *
     * @param tiquete El usuario a agregar.
     */
    private void agregar_tiquete_json_tiquetes(Tiquete tiquete) throws Exception {
        List<Tiquete> contenido = get_lista_tiquetes_json_tiquetes();

        FileWriter fileWriter = new FileWriter(json_tiquetes);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            contenido.add(tiquete);
            bufferedWriter.write(gson.toJson(contenido));
        } catch (Exception e) {
            throw new Exception("Error");
        } finally {
            bufferedWriter.close();
            fileWriter.close();
        }
    }

    /**
     * Sobreescribe los datos que ya existen en el JSON de usuarios.
     *
     * @throws Exception
     */
    private void actualizar_usuarios_json_usuarios() throws Exception {
        List<Usuario> contenido = arbol_usuarios.to_list();

        FileWriter fileWriter = new FileWriter(json_usuarios);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(gson.toJson(contenido));
        } catch (Exception e) {
            throw new Exception("Error");
        } finally {
            bufferedWriter.close();
            fileWriter.close();
        }
    }

    /**
     * Carga todos los usuarios que haya en el JSON a un árbol binario de búsqueda.
     */
    private void cargar_usuarios() throws IOException {
        List<Usuario> lista_usuarios = get_lista_usuarios_json_usuarios();

        for (Usuario usuario : lista_usuarios) {
            arbol_usuarios.add(usuario, usuario.get_cedula());
        }
    }

    /**
     * Carga todos los tiquetes que haya en el JSON a un árbol binario de búsqueda.
     */
    private void cargar_tiquetes() throws IOException {
        List<Tiquete> lista_tiquetes = get_lista_tiquetes_json_tiquetes();

        for (Tiquete tiquete : lista_tiquetes) {
            arbol_tiquetes.add(tiquete, tiquete.get_id());
        }
    }

    /**
     * Crea el grafo con todas las rutas.
     */
    private void cargar_rutas() {
        this.san_jose = 0;
        this.san_pedro = 1;
        this.zapote = 2;
        this.sabanilla = 3;
        this.curridabat = 4;
        this.tres_rios = 5;
        this.cartago = 6;
        this.paraiso = 7;
        this.guadalupe = 8;
        this.moravia = 9;
        this.tibas = 10;
        this.santo_domingo = 11;
        this.heredia = 12;

        this.aristas = new ArrayList<>();

        //San José
        aristas.add(new AristaGrafo(san_jose, san_pedro, 5));
        aristas.add(new AristaGrafo(san_jose, zapote, 7));
        aristas.add(new AristaGrafo(san_jose, sabanilla, 8));
        aristas.add(new AristaGrafo(san_jose, tibas, 5));

        //San Pedro
        aristas.add(new AristaGrafo(san_pedro, guadalupe, 4));
        aristas.add(new AristaGrafo(san_pedro, sabanilla, 5));
        aristas.add(new AristaGrafo(san_pedro, curridabat, 3));

        //Zapote
        aristas.add(new AristaGrafo(zapote, tres_rios, 3));

        //Sabanilla
        aristas.add(new AristaGrafo(sabanilla, tres_rios, 8));
        aristas.add(new AristaGrafo(sabanilla, guadalupe, 4));

        //Curridabat
        aristas.add(new AristaGrafo(curridabat, tres_rios, 7));

        //Tres Ríos
        aristas.add(new AristaGrafo(tres_rios, cartago, 13));

        //Cartago
        aristas.add(new AristaGrafo(cartago, paraiso, 7));

        //Guadalupe
        aristas.add(new AristaGrafo(guadalupe, moravia, 10));

        //Moravia
        aristas.add(new AristaGrafo(moravia, tibas, 12));

        //Tibas
        aristas.add(new AristaGrafo(tibas, santo_domingo, 3));

        //Santo Domingo
        aristas.add(new AristaGrafo(santo_domingo, heredia, 5));

        this.rutas = new Grafo(aristas.toArray(new AristaGrafo[0]));
    }

    /**
     * Encripta un string usando el algoritmo hashMD5
     *
     * @param string El string a encriptar.
     * @return Un string con el string encriptado.
     */
    public String hash_MD5(String string) {
        return DigestUtils.md5Hex(string);
    }

    /**
     * Calcula el costo en dinero y distancia de un viaje.
     *
     * @param partida El índice del nodo de partida.
     * @param destino El índice del nodo de llegada.
     * @return Un string compuesto por la distancia más corta entre los puntos de
     * llegada y partida y el precio del viaje, separados por un "%".
     */
    public String calcular_costo(int partida, int destino) {
        rutas.calculateShortestDistances(partida);
        int distancia = rutas.getNodes()[destino].getDistanceFromSource();
        int precio = (distancia - partida) * 25;
        String result = String.valueOf(distancia - partida) + "%" + String.valueOf(precio);
        return result;
    }

    public String obtener_compras_usuario(int id_usuario) {
        List<Integer> tiquetes_comprados_usuario = arbol_usuarios.get_nodo(id_usuario).get_valor().get_tiquetes();
        String tiquetes_comprados_json = gson.toJson(tiquetes_comprados_usuario);
        return tiquetes_comprados_json;
    }

    public String obtener_tiquetes_comprados_por_fecha(String fecha) throws IOException {
        List<Tiquete> lista_tiquetes = get_lista_tiquetes_json_tiquetes();
        List<Tiquete> lista_tiquetes_que_coinciden = new ArrayList<>();
        String tiquetes_en_fecha_json;
        for(Tiquete tiquete : lista_tiquetes){
            if(tiquete.get_fecha().equals(fecha)){
                lista_tiquetes_que_coinciden.add(tiquete);
            }
        }
        if (lista_tiquetes_que_coinciden.isEmpty()){
            tiquetes_en_fecha_json = "No se encontraron coincidencias";
        }
        else{
            tiquetes_en_fecha_json = gson.toJson(lista_tiquetes_que_coinciden);
        }
        return  tiquetes_en_fecha_json;
    }
}

