
package net.imagej.ops.geometric;

import java.util.ArrayList;
import java.util.List;

import net.imagej.ops.AbstractFunctionOp;
import net.imagej.ops.Contingent;
import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Area;
import net.imagej.ops.Ops.Geometric.BoundingBox;
import net.imagej.ops.Ops.Geometric.CenterOfGravity;
import net.imagej.ops.Ops.Geometric.ConvexHull;
import net.imagej.ops.Ops.Geometric.SmallestEnclosingRectangle;
import net.imglib2.RealLocalizable;
import net.imglib2.RealPoint;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link SmallestEnclosingRectangle}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class,
	label = "Geometric: Smallest Enclosing Rectangle",
	name = Geometric.SmallestEnclosingRectangle.NAME)
public class DefaultSmallestEnclosingRectangle extends
	AbstractFunctionOp<Polygon, Polygon> implements
	GeometricOp<Polygon, Polygon>, Contingent,
	Geometric.SmallestEnclosingRectangle
{

	private FunctionOp<Polygon, Polygon> convexHullFunc;
	private FunctionOp<Polygon, RealLocalizable> cogFunc;
	private FunctionOp<Polygon, Polygon> boundingBoxFunc;
	private FunctionOp<Polygon, DoubleType> areaFunc;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize() {
		convexHullFunc =
			(FunctionOp) ops().function(ConvexHull.class, Polygon.class, in());
		cogFunc =
			(FunctionOp) ops().function(CenterOfGravity.class, RealLocalizable.class,
				in());
		boundingBoxFunc = ops().function(BoundingBox.class, Polygon.class, in());
		areaFunc = ops().function(Area.class, DoubleType.class, in());
	}

	@Override
	public Polygon compute(final Polygon input) {

		final Polygon ch = convexHullFunc.compute(input);
		final RealLocalizable cog = cogFunc.compute(ch);

		Polygon minBounds = null;
		double minArea = Double.POSITIVE_INFINITY;
		// for each edge (i.e. line from P(i-1) to P(i)
		for (int i = 1; i < ch.getVertices().size() - 1; i++) {
			double angle =
				Math.atan2(ch.getVertices().get(i).getDoublePosition(1) -
					ch.getVertices().get(i - 1).getDoublePosition(1), ch.getVertices()
					.get(i).getDoublePosition(0) -
					ch.getVertices().get(i - 1).getDoublePosition(0));

			// rotate the polygon in such a manner that the line has an angle of
			// 0
			final Polygon rotatedPoly = rotate(ch, -angle, cog);

			// get the bounds
			final Polygon bounds = boundingBoxFunc.compute(rotatedPoly);

			// calculate the area of the bounds
			// double area = getBoundsArea(bounds);
			double area = areaFunc.compute(rotatedPoly).get();

			// if the area of the bounds is smaller, rotate it to match the
			// original polygon and save it.
			if (area < minArea) {
				minArea = area;
				minBounds = rotate(bounds, angle, cog);
			}
		}

		return minBounds;
	}

	/**
	 * Rotates the given Polygon consisting of a list of RealPoints by the given
	 * angle about the given center.
	 *
	 * @param inPoly A Polygon consisting of a list of RealPoint RealPoints
	 * @param angle the rotation angle
	 * @param center the rotation center
	 * @return a rotated polygon
	 */
	private Polygon rotate(Polygon inPoly, double angle, RealLocalizable center) {

		List<RealLocalizable> out = new ArrayList<RealLocalizable>();

		for (RealLocalizable RealPoint : inPoly.getVertices()) {

			// double angleInRadians = Math.toRadians(angleInDegrees);
			double cosTheta = Math.cos(angle);
			double sinTheta = Math.sin(angle);

			double x =
				cosTheta *
					(RealPoint.getDoublePosition(0) - center.getDoublePosition(0)) -
					sinTheta *
					(RealPoint.getDoublePosition(1) - center.getDoublePosition(1)) +
					center.getDoublePosition(0);

			double y =
				sinTheta *
					(RealPoint.getDoublePosition(0) - center.getDoublePosition(0)) +
					cosTheta *
					(RealPoint.getDoublePosition(1) - center.getDoublePosition(1)) +
					center.getDoublePosition(1);

			out.add(new RealPoint(x, y));
		}

		return new Polygon(out);
	}

	@Override
	public boolean conforms() {
		return 2 == in().numDimensions();
	}

}
