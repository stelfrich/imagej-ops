
package net.imagej.ops.features.haralick.helper;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.OutputOp;
import net.imagej.ops.features.haralick.helper.CoocHelperCalculator.HelperResults;

/**
 * Helper class to calculate features with only one run through the cooccurrence
 * matrix.
 *
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = Op.class)
public class CoocHelperCalculator implements OutputOp<HelperResults> {

	@Parameter
	private CooccurrenceMatrix cooc;

	@Parameter(type = ItemIO.OUTPUT)
	private HelperResults output;

	@Override
	public void run() {

		final double[][] matrix = this.cooc.getOutput();
		final int nrGrayLevels = matrix.length;

		final double[] px = new double[nrGrayLevels];
		final double[] py = new double[nrGrayLevels];
		final double[] pxminusy = new double[nrGrayLevels];
		final double[] pxplusy = new double[2 * nrGrayLevels + 1];

		// calculate px, py, px-y and px+y
		for (int i = 0; i < nrGrayLevels; i++) {
			for (int j = 0; j < nrGrayLevels; j++) {

				final double matrix_ij = matrix[i][j];

				px[j] += matrix_ij;
				py[i] += matrix_ij;

				final int kminus = Math.abs(i - j);
				pxminusy[kminus] += matrix_ij;

				final int kplus = i + j + 2;
				pxplusy[kplus] += matrix_ij;
			}
		}

		// calculate mean
		double meanx = 0;
		double meany = 0;
		for (int i = 0; i < nrGrayLevels; i++) {
			meanx += i * px[i];
			meany += i * py[i];
		}

		// calculate stdx und stdy
		double stdx = 0;
		double stdy = 0;
		for (int i = 0; i < nrGrayLevels; i++) {
			stdx += ((i - meanx) * (i - meanx)) * px[i];
			stdy += ((i - meany) * (i - meany)) * py[i];
		}

		this.output = new HelperResults(px, py, pxminusy, pxplusy, meanx, meany,
			stdx, stdy);
	}

	@Override
	public HelperResults getOutput() {
		return this.output;
	}

	@Override
	public void setOutput(final HelperResults output) {
		this.output = output;
	}

	public class HelperResults {

		private final double[] px;
		private final double[] py;
		private final double[] pxminusy;
		private final double[] pxplusy;
		private final double meanx;
		private final double meany;
		private final double stdx;
		private final double stdy;

		public HelperResults(final double[] px, final double[] py,
			final double[] pxminusy, final double[] pxplusy, final double meanx,
			final double meany, final double stdx, final double stdy)
		{
			this.px = px;
			this.py = py;
			this.pxminusy = pxminusy;
			this.pxplusy = pxplusy;
			this.meanx = meanx;
			this.meany = meany;
			this.stdx = stdx;
			this.stdy = stdy;
		}

		public double[] getPx() {
			return this.px;
		}

		public double[] getPy() {
			return this.py;
		}

		public double[] getPxMinusY() {
			return this.pxminusy;
		}

		public double[] getPxPlusY() {
			return this.pxplusy;
		}

		public double getMeanX() {
			return this.meanx;
		}

		public double getMeanY() {
			return this.meany;
		}

		public double getStdX() {
			return this.stdx;
		}

		public double getStdY() {
			return this.stdy;
		}

	}
}
