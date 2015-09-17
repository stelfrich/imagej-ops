
package net.imagej.ops.geometric;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Ops.Geometric;
import net.imagej.ops.Ops.Geometric.Area;
import net.imglib2.RealLocalizable;
import net.imglib2.roi.geometric.Polygon;
import net.imglib2.type.numeric.RealType;

/**
 * Specific implementation of {@link Area} for a Polygon.
 * 
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = GeometricOp.class, label = "Geometric: Area",
	name = Geometric.Area.NAME, priority = Priority.FIRST_PRIORITY)
public class DefaultArea<O extends RealType<O>> extends
	AbstractGeometricFeature<Polygon, O> implements Geometric.Area
{

	@Override
	public void compute(Polygon input, O output) {

		double sum = 0;
		for (int i = 0; i < input.getVertices().size(); i++) {

			RealLocalizable p0 =
				input.getVertices().get(i % input.getVertices().size());
			RealLocalizable p1 =
				input.getVertices().get((i + 1) % input.getVertices().size());

			double p0_x = p0.getDoublePosition(0);
			double p0_y = p0.getDoublePosition(1);
			double p1_x = p1.getDoublePosition(0);
			double p1_y = p1.getDoublePosition(1);

			sum += p0_x * p1_y - p0_y * p1_x;
		}

		output.setReal(Math.abs(sum) / 2d);
	}

}
