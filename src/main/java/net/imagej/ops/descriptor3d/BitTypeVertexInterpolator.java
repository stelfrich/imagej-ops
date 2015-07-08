package net.imagej.ops.descriptor3d;

import net.imagej.ops.Op;

import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "bittypevertexinterpolator")
public class BitTypeVertexInterpolator extends AbstractVertexInterpolator {

	@Override
	public void run() {
		output = new double[3];
//		if (p1Value > p2Value) {
//			for (int i = 0; i < 3; i++) {
//				output[i] = p1[i];
//			}
//		} else if (p1Value < p2Value) {
//			for (int i = 0; i < 3; i++) {
//				output[i] = p2[i];
//			}
//		} else {
			for (int i = 0; i < 3; i++) {
				output[i] = (p1[i] + p2[i])/2.0;
			}
//		}
	}

}
