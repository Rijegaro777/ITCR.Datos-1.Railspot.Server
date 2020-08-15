package EstructurasDatos;

import java.util.ArrayList;

public class Grafos {
	  private NodoGrafo[] nodes;
	  private int noOfNodes;
	  private AristaGrafo[] edges;
	  private int noOfEdges;
	  
	  
	  public Grafos(AristaGrafo[] aristas) {
	    this.edges = aristas;
	    this.noOfNodes = calculateNoOfNodes(aristas);
	    this.nodes = new NodoGrafo[this.noOfNodes];
	    for (int n = 0; n < this.noOfNodes; n++) {
	      this.nodes[n] = new NodoGrafo();
	    }
	    this.noOfEdges = aristas.length;
	    for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
	      this.nodes[aristas[edgeToAdd].getFromNodeIndex()].getEdges().add(aristas[edgeToAdd]);
	      this.nodes[aristas[edgeToAdd].getToNodeIndex()].getEdges().add(aristas[edgeToAdd]);
	    }
	  }
	  
	  
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
	  
	  
	 
	  public void calculateShortestDistances() {
	    this.nodes[0].setDistanceFromSource(0);
	    int nextNode = 0;
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
	      nodes[nextNode].setVisited(true);
	      nextNode = getNodeShortestDistanced();
	   }
	  }
	  
	  
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
	  
	  
	  /*
	   * Despliega el resultado
	   */
	  public void printResult() {
	    String output = "Numero de nodos = " + this.noOfNodes;
	    output += "\nNumero de Aristas = " + this.noOfEdges;
	    for (int i = 0; i < this.nodes.length; i++) {
	      output += ("\nLa distancia mas corta del nodo 0 al nodo " + i + " es " + nodes[i].getDistanceFromSource());
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