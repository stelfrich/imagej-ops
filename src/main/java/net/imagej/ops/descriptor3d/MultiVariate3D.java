package net.imagej.ops.descriptor3d;

import net.imagej.ops.AbstractOutputFunction;
import net.imagej.ops.Contingent;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.RealType;

public class Variance3D<T extends RealType<T>> extends
		AbstractOutputFunction<IterableInterval<T>, Moments2> implements Contingent {

	@Override
	public Moments2 createOutput(IterableInterval<T> input) {
		return new Moments2(0, 0, 0, 0, 0, 0);
	}

	@Override
	protected Moments2 safeCompute(IterableInterval<T> input, Moments2 output) {
		Cursor<T> c = input.localizingCursor();
		int[] pos = new int[3];
		double mX = (input.max(0) + input.min(0)) / 2;
		double mY = (input.max(1) + input.min(1)) / 2;
		double mZ = (input.max(2) + input.min(2)) / 2;
		while (c.hasNext()) {
			c.next();
			c.localize(pos);
			output.setS200(output.getS00() + (pos[0] - mX) * (pos[0] - mX));
			output.setS110(output.getS01() + (pos[0] - mX) * (pos[1] - mY));
			output.setS101(output.getS02() + (pos[0] - mX) * (pos[2] - mZ));
			output.setS020(output.getS11() + (pos[1] - mY) * (pos[1] - mY));
			output.setS011(output.getS12() + (pos[1] - mY) * (pos[2] - mZ));
			output.setS002(output.getS22() + (pos[2] - mZ) * (pos[2] - mZ));
		}
		return output;
	}

	@Override
	public boolean conforms() {
		return getInput().numDimensions() == 3;
	}

}
