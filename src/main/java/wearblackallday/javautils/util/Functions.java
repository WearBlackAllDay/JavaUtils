package wearblackallday.javautils.util;

import java.util.Comparator;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;

public final class Functions {
	private Functions() {}

	public static <T> Function<? super Object, T> cast(Class<T> type) {
		return object -> (T)object;
	}

	public static <T> Stream<T> shuffle(Stream<T> stream) {
		return shuffle(stream, new Random());
	}

	public static <T> Stream<T> shuffle(Stream<T> stream, Random rng) {
		return stream.sorted(Comparator.comparingInt(t -> rng.nextInt()));
	}
}
