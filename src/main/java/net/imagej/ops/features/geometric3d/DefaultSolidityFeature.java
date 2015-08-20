package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SolidityFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = SolidityFeature.NAME, label = SolidityFeature.LABEL)
public class DefaultSolidityFeature implements SolidityFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private DefaultVolumeFeature<?> volume;

	@Parameter(type = ItemIO.INPUT)
	private ConvexHullVolumeFeature convexHullVolume;

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
		out = new DoubleType(volume.getOutput().get()
				/ convexHullVolume.getOutput().get());
	}

}
