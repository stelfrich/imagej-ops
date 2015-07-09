package net.imagej.ops.descriptor3d;

public class Moments2 {

	private double s00;
	private double s01;
	private double s02;
	private double s11;
	private double s12;
	private double s22;

	public Moments2(final double s00, final double s01, final double s02,
			final double s11, final double s12, final double s22) {
		this.s00 = s00;
		this.s01 = s01;
		this.s02 = s02;
		this.s11 = s11;
		this.s12 = s12;
		this.s22 = s22;
	}

	public double getS00() {
		return s00;
	}

	public void setS200(double s00) {
		this.s00 = s00;
	}

	public double getS01() {
		return s01;
	}

	public void setS110(double s01) {
		this.s01 = s01;
	}

	public double getS02() {
		return s02;
	}

	public void setS101(double s02) {
		this.s02 = s02;
	}

	public double getS11() {
		return s11;
	}

	public void setS020(double s11) {
		this.s11 = s11;
	}

	public double getS12() {
		return s12;
	}

	public void setS011(double s12) {
		this.s12 = s12;
	}

	public double getS22() {
		return s22;
	}

	public void setS002(double s22) {
		this.s22 = s22;
	}

}
