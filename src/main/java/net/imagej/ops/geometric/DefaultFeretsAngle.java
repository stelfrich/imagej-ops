
package net.imagej.ops.geometric;

import org.scijava.plugin.Plugin;

import net.imagej.ops.FunctionOp;
import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Feret;
import net.imagej.ops.Ops.Geometric.FeretsAngle;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Pair;

/**
 * Generic implementation of {@link FeretsAngle}.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Ferets Angle",
	name = Geometric.FeretsAngle.NAME)
public class DefaultFeretsAngle<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.FeretsAngle
{

	private FunctionOp<Polygon, Pair<RealLocalizable, RealLocalizable>> feretFunc;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize() {
		feretFunc = (FunctionOp) ops().function(Feret.class, Pair.class, in());
	}

	@Override
	public void compute(Polygon input, O output) {
		final Pair<RealLocalizable, RealLocalizable> ferets =
			feretFunc.compute(input);

		final RealLocalizable p1 = ferets.getA();
		final RealLocalizable p2 = ferets.getB();

		if (p1.getDoublePosition(0) == p2.getDoublePosition(0)) {
			output.setReal(90);
		}

		// tan alpha = opposite leg / adjacent leg
		// angle in radiants = atan(alpha)
		// angle in degree = atan(alpha) * (180/pi)
		final double opLeg = p2.getDoublePosition(1) - p1.getDoublePosition(1);
		final double adjLeg = p2.getDoublePosition(0) - p1.getDoublePosition(0);
		double degree = Math.atan((opLeg / adjLeg)) * (180.0 / Math.PI);
		if (adjLeg < 0) {
			degree = 180 - degree;
		}

		output.setReal(Math.abs(degree));
	}

}
