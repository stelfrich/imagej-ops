package net.imagej.ops.descriptor3d;

/**
 * A Horizon is the result of n neighboring {@link TriangularFacet} which are merged.
 * The horizon is a polygon of all outer edges/vertices of the merged
 * {@link TriangularFacet}. 
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
public class Horizon extends AbstractPolygon {
	
	/**
	 * Create a new {@link Horizon} from a {@link TriangularFacet}
	 * @param f the facet
	 */
	public Horizon (TriangularFacet f) {
		m_vertices = f.getVertices();
		m_neighbors = f.getNeighbors();
	}

	/**
	 * Merges another facet to this facet.
	 * The facet has to be connected to this facet by only one edge
	 * and only the tail and head vertex of this edge are part of this 
	 * facet.
	 * Note: The neighbors of f pointing to f have to be updated manually. 
	 * @param f the facet to merge into this facet.
	 */
	public void simpleMerge(TriangularFacet f) {
		int neighborIndex = indexOfNeighbor(f);
		int newVertex = -1;
		for (int i = 0; i < f.getVertices().size(); i++) {
			if (indexOfVertex(f.getVertices().get(i)) == -1) {
				newVertex = i;
				break;
			}
		}
		getVertices().add(neighborIndex, f.getVertices().get(newVertex));
		getNeighbors().remove(neighborIndex);
		getNeighbors().add(neighborIndex, f.getNeighbor(newVertex));
		neighborIndex = (neighborIndex + 1) % (getNeighbors().size()+1);
		newVertex = (newVertex + 1) % 3;
		getNeighbors().add(neighborIndex, f.getNeighbor(newVertex));
	}

	/**
	 * Merges another facet to this facet.
	 * The facet has to be connected to this facet by only two consecutive edges
	 * and only the tail and head vertices of these edges are part of this 
	 * facet.
	 * Note: The neighbors of f pointing to f have to be updated manually.
	 * @param f the facet to merge into this facet.
	 */
	public void complexMerge(TriangularFacet f) {
		Vertex v0 = f.getVertices().get(0);
		Vertex v1 = f.getVertices().get(1);
		Vertex v2 = f.getVertices().get(2);
		if (hasEdge(v0, v2)) {
			if (hasEdge(v1, v0)) {
				mergeTwoAdjacentEdges(f, v0, 2);
			} else if (hasEdge(v2, v1)) {
				mergeTwoAdjacentEdges(f, v2, 1);
			} 
		} else if (hasEdge(v2, v1)) {
			if (hasEdge(v0, v2)) {
				mergeTwoAdjacentEdges(f, v2, 1);
			} else if (hasEdge(v1, v0)) {
				mergeTwoAdjacentEdges(f, v1, 0);
			}
		} else if (hasEdge(v1, v0)) {
			if (hasEdge(v2, v1)) {
				mergeTwoAdjacentEdges(f, v1, 0);
			} else if (hasEdge(v0, v2)) {
				mergeTwoAdjacentEdges(f, v0, 2);
			} 
		}
	}

	/**
	 * Merge a triangle with two adjacent edges into the horizon. 
	 * @param f the triangle to merge
	 * @param v0 the vertex of the triangle which lies between the two edges
	 * @param neighborIndex of the new outer neighbor
	 */
	private void mergeTwoAdjacentEdges(TriangularFacet f, Vertex v0, int neighborIndex) {
		int i = indexOfVertex(v0) ;
		getVertices().remove(i);
		getNeighbors().remove(i);
		i = i  % getNeighbors().size();
		getNeighbors().remove(i);
		getNeighbors().add(i, f.getNeighbor(neighborIndex));
	}
	
}
