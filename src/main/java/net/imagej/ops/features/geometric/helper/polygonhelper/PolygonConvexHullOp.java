package net.imagej.ops.features.geometric.helper.polygonhelper;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.OutputOp;
import net.imagej.ops.geometric.polygon.GeometricPolygonOps.ConvexHull2DPolygon;
import net.imagej.ops.geometric.polygon.Polygon;

@Plugin(type = Op.class, name = "PolygonConvexHullProvider")
public class PolygonConvexHullOp implements OutputOp<Polygon> {

	@Parameter(type = ItemIO.OUTPUT)
	private Polygon out;

	@Parameter(type = ItemIO.INPUT)
	private Polygon in;

	@Parameter(type = ItemIO.INPUT)
	private OpService ops;

	@Override
	public void run() {
		out = (Polygon) ops.run(ConvexHull2DPolygon.class, in);
	}

	@Override
	public Polygon getOutput() {
		return out;
	}

	@Override
	public void setOutput(Polygon output) {
		this.out = output;
	}

}
