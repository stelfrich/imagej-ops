package net.imagej.ops.filter.fft;


public interface FastFFT {
	/**
	 * returns fastest FFT size possible for the input size
	 * 
	 * @param inputSize
	 * @return
	 */
	abstract void computeFFTFastSize(long[] inputSize);

	/**
	 * returns smallest FFT size possible for the input size
	 * 
	 * @param inputSize
	 */
	abstract void computeFFTSmallSize(long[] inputSize);
}
