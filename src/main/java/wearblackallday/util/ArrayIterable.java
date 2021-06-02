package wearblackallday.util;

import wearblackallday.data.ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ArrayIterable<T> extends Iterable<T> {

	T[] toArray();

	@Override
	default Iterator<T> iterator() {
		return ArrayUtils.iterator(this.toArray());
	}

	@Override
	default void forEach(Consumer<? super T> action) {
		for(T t : this.toArray()) {
			action.accept(t);
		}
	}

	@Override
	default Spliterator<T> spliterator() {
		return Arrays.spliterator(this.toArray());
	}

	default Stream<T> stream() {
		return Arrays.stream(this.toArray());
	}

	default boolean anyMatch(Predicate<T> predicate) {
		for(T t : this.toArray()) {
			if(predicate.test(t)) return true;
		}
		return false;
	}

	default boolean allMatch(Predicate<T> predicate) {
		for(T t : this.toArray()) {
			if(!predicate.test(t)) return false;
		}
		return true;
	}

	default boolean noneMatch(Predicate<T> predicate) {
		for(T t : this.toArray()) {
			if(predicate.test(t)) return false;
		}
		return true;
	}
}
