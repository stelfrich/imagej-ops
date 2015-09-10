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
package net.imagej.ops.features.lbp2d;

import java.util.ArrayList;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.Ops.Lbp.Lbp2d;
import net.imagej.ops.image.histogram.HistogramCreate;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.histogram.Histogram1d;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.view.Views;

/**
 * 
 * Default implementation of 2d local binary patterns
 * 
 * @author Andreas Graumann, University of Konstanz
 *
 * @param <I>
 * @param <O>
 */
@Plugin(type = Lbp2dFeature.class, label = "2d Local Binary Pattern", name = Lbp2d.NAME)
public class DefaultLbp2d<I extends RealType<I>, O extends RealType<O>> extends AbstractLbp2dFeature<I, O>
		implements Lbp2dFeature<I, O> {
	
	@Parameter
	protected OpService ops;
	
	@Parameter(required = true)
	private int distance = 1;

	@SuppressWarnings("unchecked")
	@Override
	public void compute(RandomAccessibleInterval<I> input, O output) {
		
		ArrayList<LongType> numberList = new ArrayList<LongType>();
		RandomAccess<I> raInput = Views.extendZero(input).randomAccess();
		final Cursor<I> cInput = Views.flatIterable(input).cursor();
		final ClockwiseDistanceNeighborhoodIterator<I> cNeigh = new ClockwiseDistanceNeighborhoodIterator<I>(raInput, distance);
		
		while (cInput.hasNext()) {
			cInput.next();
			double centerValue = cInput.get().getRealDouble();
			
			int resultBinaryValue = 0;
			
			cNeigh.reset();
			while (cNeigh.hasNext()) {
				double nValue = cNeigh.next().getRealDouble();
				int pos = cNeigh.getIndex();
				if (nValue > centerValue) {
					resultBinaryValue |= (1 << pos);
				}
			}
			numberList.add(new LongType(resultBinaryValue));
		}
		
		Histogram1d<Integer> hist = (Histogram1d<Integer>) ops.run(HistogramCreate.class, numberList, 16);
		System.out.println("done");
	}

}
