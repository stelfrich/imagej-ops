package net.imagej.ops.descriptor3d;

import net.imagej.ops.AbstractOutputFunction;
import net.imagej.ops.Contingent;
import net.imagej.ops.Op;
import net.imagej.ops.features.firstorder.DefaultMoment2AboutMeanFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imglib2.Cursor;
import net.imglib2.roi.IterableRegion;
import net.imglib2.type.BooleanType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "multivariate3d")
public class SecondMultiVariate3D<B extends BooleanType<B>> extends
		AbstractOutputFunction<IterableRegion<B>, CovarianceOf2ndMultiVariate3D> implements Contingent {

	@Parameter(type = ItemIO.INPUT)
	private VolumeFeature<DoubleType> volume;
	
	@Parameter(type = ItemIO.INPUT)
	private DefaultCentroid3D<B> centroid;
	
	@Override
	public CovarianceOf2ndMultiVariate3D createOutput(IterableRegion<B> input) {
		return new CovarianceOf2ndMultiVariate3D();
	}

	@Override
	protected CovarianceOf2ndMultiVariate3D safeCompute(IterableRegion<B> input, CovarianceOf2ndMultiVariate3D output) {
		Cursor<B> c = input.localizingCursor();
		int[] pos = new int[3];
		double mX = centroid.getOutput().getX();
		double mY = centroid.getOutput().getY();
		double mZ = centroid.getOutput().getZ();
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
