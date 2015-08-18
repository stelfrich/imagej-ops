package net.imagej.ops.descriptor3d;

import net.imagej.ops.OutputFunction;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.Type;

public interface Polygonize<I extends Type<I>, O extends Facets> extends
		OutputFunction<RandomAccessibleInterval<I>, O> {
	// NB: Marker Interface
}
