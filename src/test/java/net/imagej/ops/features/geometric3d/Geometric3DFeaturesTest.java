package net.imagej.ops.features.geometric3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.imagej.ops.features.AbstractFeatureTest;
import net.imagej.ops.features.geometric.Geometric2DFeatures.AreaFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CentroidXFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CentroidYFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CentroidZFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.CompactnessFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SphericityFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SurfaceAreaFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.SurfacePixelFeature;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imagej.ops.features.sets.Geometric3DFeatureSet;
import net.imglib2.roi.Regions;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;

import org.junit.Before;
import org.junit.Test;

/**
 * To get comparable values with ImageJ I created an image of a polygon, read
 * that image into ImageJ and used the Wand (tracing) tool to select the polygon
 * and used the corners of this polygon here.
 * 
 * @author Tim-Oliver Buchholz, University of Konstanz.
 *
 */
public class Geometric3DFeaturesTest extends AbstractFeatureTest {

	private Map<String, Double> results = new HashMap<String, Double>();

	@Before
	public void setup() {

		List<Pair<String, DoubleType>> compute = null;
		try {
			compute = ops.op(Geometric3DFeatureSet.class, LabelRegion.class)
					.getFeatureList(Regions.iterable(createLabelRegion3D()));

		} catch (MalformedURLException e) {
			fail("Couldn't create LabelRegion " + e.getMessage());
		} catch (IOException e) {
			fail("Couldn't create LabelRegion " + e.getMessage());
		}

		for (final Pair<String, DoubleType> featureResult : compute) {
			results.put(featureResult.getA(), featureResult.getB()
					.getRealDouble());
		}
	}

	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testSurfaceArea() {
		// value taken from imagej
		assertEquals(SurfaceAreaFeature.NAME, 8783.147, results.get(SurfaceAreaFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testSurfacePixel() {
		// value taken from imagej
		assertEquals(SurfacePixelFeature.NAME, 12072, results.get(SurfacePixelFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}

	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testVolume() {
		// value taken from imagej
		assertEquals(VolumeFeature.NAME, 68393, results.get(VolumeFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testCompactness() {
		// value taken from imagej
		assertEquals(CompactnessFeature.NAME, 0.301, results.get(CompactnessFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testSphericity() {
		// value taken from imagej
		assertEquals(SphericityFeature.NAME, 0.670, results.get(SphericityFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testCentroidX() {
		// value taken from imagej
		assertEquals(CentroidXFeature.NAME, 50, results.get(CentroidXFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testCentroidY() {
		// value taken from imagej
		assertEquals(CentroidYFeature.NAME, 50, results.get(CentroidYFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
	
	/**
	 * Test the {@link AreaFeature} Op.
	 */
	@Test
	public void testCentroidZ() {
		// value taken from imagej
		assertEquals(CentroidZFeature.NAME, 50, results.get(CentroidZFeature.NAME),
				AbstractFeatureTest.BIG_DELTA);
	}
}
