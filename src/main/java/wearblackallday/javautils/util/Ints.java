package wearblackallday.javautils.util;

import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public final class Ints {

	public static final IntBinaryOperator SUBTRACT = (a, b) -> a - b;
	public static final IntBinaryOperator MULTIPLY = (a, b) -> a * b;
	public static final IntBinaryOperator DIVIDE = (a, b) -> a / b;

	private Ints() {
	}

	public static int concat(int... ints) {
		return concat((short)10, ints);
	}

	public static int concat(short radix, int... ints) {
		int result = 0;
		for(int i : ints) {
			result *= radix;
			result += i;
		}
		return result;
	}

	public static int sum(int... ints) {
		int result = 0;
		for(int i : ints) {
			result += i;
		}
		return result;
	}

	public static int clamp(int i, int min, int max) {
		return Math.min(max, Math.max(min, i));
	}

	public static boolean inRange(int i, int bound) {
		return i >= 0 && i < bound;
	}

	public static boolean inRange(int i, int minInclusive, int maxExclusive) {
		return i >= minInclusive && i < maxExclusive;
	}

	public static boolean inRangeClosed(int i, int bound) {
		return i >= 0 && i <= bound;
	}

	public static boolean inRangeClosed(int i, int minInclusive, int maxInclusive) {
		return i >= minInclusive && i <= maxInclusive;
	}

	public static IntStream randomRange(int bound) {
		return randomRange(0, bound);
	}

	public static IntStream randomRange(int startInclusive, int endExclusive) {
		return randomRange(startInclusive, endExclusive, new Random());
	}

	public static IntStream randomRange(int bound, Random random) {
		return randomRange(0, bound, random);
	}

	public static IntStream randomRange(int startInclusive, int endExclusive, Random random) {
		int[] source = new int[endExclusive - startInclusive];
		Arrays.setAll(source, i -> i + startInclusive);
		return StreamSupport.intStream(new RandomSpliterator(random, source), false);
	}

	private static class RandomSpliterator extends Spliterators.AbstractIntSpliterator {
		private final Random random;
		private final int[] source;
		private int currentIndex = 0;

		private RandomSpliterator(Random random, int[] source) {
			super(source.length, SIZED | DISTINCT | NONNULL | IMMUTABLE);
			this.source = source;
			this.random = random;
		}

		@Override
		public boolean tryAdvance(IntConsumer action) {
			if(this.currentIndex >= this.source.length) return false;
			int nextIndex = this.random.nextInt(this.source.length - this.currentIndex) + this.currentIndex;
			action.accept(this.source[nextIndex]);
			this.source[nextIndex] = this.source[this.currentIndex++];
			return true;
		}
	}
}
