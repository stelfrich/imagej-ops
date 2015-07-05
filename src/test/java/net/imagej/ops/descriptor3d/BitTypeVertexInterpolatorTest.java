package net.imagej.ops.descriptor3d;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.imagej.ops.AbstractOpTest;

public class BitTypeVertexInterpolatorTest extends AbstractOpTest {

	@Test
	public void interpolatorTest_v2() {
			int[] p1 = new int[]{0,0,0};
			int[] p2 = new int[]{10, 0, 10};
			double v1 = 0;
			double v2 = 1;
			double[] res = (double[]) ops.run(BitTypeVertexInterpolator.class, p1, p2, v1, v2);
				assertTrue(res[0] == 10);
				assertTrue(res[1] == 0.0);
				assertTrue(res[2] == 10);
	}
	
	@Test
	public void interpolatorTest_v1() {
			int[] p1 = new int[]{0,0,0};
			int[] p2 = new int[]{10, 0, 10};
			double v1 = 1;
			double v2 = 0;
			double[] res = (double[]) ops.run(BitTypeVertexInterpolator.class, p1, p2, v1, v2);
				assertTrue(res[0] == 0);
				assertTrue(res[1] == 0.0);
				assertTrue(res[2] == 0);
	}
	
	@Test
	public void interpolatorTest_equal() {
			int[] p1 = new int[]{0,0,0};
			int[] p2 = new int[]{10, 0, 10};
			double v1 = 1;
			double v2 = 1;
			double[] res = (double[]) ops.run(BitTypeVertexInterpolator.class, p1, p2, v1, v2);
				assertTrue(res[0] == 5);
				assertTrue(res[1] == 0);
				assertTrue(res[2] == 5);
	}
}
