package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.MarchingCubes;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SurfacePixelFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = SurfacePixelFeature.NAME, label = SurfacePixelFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultSurfacePixelFeature implements
		SurfacePixelFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private MarchingCubes<?> input;

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
		out = new DoubleType(input.getOutput().getPoints().size());
	}

}
