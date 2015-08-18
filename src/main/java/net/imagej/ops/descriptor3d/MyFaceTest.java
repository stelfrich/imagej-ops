package net.imagej.ops.descriptor3d;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MyFaceTest {
	
	@Test
	public void mergeTest1() {
		Vertex[] v = new Vertex[]{new Vertex(0, 0, 0), new Vertex(1, 0, 0), new Vertex(1, 1, 0), new Vertex(0, 1, 0)};
		MyFace f0 = new MyFace(v[0], v[1], v[3]);
		MyFace f1 = new MyFace(v[1], v[2], v[3]);
		MyFace n = new MyFace();
		
		f0.setNeighbor(0, n);
		f0.setNeighbor(1, n);
		f0.setNeighbor(2, f1);
		f1.setNeighbor(0, f0);
		f1.setNeighbor(1, n);
		f1.setNeighbor(2, n);

		f0.addVertex(new Vertex(0.5,0.5, 0), 2);
		f1.addVertex(new Vertex(0.5, 0.5,0), 3);
		f0.setNeighbor(2, f1);
		f1.setNeighbor(3, f0);
		f0.mergeFace(f1);
		
		assertEquals(4, f0.getVertices().size());
		assertEquals(v[0], f0.getVertices().get(0));
		assertEquals(v[1], f0.getVertices().get(1));
		assertEquals(v[2], f0.getVertices().get(2));
		assertEquals(v[3], f0.getVertices().get(3));
	}
	
	@Test
	public void mergeTest2() {
		Vertex[] v = new Vertex[]{new Vertex(0, 0, 0), new Vertex(1, 0, 0), new Vertex(1, 1, 0), new Vertex(0, 1, 0)};
		MyFace f0 = new MyFace(v[3], v[0], v[1]);
		MyFace f1 = new MyFace(v[3], v[1], v[2]);
		
		f0.setNeighbor(0, f1);
		f0.setNeighbor(1, null);
		f0.setNeighbor(2, null);
		f1.setNeighbor(0, null);
		f1.setNeighbor(1, f0);
		f1.setNeighbor(2, null);

		f0.mergeFace(f1);
		
		assertEquals(4, f0.getVertices().size());
		assertEquals(v[0], f0.getVertices().get(0));
		assertEquals(v[1], f0.getVertices().get(1));
		assertEquals(v[2], f0.getVertices().get(2));
		assertEquals(v[3], f0.getVertices().get(3));
	}

	@Test
	public void mergeTest3() {
		Vertex[] v = new Vertex[]{new Vertex(0, 0, 0), new Vertex(1, 0, 0), new Vertex(1, 1, 0), new Vertex(0, 1, 0)};
		MyFace f0 = new MyFace(v[3], v[0], v[1]);
		MyFace f1 = new MyFace(v[3], v[1], v[2]);
		
		f0.setNeighbor(0, f1);
		f0.setNeighbor(1, null);
		f0.setNeighbor(2, null);
		f1.setNeighbor(0, null);
		f1.setNeighbor(1, f0);
		f1.setNeighbor(2, null);

		f0.addVertex(new Vertex(0.5,0.5, 0), 3);
		f1.addVertex(new Vertex(0.5, 0.5,0), 1);
		f0.setNeighbor(3, f1);
		f1.setNeighbor(1, f0);
		f0.mergeFace(f1);
		
		assertEquals(4, f0.getVertices().size());
		assertEquals(v[0], f0.getVertices().get(0));
		assertEquals(v[1], f0.getVertices().get(1));
		assertEquals(v[2], f0.getVertices().get(2));
		assertEquals(v[3], f0.getVertices().get(3));
	}
	
	@Test
	public void mergeTest4() {
		Vertex[] v = new Vertex[]{new Vertex(0,0,0), new Vertex(1,0,0), new Vertex(1.5,0.5,0), new Vertex(0.5,0.5,0), new Vertex(0.5, 1.5, 0), new Vertex(0,1,0)};
		MyFace f0 = new MyFace(v[0], v[1], v[5]);
		f0.addVertex(v[3], 2);
		MyFace f1 = new MyFace(v[1], v[2], v[3]);
		MyFace f2 = new MyFace(v[3], v[4], v[5]);
		
		f0.setNeighbor(0, null);
		f0.setNeighbor(1, null);
		f0.setNeighbor(2, f1);
		f0.setNeighbor(3, f2);
		f1.setNeighbor(0, f0);
		f1.setNeighbor(1, null);
		f1.setNeighbor(2, null);
		f2.setNeighbor(0, f0);
		f2.setNeighbor(1, null);
		f2.setNeighbor(2, null);
		
		
		f0.mergeFace(f1);
		assertEquals(v[0], f0.getVertices().get(0));
		assertEquals(v[1], f0.getVertices().get(1));
		assertEquals(v[2], f0.getVertices().get(2));
		assertEquals(v[3], f0.getVertices().get(3));
		assertEquals(v[5], f0.getVertices().get(4));
		f0.mergeFace(f2);
		assertEquals(v[4], f0.getVertices().get(4));
		assertEquals(v[5], f0.getVertices().get(5));
	}
	
	@Test
	public void mergeTest5() {
		Vertex[] v = new Vertex[]{new Vertex(0,0,0), new Vertex(1,0,0), new Vertex(1, 1, 0), new Vertex(1, 2, 0), new Vertex(0, 2, 0)};
		
		MyFace f0 = new MyFace(v[0], v[1], v[2]);
		MyFace f1 = new MyFace(v[0], v[2], v[4]);
		MyFace f2 = new MyFace(v[4], v[2], v[3]);
		
		f0.setNeighbor(0, f1);
		f0.setNeighbor(1, null);
		f0.setNeighbor(2, null);
		f1.setNeighbor(0, null);
		f1.setNeighbor(1, f0);
		f1.setNeighbor(2, f2);
		f2.setNeighbor(0, null);
		f2.setNeighbor(1, f1);
		f2.setNeighbor(2, null);
		
		f1.mergeFace(f0);
		f1.mergeFace(f2);
		
		assertEquals(5, f1.getVertices().size());
		assertEquals(v[0], f1.getVertices().get(0));
		assertEquals(v[1], f1.getVertices().get(1));
		assertEquals(v[2], f1.getVertices().get(2));
		assertEquals(v[3], f1.getVertices().get(3));
		assertEquals(v[4], f1.getVertices().get(4));
	}
}
