package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.RugosityFeature;
import net.imagej.ops.statistics.Geometric3DOps.Rugosity;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link RugosityFeature}. Use {@link FeatureSet} to
 * compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = Rugosity.NAME, label = Rugosity.LABEL)
public class DefaultRugosityFeature implements RugosityFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private DefaultSurfaceAreaFeature<?> surface;

	@Parameter(type = ItemIO.INPUT)
	private DefaultConvexHullSurfaceAreaFeature convexHullSurface;

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
		out = new DoubleType(surface.getOutput().get()
				/ convexHullSurface.getOutput().get());
	}

}
