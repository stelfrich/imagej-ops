package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Iterator;

public class DefaultFaces implements Faces, Iterable<DefaultFace> {

	private ArrayList<DefaultFace> faces;
	
	private double area;

	public DefaultFaces() {
		faces = new ArrayList<DefaultFace>();
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
	}

	public double getArea() {
		return area;
	}

	@Override
	public Iterator<DefaultFace> iterator() {
		return faces.iterator();
	}
}
