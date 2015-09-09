/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, University of Konstanz and Brian Northan.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package net.imagej.ops.features.tamura2d;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.Ops.Tamura2d;
import net.imagej.ops.Ops.Tamura2d.Contrast;
import net.imagej.ops.stats.DefaultMoment4AboutMean;
import net.imagej.ops.stats.DefaultStandardDeviation;
import net.imagej.ops.stats.DefaultVariance;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * 
 * Default implementation of tamura feature contrast.
 * 
 * @author Andeas Graumann, Univesity of Konstanz
 *
 */
@Plugin(type = TamuraFeature.class, label = "Tamura 2D: Contrast", name = Tamura2d.Contrast.NAME)
public class DefaultContrastFeature<I extends RealType<I>, O extends RealType<O>> extends AbstractTamuraFeature<I, O>
		implements Contrast {

	@Parameter
	private OpService ops;

	@Override
	public void compute(final RandomAccessibleInterval<I> input, final O output) {
		
		// Get fourth moment about mean
		DoubleType m4 = new DoubleType();	
		ops.run(DefaultMoment4AboutMean.class, m4, input);
		
		// Get variance
		DoubleType var = new DoubleType();	
		ops.run(DefaultVariance.class, var, input);
		
		// Get standard deviation
		DoubleType std = new DoubleType();	
		ops.run(DefaultStandardDeviation.class, std, input);
		
		double l4 = m4.getRealDouble() / (var.getRealDouble()*var.getRealDouble());
		
		// contrast
		double fCon = std.getRealDouble() / Math.pow(l4, 0.25);
		output.setReal(fCon);

	}

}
