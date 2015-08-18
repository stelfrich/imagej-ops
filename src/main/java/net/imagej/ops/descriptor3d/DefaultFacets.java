package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DefaultFacets implements Facets, Iterable<DefaultFacet> {

	private ArrayList<DefaultFacet> faces;
	
	private HashSet<Vertex> points;
	
	private double area;
	
	private Vertex centroid;

	private double m_epsilon;

	public DefaultFacets() {
		faces = new ArrayList<DefaultFacet>();
		points = new HashSet<Vertex>();
		area = 0;
	}

	public ArrayList<DefaultFacet> getFacets() {
		return faces;
	}

	public void setFaces(ArrayList<DefaultFacet> faces) {
		this.faces = faces;
	}
	
	public void addFace(DefaultFacet f) {
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
	public Iterator<DefaultFacet> iterator() {
		return faces.iterator();
	}

	public void setEpsilon(double epsilon) {
		m_epsilon = epsilon;
	}
	
	public double getEpsilon() {
		return m_epsilon;
	}
}
