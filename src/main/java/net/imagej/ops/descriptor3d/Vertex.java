package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Represents vertices of the hull, as well as the points from which it is
 * formed.
 *
 * @author Tim-Oliver Buchholz, University of Konstanz
 */
class Vertex extends Vector3D {

	
	private double m_distanceToFaceInFront = 0;
	
	private List<MyFace> m_facesInFront = new ArrayList<MyFace>();
	
	public Vertex(double x, double y, double z) {
		super(x, y, z);
	}

	public double getDistanceToFaceInFront() {
		return m_distanceToFaceInFront;
	}

	public void setDistanceToFaceInFront(double m_distanceToFaceInFront) {
		this.m_distanceToFaceInFront = m_distanceToFaceInFront;
	}

	public void addFaceInFront(MyFace f) {
		m_facesInFront.add(f);
	}
	
	public void clearFacesInFront() {
		m_facesInFront.clear();
	}
	
	
	public List<Vertex> getPointsInFront() {
		Iterator<MyFace> it = m_facesInFront.iterator();
		List<Vertex> l = new ArrayList<Vertex>();
		while (it.hasNext()) {
			l.addAll(it.next().getPointsInFront());
		}
		return l;
	}

	public List<MyFace> getFacesInFront() {
		// TODO Auto-generated method stub
		return m_facesInFront;
	}

	public void cleanFaceInFront() {
		m_facesInFront.clear();
	}
}
