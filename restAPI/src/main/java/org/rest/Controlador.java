package org.rest;

import EstructurasDatos.BST;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private String json_usuarios;
    private Gson gson;
    private BST<Usuario> arbol_usuarios;
    private static Controlador instance = null;

    /**
     * Clase singleton de Controlador.
     */
    private Controlador() {
        this.gson = new Gson();
        this.arbol_usuarios = new BST<>();

        this.json_usuarios = "src/main/resources/usuarios.json";

        try {
            cargar_usuarios();
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

    public void registrar_usuario(String usuario) throws Exception {
        Usuario usuario_aux = gson.fromJson(usuario, Usuario.class);
        Usuario usuario_final = new Usuario(usuario_aux.get_cedula(), usuario_aux.get_pswrd());
        arbol_usuarios.add(usuario_final, usuario_final.get_cedula());
        escribir_json_usuarios(usuario_final);
    }

    /**
     * Lee el archivo JSON de los usuarios.
     * @return Una lista con todos los Usuarios del JSON.
     */
    private List<Usuario> leer_json_usuarios() throws IOException {
        FileReader file_reader = new FileReader(json_usuarios);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        JsonReader json_reader = new JsonReader(buffered_reader);

        List<Usuario> result = new ArrayList<>();

        try {
            Usuario[] lista_json;

            lista_json = gson.fromJson(json_reader, Usuario[].class);

            for (int i = 0; i < lista_json.length; i++) {
                result.add(lista_json[i]);
            }
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
    private void escribir_json_usuarios(Usuario usuario) throws Exception {
        List<Usuario> contenido = leer_json_usuarios();

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
     * Carga todos los usuarios que haya en el JSON a un árbol binario de búsqueda.
     */
    private void cargar_usuarios() throws IOException {
        List<Usuario> lista_usuarios = leer_json_usuarios();

        for(int i = 0; i < lista_usuarios.size(); i++){
            Usuario usuario = lista_usuarios.get(i);
            arbol_usuarios.add(usuario, usuario.get_cedula());
        }
    }

    /**
     * Encripta un string usando el algoritmo hashMD5
     * @param string El string a encriptar.
     * @return Un string con el string encriptado.
     */
    public String hash_MD5(String string) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(string);
    }

    public String validar_usuario(int cedula, String pswrd) throws NoSuchAlgorithmException {
        if(arbol_usuarios.contains(cedula)){
            if(arbol_usuarios.get_nodo(cedula).get_valor().get_pswrd().equals(pswrd)){
                return String.valueOf(true);
            }
        }
        return String.valueOf(false);
    }
}
