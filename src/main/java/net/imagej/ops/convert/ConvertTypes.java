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

package net.imagej.ops.convert;

import java.math.BigInteger;

import net.imagej.ops.AbstractHybridOp;
import net.imagej.ops.Ops;
import net.imagej.ops.Ops.ConvertOps;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.IntegerType;
import net.imglib2.type.numeric.complex.ComplexDoubleType;
import net.imglib2.type.numeric.complex.ComplexFloatType;
import net.imglib2.type.numeric.integer.ByteType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.type.numeric.integer.ShortType;
import net.imglib2.type.numeric.integer.Unsigned128BitType;
import net.imglib2.type.numeric.integer.Unsigned12BitType;
import net.imglib2.type.numeric.integer.Unsigned2BitType;
import net.imglib2.type.numeric.integer.Unsigned4BitType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedIntType;
import net.imglib2.type.numeric.integer.UnsignedLongType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.plugin.Attr;
import org.scijava.plugin.Plugin;

public final class ConvertTypes {

	private ConvertTypes() {
		// NB: Prevent instantiation of utility class.
	}

	@Plugin(type = Ops.ConvertOps.Bit.class, name = Ops.ConvertOps.Bit.NAME,
		priority = 0.4)
	public static class ComplexToBit<C extends ComplexType<C>> extends
		AbstractHybridOp<C, BitType> implements ConvertOps.Bit
	{

		@Override
		public BitType createOutput(final C input) {
			return new BitType();
		}

		@Override
		public void compute(final C input, final BitType output) {
			output.set(input.getRealDouble() != 0);
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint2.class, name = Ops.ConvertOps.Uint2.NAME,
		priority = 0.4)
	public static class ComplexToUint2<C extends ComplexType<C>> extends
		AbstractHybridOp<C, Unsigned2BitType> implements ConvertOps.Uint2
	{

		@Override
		public Unsigned2BitType createOutput(final C input) {
			return new Unsigned2BitType();
		}

		@Override
		public void compute(final C input, final Unsigned2BitType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint2.class, name = Ops.ConvertOps.Uint2.NAME,
		priority = 0.4)
	public static class IntegerToUint2<T extends IntegerType<T>> extends
		AbstractHybridOp<T, Unsigned2BitType> implements ConvertOps.Uint2
	{

		@Override
		public Unsigned2BitType createOutput(final T input) {
			return new Unsigned2BitType();
		}

		@Override
		public void compute(final T input, final Unsigned2BitType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint4.class, name = Ops.ConvertOps.Uint4.NAME,
		priority = 0.4)
	public static class ComplexToUint4<C extends ComplexType<C>> extends
		AbstractHybridOp<C, Unsigned4BitType> implements ConvertOps.Uint4
	{

		@Override
		public Unsigned4BitType createOutput(final C input) {
			return new Unsigned4BitType();
		}

		@Override
		public void compute(final C input, final Unsigned4BitType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint4.class, name = Ops.ConvertOps.Uint4.NAME,
		priority = 0.4)
	public static class IntegerToUint4<T extends IntegerType<T>> extends
		AbstractHybridOp<T, Unsigned4BitType> implements ConvertOps.Uint4
	{

		@Override
		public Unsigned4BitType createOutput(final T input) {
			return new Unsigned4BitType();
		}

		@Override
		public void compute(final T input, final Unsigned4BitType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int8.class, name = Ops.ConvertOps.Int8.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int8.ALIASES) })
	public static class ComplexToInt8<C extends ComplexType<C>> extends
		AbstractHybridOp<C, ByteType> implements ConvertOps.Int8
	{

		@Override
		public ByteType createOutput(final C input) {
			return new ByteType();
		}

		@Override
		public void compute(final C input, final ByteType output) {
			output.set((byte) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint8.class, name = Ops.ConvertOps.Uint8.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Uint8.ALIASES) })
	public static class ComplexToUint8<C extends ComplexType<C>> extends
		AbstractHybridOp<C, UnsignedByteType> implements ConvertOps.Uint8
	{

		@Override
		public UnsignedByteType createOutput(final C input) {
			return new UnsignedByteType();
		}

		@Override
		public void compute(final C input, final UnsignedByteType output) {
			output.set((int) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint8.class, name = Ops.ConvertOps.Uint8.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Uint8.ALIASES) })
	public static class IntegerToUint8<T extends IntegerType<T>> extends
		AbstractHybridOp<T, UnsignedByteType> implements ConvertOps.Uint8
	{

		@Override
		public UnsignedByteType createOutput(final T input) {
			return new UnsignedByteType();
		}

		@Override
		public void compute(final T input, final UnsignedByteType output) {
			output.set(input.getInteger());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint12.class,
		name = Ops.ConvertOps.Uint12.NAME, priority = 0.4)
	public static class ComplexToUint12<C extends ComplexType<C>> extends
		AbstractHybridOp<C, Unsigned12BitType> implements ConvertOps.Uint12
	{

		@Override
		public Unsigned12BitType createOutput(final C input) {
			return new Unsigned12BitType();
		}

		@Override
		public void compute(final C input, final Unsigned12BitType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint12.class,
		name = Ops.ConvertOps.Uint12.NAME, priority = 0.4)
	public static class IntegerToUint12<T extends IntegerType<T>> extends
		AbstractHybridOp<T, Unsigned12BitType> implements ConvertOps.Uint12
	{

		@Override
		public Unsigned12BitType createOutput(final T input) {
			return new Unsigned12BitType();
		}

		@Override
		public void compute(final T input, final Unsigned12BitType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int16.class, name = Ops.ConvertOps.Int16.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int16.ALIASES) })
	public static class ComplexToInt16<C extends ComplexType<C>> extends
		AbstractHybridOp<C, ShortType> implements ConvertOps.Int16
	{

		@Override
		public ShortType createOutput(final C input) {
			return new ShortType();
		}

		@Override
		public void compute(final C input, final ShortType output) {
			output.set((short) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint16.class,
		name = Ops.ConvertOps.Uint16.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint16.ALIASES) })
	public static class ComplexToUint16<C extends ComplexType<C>> extends
		AbstractHybridOp<C, UnsignedShortType> implements ConvertOps.Uint16
	{

		@Override
		public UnsignedShortType createOutput(final C input) {
			return new UnsignedShortType();
		}

		@Override
		public void compute(final C input, final UnsignedShortType output) {
			output.set((int) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint16.class,
		name = Ops.ConvertOps.Uint16.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint16.ALIASES) })
	public static class IntegerToUint16<T extends IntegerType<T>> extends
		AbstractHybridOp<T, UnsignedShortType> implements ConvertOps.Uint16
	{

		@Override
		public UnsignedShortType createOutput(final T input) {
			return new UnsignedShortType();
		}

		@Override
		public void compute(final T input, final UnsignedShortType output) {
			output.set(input.getInteger());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int32.class, name = Ops.ConvertOps.Int32.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int32.ALIASES) })
	public static class ComplexToInt32<C extends ComplexType<C>> extends
		AbstractHybridOp<C, IntType> implements ConvertOps.Int32
	{

		@Override
		public IntType createOutput(final C input) {
			return new IntType();
		}

		@Override
		public void compute(final C input, final IntType output) {
			output.set((int) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int32.class, name = Ops.ConvertOps.Int32.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int32.ALIASES) })
	public static class IntegerToInt32<T extends IntegerType<T>> extends
		AbstractHybridOp<T, IntType> implements ConvertOps.Int32
	{

		@Override
		public IntType createOutput(final T input) {
			return new IntType();
		}

		@Override
		public void compute(final T input, final IntType output) {
			output.set(input.getInteger());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint32.class,
		name = Ops.ConvertOps.Uint32.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint32.ALIASES) })
	public static class ComplexToUint32<C extends ComplexType<C>> extends
		AbstractHybridOp<C, UnsignedIntType> implements ConvertOps.Uint32
	{

		@Override
		public UnsignedIntType createOutput(final C input) {
			return new UnsignedIntType();
		}

		@Override
		public void compute(final C input, final UnsignedIntType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint32.class,
		name = Ops.ConvertOps.Uint32.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint32.ALIASES) })
	public static class IntegerToUint32<T extends IntegerType<T>> extends
		AbstractHybridOp<T, UnsignedIntType> implements ConvertOps.Uint32
	{

		@Override
		public UnsignedIntType createOutput(final T input) {
			return new UnsignedIntType();
		}

		@Override
		public void compute(final T input, final UnsignedIntType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int64.class, name = Ops.ConvertOps.Int64.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int64.ALIASES) })
	public static class ComplexToInt64<C extends ComplexType<C>> extends
		AbstractHybridOp<C, LongType> implements ConvertOps.Int64
	{

		@Override
		public LongType createOutput(final C input) {
			return new LongType();
		}

		@Override
		public void compute(final C input, final LongType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Int64.class, name = Ops.ConvertOps.Int64.NAME,
		priority = 0.4, attrs = { @Attr(name = "aliases",
			value = Ops.ConvertOps.Int64.ALIASES) })
	public static class IntegerToInt64<T extends IntegerType<T>> extends
		AbstractHybridOp<T, LongType> implements ConvertOps.Int64
	{

		@Override
		public LongType createOutput(final T input) {
			return new LongType();
		}

		@Override
		public void compute(final T input, final LongType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint64.class,
		name = Ops.ConvertOps.Uint64.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint64.ALIASES) })
	public static class ComplexToUint64<C extends ComplexType<C>> extends
		AbstractHybridOp<C, UnsignedLongType> implements ConvertOps.Uint64
	{

		@Override
		public UnsignedLongType createOutput(final C input) {
			return new UnsignedLongType();
		}

		@Override
		public void compute(final C input, final UnsignedLongType output) {
			output.set((long) input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint64.class,
		name = Ops.ConvertOps.Uint64.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Uint64.ALIASES) })
	public static class IntegerToUint64<T extends IntegerType<T>> extends
		AbstractHybridOp<T, UnsignedLongType> implements ConvertOps.Uint64
	{

		@Override
		public UnsignedLongType createOutput(final T input) {
			return new UnsignedLongType();
		}

		@Override
		public void compute(final T input, final UnsignedLongType output) {
			output.set(input.getIntegerLong());
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint128.class,
		name = Ops.ConvertOps.Uint128.NAME, priority = 0.4)
	public static class ComplexToUint128<C extends ComplexType<C>> extends
		AbstractHybridOp<C, Unsigned128BitType> implements ConvertOps.Uint128
	{

		@Override
		public Unsigned128BitType createOutput(final C input) {
			return new Unsigned128BitType();
		}

		@Override
		public void compute(final C input, final Unsigned128BitType output) {
			output.set(BigInteger.valueOf((long) input.getRealDouble()));
		}

	}

	@Plugin(type = Ops.ConvertOps.Uint128.class,
		name = Ops.ConvertOps.Uint128.NAME, priority = 0.4)
	public static class IntegerToUint128<T extends IntegerType<T>> extends
		AbstractHybridOp<T, Unsigned128BitType> implements ConvertOps.Uint128
	{

		@Override
		public Unsigned128BitType createOutput(final T input) {
			return new Unsigned128BitType();
		}

		@Override
		public void compute(final T input, final Unsigned128BitType output) {
			output.set(BigInteger.valueOf(input.getIntegerLong()));
		}

	}

	@Plugin(type = Ops.ConvertOps.Float32.class,
		name = Ops.ConvertOps.Float32.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Float32.ALIASES) })
	public static class ComplexToFloat32<C extends ComplexType<C>> extends
		AbstractHybridOp<C, FloatType> implements ConvertOps.Float32
	{

		@Override
		public FloatType createOutput(final C input) {
			return new FloatType();
		}

		@Override
		public void compute(final C input, final FloatType output) {
			output.set(input.getRealFloat());
		}

	}

	@Plugin(type = Ops.ConvertOps.Cfloat32.class,
		name = Ops.ConvertOps.Cfloat32.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Cfloat32.ALIASES) })
	public static class ComplexToCfloat32<C extends ComplexType<C>> extends
		AbstractHybridOp<C, ComplexFloatType> implements ConvertOps.Cfloat32
	{

		@Override
		public ComplexFloatType createOutput(final C input) {
			return new ComplexFloatType();
		}

		@Override
		public void compute(final C input, final ComplexFloatType output) {
			output.set(input.getRealFloat(), input.getImaginaryFloat());
		}

	}

	@Plugin(type = Ops.ConvertOps.Float64.class,
		name = Ops.ConvertOps.Float64.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Float64.ALIASES) })
	public static class ComplexToFloat64<C extends ComplexType<C>> extends
		AbstractHybridOp<C, DoubleType> implements ConvertOps.Float64
	{

		@Override
		public DoubleType createOutput(final C input) {
			return new DoubleType();
		}

		@Override
		public void compute(final C input, final DoubleType output) {
			output.set(input.getRealDouble());
		}

	}

	@Plugin(type = Ops.ConvertOps.Cfloat64.class,
		name = Ops.ConvertOps.Cfloat64.NAME, priority = 0.4, attrs = { @Attr(
			name = "aliases", value = Ops.ConvertOps.Cfloat64.ALIASES) })
	public static class ComplexToCfloat64<C extends ComplexType<C>> extends
		AbstractHybridOp<C, ComplexDoubleType> implements ConvertOps.Cfloat64
	{

		@Override
		public ComplexDoubleType createOutput(final C input) {
			return new ComplexDoubleType();
		}

		@Override
		public void compute(final C input, final ComplexDoubleType output) {
			output.set(input.getRealDouble(), input.getImaginaryDouble());
		}

	}
}
