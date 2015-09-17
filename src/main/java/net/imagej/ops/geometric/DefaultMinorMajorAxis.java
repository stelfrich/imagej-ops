
package net.imagej.ops.geometric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.imagej.ops.AbstractFunctionOp;
import net.imagej.ops.Contingent;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.MinorMajorAxis;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;
import net.imglib2.util.ValuePair;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link MinorMajorAxis}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: MinorMajorAxis",
	name = Geometric.MinorMajorAxis.NAME)
public class DefaultMinorMajorAxis extends
	AbstractFunctionOp<Polygon, Pair<DoubleType, DoubleType>>implements
	GeometricOp<Polygon, Pair<DoubleType, DoubleType>>, Contingent,
	Geometric.MinorMajorAxis
{

	/**
	 * Code taken from ImageJ1 (EllipseFitter -> getEllipseParam()) and adapted to
	 * work with a {@link Polygon}
	 * 
	 * @param points The vertices of the polygon in counter clockwise order.
	 * @return the minor and the major axis
	 */
	private double[] getMinorMajorAxis(List<RealLocalizable> points) {
		double[] moments = getMoments(points);

		double m00 = moments[0];
		double u20 = moments[1];
		double u11 = moments[2];
		double u02 = moments[3];

		double m4 = 4.0 * Math.abs(u02 * u20 - u11 * u11);
		if (m4 < 0.000001) {
			m4 = 0.000001;
		}

		double a11 = u02 / m4;
		double a12 = u11 / m4;
		double a22 = u20 / m4;

		double tmp = a11 - a22;
		if (tmp == 0.0) {
			tmp = 0.000001;
		}

		double theta = 0.5 * Math.atan(2.0 * a12 / tmp);
		if (theta < 0.0) {
			theta += Math.PI / 2d;
		}
		if (a12 > 0.0) {
			theta += Math.PI / 2d;
		}

		else if (a12 == 0.0) {
			if (a22 > a11) {
				theta = 0.0;
				tmp = a22;
				a22 = a11;
				a11 = tmp;
			}
			else if (a11 != a22) {
				theta = Math.PI / 2d;
			}
		}

		tmp = Math.sin(theta);
		if (tmp == 0.0) tmp = 0.000001;
		double z = a12 * Math.cos(theta) / tmp;
		double major = Math.sqrt(1.0 / Math.abs(a22 + z));
		double minor = Math.sqrt(1.0 / Math.abs(a11 - z));
		// equalize areas
		
		//FIXME: m00 is < zero thats why the tests fail.
		double scale = Math.sqrt(m00 / (Math.PI * major * minor));

		major = major * scale * 2.0;
		minor = minor * scale * 2.0;
		double angle = 180.0 * theta / Math.PI;

		if (angle == 180.0) angle = 0.0;
		if (major < minor) {
			tmp = major;
			major = minor;
			minor = tmp;
		}

		return new double[] { minor, major };
	}

	/**
	 * Calculates the moments for the {@link Polygon}
	 * 
	 * @param points The vertices of the polygon in counter clockwise order.
	 * @return the moments m00, n20, n11 and n02
	 * @see "On the Calculation of Arbitrary Moments of Polygons, Carsten Steger, October 1996"
	 */
	private double[] getMoments(List<RealLocalizable> points) {

		// calculate normalized moment
		double m00 = 0;
		double m01 = 0;
		double m02 = 0;
		double m10 = 0;
		double m11 = 0;
		double m20 = 0;

		for (int i = 1; i < points.size(); i++) {
			double a = getX(points, i - 1) * getY(points, i) - getX(points, i) * getY(
				points, i - 1);

			m00 += a;
			m10 += a * (getX(points, i - 1) + getX(points, i));
			m01 += a * (getY(points, i - 1) + getY(points, i));

			m20 += a * (Math.pow(getX(points, i - 1), 2) + getX(points, i - 1) * getX(
				points, i) + Math.pow(getX(points, i), 2));
			m11 += a * (2 * getX(points, i - 1) * getY(points, i - 1) + getX(points,
				i - 1) * getY(points, i) + getX(points, i) * getY(points, i - 1) + 2 *
					getX(points, i) * getY(points, i));
			m02 += a * (Math.pow(getY(points, i - 1), 2) + getY(points, i - 1) * getY(
				points, i) + Math.pow(getY(points, i), 2));
		}

		m00 /= 2d;
		m01 /= 6 * m00;
		m02 /= 12d * m00;
		m10 /= 6d * m00;
		m11 /= 24d * m00;
		m20 /= 12d * m00;

		// calculate central moments
		double n20 = m20 - Math.pow(m10, 2);
		double n11 = m11 - m10 * m01;
		double n02 = m02 - Math.pow(m01, 2);

		return new double[] { m00, n20, n11, n02 };
	}

	private double getY(List<RealLocalizable> points, int index) {
		return points.get(index).getDoublePosition(1);
	}

	private double getX(List<RealLocalizable> points, int index) {
		return points.get(index).getDoublePosition(0);
	}

	@Override
	public Pair<DoubleType, DoubleType> compute(Polygon input) {

		List<RealLocalizable> points = new ArrayList<RealLocalizable>(input
			.getVertices());

		// Sort the RealPoints of P by x-coordinate (in case of a tie, sort by
		// y-coordinate). Sorting is counter clockwise.
		Collections.sort(points, new Comparator<RealLocalizable>() {

			@Override
			public int compare(final RealLocalizable o1, final RealLocalizable o2) {
				final Double o1x = new Double(o1.getDoublePosition(0));
				final Double o2x = new Double(o2.getDoublePosition(0));
				final int result = o2x.compareTo(o1x);
				if (result == 0) {
					return new Double(o2.getDoublePosition(1)).compareTo(new Double(o1
						.getDoublePosition(1)));
				}

				return result;
			}
		});
		points.add(points.get(0));

		// calculate minor and major axis
		double[] minorMajorAxis = getMinorMajorAxis(points);
		return new ValuePair<DoubleType, DoubleType>(new DoubleType(
			minorMajorAxis[0]), new DoubleType(minorMajorAxis[1]));
	}

	@Override
	public boolean conforms() {
		return in().numDimensions() == 2;
	}
}
