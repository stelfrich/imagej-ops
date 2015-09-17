
package net.imagej.ops.geometric;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Area;
import net.imagej.ops.Ops.Geometric.Rectangularity;
import net.imagej.ops.Ops.Geometric.SmallestEnclosingRectangle;
import net.imagej.ops.RTs;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link Rectangularity}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Rectangularity",
	name = Geometric.Rectangularity.NAME)
public class DefaultRectangularity<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Rectangularity
{

	private FunctionOp<Polygon, O> areaFunc;
	private FunctionOp<Polygon, Polygon> smallestEnclosingRectangleFunc;

	@Override
	public void initialize() {
		areaFunc = RTs.function(ops(), Area.class, in());
		smallestEnclosingRectangleFunc =
			ops().function(SmallestEnclosingRectangle.class, Polygon.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output.setReal(areaFunc.compute(input).getRealDouble() /
			areaFunc.compute(smallestEnclosingRectangleFunc.compute(input))
				.getRealDouble());
	}

}
