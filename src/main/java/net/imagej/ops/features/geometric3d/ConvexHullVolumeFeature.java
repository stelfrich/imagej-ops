package net.imagej.ops.features.geometric3d;

import net.imagej.ops.Op;
import net.imagej.ops.descriptor3d.QuickHull3D;
import net.imagej.ops.descriptor3d.TriangularFacet;
import net.imagej.ops.descriptor3d.Vertex;
import net.imagej.ops.features.geometric.Geometric3DFeatures.VolumeFeature;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = VolumeFeature.NAME, label = VolumeFeature.LABEL, priority = Priority.VERY_HIGH_PRIORITY)
public class ConvexHullVolumeFeature implements VolumeFeature<DoubleType> {

	@Parameter(type = ItemIO.INPUT)
	private QuickHull3D input;
	
	@Parameter(type = ItemIO.OUTPUT)
	private DoubleType out;
	
	@Override
	public DoubleType getOutput() {
		return out;
	}

	@Override
	public void setOutput(DoubleType output) {
		this.out = output;
	}

	@Override
	public void run() {
		Vertex centroid = input.getOutput().getCentroid();
		double volume = 0;
		for (TriangularFacet f : input.getOutput().getFacets()) {
			volume += 1/3d * f.getArea() * f.distanceToPlane(centroid);
		}
		out = new DoubleType(volume);
	}

}
