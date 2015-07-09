package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.MultiVariate3D;
import net.imagej.ops.features.geometric.Geometric3DFeatures.MainElongationFeature;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = MainElongationFeature.NAME, label = MainElongationFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultMainElongationFeature implements
		MainElongationFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private MultiVariate3D<BoolType> input;

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
		out = new DoubleType(Math.sqrt(input.getOutput().getEigenvalue(2)
				/ input.getOutput().getEigenvalue(1)));
	}

}
