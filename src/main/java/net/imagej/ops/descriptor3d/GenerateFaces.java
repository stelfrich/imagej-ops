package net.imagej.ops.descriptor3d;

import net.imagej.ops.Contingent;
import net.imagej.ops.OutputFunction;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.Type;

public interface GenerateFaces<I extends Type<I>, O extends Faces> extends
		OutputFunction<RandomAccessibleInterval<I>, O>, Contingent {
	// NB: Marker Interface
}
