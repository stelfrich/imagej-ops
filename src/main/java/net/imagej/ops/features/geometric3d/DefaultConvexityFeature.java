package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.features.geometric.Geometric3DFeatures.ConvexityFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = ConvexityFeature.NAME, label = ConvexityFeature.LABEL)
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
