package EstructurasDatos;

import java.util.ArrayList;

public class NodoGrafo {
	  private int distanceFromSource = Integer.MAX_VALUE;
	  private boolean visited;
	  private ArrayList<AristaGrafo> edges = new ArrayList<AristaGrafo>(); 
	  public int getDistanceFromSource() {
	    return distanceFromSource;
	  }
	  public void setDistanceFromSource(int distanceFromSource) {
	    this.distanceFromSource = distanceFromSource;
	  }
	  public boolean isVisited() {
	    return visited;
	  }
	  public void setVisited(boolean visited) {
	    this.visited = visited;
	  }
	  public ArrayList<AristaGrafo> getEdges() {
	    return edges;
	  }
	  public void setEdges(ArrayList<AristaGrafo> edges) {
	    this.edges = edges;
	  }
	}
