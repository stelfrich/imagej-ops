package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class DefaultFacet {

	private List<Vertex> m_vertices;

	private Vector3D m_centroid = null;

	private Vector3D m_normal = null;

	private double m_area = 0;

	private List<DefaultFacet> m_neighbors;

	private List<Vertex> m_pointsInFront;

	public DefaultFacet() {
		m_vertices = new ArrayList<Vertex>();
		m_pointsInFront = new ArrayList<Vertex>();
	}

	public DefaultFacet(Vertex v0, Vertex v1, Vertex v2) {
		m_vertices = new ArrayList<Vertex>();
		m_vertices.add(v0);
		m_vertices.add(v1);
		m_vertices.add(v2);
		m_pointsInFront = new ArrayList<Vertex>();
		m_neighbors = new ArrayList<DefaultFacet>();
		computeArea();
	}

	public void addVertex(Vertex v, int pos) {
		m_vertices.add(pos, v);
	}

	public void setNeighbor(int i, DefaultFacet n) {
		m_neighbors.add(i, n);
	}

	public DefaultFacet getNeighbor(int i) {
		return m_neighbors.get(i);
	}

	public boolean hasEdge(Vertex tail, Vertex head) {
		int start = m_vertices.indexOf(tail);
		int end = m_vertices.indexOf(head);
		if (start == -1 || end == -1) {
			return false;
		}
		return (start + 1) % m_vertices.size() == end;
	}

	public void simpleMerge(DefaultFacet f) {
		int neighborIndex = m_neighbors.indexOf(f);
		int newVertex = -1;
		for (int i = 0; i < f.getVertices().size(); i++) {
			if (contains(f.getVertices().get(i)) == -1) {
				newVertex = i;
				break;
			}
		}
		m_vertices.add(neighborIndex, f.getVertices().get(newVertex));
		m_neighbors.remove(neighborIndex);
		m_neighbors.add(neighborIndex, f.getNeighbor(newVertex));
		neighborIndex = (neighborIndex + 1) % (m_neighbors.size()+1);
		newVertex = (newVertex + 1) % 3;
		m_neighbors.add(neighborIndex, f.getNeighbor(newVertex));
	}

	public void complexMerge(DefaultFacet f) {
		Vertex v0 = f.getVertices().get(0);
		Vertex v1 = f.getVertices().get(1);
		Vertex v2 = f.getVertices().get(2);
		if (hasEdge(v0, v2)) {
			if (hasEdge(v1, v0)) {
				int i = m_vertices.indexOf(v0) ;
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i  % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(2));
			} else if (hasEdge(v2, v1)) {
				int i = m_vertices.indexOf(v2);
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(1));
			} else {
				int iV0 = 0;
				int iV2 = 0;
			}
		} else if (hasEdge(v2, v1)) {
			if (hasEdge(v0, v2)) {
				int i = m_vertices.indexOf(v2);
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(1));
			} else if (hasEdge(v1, v0)) {
				int i = m_vertices.indexOf(v1);
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(0));
			} else {

			}
		} else if (hasEdge(v1, v0)) {
			if (hasEdge(v2, v1)) {
				int i = m_vertices.indexOf(v1);
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(0));
			} else if (hasEdge(v0, v2)) {
				int i = m_vertices.indexOf(v0);
				m_vertices.remove(i);
				m_neighbors.remove(i);
				i = i % m_neighbors.size();
				m_neighbors.remove(i);
				m_neighbors.add(i, f.getNeighbor(2));
			} else {

			}
		}
	}

	private boolean hasOneEdge(DefaultFacet f) {
		Vertex v0 = f.getVertices().get(0);
		Vertex v1 = f.getVertices().get(1);
		Vertex v2 = f.getVertices().get(2);
		int numEdges = 0;
		if (hasEdge(v0, v2)) {
			numEdges++;
		} else if (hasEdge(v2, v1)) {
			numEdges++;
		} else if (hasEdge(v1, v0)) {
			numEdges++;
		}
		return numEdges == 1;
	}

	public int contains(Vertex v) {
		if (m_vertices.contains(v)) {
			return m_vertices.indexOf(v);
		}
		return -1;
	}

	private void computeArea() {
		Vector3D cross = m_vertices.get(0).subtract(m_vertices.get(1))
				.crossProduct(m_vertices.get(2).subtract(m_vertices.get(0)));
		m_area = cross.getNorm() * 0.5;
	}

	public double getArea() {
		return m_area;
	}

	public Vector3D getCentroid() {
		if (m_centroid == null) {
			computeCentroid();
		}
		return m_centroid;
	}

	private void computeCentroid() {
		m_centroid = Vector3D.ZERO;
		Iterator<Vertex> it = m_vertices.iterator();

		while (it.hasNext()) {
			m_centroid = m_centroid.add(it.next());
		}

		m_centroid = m_centroid.scalarMultiply(1 / (double) m_vertices.size());
	}

	public Vector3D getNormal() {
		if (m_normal == null) {
			computeNormal();
		}
		return m_normal;
	}

	private void computeNormal() {
		Vector3D v0 = m_vertices.get(0);
		Vector3D v1 = m_vertices.get(1);
		Vector3D v2 = m_vertices.get(2);
		m_normal = v1.subtract(v0).crossProduct(v2.subtract(v0)).normalize();
	}

	public double getPlaneOffset() {
		return getNormal().dotProduct(getCentroid());
	}

	public double distanceToPlane(Vector3D p) {
		return getNormal().dotProduct(p) - getPlaneOffset();
	}

	public List<Vertex> getVertices() {
		return m_vertices;
	}

	public void setPointInFront(Vertex v, double distanceToPlane) {
		if (m_pointsInFront.isEmpty()) {
			v.setDistanceToFaceInFront(distanceToPlane);
			m_pointsInFront.add(v);
		} else {
			if (m_pointsInFront.get(0)
					.getDistanceToFaceInFront() < distanceToPlane) {
				v.setDistanceToFaceInFront(distanceToPlane);
				m_pointsInFront.add(0, v);
			} else {
				m_pointsInFront.add(v);
			}
		}
	}

	public boolean hasPointInFront() {
		return !m_pointsInFront.isEmpty();
	}

	public Vertex nextPointInFront() {
		Vertex v = m_pointsInFront.get(0);
		m_pointsInFront.remove(0);
		return v;
	}

	public List<Vertex> getPointsInFront() {
		return m_pointsInFront;
	}
	
	public Vertex getMaximumDistanceVertex() {
		return m_pointsInFront.remove(0);
	}

	public List<DefaultFacet> getNeighbors() {
		return m_neighbors;
	}

	public void replaceNeighbor(int i, DefaultFacet f) {
		m_neighbors.remove(i);
		m_neighbors.add(i, f);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(m_area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((m_centroid == null) ? 0 : m_centroid.hashCode());
		result = prime * result
				+ ((m_neighbors == null) ? 0 : m_neighbors.hashCode());
		result = prime * result
				+ ((m_normal == null) ? 0 : m_normal.hashCode());
		result = prime * result
				+ ((m_pointsInFront == null) ? 0 : m_pointsInFront.hashCode());
		result = prime * result
				+ ((m_vertices == null) ? 0 : m_vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultFacet other = (DefaultFacet) obj;
		if (Double.doubleToLongBits(m_area) != Double
				.doubleToLongBits(other.m_area))
			return false;
		if (m_centroid == null) {
			if (other.m_centroid != null)
				return false;
		} else if (!m_centroid.equals(other.m_centroid))
			return false;
		if (m_neighbors == null) {
			if (other.m_neighbors != null)
				return false;
		} else if (!m_neighbors.equals(other.m_neighbors))
			return false;
		if (m_normal == null) {
			if (other.m_normal != null)
				return false;
		} else if (!m_normal.equals(other.m_normal))
			return false;
		if (m_pointsInFront == null) {
			if (other.m_pointsInFront != null)
				return false;
		} else if (!m_pointsInFront.equals(other.m_pointsInFront))
			return false;
		if (m_vertices == null) {
			if (other.m_vertices != null)
				return false;
		} else if (!m_vertices.equals(other.m_vertices))
			return false;
		return true;
	}

	public boolean containsAll(List<Vertex> vertices) {
		boolean containsAll = true;
		for (Vertex v : vertices) {
			containsAll = containsAll && m_vertices.contains(v);
		}
		return containsAll;
	}

	public int indexOfVertex(Vertex vertex) {
		return m_vertices.indexOf(vertex);
	}

	public Vertex getVertex(int i) {
		return m_vertices.get(i);
	}
	
	public int size() {
		return m_vertices.size();
	}

	public Vertex getLastVertex() {
		return m_vertices.get(m_vertices.size() - 1);
	}

	public int indexOfNeighbor(DefaultFacet merge) {
		return m_neighbors.indexOf(merge);
	}
}
