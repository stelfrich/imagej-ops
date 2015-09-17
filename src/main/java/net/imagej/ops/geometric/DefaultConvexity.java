
package net.imagej.ops.geometric;

import org.scijava.plugin.Plugin;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.ConvexHull;
import net.imagej.ops.Ops.Geometric.Convexity;
import net.imagej.ops.Ops.Geometric.Perimeter;
import net.imagej.ops.RTs;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

/**
 * Generic implementation of {@link Convexity}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Convexity",
	name = Geometric.Convexity.NAME)
public class DefaultConvexity<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Convexity
{

	private FunctionOp<Polygon, Polygon> convexHullFunc;
	private FunctionOp<Polygon, O> perimiterFunc;

	@Override
	public void initialize() {
		convexHullFunc = ops().function(ConvexHull.class, Polygon.class, in());
		perimiterFunc = RTs.function(ops(), Perimeter.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {

		// get perimeter of input and its convex hull
		O inputArea = perimiterFunc.compute(input);
		O convexHullArea = perimiterFunc.compute(convexHullFunc.compute(input));

		output.setReal(convexHullArea.getRealDouble() / inputArea.getRealDouble());
	}

}
