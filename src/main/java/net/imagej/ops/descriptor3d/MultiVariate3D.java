package net.imagej.ops.descriptor3d;

import net.imagej.ops.AbstractOutputFunction;
import net.imagej.ops.Contingent;
import net.imagej.ops.Op;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imglib2.Cursor;
import net.imglib2.roi.IterableRegion;
import net.imglib2.type.BooleanType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "multivariate3d")
public class MultiVariate3D<B extends BooleanType<B>> extends
		AbstractOutputFunction<IterableRegion<B>, Covariance3D> implements Contingent {

	@Parameter(type = ItemIO.INPUT)
	private VolumeFeature<DoubleType> volume;
	
	@Override
	public Covariance3D createOutput(IterableRegion<B> input) {
		return new Covariance3D();
	}

	@Override
	protected Covariance3D safeCompute(IterableRegion<B> input, Covariance3D output) {
		Cursor<B> c = input.localizingCursor();
		int[] pos = new int[3];
		double mX = (input.max(0) + input.min(0)) / 2;
		double mY = (input.max(1) + input.min(1)) / 2;
		double mZ = (input.max(2) + input.min(2)) / 2;
		while (c.hasNext()) {
			c.next();
			c.localize(pos);
			output.setS200(output.getS200() + (pos[0] - mX) * (pos[0] - mX));
			output.setS020(output.getS020() + (pos[1] - mX) * (pos[1] - mY));
			output.setS002(output.getS002() + (pos[2] - mX) * (pos[2] - mZ));
			output.setS110(output.getS110() + (pos[0] - mY) * (pos[1] - mY));
			output.setS101(output.getS101() + (pos[0] - mY) * (pos[2] - mZ));
			output.setS011(output.getS011() + (pos[1] - mZ) * (pos[2] - mZ));
		}
		
		output.setS200(output.getS200() / volume.getOutput().get());
		output.setS020(output.getS020() / volume.getOutput().get());
		output.setS002(output.getS002() / volume.getOutput().get());
		output.setS110(output.getS110() / volume.getOutput().get());
		output.setS101(output.getS101() / volume.getOutput().get());
		output.setS011(output.getS011() / volume.getOutput().get());
		
		return output;
	}

	@Override
	public boolean conforms() {
		return true;
	}

}
