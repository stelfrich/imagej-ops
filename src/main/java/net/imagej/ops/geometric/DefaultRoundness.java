
package net.imagej.ops.geometric;

import org.scijava.plugin.Plugin;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Area;
import net.imagej.ops.Ops.Geometric.MajorAxis;
import net.imagej.ops.Ops.Geometric.Roundness;
import net.imagej.ops.RTs;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

/**
 * Generic implementation of {@link Roundness}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Roundness",
	name = Geometric.Roundness.NAME)
public class DefaultRoundness<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Roundness
{

	private FunctionOp<Polygon, O> areaFunc;
	private FunctionOp<Polygon, O> majorAxisFunc;

	@Override
	public void initialize() {
		areaFunc = RTs.function(ops(), Area.class, in());
		majorAxisFunc = RTs.function(ops(), MajorAxis.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output
			.setReal(4 * (areaFunc.compute(input).getRealDouble() / (Math.PI * Math
				.pow(majorAxisFunc.compute(input).getRealDouble(), 2))));
	}

}
