package net.imagej.ops.descriptor3d;

import net.imglib2.RealPoint;

public class Vector3d extends RealPoint {

	private double x;
	private double y;
	private double z;

	public Vector3d(final double x, final double y, final double z) {
		super(x,y,z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return getDoublePosition(0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Vector3d other = (Vector3d) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	public double getY() {
		return getDoublePosition(1);
	}

	public double getZ() {
		return getDoublePosition(2);
	}

	public Vector3d crossProduct(Vector3d w) {
		return new Vector3d(this.getY() * w.getZ() - this.getZ() * w.getY(),
				this.getZ() * w.getX() - this.getX() * w.getZ(), this.getX()
						* w.getY() - this.getY() * w.getX());
	}

	public Vector3d to(Vector3d to) {
		return new Vector3d(to.getX() - this.getX(), to.getY() - this.getY(),
				to.getZ() - this.getZ());
	}
	
	@Override
	public String toString() {
		return "X: " + this.getX() + ", Y: " + this.getY() + ", Z: " + this.getZ();
	}
	
}
