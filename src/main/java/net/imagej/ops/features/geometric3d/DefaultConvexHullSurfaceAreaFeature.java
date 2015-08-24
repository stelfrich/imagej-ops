package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.QuickHull3DFromMC;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.ConvexHullSurfaceAreaFeature;
import net.imagej.ops.statistics.Geometric3DOps.ConvexHullSurfaceArea;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link ConvexHullSurfaceAreaFeature}. Use
 * {@link FeatureSet} to compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = ConvexHullSurfaceArea.NAME, label = ConvexHullSurfaceArea.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultConvexHullSurfaceAreaFeature
		implements
			ConvexHullSurfaceAreaFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private QuickHull3DFromMC input;

	@Parameter(type = ItemIO.OUTPUT)
	private DoubleType out;

	@Override
	public DoubleType getOutput() {
		return out;
	}

	@Override
	public void setOutput(DoubleType output) {
		this.out = output;
	}

	@Override
	public void run() {
		out = new DoubleType(input.getOutput().getArea());
	}

}
