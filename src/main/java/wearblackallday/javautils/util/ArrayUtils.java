package wearblackallday.javautils.util;

import java.util.*;
import java.util.function.*;

public final class ArrayUtils {
	private ArrayUtils() {}

	public static <F, T> T[] map(F[] origin, Function<F, T> mapper) {
		T[] result = (T[])new Object[origin.length];
		Arrays.setAll(result, i -> mapper.apply(origin[i]));
		return result;
	}

	public static <F, T> T[] flatMap(F[] origin, Function<F, T[]> mapper) {
		T[][] buffer = (T[][])new Object[origin.length][];
		Arrays.setAll(buffer, i -> mapper.apply(origin[i]));
		return flatten(buffer);
	}

	public static <T> T[] flatten(T[][] array) {
		int newLength = 0;

		for(T[] subArray : array) {
			newLength += subArray.length;
		}

		T[] result = (T[])new Object[newLength];
		int totalIndex = 0;

		for(T[] subArray : array) {
			System.arraycopy(subArray, 0, result, totalIndex, subArray.length);
			totalIndex += subArray.length;
		}

		return result;
	}

	public static <T> T max(T[] array, Comparator<T> comparator) {
		T max = array[0];

		for(T t : array) {
			if(comparator.compare(t, max) > 0) max = t;
		}

		return max;
	}

	public static <T> T min(T[] array, Comparator<T> comparator) {
		T min = array[0];

		for(T t : array) {
			if(comparator.compare(t, min) < 0) min = t;
		}

		return min;
	}

	public static <T> Iterator<T> iterator(T[] array) {
		return new ArrayIterator<>(array);
	}

	public static PrimitiveIterator.OfInt iterator(int[] array) {
		return new IntArrayIterator(array);
	}

	public static PrimitiveIterator.OfLong iterator(long[] array) {
		return new LongArrayIterator(array);
	}

	public static PrimitiveIterator.OfDouble iterator(double[] array) {
		return new DoubleArrayIterator(array);
	}

	public static <T> Supplier<T> supplier(T[] array) {
		return new ArrayIterator<>(array);
	}

	public static IntSupplier supplier(int[] array) {
		return new IntArrayIterator(array);
	}

	public static LongSupplier supplier(long[] array) {
		return new LongArrayIterator(array);
	}

	public static DoubleSupplier supplier(double[] array) {
		return new DoubleArrayIterator(array);
	}

	public static <T> boolean hasDupes(T[] array) {
		Set<T> set = new HashSet<>();
		for(T t : array) {
			if(!set.add(t)) return true;
		}
		return false;
	}

	public static <T> List<T> getDupes(T[] array) {
		Set<T> set = new HashSet<>();
		List<T> dupes = new ArrayList<>();
		for(T t : array) {
			if(!set.add(t)) dupes.add(t);
		}
		return dupes;
	}

	private static class ArrayIterator<T> implements Iterator<T>, Supplier<T> {
		private int current = 0;
		private final T[] array;

		private ArrayIterator(T[] array) {
			this.array = array;
		}

		@Override
		public boolean hasNext() {
			return this.current < this.array.length;
		}

		@Override
		public T next() {
			return this.array[this.current++];
		}

		@Override
		public void forEachRemaining(Consumer<? super T> action) {
			for(int i = this.current; i < this.array.length; i++) {
				action.accept(this.array[i]);
			}
		}

		@Override
		public T get() {
			return this.next();
		}
	}

	private static class IntArrayIterator implements PrimitiveIterator.OfInt, IntSupplier {
		private int current = 0;
		private final int[] array;

		public IntArrayIterator(int[] array) {
			this.array = array;
		}

		@Override
		public int nextInt() {
			return this.array[this.current++];
		}

		@Override
		public void forEachRemaining(IntConsumer action) {
			for(int i = this.current; i < this.array.length; i++) {
				action.accept(this.array[i]);
			}
		}

		@Override
		public boolean hasNext() {
			return this.current < this.array.length;
		}

		@Override
		public int getAsInt() {
			return this.nextInt();
		}
	}

	private static class LongArrayIterator implements PrimitiveIterator.OfLong, LongSupplier {
		private int current = 0;
		private final long[] array;

		public LongArrayIterator(long[] array) {
			this.array = array;
		}

		@Override
		public long nextLong() {
			return this.array[this.current++];
		}

		@Override
		public void forEachRemaining(LongConsumer action) {
			for(int i = this.current; i < this.array.length; i++) {
				action.accept(this.array[i]);
			}
		}

		@Override
		public boolean hasNext() {
			return this.current < this.array.length;
		}

		@Override
		public long getAsLong() {
			return this.nextLong();
		}
	}

	private static class DoubleArrayIterator implements PrimitiveIterator.OfDouble, DoubleSupplier {
		private int current = 0;
		private final double[] array;

		public DoubleArrayIterator(double[] array) {
			this.array = array;
		}

		@Override
		public double nextDouble() {
			return this.array[this.current++];
		}

		@Override
		public void forEachRemaining(DoubleConsumer action) {
			for(int i = this.current; i < this.array.length; i++) {
				action.accept(this.array[i]);
			}
		}

		@Override
		public boolean hasNext() {
			return this.current < this.array.length;
		}

		@Override
		public double getAsDouble() {
			return this.nextDouble();
		}
	}
}
