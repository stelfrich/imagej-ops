
package net.imagej.ops.geometric;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.MajorAxis;
import net.imagej.ops.Ops.Geometric.MinorMajorAxis;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;

import org.scijava.plugin.Plugin;

/**
 * Generic implementation of {@link MajorAxis}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Major Axis",
	name = Geometric.MajorAxis.NAME)
public class DefaultMajorAxis<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.MajorAxis
{

	private FunctionOp<Polygon, Pair<DoubleType, DoubleType>> minorMajorAxisFunc;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize() {
		minorMajorAxisFunc =
			(FunctionOp) ops().function(MinorMajorAxis.class, Pair.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		output.setReal(minorMajorAxisFunc.compute(input).getB().get());
	}
}
