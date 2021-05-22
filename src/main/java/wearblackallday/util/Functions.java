package wearblackallday.util;

import java.util.Comparator;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public final class Functions {
	private Functions() {}

	public static <T> Function<? super Object, T> cast(Class<T> type) {
		return object -> (T)object;
	}

	@SafeVarargs
	public static <F, T> T[] map(Function<F, T> mapper, F... targets) {
		T[] result = (T[])new Object[targets.length];
		for(int i = 0; i < targets.length; i++) {
			result[i] = mapper.apply(targets[i]);
		}
		return result;
	}

	public static <F, T> Predicate<F> filterAttribute(Function<F, T> extractor, Predicate<T> condition) {
		return object -> condition.test(extractor.apply(object));
	}

	public static <F> Predicate<F> filterInt(ToIntFunction<F> intExtractor, IntPredicate condition) {
		return object -> condition.test(intExtractor.applyAsInt(object));
	}

	public static <F> Predicate<F> filterLong(ToLongFunction<F> longExtractor, LongPredicate condition) {
		return object -> condition.test(longExtractor.applyAsLong(object));
	}

	public static <F> Predicate<F> filterDouble(ToDoubleFunction<F> doubleExtractor, DoublePredicate condition) {
		return object -> condition.test(doubleExtractor.applyAsDouble(object));
	}

	public static <T> Stream<T> shuffle(Stream<T> stream) {
		return shuffle(stream, new Random());
	}

	public static <T> Stream<T> shuffle(Stream<T> stream, Random rng) {
		return stream.sorted(Comparator.comparingInt(t -> rng.nextInt()));
	}

//	public static <T> Function<? super Object, Stream<T>> filterCast(Class<T> type) {
//		return object -> type.isInstance(object) ? Stream.of((T)object) : Stream.empty();
//	}
//
//	public static <T> Function<Optional<T>, Stream<T>> keepExisting() {
//		return optional -> optional.map(Stream::of).orElseGet(Stream::empty);
//	}
}
