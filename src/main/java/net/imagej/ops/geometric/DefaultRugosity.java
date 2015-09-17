
package net.imagej.ops.geometric;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.ConvexHull;
import net.imagej.ops.Ops.Geometric.Perimeter;
import net.imagej.ops.Ops.Geometric.Rugosity;
import net.imagej.ops.RTs;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link Rugosity}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Rugosity",
	name = Geometric.Rugosity.NAME)
public class DefaultRugosity<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Rugosity
{

	private FunctionOp<Polygon, O> perimeterFunc;
	private FunctionOp<Polygon, Polygon> convexHullFunc;

	@Override
	public void initialize() {
		perimeterFunc = RTs.function(ops(), Perimeter.class, in());
		convexHullFunc = ops().function(ConvexHull.class, Polygon.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output.setReal(perimeterFunc.compute(input).getRealDouble() /
			perimeterFunc.compute(convexHullFunc.compute(input)).getRealDouble());
	}

}
