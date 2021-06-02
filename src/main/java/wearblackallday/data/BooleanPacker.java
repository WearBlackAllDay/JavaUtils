package wearblackallday.data;

import java.util.Arrays;

public class BooleanPacker {
	public final int length;
	private final long[] storage;

	public BooleanPacker() {
		this(64);
	}

	public BooleanPacker(int length) {
		this.length = length;
		this.storage = new long[(this.length - 1 >>> 6) + 1 & (1 << 26) - 1];
	}

	public BooleanPacker(boolean... booleans) {
		this(booleans.length);
		for(int i = 0; i < booleans.length; i++) {
			this.setIndex(i, booleans[i]);
		}
	}

	public BooleanPacker(String binary) {
		this(binary.length());
		int i = 0;
		for(char c : binary.toCharArray()) {
			this.setIndex(i++, c == '1');
		}
	}

	public boolean getIndex(int index) {
		return (this.storage[index >> 6] >> index % 64 & 1) != 0;
	}

	public void setIndex(int index, boolean state) {
		this.storage[index >> 6] = this.storage[index >> 6] & ~(1L << (index &= 63)) | (state ? 1L : 0L) << index;
	}

	public void flipIndex(int index) {
		this.storage[index >> 6] ^= 1L << index % 64;
	}

	public boolean[] toArray() {
		boolean[] array = new boolean[this.length];
		for(int i = 0; i < array.length; i++) {
			array[i] = this.getIndex(i);
		}
		return array;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof BooleanPacker)) return false;

		return Arrays.equals(this.storage, ((BooleanPacker)o).storage);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < this.length; i++) {
			stringBuilder.append(this.getIndex(i) ? "1" : "0");
		}
		return stringBuilder.toString();
	}
}
