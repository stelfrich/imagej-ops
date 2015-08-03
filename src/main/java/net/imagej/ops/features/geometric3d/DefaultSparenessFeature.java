package net.imagej.ops.features.geometric3d;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.SecondMultiVariate3D;
import net.imagej.ops.features.geometric.Geometric3DFeatures.MainElongationFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.MedianElongationFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SparenessFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = Op.class, name = DefaultSparenessFeature.NAME, label = DefaultSparenessFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class DefaultSparenessFeature implements SparenessFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private MainElongationFeature<DoubleType> mainElongation;

	@Parameter(type = ItemIO.INPUT)
	private MedianElongationFeature<DoubleType> medianElongation;

	@Parameter(type = ItemIO.INPUT)
	private SecondMultiVariate3D<BoolType> multivar;

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
		double r1 = Math.sqrt(5.0 * multivar.getOutput().getEigenvalue(2));
		double r2 = r1 / mainElongation.getOutput().get();
		double r3 = r2 / medianElongation.getOutput().get();

		double volumeEllipsoid = (4.18879 * r1 * r2 * r3);

		out = new DoubleType(volume.getOutput().get() / volumeEllipsoid);
	}

}
