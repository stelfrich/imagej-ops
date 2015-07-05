package net.imagej.ops.descriptor3d;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.imagej.ops.AbstractOpTest;

public class DefaultVertexInterpolatorTest extends AbstractOpTest {

	@Test
	public void vertexInterpolator_025_v2_Test() {
		int[] p1 = new int[]{0,0,0};
		int[] p2 = new int[]{10, 0, 10};
		double v1 = 0;
		double v2 = 1;
		double[] res = (double[]) ops.run(DefaultVertexInterpolator.class, p1, p2, v1, v2, 0.25);
			assertTrue(res[0] == 2.5);
			assertTrue(res[1] == 0.0);
			assertTrue(res[2] == 2.5);
	}
	
	@Test
	public void vertexInterpolator_1_v2_Test() {
		int[] p1 = new int[]{0,0,0};
		int[] p2 = new int[]{10, 0, 10};
		double v1 = 0;
		double v2 = 1;
		double[] res = (double[]) ops.run(DefaultVertexInterpolator.class, p1, p2, v1, v2, 1);
			assertTrue(res[0] == 10);
			assertTrue(res[1] == 0.0);
			assertTrue(res[2] == 10);
	}
	
	@Test
	public void vertexInterpolator_1_v1_Test() {
		int[] p1 = new int[]{0,0,0};
		int[] p2 = new int[]{10, 0, 10};
		double v1 = 1;
		double v2 = 0;
		double[] res = (double[]) ops.run(DefaultVertexInterpolator.class, p1, p2, v1, v2, 1);
			assertTrue(res[0] == 10);
			assertTrue(res[1] == 0.0);
			assertTrue(res[2] == 10);
	}
	
	@Test
	public void vertexInterpolator_1_equalValues_Test() {
		int[] p1 = new int[]{0,0,0};
		int[] p2 = new int[]{10, 0, 10};
		double v1 = 1;
		double v2 = 1;
		double[] res = (double[]) ops.run(DefaultVertexInterpolator.class, p1, p2, v1, v2, 1);
			assertTrue(res[0] == 0.0);
			assertTrue(res[1] == 0.0);
			assertTrue(res[2] == 0.0);
	}
}
