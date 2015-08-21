package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.SecondMultiVariate3D;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric.Geometric3DFeatures.MainElongationFeature;
import net.imagej.ops.statistics.Geometric3DOps.MainElongation;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Generic implementation of {@link MainElongationFeature}. Use
 * {@link FeatureSet} to compile this {@link Op}.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 */
@Plugin(type = Op.class, name = MainElongation.NAME, label = MainElongation.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultMainElongationFeature
		implements
			MainElongationFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private SecondMultiVariate3D<BoolType> input;

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
		out = new DoubleType(Math.sqrt(input.getOutput().getEigenvalue(0)
				/ input.getOutput().getEigenvalue(1)));
	}

}
