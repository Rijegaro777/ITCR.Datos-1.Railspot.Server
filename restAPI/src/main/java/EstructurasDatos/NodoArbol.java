package EstructurasDatos;

public class NodoArbol<T extends Comparable<T>>{
    private T valor;
    private int key;
    private NodoArbol<T> padre;
    private NodoArbol<T> left;
    private NodoArbol<T> right;

    /**
     * Crea un nodo.
     * @param valor El objeto que va a estar contenido en el nodo.
     */
    public NodoArbol(T valor, int key){
        this.valor = valor;
        this.key = key;
        this.padre = null;
        this.left = null;
        this.right = null;
    }

    /**
     * @return Retorna el objeto contenido en el nodo.
     */
    public T get_valor(){return this.valor;}

    /**
     * @return Retorna el objeto contenido en el nodo.
     */
    public int get_key(){return this.key;}

    /**
     * @return Retorna el padre del nodo.
     */
    public NodoArbol<T> get_padre() {
        return padre;
    }

    /**
     * @return Retorna el nodo izquierdo.
     */
    public NodoArbol<T> get_left(){return this.left;}

    /**
     * @return Retorna el nodo derecho.
     */
    public NodoArbol<T> get_right(){return this.right;}

    /**
     * Cambia el objeto contenido en el nodo.
     * @param valor el objeto que se desea cambiar.
     */
    public void set_valor(T valor){
        this.valor = valor;
    }

    /**
     * Cambia el nodo izquierdo.
     * @param nodo el nuevo nodo.
     */
    public void set_left(NodoArbol<T> nodo){this.left = nodo;}

    /**
     * Cambia el nodo derecho.
     * @param nodo el nuevo nodo.
     */
    public void set_right(NodoArbol<T> nodo){this.right = nodo;}

    /**
     * Cambia el padre del nodo.
     * @param padre el nuevo padre.
     */
    public void set_padre(NodoArbol<T> padre) {
        this.padre = padre;
    }
}
