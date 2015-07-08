package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.DefaultGenerateFaces;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SurfaceAreaFeature;
import net.imglib2.type.BooleanType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = SurfaceAreaFeature.NAME, label = SurfaceAreaFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultSurfaceAreaFeature<I extends BooleanType<I>> implements SurfaceAreaFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private DefaultGenerateFaces<?> input;
	
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
		out = new DoubleType(input.getOutput().getArea());
	}

}
