package net.imagej.ops.descriptor3d;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import ij.io.RandomAccessStream;

public class QHTest {

	public static void main(String[] args) {
		int numberOfTest = 100;
		int numberOfPassedTest = 0;
		int numPoints = 4;
		Random r = new Random();
		for (int i = 0; i < numberOfTest; i++) {
			Vertex[] points = randomPointSet(numPoints);
			System.out.print("Run test #: " + i + " with " + points.length + " points.");
			numPoints += r.nextInt(1000) + 4;
			TheBestQuickHull3DEver qh = new TheBestQuickHull3DEver(points);
			try {
//				writeOutPoints(points, 0);
				qh.build();
				if (test(qh.getFacets(), qh.getTolerance())) {
				} else {
					System.out.println(" Passed.");
					numberOfPassedTest++;
				}
			} catch (Exception mae) {
				writeOutPoints(points, i);
			}
		}

		System.out.println("Test summary:");
		System.out.println("Number of Tests =        " + numberOfTest);
		System.out.println("Number of passed Tests = " + numberOfPassedTest);
		if (numberOfPassedTest < numberOfTest) {
			System.out.println(
					"Point clouds of failed tests are saved at /home/tibuch/failedQHTests/");
		}
	}

	private static void writeOutPoints(Vertex[] points, int j) {
		File f = new File("/home/tibuch/failedQHTests/pointsOfFailedTest_" + j);
		try {
			FileWriter writer = new FileWriter(f, false);
			for (int i = 0; i < points.length; i++) {
				Vertex u = points[i];
				String s = u.getX() + " " + u.getY() + " " + u.getZ()
						+ " black\n";
				writer.write(s);
			}

			writer.close();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
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

	private static Vertex[] randomPointSet(int n) {
		Vertex[] points = new Vertex[n];
		Random r = new Random();

		for (int i = 0; i < points.length; i++) {
			points[i] = new Vertex(r.nextDouble(), r.nextDouble(),
					r.nextDouble());
		}

		return points;
	}

}
