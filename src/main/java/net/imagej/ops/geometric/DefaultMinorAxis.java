
package net.imagej.ops.geometric;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.MinorAxis;
import net.imagej.ops.Ops.Geometric.MinorMajorAxis;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link MinorAxis}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Minor Axis",
	name = Geometric.MinorAxis.NAME)
public class DefaultMinorAxis<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.MinorAxis
{

	private FunctionOp<Polygon, Pair<DoubleType, DoubleType>> minorMajorAxisFunc;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize() {
		minorMajorAxisFunc =
			(FunctionOp) ops().function(MinorMajorAxis.class, Pair.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output.setReal(minorMajorAxisFunc.compute(input).getA().get());
	}
}
