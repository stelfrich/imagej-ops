
package net.imagej.ops.geometric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;

import net.imagej.ops.Ops.Geometric.Area;
import net.imagej.ops.Ops.Geometric.Circularity;
import net.imagej.ops.Ops.Geometric.Eccentricity;
import net.imagej.ops.Ops.Geometric.FeretsAngle;
import net.imagej.ops.Ops.Geometric.FeretsDiameter;
import net.imagej.ops.Ops.Geometric.MajorAxis;
import net.imagej.ops.Ops.Geometric.MinorAxis;
import net.imagej.ops.Ops.Geometric.Perimeter;
import net.imagej.ops.Ops.Geometric.Roundness;
import net.imagej.ops.Ops.Geometric.Solidity;
import net.imagej.ops.features.AbstractFeatureTest;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.integer.IntType;

public class GeometricTest extends AbstractFeatureTest {

	private static LabelRegion<String> region;

	@BeforeClass
	public static void setupBefore() throws MalformedURLException, IOException {
		// read simple polygon image
		final BufferedImage read = ImageIO.read(GeometricTest.class
			.getResourceAsStream("cZgkFsK.png"));

		final ImgLabeling<String, IntType> img = new ImgLabeling<String, IntType>(
			ArrayImgs.ints(read.getWidth(), read.getHeight()));

		// at each black pixel of the polygon add a "1" label.
		final RandomAccess<LabelingType<String>> randomAccess = img.randomAccess();
		for (int y = 0; y < read.getHeight(); y++) {
			for (int x = 0; x < read.getWidth(); x++) {
				randomAccess.setPosition(new int[] { x, y });
				final Color c = new Color(read.getRGB(x, y));
				if (c.getRed() == Color.black.getRed()) {
					randomAccess.get().add("1");
				}
			}
		}

		final LabelRegions<String> labelRegions = new LabelRegions<String>(img);
		region = labelRegions.getLabelRegion("1");
	}

	@Test
	public void createPolygon() {
		ops.geometric().contour(region, true, true);
	}

	/**
	 * Test the {@link Area} Op.
	 */
	@Test
	public void testArea() {
		// value taken from imagej
		assertEquals(Area.NAME, 355630.5, ops.geometric().area(ops.geometric()
			.contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link Perimeter} Op.
	 */
	@Test
	public void testPerimeter() {
		// value taken from imagej
		assertEquals(Perimeter.NAME, 2658.990257670, ops.geometric().perimeter(ops
			.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link Circularity} Op.
	 */
	@Test
	public void testCircularity() {
		// value taken from imagej
		assertEquals(Circularity.NAME, 0.632083948, ops.geometric().circularity(ops
			.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link MinorAxis} Op.
	 */
	@Test
	public void testMinorAxis() {
		// value taken from imagej
		assertEquals(MinorAxis.NAME, 520.667420750, ops.geometric().minorAxis(ops
			.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link MajorAxis} Op.
	 */
	@Test
	public void testMajorAxis() {
		// value taken from imagej
		assertEquals(MajorAxis.NAME, 869.657215429, ops.geometric().majorAxis(ops
			.geometric().contour(region, true, true)).getRealDouble(), 0.01);
	}

	/**
	 * Test the {@link FeretsDiameter} Op.
	 */
	@Test
	public void testFeretDiameter() {
		// value taken from imagej
		assertEquals(FeretsDiameter.NAME, 908.002202641, ops.geometric()
			.feretsDiameter(ops.geometric().contour(region, true, true))
			.getRealDouble(), AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link FeretsAngle} Op.
	 */
	@Test
	public void testFeretAngle() {

		// value taken from imagej, angle could be reversed so check
		// 148.235410152 and
		// 148.235410152.. + 180
		final double expectedAngle = 148.235410152;
		final double actualAngle = ops.geometric().feretsAngle(ops.geometric()
			.contour(region, true, true)).getRealDouble();

		boolean isEquals = false;
		if (Math.abs(expectedAngle -
			actualAngle) < AbstractFeatureTest.SMALL_DELTA || Math.abs(expectedAngle +
				180 - actualAngle) < AbstractFeatureTest.SMALL_DELTA)
		{
			isEquals = true;
		}

		assertTrue(FeretsAngle.NAME + " Expected [" + expectedAngle + "] was [" +
			actualAngle + "]", isEquals);
	}

	/**
	 * Test the {@link Eccentricity} Op.
	 */
	@Test
	public void testEccentricity() {
		// value taken from imagej
		assertEquals(Eccentricity.NAME, 1.670273923, ops.geometric().eccentricity(
			ops.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);

	}

	/**
	 * Test the {@link Roundness} Op.
	 */
	@Test
	public void testRoundness() {
		// value taken from imagej
		assertEquals(Roundness.NAME, 0.598704192, ops.geometric().roundness(ops
			.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link Solidity} Op.
	 */
	@Test
	public void testSolidity() {
		// value taken from imagej
		assertEquals(Solidity.NAME, 0.997063173, ops.geometric().solidity(ops
			.geometric().contour(region, true, true)).getRealDouble(),
			AbstractFeatureTest.BIG_DELTA);
	}
}
