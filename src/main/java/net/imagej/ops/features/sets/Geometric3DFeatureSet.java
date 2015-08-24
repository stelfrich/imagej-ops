package net.imagej.ops.features.sets;

import java.util.HashSet;
import java.util.Set;

import org.scijava.plugin.Plugin;

import net.imagej.ops.OpRef;
import net.imagej.ops.descriptor3d.BitTypeVertexInterpolator;
import net.imagej.ops.descriptor3d.DefaultCentroid3D;
import net.imagej.ops.descriptor3d.MarchingCubes;
import net.imagej.ops.descriptor3d.QuickHull3D;
import net.imagej.ops.descriptor3d.QuickHull3DFromMC;
import net.imagej.ops.descriptor3d.SecondMultiVariate3D;
import net.imagej.ops.features.AbstractAutoResolvingFeatureSet;
import net.imagej.ops.features.FeatureSet;
import net.imagej.ops.features.geometric3d.DefaultConvexHullSurfaceAreaFeature;
import net.imagej.ops.features.geometric3d.DefaultConvexHullSurfacePixelFeature;
import net.imagej.ops.features.geometric3d.DefaultConvexHullVolumeFeature;
import net.imagej.ops.features.geometric3d.DefaultCompactnessFeature;
import net.imagej.ops.features.geometric3d.DefaultConvexityFeature;
import net.imagej.ops.features.geometric3d.DefaultMainElongationFeature;
import net.imagej.ops.features.geometric3d.DefaultMedianElongationFeature;
import net.imagej.ops.features.geometric3d.DefaultRugosityFeature;
import net.imagej.ops.features.geometric3d.DefaultSolidityFeature;
import net.imagej.ops.features.geometric3d.DefaultSparenessFeature;
import net.imagej.ops.features.geometric3d.DefaultSphericityFeature;
import net.imagej.ops.features.geometric3d.DefaultSurfaceAreaFeature;
import net.imagej.ops.features.geometric3d.DefaultSurfacePixelFeature;
import net.imagej.ops.features.geometric3d.DefaultVolumeFeature;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.BooleanType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type = FeatureSet.class, label = "Geometric 3D Features", description = "Calculates the Geometric 3D Features")
public class Geometric3DFeatureSet<T extends BooleanType<T>> extends
		AbstractAutoResolvingFeatureSet<RandomAccessibleInterval<T>, DoubleType> {

	@Override
	public Set<OpRef<?>> getOutputOps() {
		final HashSet<OpRef<?>> outputOps = new HashSet<OpRef<?>>();
		
		outputOps.add(createOpRef(DefaultSurfaceAreaFeature.class));
		outputOps.add(createOpRef(DefaultSurfacePixelFeature.class));
		outputOps.add(createOpRef(DefaultVolumeFeature.class));
		outputOps.add(createOpRef(DefaultCompactnessFeature.class));
		outputOps.add(createOpRef(DefaultSphericityFeature.class));
		outputOps.add(createOpRef(DefaultMainElongationFeature.class));
		outputOps.add(createOpRef(DefaultMedianElongationFeature.class));
		outputOps.add(createOpRef(DefaultSparenessFeature.class));
		outputOps.add(createOpRef(DefaultConvexHullSurfaceAreaFeature.class));
		outputOps.add(createOpRef(DefaultConvexHullSurfacePixelFeature.class));
		outputOps.add(createOpRef(DefaultConvexHullVolumeFeature.class));
		outputOps.add(createOpRef(DefaultConvexityFeature.class));
		outputOps.add(createOpRef(DefaultSolidityFeature.class));
		outputOps.add(createOpRef(DefaultRugosityFeature.class));
		return outputOps;
	}

	@Override
	public Set<OpRef<?>> getHiddenOps() {
		final HashSet<OpRef<?>> hiddenOps = new HashSet<OpRef<?>>();
		hiddenOps.add(createOpRef(MarchingCubes.class, 1, new BitTypeVertexInterpolator()));
		hiddenOps.add(createOpRef(SecondMultiVariate3D.class));
		hiddenOps.add(createOpRef(QuickHull3DFromMC.class));
		hiddenOps.add(createOpRef(DefaultCentroid3D.class));
		return hiddenOps;
	}

}
