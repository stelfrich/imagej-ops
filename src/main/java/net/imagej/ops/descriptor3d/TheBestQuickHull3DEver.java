package net.imagej.ops.descriptor3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class TheBestQuickHull3DEver {

	/**
	 * Points which are not part of the convex hull.
	 */
	private Set<Vertex> m_points;

	/**
	 * Created facets.
	 */
	private List<MyFace> m_facets;

	/**
	 * Facets with points in front.
	 */
	private List<MyFace> m_facetsWithPointInFront;

	/**
	 * Minimum distance between a point and a facet.
	 */
	private double m_epsilon;

	/**
	 * Precision of a double.
	 */
	static private final double DOUBLE_PREC = 2.2204460492503131e-16;

	/**
	 * Creates a new QuickHull object.
	 * @param points the points of which the convex hull should be computed.
	 */
	public TheBestQuickHull3DEver(Vertex[] points) {
		m_points = new HashSet<Vertex>(Arrays.asList(points));
		m_facets = new ArrayList<MyFace>();
		m_facetsWithPointInFront = new ArrayList<MyFace>();
	}

	/**
	 * Compute the convex hull.
	 */
	public void build() {
		createSimplex();
		assignPointsToFacets(m_facets);
		while (!m_facetsWithPointInFront.isEmpty()) {
			MyFace next = m_facetsWithPointInFront.remove(0);
			m_facets.remove(next);
			replaceFacet(next);
		}
	}

	/**
	 * Get the facets of the convex hull.
	 * @return the facets
	 */
	public MyFace[] getFacets() {
		return m_facets.toArray(new MyFace[m_facets.size()]);
	}

	/**
	 * Get the used minimum distance between a point and a facet.
	 * @return the minimum distance
	 */
	public double getTolerance() {
		return m_epsilon;
	}

	/**
	 * Replaces a facet with at least three new facets.
	 * @param facet the facet to replace. At least one point must be in front
	 * of next.
	 */
	private void replaceFacet(MyFace facet) {
		Vertex v0 = facet.getMaximumDistanceVertex();
		MyFace horizon = gethorizon(facet, v0);
		List<MyFace> newFaces = addFacets(horizon, v0);
		assignPointsToFacets(newFaces);
		m_facets.addAll(newFaces);
	}

	/**
	 * Adds for each edge of the horizon and v0 a new facet.
	 * @param horizon facet of all facets seen from point v0
	 * @param v0 point which is added to the convex hull
	 * @return new created facets
	 */
	private List<MyFace> addFacets(MyFace horizon, Vertex v0) {
		List<MyFace> newFacets = new ArrayList<MyFace>();
		for (int i = 1; i < horizon.getVertices().size(); i++) {
			MyFace f = new MyFace(horizon.getVertices().get(i), v0,
					horizon.getVertices().get(i - 1));

			MyFace n = horizon.getNeighbor(i);
			int vertexIndex = n.getVertices()
					.indexOf(horizon.getVertices().get(i - 1));
			n.replaceNeighbor(vertexIndex, f);

			f.setNeighbor(0, n);
			newFacets.add(f);
		}
		MyFace f = new MyFace(horizon.getVertices().get(0), v0,
				horizon.getVertices().get(horizon.getVertices().size() - 1));

		MyFace n = horizon.getNeighbor(0);
		int vertexIndex = n.getVertices().indexOf(
				horizon.getVertices().get(horizon.getVertices().size() - 1));
		n.replaceNeighbor(vertexIndex, f);

		f.setNeighbor(0, n);

		newFacets.add(f);
		for (int i = 1; i < newFacets.size() - 1; i++) {
			newFacets.get(i).setNeighbor(1, newFacets.get(i + 1));
			newFacets.get(i).setNeighbor(2, newFacets.get(i - 1));
		}
		newFacets.get(0).setNeighbor(1, newFacets.get(1));
		newFacets.get(0).setNeighbor(2, newFacets.get(newFacets.size() - 1));
		newFacets.get(newFacets.size() - 1).setNeighbor(1, newFacets.get(0));
		newFacets.get(newFacets.size() - 1).setNeighbor(2,
				newFacets.get(newFacets.size() - 2));
		return newFacets;
	}
	
	/**
	 * Computes the horizon of v. The horizon is the merged facet of all facets which 
	 * are in front of the point v. 
	 * @param front a face which is in front of v
	 * @param v a point outside of the convex hull
	 * @return facet containing all facets which are in front of v
	 */
	private MyFace gethorizon(MyFace front, Vertex v) {
		m_points.addAll(front.getPointsInFront());
		m_facets.remove(front);
		MyFace merge = nextToMerge(front, v);
		while (merge != null) {
			m_points.addAll(merge.getPointsInFront());
			m_facets.remove(merge);
			m_facetsWithPointInFront.remove(merge);
			if (front.containsAll(merge.getVertices()) ) {
				for (MyFace f : merge.getNeighbors()) {
					if (!f.equals(front)) {
						int i = f.getNeighbors().indexOf(merge);
						f.replaceNeighbor(i, front);
					}
				}
				
				
				front.complexMerge(merge);
			} else {
				for (MyFace f : merge.getNeighbors()) {
					if (!f.equals(front)) {
						int i = f.getNeighbors().indexOf(merge);
						f.replaceNeighbor(i, front);
					}
				}
				front.simpleMerge(merge);
			}
			merge = nextToMerge(front, v);
		}
		
		return front;
	}

	/**
	 * Returns a facet which is in front of v and neighbor of front.
	 * @param front facet in front of v
	 * @param v point which is added to the convex hull
	 * @return neighboring facet of front or null if no facet is in front
	 */
	private MyFace nextToMerge(MyFace front, Vertex v) {
		Iterator<MyFace> it = front.getNeighbors().iterator();
		while (it.hasNext()) {
			MyFace f = it.next();
			if (f.distanceToPlane(v) > m_epsilon) {
				if (front.containsAll(f.getVertices())) {
					Vertex v0 = f.getVertices().get(0);
					Vertex v1 = f.getVertices().get(1);
					Vertex v2 = f.getVertices().get(2);
					int numEdges = 0;
					if (front.hasEdge(v0, v2)) {
						numEdges++;
					} 
					if (front.hasEdge(v2, v1)) {
						numEdges++;
					}
					if (front.hasEdge(v1, v0)) {
						numEdges++;
					}
					if (numEdges == 1) {
						continue;
					}
				}
				return f;
			}
		}
		return null;
	}

	/**
	 * Assigns all points which are not part of the convex hull to a facet. 
	 * A point is assigned to a facet if the point is in front of this facet.
	 * Every point is assigned to only one facet.
	 * If a facet has a point in front the facet is added to {@link TheBestQuickHull3DEver#m_facetsWithPointInFront}.
	 * After this call {@link TheBestQuickHull3DEver#m_points} is empty.
	 * Points which are behind all facets are removed because they are on the inside of the convex hull.
	 * @param facets which could have a point in front
	 */
	private void assignPointsToFacets(List<MyFace> facets) {
		Iterator<Vertex> it = m_points.iterator();
		while (it.hasNext()) {
			Vertex v = it.next();
			Iterator<MyFace> itF = facets.iterator();
			MyFace maxFace = null;
			double maxdis = m_epsilon;
			while (itF.hasNext()) {
				MyFace f = itF.next();
				double distanceToPlane = f.distanceToPlane(v);
				if (distanceToPlane > maxdis) {
					maxdis = distanceToPlane;
					maxFace = f;
				}
			}
			if (maxFace != null) {
				maxFace.setPointInFront(v, maxdis);
				if (!m_facetsWithPointInFront.contains(maxFace)) {
					m_facetsWithPointInFront.add(maxFace);
				}
			}
		}
		m_points.clear();
	}

	/**
	 * Computes an initial simplex of four facets. The simplex consists of the four points v0-v3.
	 * v0 and v1 have the largest possible distance in one dimension.
	 * v2 is the point with the largest distance to v0----v1.
	 * v3 is the point with the largest distance to the plane described by v0, v1, v2.
	 */
	private void createSimplex() {
		Vertex[] minMax = computeMinMax();
		int i = getMaxDistPointIndex(minMax);

		Vertex v0 = minMax[i];
		Vertex v1 = minMax[i + 3];

		m_points.remove(v0);
		m_points.remove(v1);

		Vertex v2 = getV2(v0, v1);

		m_points.remove(v2);

		Vertex v3 = getV3(v0, v1, v2);

		m_points.remove(v3);

		MyFace f0 = new MyFace(v0, v1, v2);
		if (f0.distanceToPlane(v3) > m_epsilon) {
			Vertex tmp = v1;
			v1 = v2;
			v2 = tmp;
			f0 = new MyFace(v0, v1, v2);
		}
		// v3 is behind f0
		assert f0.distanceToPlane(v3) < m_epsilon;

		MyFace f1 = new MyFace(v1, v0, v3);
		MyFace f2 = new MyFace(v2, v1, v3);
		MyFace f3 = new MyFace(v0, v2, v3);

		f0.setNeighbor(0, f3);
		f0.setNeighbor(1, f1);
		f0.setNeighbor(2, f2);

		f1.setNeighbor(0, f2);
		f1.setNeighbor(1, f0);
		f1.setNeighbor(2, f3);

		f2.setNeighbor(0, f3);
		f2.setNeighbor(1, f0);
		f2.setNeighbor(2, f1);

		f3.setNeighbor(0, f1);
		f3.setNeighbor(1, f0);
		f3.setNeighbor(2, f2);

		assert f0.distanceToPlane(v3) < m_epsilon;
		assert f1.distanceToPlane(v2) < m_epsilon;
		assert f2.distanceToPlane(v0) < m_epsilon;
		assert f3.distanceToPlane(v1) < m_epsilon;

		m_facets.add(f0);
		m_facets.add(f1);
		m_facets.add(f2);
		m_facets.add(f3);
	}

	/**
	 * Finds the point with the largest distance to the plane described by v0, v1, v2.
	 * @param v0 Vertex of the plane.
	 * @param v1 Vertex of the plane.
	 * @param v2 Vertex of the plane.
	 * @return Vertex with the largest distance.
	 */
	private Vertex getV3(Vertex v0, Vertex v1, Vertex v2) {
		double distPlanePoint = m_epsilon;
		Vertex v3 = null;
		Iterator<Vertex> it = m_points.iterator();
		Vector3D d0 = v1.subtract(v0);
		Vector3D d1 = v2.subtract(v0);
		Vector3D normal = d0.crossProduct(d1).normalize();
		while (it.hasNext()) {
			Vertex v = it.next();
			double d = Math.abs(normal.dotProduct(v.subtract(v0)));
			if (d > distPlanePoint) {
				distPlanePoint = d;
				v3 = v;
			}
		}
		return v3;
	}

	/**
	 * Finds the vertex with the largest distance to the line described by v0, v1.
	 * @param v0 Vertex of the line.
	 * @param v1 Vertex of the line.
	 * @return Vertex with the largest distance.
	 */
	private Vertex getV2(Vertex v0, Vertex v1) {
		Iterator<Vertex> it = m_points.iterator();

		// v0 -------------------------------------v1
		// |
		// | d
		// |
		// * v
		//
		// d = |(v - v0) x (v - v1)| / |(v1 - v0)|
		// We can omit the common denominator because it does not change over
		// all computations.
		double distLinePoint = m_epsilon;
		Vertex v2 = null;
		while (it.hasNext()) {
			Vertex v = it.next();
			Vector3D d0 = v.subtract(v1);
			Vector3D d1 = v.subtract(v0);

			double lengthSq = d0.crossProduct(d1).getNormSq();
			if (lengthSq > distLinePoint) {
				distLinePoint = lengthSq;
				v2 = v;
			}
		}
		return v2;
	}

	/**
	 * Computes the index of the dimension containing the points with the largest distance.
	 * @param minMax Vertices with the min and max coordinates of each dimension.
	 * @return index of the dimension with the largest distance between two points.
	 */
	private int getMaxDistPointIndex(Vertex[] minMax) {
		double[] diff = new double[]{minMax[3].getX() - minMax[0].getX(),
				minMax[4].getY() - minMax[1].getY(),
				minMax[5].getZ() - minMax[2].getZ()};

		double max = 0;
		int imax = 0;
		for (int i = 0; i < diff.length; i++) {
			if (diff[i] > max) {
				max = diff[i];
				imax = i;
			}
		}
		return imax;
	}

	/**
	 * Finds for each dimension the min and max vertex.
	 * @return min and max vertices of each dimension
	 */
	private Vertex[] computeMinMax() {
		Vertex[] minMax = new Vertex[6];
		double maxX, maxY, maxZ;
		double minX, minY, minZ;
		Iterator<Vertex> it = m_points.iterator();

		Vertex initPoint = it.next();
		for (int i = 0; i < minMax.length; i++) {
			minMax[i] = initPoint;
		}
		minX = maxX = initPoint.getX();
		minY = maxY = initPoint.getY();
		minZ = maxZ = initPoint.getZ();

		while (it.hasNext()) {
			Vertex v = it.next();
			if (v.getX() > maxX) {
				maxX = v.getX();
				minMax[3] = v;
			} else if (v.getX() < minX) {
				minX = v.getX();
				minMax[0] = v;
			}
			if (v.getY() > maxY) {
				maxY = v.getY();
				minMax[4] = v;
			} else if (v.getY() < minY) {
				minY = v.getY();
				minMax[2] = v;
			}
			if (v.getZ() > maxZ) {
				maxZ = v.getZ();
				minMax[5] = v;
			} else if (v.getZ() < minZ) {
				minZ = v.getZ();
				minMax[3] = v;
			}
		}

		// This epsilon formula comes from John Lloyd's quickhull
		// implementation http://www.cs.ubc.ca/~lloyd/java/quickhull3d.html
		m_epsilon = 3 * DOUBLE_PREC
				* (Math.max(Math.abs(maxX), Math.abs(minX))
						+ Math.max(Math.abs(maxY), Math.abs(minY))
						+ Math.max(Math.abs(maxZ), Math.abs(minZ)));
		return minMax;
	}

}
