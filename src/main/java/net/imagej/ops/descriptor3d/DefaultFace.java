package net.imagej.ops.descriptor3d;

public class DefaultFace {
	private Vector3d[] vertices;
	private Vector3d centroid;
	private Vector3d normal;
	private double area = -1;

	public DefaultFace() {
		vertices = new Vector3d[3];
	}
	
	public DefaultFace(Vector3d p1, Vector3d p2, Vector3d p3) {
		vertices = new Vector3d[] { p1, p2, p3 };
	}
	
	public void setP(Vector3d p, int pos) {
		vertices[pos] = p;
	}

	public Vector3d[] getVertices() {
		return vertices;
	}

	public Vector3d getCentroid() {
		if (centroid == null) {
			centroid = new Vector3d(
					(vertices[0].getX() + vertices[1].getX() + vertices[2]
							.getX()) / 3.0,
					(vertices[0].getY() + vertices[1].getY() + vertices[2]
							.getY()) / 3.0, (vertices[0].getZ()
							+ vertices[1].getZ() + vertices[2].getZ()) / 3.0);
		}
		return centroid;
	}

	public Vector3d getNormal() {
		if (normal == null) {
			Vector3d XY = vertices[0].to(vertices[1]);
			Vector3d XZ = vertices[0].to(vertices[2]);
			normal = XY.crossProduct(XZ);
		}
		return normal;
	}

	public double getArea() {
		
		if (area == -1) {
			computeArea();
		}
		return area;
	}

	private void computeArea() {
		Vector3d XY = vertices[0].to(vertices[1]);
		Vector3d XZ = vertices[0].to(vertices[2]);

		Vector3d cp = XY.crossProduct(XZ);

		area = Math.abs((1 / 2.0)
				* Math.sqrt(Math.pow(cp.getX(), 2) + Math.pow(cp.getY(), 2)
						+ Math.pow(cp.getZ(), 2)));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DefaultFace) {
			DefaultFace o = (DefaultFace) obj;

			return this.vertices[0].equals(o.vertices[0])
					&& this.vertices[1].equals(o.vertices[1])
					&& this.vertices[2].equals(o.vertices[2]);
		}
		return false;
	}
}
