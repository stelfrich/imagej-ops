package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DefaultFacets implements Facets, Iterable<TriangularFacet> {

	private ArrayList<TriangularFacet> faces;
	
	private HashSet<Vertex> points;
	
	private double area;
	
	private Vertex centroid;

	private double m_epsilon;

	public DefaultFacets() {
		faces = new ArrayList<TriangularFacet>();
		points = new HashSet<Vertex>();
		area = 0;
	}

	public ArrayList<TriangularFacet> getFacets() {
		return faces;
	}

	public void setFaces(ArrayList<TriangularFacet> faces) {
		this.faces = faces;
	}
	
	public void addFace(TriangularFacet f) {
		faces.add(f);
		area += f.getArea();

		points.addAll(f.getVertices());
	}
	
	public HashSet<Vertex> getPoints() {
		return points;
	}

	public double getArea() {
		return area;
	}
	
	public Vertex getCentroid() {
		if (centroid == null) {
			Iterator<Vertex> it = points.iterator();
			double x,y,z = y = x = 0;
			while (it.hasNext()) {
				Vertex next = it.next();
				x += next.getX();
				y += next.getY();
				z += next.getZ();
			}
			
			x /= points.size();
			y /= points.size();
			z /= points.size();
			centroid = new Vertex(x, y, z);
		}
		return centroid;
	}

	@Override
	public Iterator<TriangularFacet> iterator() {
		return faces.iterator();
	}

	public void setEpsilon(double epsilon) {
		m_epsilon = epsilon;
	}
	
	public double getEpsilon() {
		return m_epsilon;
	}

	public void setPoints(HashSet<Vertex> points) {
		this.points = points;
	}
}
