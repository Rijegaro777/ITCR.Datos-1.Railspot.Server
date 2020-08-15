package org.rest;

import EstructurasDatos.AristaGrafo;
import EstructurasDatos.BST;
import EstructurasDatos.Grafo;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controlador {
    private int usuario_actual;
    private String json_usuarios, json_tiquetes, json_rutas, json_estaciones;
    private Gson gson;
    private List<AristaGrafo> aristas;
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
        this.json_rutas = "src/main/resources/rutas.json";
        this.json_estaciones = "src/main/resources/estaciones.json";

        try {
            cargar_usuarios();
            cargar_tiquetes();
            this.aristas = get_lista_rutas_json_rutas();
            this.rutas = new Grafo(aristas.toArray(new AristaGrafo[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea y devuelve una instancia única de Controlador.
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
     * Calcula el costo en dinero y distancia de un viaje.
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

    /**
     * Agrega una arista nueva al grafo.
     * @param ruta Un string en formato JSON que contiene la nueva arista que se agregará al grafo.
     * @throws Exception
     */
    public void agregar_ruta(String ruta) throws Exception {
        AristaGrafo arista_nueva = gson.fromJson(ruta, AristaGrafo.class);
        int partida = arista_nueva.getFromNodeIndex();
        int destino = arista_nueva.getToNodeIndex();
        int distancia = arista_nueva.getLength();
        aristas.add(new AristaGrafo(partida, destino, distancia));
        rutas = new Grafo(aristas.toArray(new AristaGrafo[0]));
        actualizar_rutas_json_rutas();
    }

    /**
     * Busca todos los tiquetes comprados por un usuarios específico.
     * @param id_usuario El id del usuario cuyas compras se desea conocer.
     * @return Un string con el JSON de la lista de tiquetes comprados por usuario.
     */
    public String obtener_compras_usuario(int id_usuario) {
        List<Integer> tiquetes_comprados_usuario = arbol_usuarios.get_nodo(id_usuario).get_valor().get_tiquetes();
        String tiquetes_comprados_json = gson.toJson(tiquetes_comprados_usuario);
        return tiquetes_comprados_json;
    }

    /**
     * Busca todos los tiquetes comprados en una fecha específica.
     * @param fecha La fecha que se desea consultar.
     * @return Un string con el JSON de la lista de tiquetes comprados para la fecha especificada.
     */
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

    /**
     * Lee el archivo JSON de los usuarios.
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
     * Lee el archivo JSON de los tiquetes.
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
     * Lee el archivo JSON de los tiquetes.
     * @return Una lista con todos los tiquetes del JSON.
     */
    private List<Estacion> get_lista_estaciones_json_estaciones() throws IOException {
        FileReader file_reader = new FileReader(json_estaciones);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        JsonReader json_reader = new JsonReader(buffered_reader);

        List<Estacion> result = new ArrayList<>();

        try {
            Estacion[] lista_json;

            lista_json = gson.fromJson(json_reader, Estacion[].class);

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
     * Lee el archivo JSON de las rutas.
     * @return Una lista con todas las aristas del JSON.
     */
    private List<AristaGrafo> get_lista_rutas_json_rutas() throws IOException {
        FileReader file_reader = new FileReader(json_rutas);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        JsonReader json_reader = new JsonReader(buffered_reader);

        List<AristaGrafo> result = new ArrayList<>();

        try {
            AristaGrafo[] lista_json;

            lista_json = gson.fromJson(json_reader, AristaGrafo[].class);

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
     * Agrega una estación al JSON.
     * @param estacion El usuario a agregar.
     */
    private void agregar_estacion_json_estaciones(Estacion estacion) throws Exception {
        List<Estacion> contenido = get_lista_estaciones_json_estaciones();

        FileWriter fileWriter = new FileWriter(json_estaciones);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            contenido.add(estacion);
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
     * Sobreescribe los datos que ya existen en el JSON de rutas.
     * @throws Exception
     */
    public void actualizar_rutas_json_rutas() throws Exception {
        FileWriter fileWriter = new FileWriter(json_rutas);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(gson.toJson(aristas));
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
     * Encripta un string usando el algoritmo hashMD5
     * @param string El string a encriptar.
     * @return Un string con el string encriptado.
     */
    public String hash_MD5(String string) {
        return DigestUtils.md5Hex(string);
    }

    public String obtener_estaciones() throws IOException {
        List<Estacion> lista_estaciones = get_lista_estaciones_json_estaciones();
        String lista_estaciones_json = gson.toJson(lista_estaciones);
        return lista_estaciones_json;
    }

    public void agregar_estacion(String estacion) throws Exception {
        Estacion estacion_nueva = gson.fromJson(estacion, Estacion.class);
        agregar_estacion_json_estaciones(estacion_nueva);
    }
}

