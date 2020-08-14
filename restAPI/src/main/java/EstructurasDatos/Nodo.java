package EstructurasDatos;

public class Nodo <T extends Comparable <T>>{
    private T valor;
    private Nodo<T> next;
    private Nodo<T> prev;
    
    /**
     * Crea un nodo.
     * @param valor El objeto que va a estar contenido en el nodo.
     */
    public Nodo(T valor){
        this.valor = valor;
        this.next = null;
        this.prev = null;
    }
    
    /**
     * @return Retorna el objeto contenido en el nodo.
     */
    public T get_valor(){return this.valor;}

    /**
     * @return Retorna el nodo siguiente.
     */
    public Nodo<T> get_next(){return this.next;}

    /**
     * @return Retorna el nodo anterior.
     */
    public Nodo<T> get_prev(){return this.prev;}
    
    /**
     * Cambia el objeto contenido en el nodo.
     * @param valor el objeto que se desea cambiar.
     */
    public void set_valor(T valor){
        this.valor = valor;
    }

    /**
     * Cambia el nodo siguiente.
     * @param nodo el nuevo nodo.
     */
    public void set_next(Nodo<T> nodo){this.next = nodo;}

    /**
     * Cambia el nodo anterior.
     * @param nodo el nuevo nodo.
     */
    public void set_prev(Nodo<T> nodo){this.prev = nodo;}
}
