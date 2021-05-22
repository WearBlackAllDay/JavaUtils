package wearblackallday.util;

import java.util.function.*;

public final class Filters {
	private Filters() {}

	public static <F, T> Predicate<F> byKey(Function<F, T> extractor, Predicate<T> condition) {
		return object -> condition.test(extractor.apply(object));
	}

	public static <F, T> Predicate<F> byKeyID(Function<F, T> extractor, T identity) {
		return object -> identity == extractor.apply(object);
	}

	public static <F> Predicate<F> byInt(ToIntFunction<F> intExtractor, IntPredicate condition) {
		return object -> condition.test(intExtractor.applyAsInt(object));
	}

	public static <F> Predicate<F> byInt(ToIntFunction<F> intExtractor, int identity) {
		return object -> identity == intExtractor.applyAsInt(object);
	}

	public static <F> Predicate<F> byLong(ToLongFunction<F> longExtractor, LongPredicate condition) {
		return object -> condition.test(longExtractor.applyAsLong(object));
	}

	public static <F> Predicate<F> byLong(ToLongFunction<F> longExtractor, long identity) {
		return object -> identity == longExtractor.applyAsLong(object);
	}

	public static <F> Predicate<F> byDouble(ToDoubleFunction<F> doubleExtractor, DoublePredicate condition) {
		return object -> condition.test(doubleExtractor.applyAsDouble(object));
	}
}
