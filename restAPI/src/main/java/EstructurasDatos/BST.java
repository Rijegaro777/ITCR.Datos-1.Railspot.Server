package EstructurasDatos;

import java.util.ArrayList;
import java.util.List;

public class BST<T extends Comparable<T>> {
    private NodoArbol<T> root = null;
    private List<T> list_version;
    private int size = 0;

    /**
     * Agrega un nodo al arbol.
     * @param valor el objeto que contendrá el nodo.
     * @param key un valor representativo del valor del nodo.
     */
    public void add(T valor, int key){
        if (this.is_empty()){root = new NodoArbol<T>(valor, key);}
        else{
            NodoArbol<T> tmp = root;
            while (tmp != null){
                if (key > tmp.get_key()){
                    if (tmp.get_right() == null){tmp.set_right(new NodoArbol<T>(valor, key)); return;}
                    else{tmp = tmp.get_right();}
                }
                else{
                    if (tmp.get_left() == null){tmp.set_left(new NodoArbol<T>(valor, key)); return;}
                    else{tmp = tmp.get_left();}
                }
            }
        }
        size++;
    }

    /**
     * Verifica sin el key ingresado está en el arbol.
     * @param key key a verificar.
     * @return retorna true si el arbol contiene el key y false si no.
     */
    public boolean contains (int key) {
        if (root == null) {
            return false;
        }
        NodoArbol<T> tmp = root;
        while (tmp != null) {
            if (tmp.get_key() == key) {return true;}
            else if (tmp.get_key() > key) {tmp = tmp.get_left();}
            else {tmp = tmp.get_right();}
        }
        return false;
    }

    /**
     * Obtiene el nodo con el valor de llave indicada.
     * @param key La llave que se quiere buscar.
     * @return El nodo con esa llave. Si el árbol está vacío o el elemento no existe, retorna null.
     */
    public NodoArbol<T> get_nodo(int key){
        if (root == null) {
            return null;
        }
        NodoArbol<T> tmp = root;
        while (tmp != null) {
            if (tmp.get_key() == key) {
                return tmp;
            }
            else if (tmp.get_key() > key) {
                tmp = tmp.get_left();
            }
            else {
                tmp = tmp.get_right();
            }
        }
        return null;
    }

    /**
     * @return Retorna el valor mínimo en el arbol. Si el árbol está vacío, retorna -1.
     */
    public int get_min(){
        if (!is_empty()){
            NodoArbol<T> tmp = root;
            while (tmp.get_left() != null) {tmp = tmp.get_left();}
            return tmp.get_key();
        }
        else{
            return -1;
        }
    }


    /**
     * @return Retorna el valor máximo en el arbol. Si el árbol está vacío, retorna -1.
     */
    public int get_max(){
        if (!is_empty()){
            NodoArbol<T> tmp = root;
            while (tmp.get_right() != null) {tmp = tmp.get_right();}
            return tmp.get_key();
        }
        else{
            return -1;
        }
    }

    /**
     * Elimina el nodo que tenga la llave indicada.
     * @param key la llave del nodo a eliminar.
     */
    public void eliminar(int key) throws Exception {
        this.root = eliminar_aux(this.root, key);
        size--;
    }

    /**
     * Busca de forma recursiva el nodo a eliminar y lo elimina.
     * @param root El nodo desde el que se comenzará a recorrer el árbol.
     * @param key El valor que será eliminado del árbol.
     * @return La nueva ráiz del árbol tras borrar el valor indicado.
     * @throws Exception
     */
    private NodoArbol<T> eliminar_aux(NodoArbol<T> root, int key) throws Exception {
        if (root == null){
            throw new IllegalArgumentException();
        }
        else if(key < root.get_key()){
            NodoArbol<T> left = eliminar_aux(root.get_left(), key);
            root.set_left(left);
        }
        else if(key > root.get_key()){
            NodoArbol<T> right = eliminar_aux(root.get_right(), key);
            root.set_right(right);
        }
        else{
            NodoArbol<T> copy = root;
            if (copy.get_right() == null){
                root = copy.get_left();
            }
            else if(copy.get_left() == null){
                root = copy.get_right();
            }
            else{
                cambiar_nodos(copy);
            }
        }
        return root;
    }

    /**
     * Busca el nodo mayor a la izquierda de la raíz y los intercambia.
     * @param root El nodo que será borrado.
     * @return La nueva raíz tras realizar el cambio.
     */
    private NodoArbol<T> cambiar_nodos(NodoArbol<T> root){
        NodoArbol<T> copy = root;
        NodoArbol<T> maxLeft = root.get_left();
        while (maxLeft.get_right() != null){
            copy = maxLeft;
            maxLeft = maxLeft.get_right();
        }
        root.set_valor(maxLeft.get_valor());
        if(copy == root){
            copy.set_left(maxLeft.get_right());
        }
        else{
            copy.set_right(maxLeft.get_right());
        }
        return maxLeft;
    }

    /**
     * Añade todos los elementos del árbol a una lista.
     * @return Retorna una lista con todos los elementos del árbol.
     */
    public List<T> to_list(){
         list_version = new ArrayList<>();
         to_list_aux(this.root);
         return list_version;
    }

    /**
     * Recorre el árbol in orden, agregando cada nodo a una lista.
     * @param root El nodo desde el que se comenzará a recorrer el árbol.
     */
    private void to_list_aux(NodoArbol<T> root){
        if (root != null){
            to_list_aux(root.get_left());
            list_version.add(root.get_valor());
            to_list_aux(root.get_right());
        }
    }

    /**
     * @return Retorna true si la lista está vacía y false si no.
     */
    public boolean is_empty(){return this.root == null;}

    /**
     * @return Retorna la raiz del arbol.
     */
    public NodoArbol<T> get_root(){return this.root;}

    public void print_in_orden(NodoArbol<T> nodo){
        if (nodo != null){
            print_in_orden(nodo.get_left());
            System.out.println(nodo.get_key());
            print_in_orden(nodo.get_right());
        }
    }
}