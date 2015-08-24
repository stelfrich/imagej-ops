package net.imagej.ops.descriptor3d;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.AbstractOutputFunction;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.geometric.GeometricOps.ConvexHull3D;

@Plugin(type = Op.class, name = ConvexHull3D.NAME)
public class QuickHull3DFromMC extends AbstractOutputFunction<MarchingCubes<?>, DefaultFacets> {

	@Parameter(type = ItemIO.INPUT) 
	private OpService ops;
	
	@Override
	public DefaultFacets createOutput(MarchingCubes<?> input) {
		return (DefaultFacets) ops.run(QuickHull3D.class, input.getOutput().getPoints());
	}

	@Override
	protected DefaultFacets safeCompute(MarchingCubes<?> input,
			DefaultFacets output) {
		return output;
	}

}
