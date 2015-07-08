package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CompactnessFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SurfacePixelFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = CompactnessFeature.NAME, label = CompactnessFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultCompactnessFeature implements
		CompactnessFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private SurfacePixelFeature<DoubleType> surfpix;

	@Parameter(type = ItemIO.INPUT)
	private VolumeFeature<DoubleType> volume;

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
		double s3 = Math.pow(surfpix.getOutput().get(), 3);
		double v2 = Math.pow(volume.getOutput().get(), 2);

		out = new DoubleType((v2 * 36.0 * Math.PI) / s3);
	}

}
