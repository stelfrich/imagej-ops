package net.imagej.ops.descriptor3d;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImageFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;
import org.python.google.common.io.Files;

public class TheBestQuickHull3DTest {

	@Test
	public void simplexTest1() {
		Vertex[] v = new Vertex[]{new Vertex(0, 0, 0), new Vertex(1, 0, 0),
				new Vertex(0, 0, 1), new Vertex(0, 1, 0)};
		
		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(v);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	@Test
	public void simplexTest2() {
		Vertex[] v = new Vertex[]{new Vertex(0, 0, 0), new Vertex(1, 0, 0),
				new Vertex(0, 0, -1), new Vertex(0, 1, 0)};
		
		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(v);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	@Test
	public void wtfTest() {

		List<Vertex> p = new ArrayList<Vertex>();
		File f = new File("/home/tibuch/failedQHTests/pointsOfFailedTest_0");
		try {
			BufferedReader reader = Files.newReader(f, Charset.forName("US-ASCII"));
			String l = reader.readLine();
			while (l != null) {
				String[] numbers = l.split(" ");
				Vertex v = new Vertex(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]), Double.parseDouble(numbers[2]));
				p.add(v);
				l = reader.readLine();
			}
		} catch (FileNotFoundException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		} catch (IOException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
//		
		Vertex[] points = p.toArray(new Vertex[p.size()]);

		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(points);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	@Test
	public void resultEquals5() {
//		5 result points
		Vertex[] points = new Vertex[]{
		   new Vertex(3.2, 4.8, 4.4),
		   new Vertex(0, -4.9, 1.1),
		   new Vertex(-2.4, 4.9, -3.1),
		   new Vertex(4.5, -0.9, -2.5),
		   new Vertex(-4.7, 0.4, -4.2),
		   new Vertex(-1.9, 2.2, -3.3)
		   };

		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(points);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	@Test
	public void resultEquals12() {
//		12 result points
		Vertex[] points = new Vertex[]{
				   new Vertex(-0.03621271768232132, 0.3728502838619522, 0.4947140370446388),
				   new Vertex(0.3210853052521919, 0.4807189479290684, 0.4433501688235907),
				   
				   new Vertex(0.07214279572678994, -0.4960366976410492, 0.1112227161519441),
				   new Vertex(0.2229772524190855, -0.4213242506806965, -0.1966818060695024),
				   new Vertex(-0.3411871756810576, -0.3328629143842151, -0.4270033635450559),
				   new Vertex(-0.245701439441835, 0.495905311308713, -0.3194406286994373),
				   new Vertex(0.458374538420117, -0.09914027349943322, -0.2505798421339875),
				 
				   new Vertex(-0.4954086979808367, -0.3339869997780649, -0.3195065691317492),
				   new Vertex(-0.3392973838740004, 0.4288679723896719, -0.01599531622230571),
				   new Vertex(0.2724846394476338, -0.3506708492996831, 0.2750346518820475),
				   new Vertex(0.3544683273457627, -0.450828987127942, -0.0827870439577727),
				   new Vertex(0.1667164640191164, 0.003605551555385444, -0.4014989499947977),
				   };

		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(points);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	@Test
	public void resultEquals20() {

//		20 result points
		Vertex[] points = new Vertex[]{
				new Vertex(0.3215426810286406, 0.1678336189760208, -0.2203710966001927),
				new Vertex(0.2229772524190855, -0.4213242506806965, -0.1966818060695024),
				new Vertex(0.3688830163971363, -0.1831502133823468, -0.2056387967482571),
				new Vertex(-0.1712592515826777, -0.3542439228428937, 0.2223876390814666),
				new Vertex(-0.3309556113844324, -0.370961861099081, 0.2439994981922204),
				new Vertex(-0.1004397059794885, -0.09014152417903909, -0.008600084584765189),
				new Vertex(0.458374538420117, -0.09914027349943322, -0.2505798421339875),
				new Vertex(-0.4954086979808367, -0.3339869997780649, -0.3195065691317492),
				new Vertex(0.053091190339151, 0.3036317017894533, 0.1380056861210668),
				new Vertex(0.4615616439483703, 0.4665423151725366, 0.1766835406205464),
				new Vertex(-0.4797380864431505, 0.0419809916447671, -0.4254776681079321),
				new Vertex(-0.003168473023146823, -0.2525299883005488, -0.27151530400991),
				new Vertex(-0.3577162826971303, -0.1375644040643837, -0.04494194644032229),
				new Vertex(-0.3392973838740004, 0.4288679723896719, -0.01599531622230571),
				new Vertex(0.1667164640191164, 0.003605551555385444, -0.4014989499947977),
				new Vertex(0.00714666676441833, 0.1140243407469469, 0.407090128778564),
				new Vertex(-0.03621271768232132, 0.3728502838619522, 0.4947140370446388),
				new Vertex(-0.3411871756810576, -0.3328629143842151, -0.4270033635450559),
				new Vertex(0.3544683273457627, -0.450828987127942, -0.0827870439577727),
				new Vertex(-0.4018510635028137, 0.08917494033386464, -0.2367824197158054),
				new Vertex(0.3978697768392692, -0.002667689232777493, 0.1641431727112673),
				new Vertex(-0.245701439441835, 0.495905311308713, -0.3194406286994373),
				new Vertex(0.161352035739787, -0.1563404972258401, 0.3852604361113724),
				new Vertex(0.07214279572678994, -0.4960366976410492, 0.1112227161519441),
				new Vertex(0.3201855824516951, 0.359077846965825, 0.02136723140381946),
				new Vertex(0.1190541238701475, -0.05734495917087884, 0.2032677509852384),
				new Vertex(0.3210853052521919, 0.4807189479290684, 0.4433501688235907),
				new Vertex(0.3862800354941562, 0.2085496142586224, 0.09336129957191763),
				new Vertex(0.1233572616459404, 0.265491605052251, 0.117400122450106),
				new Vertex(0.1438531872293476, -0.2594872752758556, -0.2026374435076839),
				new Vertex(0.2724846394476338, -0.3506708492996831, 0.2750346518820475),
				new Vertex(-0.4926118841325975, -0.3279366743079728, 0.3683135596740186),
				new Vertex(0.2459906458351674, 0.3647787136629026, -0.1641662355178652),
				new Vertex(-0.141922976953837, -0.2994764654892278, -0.3009570467294725),
				new Vertex(-0.1850859398814719, 0.2606059478228967, 0.004159106876849283),
				new Vertex(-0.09789466634196664, -0.3156603563722785, -0.303610991503681),
				new Vertex(0.2100642609503719, -0.4499717643018549, 0.3245569875692548),
				new Vertex(-0.1707163766685095, -0.2301452446078371, -0.05112823569320907),
				new Vertex(-0.312260808713977, -0.1674135249735914, 0.2808831662692904),
				new Vertex(-0.1966306233747216, 0.2291105671125563, -0.3387042454804333)
				};
		TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(points);
		qh.build();
		assertTrue(!test(qh.getFacets(), qh.getTolerance()));
	}
	
	private static boolean test(MyFace[] facets, double tolerance) {
		Vector3D[] centroids = new Vector3D[facets.length];
		for (int i = 0; i < facets.length; i++) {
			centroids[i] = facets[i].getCentroid();
		}

		boolean failed = false;
		for (int i = 0; i < facets.length; i++) {
			for (int j = 0; j < centroids.length; j++) {
				if (j != i) {
					if (facets[i].distanceToPlane(centroids[j]) >= tolerance) {
						failed = true;
						break;
					}
				}
			}
		}
		return failed;
	}
}
