package AlgoritmosOrdenamiento;

import EstructurasDatos.ListaDoble;
import org.rest.Tiquete;

import java.util.List;

public class Sorting {
    private static Sorting instance = null;

    private Sorting(){}

    /**
     * Crea una instancia única de Sorting.
     * @return Una instancia única de Sorting.
     */
    public static Sorting get_instance (){
        if (instance == null){
            instance = new Sorting();
        }
        return instance;
    }

    /**
     * Ordena de menor a mayor una lista doblemente enlazada de enteros usando QuickSort.
     * @param lista la lista a ordenar.
     */
    public void quick_sort(List<Tiquete> lista){
        ListaDoble<Tiquete> lista_doble = new ListaDoble<>();
        for(Tiquete tiquete : lista){
            lista_doble.add_last(tiquete);
        }
        quick_sort_aux(lista_doble,0, lista_doble.get_length()-1);
    }

    /**
     * Función recursiva que se encarga del ordenamiento con quicksort.
     * @param lista la lista a ordenar.
     * @param primero el primero elemento de la sublista.
     * @param ultimo el ultimo elemento de la sublista.
     */
    private void quick_sort_aux(ListaDoble<Tiquete> lista, int primero, int ultimo){
        int pivote = (primero + (ultimo - primero) / 2);
        int valor_pivote = convertir_fecha_int(lista.get_pos(pivote).get_valor().get_fecha());
        int i = primero;
        int j = ultimo;
        while (i <= j){
            while (convertir_fecha_int(lista.get_pos(i).get_valor().get_fecha()) < valor_pivote){i++;}
            while (convertir_fecha_int(lista.get_pos(j).get_valor().get_fecha()) > valor_pivote){j--;}
            if (i <= j){
                lista.swap(i, j);
                i++;
                j--;
            }
        }
        if (primero < j) {
            quick_sort_aux(lista, primero, j);}
        if (i < ultimo) {
            quick_sort_aux(lista, i, ultimo);}
    }

    private int convertir_fecha_int(String fecha){
        String[] array_fecha = fecha.split("/");
        if (Integer.parseInt(array_fecha[1]) < 10) {
            array_fecha[1] = "0" + array_fecha[1];
        }
        int int_fecha = Integer.parseInt(array_fecha[2] + array_fecha[0] + array_fecha[1]);
        return int_fecha;
    }
}
