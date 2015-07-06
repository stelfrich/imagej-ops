package net.imagej.ops.descriptor3d;

import net.imglib2.RealPoint;

public class Vector3d extends RealPoint {

	public Vector3d(final double x, final double y, final double z) {
		super(x,y,z);
	}

	public double getX() {
		return getDoublePosition(0);
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
