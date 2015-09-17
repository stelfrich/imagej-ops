
package net.imagej.ops.geometric;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Area;
import net.imagej.ops.Ops.Geometric.ConvexHull;
import net.imagej.ops.Ops.Geometric.Solidity;
import net.imagej.ops.RTs;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link Solidity}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Solidity",
	name = Geometric.Solidity.NAME)
public class DefaultSolidity<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Solidity
{

	private FunctionOp<Polygon, O> areaFunc;
	private FunctionOp<Polygon, Polygon> convexHullFunc;

	@Override
	public void initialize() {
		areaFunc = RTs.function(ops(), Area.class, in());
		convexHullFunc = ops().function(ConvexHull.class, Polygon.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output.setReal(areaFunc.compute(input).getRealDouble() /
			areaFunc.compute(convexHullFunc.compute(input)).getRealDouble());
	}

}
