package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.DefaultGenerateFaces;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CentroidZFeature;
import net.imglib2.roi.IterableRegion;
import net.imglib2.type.BooleanType;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = CentroidZFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultCentroidZFeature<B extends BooleanType<B>> implements CentroidZFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private IterableRegion<B> input;

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
		out = new DoubleType((input.max(2) + input.min(2)) / 2d);
	}

}
