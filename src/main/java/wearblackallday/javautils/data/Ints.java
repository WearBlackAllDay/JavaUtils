package wearblackallday.javautils.data;

import java.util.Random;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public final class Ints {
	private Ints() {
	}

	public static int concat(int... ints) {
		return concat(10, ints);
	}

	public static int concat(int radix, int... ints) {
		int result = 0;
		for(int i : ints) {
			result *= radix;
			result += i;
		}
		return result;
	}

	public static IntStream randomRange(int bound) {
		return randomRange(0, bound);
	}

	public static IntStream randomRange(int start, int end) {
		return randomRange(start, end, new Random());
	}

	public static IntStream randomRange(int bound, Random random) {
		return randomRange(0, bound, random);
	}

	public static IntStream randomRange(int start, int end, Random random) {
		return StreamSupport.intStream(new RandomSpliterator(IntStream.range(start, end).toArray(), random), false);
	}

	private static class RandomSpliterator extends Spliterators.AbstractIntSpliterator {
		private final Random random;
		private int currentIndex = 0;
		private final int[] source;

		private RandomSpliterator(int[] source, Random random) {
			super(source.length, SIZED);
			this.random = random;
			this.source = source;
		}

		@Override
		public boolean tryAdvance(IntConsumer action) {
			if(this.currentIndex >= this.source.length) {
				return false;
			}
			int nextIndex = this.random.nextInt(this.source.length - this.currentIndex) + this.currentIndex;
			action.accept(this.source[nextIndex]);
			this.source[nextIndex] = this.source[this.currentIndex++];
			return true;
		}
	}
}
