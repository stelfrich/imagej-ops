package net.imagej.ops.descriptor3d;

import net.imagej.ops.feature3d.Feature3DOps.Volume;
import net.imglib2.roi.IterableRegion;
import net.imglib2.type.BooleanType;

public class DefaultVolume<B extends BooleanType<B>> implements Volume<IterableRegion<B>, Double> {

	@Override
	public Double compute(IterableRegion<B> input) {
		return (double)input.size();
	}

}
