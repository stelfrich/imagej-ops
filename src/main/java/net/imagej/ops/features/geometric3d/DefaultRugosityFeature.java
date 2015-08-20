package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.features.geometric.Geometric3DFeatures.RugosityFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = RugosityFeature.NAME, label = RugosityFeature.LABEL)
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
