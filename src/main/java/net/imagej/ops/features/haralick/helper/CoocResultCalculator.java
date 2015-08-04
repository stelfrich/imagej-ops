
package net.imagej.ops.features.haralick.helper;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.Op;
import net.imagej.ops.OutputOp;
import net.imagej.ops.features.haralick.helper.CoocResultCalculator.FeatureResults;

/**
 * Helper class to calculate features with only one run through the cooccurrence
 * matrix.
 *
 * @author Daniel Seebacher, University of Konstanz.
 */
@Plugin(type = Op.class)
public class CoocResultCalculator implements OutputOp<FeatureResults> {

	private static final double EPSILON = 0.00000001f;

	@Parameter
	private CooccurrenceMatrix cooc;

	@Parameter
	private CoocHelperCalculator chc;

	@Parameter(type = ItemIO.OUTPUT)
	private FeatureResults output;

	@Override
	public void run() {
		final double[][] matrix = this.cooc.getOutput();
		final int nrGrayLevels = matrix.length;
		final double mux = this.chc.getOutput().getMeanX();
		final double muy = this.chc.getOutput().getMeanY();
		final double stdx = this.chc.getOutput().getStdX();
		final double stdy = this.chc.getOutput().getStdY();

		// helpers
		double hx = 0.0d;
		double hy = 0.0d;
		double hxy1 = 0.0d;
		double hxy2 = 0.0d;
		final double[] px = this.chc.getOutput().getPx();
		final double[] py = this.chc.getOutput().getPy();

		for (int i = 0; i < nrGrayLevels; i++) {
			hx += px[i] * Math.log(px[i] + EPSILON);
			hy += py[i] * Math.log(py[i] + EPSILON);

		}
		hx = -hx;
		hy = -hy;

		// features
		double defaultasm = 0;
		double clustershade = 0;
		double clusterpromence = 0;
		double defaultcorrelation = 0;
		double defaultentropy = 0;
		double defaultifdm = 0;
		double defaultmaxprob = 0;
		double defaulttexturehomogenity = 0;
		double defaultvariance = 0;

		for (int i = 0; i < nrGrayLevels; i++) {
			for (int j = 0; j < nrGrayLevels; j++) {

				final double ij_mux_muy = i + j - mux - muy;
				final double i_mux = i - mux;
				final double j_muy = j - muy;
				final double matrix_ji = matrix[j][i];
				final double matrix_ij = matrix[i][j];
				final double abs_ij = Math.abs(i - j);

				defaultasm += matrix_ij * matrix_ij;
				clustershade += (ij_mux_muy * ij_mux_muy * ij_mux_muy) * matrix_ji;
				clusterpromence += (ij_mux_muy * ij_mux_muy * ij_mux_muy * ij_mux_muy) *
					matrix_ij;
				defaultcorrelation += ((i_mux) * (j_muy)) * (matrix_ij / (stdx * stdy));
				defaultentropy += matrix_ij * Math.log10(matrix_ij + EPSILON);

				if (i != j) {
					defaultifdm += matrix_ij / abs_ij;
				}

				defaultmaxprob = (matrix_ij > defaultmaxprob) ? matrix_ij
					: defaultmaxprob;

				defaulttexturehomogenity += matrix_ij / (1 + abs_ij);
				defaultvariance += ((i_mux) * (i_mux)) * matrix_ij + ((j_muy) *
					(j_muy)) * matrix_ij;

				hxy1 += matrix_ij * Math.log(px[i] * py[j] + EPSILON);
				hxy2 += px[i] * py[j] * Math.log(px[i] * py[j] + EPSILON);
			}
		}

		defaultcorrelation = (Double.isNaN(defaultcorrelation)) ? 0
			: defaultcorrelation;
		defaultentropy = -defaultentropy;
		defaultifdm = (Double.isNaN(defaultifdm)) ? 0 : defaultifdm;
		defaultvariance = defaultvariance / 2d;

		hxy1 = -hxy1;
		hxy2 = -hxy2;

		this.output = new FeatureResults(defaultasm, clustershade, clusterpromence,
			defaultcorrelation, defaultentropy, defaultifdm, defaultmaxprob,
			defaulttexturehomogenity, defaultvariance, new double[] { hx, hy, hxy1,
				hxy2 });
	}

	@Override
	public FeatureResults getOutput() {
		return this.output;
	}

	@Override
	public void setOutput(final FeatureResults output) {
		this.output = output;
	}

	public class FeatureResults {

		private final double defaultasm;
		private final double clustershade;
		private final double clusterpromence;
		private final double defaultcorrelation;
		private final double defaultentropy;
		private final double defaultifdm;
		private final double defaultmaxprob;
		private final double defaulttexturehomogenity;
		private final double defaultvariance;
		private final double[] coochxy;

		private FeatureResults(final double defaultasm, final double clustershade,
			final double clusterpromence, final double defaultcorrelation,
			final double defaultentropy, final double defaultifdm,
			final double defaultmaxprob, final double defaulttexturehomogenity,
			final double defaultvariance, final double[] coochxy)
		{
			this.defaultasm = defaultasm;
			this.clustershade = clustershade;
			this.clusterpromence = clusterpromence;
			this.defaultcorrelation = defaultcorrelation;
			this.defaultentropy = defaultentropy;
			this.defaultifdm = defaultifdm;
			this.defaultmaxprob = defaultmaxprob;
			this.defaulttexturehomogenity = defaulttexturehomogenity;
			this.defaultvariance = defaultvariance;
			this.coochxy = coochxy;
		}

		public double getDefaultASMFeature() {
			return this.defaultasm;
		}

		public double getClustershade() {
			return this.clustershade;
		}

		public double getClusterpromence() {
			return this.clusterpromence;
		}

		public double getDefaultCorrelation() {
			return this.defaultcorrelation;
		}

		public double getDefaultEntropy() {
			return this.defaultentropy;
		}

		public double getDefaultIFDMFeature() {
			return this.defaultifdm;
		}

		public double getDefaultMaxProbability() {
			return this.defaultmaxprob;
		}

		public double getDefaultTextureHomogenity() {
			return this.defaulttexturehomogenity;
		}

		public double getDefaultVariance() {
			return this.defaultvariance;
		}

		public double[] getCoocHXY() {
			return this.coochxy;
		}
	}
}
