package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SolidityFeature;
import net.imagej.ops.statistics.Geometric3DOps.Solidity;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link SolidityFeature}. Use {@link FeatureSet} to
 * compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = Solidity.NAME, label = Solidity.LABEL)
public class DefaultSolidityFeature implements SolidityFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private DefaultVolumeFeature<?> volume;

	@Parameter(type = ItemIO.INPUT)
	private DefaultConvexHullVolumeFeature convexHullVolume;

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
		out = new DoubleType(
				volume.getOutput().get() / convexHullVolume.getOutput().get());
	}

}
