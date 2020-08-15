package EstructurasDatos;

import java.util.ArrayList;

public class Grafo {
    private NodoGrafo[] nodes;
    private int noOfNodes;
    private AristaGrafo[] edges;
    private int noOfEdges;

    /**
     * Grafo 
     * @aristas Parametro de una lista con las aristas que van a ser conectadas con los nodos
     */
    public Grafo(AristaGrafo[] aristas) {
        this.edges = aristas;
        // crea los nodos para que se asignen con su arista respectiva
        this.noOfNodes = calculateNoOfNodes(aristas);
        this.nodes = new NodoGrafo[this.noOfNodes];
        for (int n = 0; n < this.noOfNodes; n++) {
            this.nodes[n] = new NodoGrafo();
        }
        //Agrega las aristas a los nodos, cada arista tiene dos nodos (salida y destino) 
        this.noOfEdges = aristas.length;
        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            this.nodes[aristas[edgeToAdd].getFromNodeIndex()].getEdges().add(aristas[edgeToAdd]);
            this.nodes[aristas[edgeToAdd].getToNodeIndex()].getEdges().add(aristas[edgeToAdd]);
        }
    }

    /**
     * 
     * @param aristas es la lista de las aristas disponibles
     * @return Retorna el numero de nodos
     */
    private int calculateNoOfNodes(AristaGrafo[] aristas) {
        int noOfNodes = 0;
        for (AristaGrafo e : aristas) {
            if (e.getToNodeIndex() > noOfNodes)
                noOfNodes = e.getToNodeIndex();
            if (e.getFromNodeIndex() > noOfNodes)
                noOfNodes = e.getFromNodeIndex();
        }
        noOfNodes++;
        return noOfNodes;
    }
    
    
    /**
     * 
     * @param source es el nodo de salida
     */
    public void calculateShortestDistances(int source) {
    	//Asigna el nodo de salida.
        this.nodes[source].setDistanceFromSource(source);
        int nextNode = source;
        
        //Visita cada nodo y revisa las aristas del nodo actual
        for (int i = 0; i < this.nodes.length; i++) {
            ArrayList<AristaGrafo> currentNodeEdges = this.nodes[nextNode].getEdges();
            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
                if (!this.nodes[neighbourIndex].isVisited()) {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
                    if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                    }
                }
            }
            //Define si el nodo ya fue visitado no
            nodes[nextNode].setVisited(true);
            nextNode = getNodeShortestDistanced();
        }
    }

    /**
     * 
     * @return el indice del nodo con la distancia mas corta
     */
    private int getNodeShortestDistanced() {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;
        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getDistanceFromSource();
            if (!this.nodes[i].isVisited() && currentDist < storedDist) {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }
        return storedNodeIndex;
    }

    
    public void printResult(int source) {
        String output = "Numero de nodos = " + this.noOfNodes;
        output += "\nNumero de Aristas = " + this.noOfEdges;
        for (int i = 0; i < this.nodes.length; i++) {
            output += ("\nLa distancia mas corta del nodo " + String.valueOf(source) + " al nodo " + i + " es " + nodes[i].getDistanceFromSource());
        }
        System.out.println(output);
    }

    public NodoGrafo[] getNodes() {
        return nodes;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public AristaGrafo[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }
}