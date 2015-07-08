package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DefaultFaces implements Faces, Iterable<DefaultFace> {

	private ArrayList<DefaultFace> faces;
	
	private HashSet<Vector3d> points;
	
	private double area;
	
	private Vector3d centroid;

	public DefaultFaces() {
		faces = new ArrayList<DefaultFace>();
		points = new HashSet<Vector3d>();
		area = 0;
	}

	public ArrayList<DefaultFace> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<DefaultFace> faces) {
		this.faces = faces;
	}
	
	public void addFace(DefaultFace f) {
		faces.add(f);
		area += f.getArea();

		points.addAll(Arrays.asList(f.getVertices()));
	}
	
	public HashSet<Vector3d> getPoints() {
		return points;
	}

	public double getArea() {
		return area;
	}
	
	public Vector3d getCentroid() {
		if (centroid == null) {
			Iterator<Vector3d> it = points.iterator();
			double x,y,z = y = x = 0;
			while (it.hasNext()) {
				Vector3d next = it.next();
				x += next.getX();
				y += next.getY();
				z += next.getZ();
			}
			
			x /= points.size();
			y /= points.size();
			z /= points.size();
			centroid = new Vector3d(x, y, z);
		}
		return centroid;
	}

	@Override
	public Iterator<DefaultFace> iterator() {
		return faces.iterator();
	}
}
