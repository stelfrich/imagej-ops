package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CompactnessFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SphericityFeature;
import net.imagej.ops.statistics.Geometric3DOps.Sphericity;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link SphericityFeature}. Use {@link FeatureSet}
 * to compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = Sphericity.NAME, label = Sphericity.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultSphericityFeature implements SphericityFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private CompactnessFeature<DoubleType> compactness;

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
		out = new DoubleType(Math.pow(compactness.getOutput().get(), (1 / 3d)));
	}

}
