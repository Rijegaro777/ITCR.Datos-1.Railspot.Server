package EstructurasDatos;

import java.util.Random;

public class ListaDoble<T extends Comparable<T>>{
    private Nodo<T> first = null;
    private Nodo<T> last = null;
    private int length = 0;

    /**
     * Agrega un objeto a la primera posición de la lista.
     * @param valor el objeto que se desea agregar a la lista.
     */
    public void add_frst(T valor) {
        if (this.first == null) {
            this.first = new Nodo<T>(valor);
            this.last = this.first;
        } else {
            Nodo<T> tmp = this.first;
            this.first = new Nodo<T>(valor);
            this.first.set_next(tmp);
            tmp.set_prev(this.first);
        }
        this.length++;
    }

    /**
     * Agrega un objeto a la última posición de la lista.
     * @param valor el objeto que se desea agregar a la lista.
     */
    public void add_last(T valor) {
        if (this.first == null){
            this.first = new Nodo<T>(valor);
            this.last = this.first;
        }else{
            Nodo<T> tmp = this.last;
            this.last = new Nodo<T>(valor);
            tmp.set_next(this.last);
            this.last.set_prev(tmp);
        }
        this.length++;
    }

    /**
     * Elimina el nodo en la posición dada.
     * @param pos posición a borrar.
     */
    public void delete(int pos) {
        if (pos >= this.length){throw new IllegalArgumentException("Índice fuera de rango.");}
        else if (this.first.get_next() == null){this.first = null; this.last = null;}
        else if (pos == 0){
            Nodo<T> tmp = this.first;
            this.first = tmp.get_next();
            tmp.set_prev(first);
        }
        else if (pos == this.length - 1){
            Nodo<T> tmp = this.first;
            while (tmp.get_next().get_next() != null){tmp = tmp.get_next();}
            tmp.set_next(null);
            this.last = tmp;
        }
        else{
            Nodo<T> tmp = this.first;
            int i = 0;
            while (i != pos-1){tmp = tmp.get_next(); i++;}
            Nodo<T> aux = tmp.get_next();
            tmp.set_next(aux.get_next());
            aux.get_next().set_prev(tmp);
        }
        this.length--;
    }

    /**
     * Corta la lista entre las posiciones indicadas.
     * @param pos1 posición en donde comienza el corte.
     * @param pos2 posición en donde termina el corte.
     * @return la lista cortada. No afecta a la original.
     */
    public ListaDoble<T> slice (int pos1, int pos2){
        ListaDoble<T> result = new ListaDoble<T>();
        if (pos1 >= this.length || pos2 >= this.length){throw new IllegalArgumentException("Índice fuera de rango.");}
        if (pos1 > pos2){throw new IllegalArgumentException("Error de índices.");}
        while (pos1 != pos2){
            result.add_last(this.get_pos(pos1).get_valor());
            pos1++;
        }
        result.add_last(this.get_pos(pos2).get_valor());
        return result;
    }

    /**
     * Intercambia los valores de las posiciones dadas.
     * @param pos1 posicion de uno de los valores.
     * @param pos2 posicion del otro valor.
     */
    public void swap(int pos1, int pos2) {
        if (pos1 >= this.length || pos2 >= this.length){throw new IllegalArgumentException();}
        Nodo<T> aux = new Nodo<T>(this.get_pos(pos1).get_valor());
        this.get_pos(pos1).set_valor(this.get_pos(pos2).get_valor());
        this.get_pos(pos2).set_valor(aux.get_valor());
    }

    /**
     * Mezcla de forma aleatoria la lista.
     * @return retorna una lista con los elementos de la original invertidos. No afecta a la o.
     */
    public ListaDoble<T> invertir_lista(){
        ListaDoble<T> result = new ListaDoble<>();
        for(int i = 0; i < get_length(); i++){
            result.add_frst(get_pos(i).get_valor());
        }
        return result;
    }

    /**
     * Mezcla de forma aleatoria la lista.
     * @return retorna una lista con los elementos de la original desordenados. No afecta a la original.
     */
    public ListaDoble<T> shuffle (){
        if (this.length == 0){throw new IllegalStateException("La lista está vacía");}
        ListaDoble<T> aux = new ListaDoble<T>();
        ListaDoble<T> result = new ListaDoble<T>();
        int i = 0;
        while (i < this.get_length()){
            aux.add_last(this.get_pos(i).get_valor());
            i++;
        }
        while (aux.length != 0){
            int random = new Random().nextInt(aux.get_length());
            result.add_last(aux.get_pos(random).get_valor());
            aux.delete(random);
        }
        return result;
    }

    /**
     * Cambia el valor de una posición dada de la lista.
     * @param pos   la posición de la lista a cambiar.
     * @param valor el nuevo valor del nodo.
     */
    public void set_pos(int pos, T valor) {
        Nodo<T> tmp = this.first;
        if (pos >= this.length){throw new IllegalArgumentException();}
        else if (pos == 0){this.first.set_valor(valor);}
        else{
            int i = 0;
            while (i != pos){tmp = tmp.get_next(); i++;}
        }
        tmp.set_valor(valor);
    }

    /**
     * Retorna el nodo en la posiciónn indicada.
     * @param pos la posición de la lista que se desea consultar (comienza en 0).
     * @return Retorna el nodo que esté en la posicion indicada de la lista.
     */
    public Nodo<T> get_pos(int pos){
        Nodo<T> tmp = this.first;
        if (pos >= this.length){throw new IllegalArgumentException();}
        else if (pos == 0){return this.first;}
        else if (pos == this.length - 1) {return this.last;}
        else{
            int i = 0;
            while (i != pos){tmp = tmp.get_next(); i++;}
        }
        return tmp;
    }

    /**
     * @return Retorna el largo que tenga la lista.
     */
    public int get_length() {return this.length;}

    /**
     * @return Retorna el primer nodo de la lista
     */
    public Nodo<T> get_first() {return this.first;}

    /**
     * @return Retorna el último nodo de la lista
     */
    public Nodo<T> get_last() {return this.last;}
}
