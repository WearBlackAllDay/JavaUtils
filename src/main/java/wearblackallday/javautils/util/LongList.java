package wearblackallday.javautils.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

public class LongList implements RandomAccess {
	private static final int INITIAL_CAPACITY = 10;
	private static final long[] EMPTY_ARRAY = {};

	private int size;
	private long[] storage;

	public LongList() {
		this.storage = EMPTY_ARRAY;
	}

	public LongList(int capacity) {
		if(capacity <= 0) this.storage = EMPTY_ARRAY;
		else this.storage = new long[capacity];
	}

	public LongList(long[] storage) {
		this(storage, 0, storage.length);
	}

	public LongList(PrimitiveIterator.OfLong iterator) {
		this();
		while(iterator.hasNext())
			this.add(iterator.nextLong());
	}

	public LongList(Iterator<Long> iterator) {
		this();
		iterator.forEachRemaining(this::add);
	}

	public LongList(Collection<Long> longs) {
		longs.forEach(this::add);
	}

	public LongList(LongList otherList) {
		this.storage = Arrays.copyOf(otherList.storage, otherList.size);
		this.size = otherList.size;
	}

	public LongList(long[] storage, int start, int length) {
		this(length);
		System.arraycopy(storage, start, this.storage, 0, length);
		this.size = length;
	}

	public static LongList view(long[] array) {
		LongList list = new LongList();
		list.storage = array;
		list.size = array.length;
		return list;
	}

	public static LongList of(long... longs) {
		return view(longs);
	}

	public static LongList fromStream(LongStream stream) {
		return stream.collect(LongList::new, LongList::add, LongList::addAll);
	}

	public long get(int index) {
		if(index >= this.size) throw new IndexOutOfBoundsException();
		return this.storage[index];
	}

	public int indexOf(long l) {
		for(int i = 0; i < this.size; i++) {
			if(l == this.storage[i]) return i;
		}
		return -1;
	}

	public void add(long l) {
		this.ensureCapacity(this.size + 1);
		this.storage[this.size++] = l;
	}

	public void add(int index, long l) {
		this.ensureCapacity(this.size + 1);
		this.size++;
		if(index != this.size)
			System.arraycopy(this.storage, index, this.storage, index + 1, this.size - index);
		this.storage[index] = l;
	}

	public void addAll(LongList otherList) {
		int otherSize = otherList.size();
		if(otherSize == 0) return;
		this.ensureCapacity(this.size + otherSize);
		System.arraycopy(otherList.storage, 0, this.storage, this.size, otherSize);
		this.size += otherSize;
	}

	public void remove(int index) {
		if(index >= this.size) throw new IndexOutOfBoundsException();
		this.size--;
		if(index != this.size)
			System.arraycopy(this.storage, index + 1, this.storage, index, this.size - index);
	}

	public boolean remove(long l) {
		int index = this.indexOf(l);
		if(index == -1) return false;
		this.remove(index);
		return true;
	}

	public void removeRange(int startInclusive, int endExclusive) {
		if(startInclusive >= endExclusive || startInclusive < 0 || endExclusive >= this.size) throw new IndexOutOfBoundsException();
		int distance = endExclusive - startInclusive;
		if(distance == this.size) {
			this.clear();
			return;
		}
		System.arraycopy(this.storage, endExclusive, this.storage, startInclusive, this.size - endExclusive);
		this.size -= distance;
	}

	public void set(int index, long l) {
		if(index >= this.size) throw new IndexOutOfBoundsException();
		this.storage[index] = l;
	}

	public int size() {
		return this.size;
	}

	public int capacity() {
		return this.storage.length;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void clear() {
		this.size = 0;
	}

	public void ensureCapacity(int capacity) {
		if(capacity <= this.storage.length) return;
		if(this.storage != EMPTY_ARRAY) capacity =
			(int)Math.max(
				Math.min((long)this.storage.length + (this.storage.length >> 1), Integer.MAX_VALUE),
				capacity);
		else capacity = Math.max(capacity, INITIAL_CAPACITY);
		long storage[] = new long[capacity];
		System.arraycopy(this.storage, 0, storage, 0, this.size);
		this.storage = storage;
	}

	public LongList subList(int from, int to) {
		if(from == 0 && to == this.size()) return this;
		return view(Arrays.copyOfRange(this.storage, from, to));
	}

	public void map(LongUnaryOperator mapper) {
		for(int i = 0; i < this.size; i++) {
			this.storage[i] = mapper.applyAsLong(this.storage[i]);
		}
	}

	public void filter(LongPredicate filter) {
		for(int i = 0; i < this.size; i++) {
			if(!filter.test(this.storage[i])) this.remove(i--); //check current index again
		}
	}

	public boolean contains(long l) {
		return this.indexOf(l) != -1;
	}

	public void sort() {
		Arrays.sort(this.storage, 0, this.size);
	}

	public void forEach(LongConsumer action) {
		for(int i = 0; i < this.size; i++) {
			action.accept(this.storage[i]);
		}
	}

	public LongStream stream() {
		return Arrays.stream(this.storage, 0, this.size);
	}

	public long[] storage() {
		return this.storage;
	}

	public long[] toArray() {
		return Arrays.copyOf(this.storage, this.size);
	}

	public int compareTo(LongList otherList) {
		return Arrays.compare(this.storage, otherList.storage);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.storage);
	}

	public boolean equals(LongList otherList) {
		if(otherList.size != this.size) return false;
		if(otherList == this) return true;
		return Arrays.equals(this.storage, 0, this.size,
			otherList.storage, 0, otherList.size);
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(o == null) return false;
		if(o instanceof LongList list) return list.equals(this);
		return false;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",\s", "[", "]");

		for(int i = 0; i < this.size; i++) {
			joiner.add(String.valueOf(this.storage[i]));
		}

		return joiner.toString();
	}
}