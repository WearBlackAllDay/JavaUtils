package wearblackallday.util;

import java.util.function.Function;

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

//	public static <T> Function<? super Object, Stream<T>> filterCast(Class<T> type) {
//		return object -> type.isInstance(object) ? Stream.of((T)object) : Stream.empty();
//	}
//
//	public static <T> Function<Optional<T>, Stream<T>> keepExisting() {
//		return optional -> optional.map(Stream::of).orElseGet(Stream::empty);
//	}
}
