package wearblackallday.util;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Functions {

	public static <T> Function<? super Object, T> cast(Class<T> type) {
		return object -> (T)object;
	}

//	public static <T> Function<? super Object, Stream<T>> filterCast(Class<T> type) {
//		return object -> type.isInstance(object) ? Stream.of((T)object) : Stream.empty();
//	}
//
//	public static <T> Function<Optional<T>, Stream<T>> keepExisting() {
//		return optional -> optional.map(Stream::of).orElseGet(Stream::empty);
//	}
}
