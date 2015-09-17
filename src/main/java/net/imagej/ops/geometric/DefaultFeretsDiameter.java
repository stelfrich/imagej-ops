
package net.imagej.ops.geometric;

import org.scijava.plugin.Plugin;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Feret;
import net.imagej.ops.Ops.Geometric.FeretsDiameter;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Pair;

/**
 * Generic implementation of {@link FeretsDiameter}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Ferets Diameter",
	name = Geometric.FeretsDiameter.NAME)
public class DefaultFeretsDiameter<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.FeretsDiameter
{

	private FunctionOp<Polygon, Pair<RealLocalizable, RealLocalizable>> feretFunction;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize() {
		feretFunction = (FunctionOp) ops().function(Feret.class, Pair.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		final Pair<RealLocalizable, RealLocalizable> ferets =
			feretFunction.compute(input);

		final RealLocalizable p1 = ferets.getA();
		final RealLocalizable p2 = ferets.getB();

		output.setReal(Math.hypot(
			p1.getDoublePosition(0) - p2.getDoublePosition(0), p1
				.getDoublePosition(1) -
				p2.getDoublePosition(1)));
	}

}
