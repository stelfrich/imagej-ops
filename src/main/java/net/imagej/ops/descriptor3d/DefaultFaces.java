package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DefaultFaces implements Faces, Iterable<MyFace> {

	private ArrayList<MyFace> faces;
	
	private HashSet<Vertex> points;
	
	private double area;
	
	private Vertex centroid;

	public DefaultFaces() {
		faces = new ArrayList<MyFace>();
		points = new HashSet<Vertex>();
		area = 0;
	}

	public ArrayList<MyFace> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<MyFace> faces) {
		this.faces = faces;
	}
	
	public void addFace(MyFace f) {
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
	public Iterator<MyFace> iterator() {
		return faces.iterator();
	}
}
