/*
 * #%L
 * SciJava OPS: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2013 Board of Regents of the University of
 * Wisconsin-Madison, and University of Konstanz.
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
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package net.imagej.ops.features.haralick;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.features.haralick.HaralickFeatures.DifferenceVarianceFeature;
import net.imagej.ops.features.haralick.helper.CoocHelperCalculator;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Implementation of Difference Variance Haralick Feature
 *
 * @author Andreas Graumann, University of Konstanz
 * @author Christian Dietz, University of Konstanz
 */
@Plugin(type = Op.class, label = "Haralick: Difference Variance",
	name = "Haralick: Difference Variance")
public class DefaultDifferenceVarianceFeature implements
	DifferenceVarianceFeature<DoubleType>
{

	@Parameter
	private CoocHelperCalculator chc;

	@Parameter(type = ItemIO.OUTPUT)
	private DoubleType output;

	@Override
	public void run() {
		final double[] pxminusy = this.chc.getOutput().getPxMinusY();
		double sum = 0.0d;
		double res = 0;
		for (int k = 0; k < pxminusy.length; k++) {
			sum += k * pxminusy[k];
		}

		for (int k = 0; k < pxminusy.length; k++) {
			res += (k - sum) * pxminusy[k];
		}

		this.output = new DoubleType(res);
	}

	@Override
	public DoubleType getOutput() {
		return this.output;
	}

	@Override
	public void setOutput(final DoubleType _output) {
		this.output = _output;
	}
}
