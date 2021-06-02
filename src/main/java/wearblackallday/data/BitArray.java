package wearblackallday.data;

import java.util.Arrays;

public class BitArray {
	public final int length;
	private final long[] storage;

	public BitArray() {
		this(64);
	}

	public BitArray(int length) {
		this.length = length;
		this.storage = new long[(length - 1 >>> 6) + 1 & (1 << 26) - 1];
	}

	public BitArray(boolean... booleans) {
		this(booleans.length);
		for(int i = 0; i < booleans.length; i++) {
			this.set(i, booleans[i]);
		}
	}

	public BitArray(String binary) {
		this(binary.length());
		int i = 0;
		for(char c : binary.toCharArray()) {
			this.set(i++, c == '1');
		}
	}

	public boolean get(int index) {
		return (this.storage[index >> 6] >> index % 64 & 1) != 0;
	}

	public void set(int index, boolean state) {
		this.storage[index >> 6] = this.storage[index >> 6] & ~(1L << (index &= 63)) | (state ? 1L : 0L) << index;
	}

	public void flip(int index) {
		this.storage[index >> 6] ^= 1L << index % 64;
	}

	public BitArray andThis(BitArray otherPacker) {
		this.compareLength(otherPacker);
		for(int i = 0; i < this.storage.length; i++) {
			this.storage[i] &= otherPacker.storage[i];
		}
		return this;
	}

	public BitArray orThis(BitArray otherPacker) {
		this.compareLength(otherPacker);
		for(int i = 0; i < this.storage.length; i++) {
			this.storage[i] |= otherPacker.storage[i];
		}
		return this;
	}

	public BitArray xorThis(BitArray otherPacker) {
		this.compareLength(otherPacker);
		for(int i = 0; i < this.storage.length; i++) {
			this.storage[i] ^= otherPacker.storage[i];
		}
		return this;
	}

	public BitArray andNew(BitArray otherPacker) {
		return this.copy().andThis(otherPacker);
	}

	public BitArray orNew(BitArray otherPacker) {
		return this.copy().orThis(otherPacker);
	}

	public BitArray xorNew(BitArray otherPacker) {
		return this.copy().xorThis(otherPacker);
	}

	private void compareLength(BitArray otherPacker) {
		if(this.length != otherPacker.length) throw new IllegalArgumentException("mismatch in length");
	}

	public BitArray copy() {
		BitArray copy = new BitArray(this.length);
		System.arraycopy(this.storage, 0, copy.storage, 0, this.storage.length);
		return copy;
	}

	public boolean[] toArray() {
		boolean[] array = new boolean[this.length];
		for(int i = 0; i < array.length; i++) {
			array[i] = this.get(i);
		}
		return array;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof BitArray bp)) return false;

		return this.length == bp.length && Arrays.equals(this.storage, bp.storage);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < this.length; i++) {
			stringBuilder.append(this.get(i) ? "1" : "0");
		}
		return stringBuilder.toString();
	}
}
