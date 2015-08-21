package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.ConvexityFeature;
import net.imagej.ops.statistics.Geometric3DOps.Convexity;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link ConvexityFeature}. Use {@link FeatureSet} to
 * compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = Convexity.NAME, label = Convexity.LABEL)
public class DefaultConvexityFeature implements ConvexityFeature<DoubleType> {

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
		out = new DoubleType(convexHullSurface.getOutput().get()
				/ surface.getOutput().get());
	}

}
