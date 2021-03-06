package EstructurasDatos;

public class AristaGrafo {
	  private int fromNodeIndex;
	  private int toNodeIndex;
	  private int length;

	  public AristaGrafo(int fromNodeIndex, int toNodeIndex, int length) {
	    this.fromNodeIndex = fromNodeIndex;
	    this.toNodeIndex = toNodeIndex;
	    this.length = length;
	  }
	  public int getFromNodeIndex() {
	    return fromNodeIndex;
	  }

	  public int getToNodeIndex() {
	    return toNodeIndex;
	  }

	  public int getLength() {
	    return length;
	  }
	  /*
	   * Indica el nodo vecino del nodo de entrada.
	   */
	  public int getNeighbourIndex(int nodeIndex) {
	    if (this.fromNodeIndex == nodeIndex) {
	      return this.toNodeIndex;
	    } else {
	      return this.fromNodeIndex;
	   }
	  }
	}
