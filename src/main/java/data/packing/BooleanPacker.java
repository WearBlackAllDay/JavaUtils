package data.packing;

import data.Strings;

public class BooleanPacker {
    private final int capacity;
    private final long[] storage;

    public BooleanPacker() {
        this(64);
    }

    public BooleanPacker(int capacity) {
        this.capacity = capacity;
        this.storage = new long[this.setStorageLength(this.capacity)];
    }

    public BooleanPacker(boolean[] booleans) {
        this.capacity = booleans.length;
        this.storage = new long[this.setStorageLength(this.capacity)];
        this.fillBooleans(booleans);
    }

    public BooleanPacker(String binary) {
        this.capacity = this.splitBinary(binary).length;
        this.storage = new long[this.setStorageLength(this.capacity)];
        this.fillString(binary);
    }

    public boolean getIndex(int index) {
        this.verifyRange(index);
        return ((this.storage[index / 64]>>(index % 64)) & 1) != 0;
    }

    public void setIndex(int index, boolean state) {
        this.verifyRange(index);
        if (this.getIndex(index) && !state) {
            this.storage[index / 64] &= ~ 1 << index % 64;
        } else if (!this.getIndex(index) && state){
            this.storage[index / 64] |= 1 << index % 64;
        }
    }

    public void flipIndex(int index) {
        this.verifyRange(index);
        this.storage[index / 64] ^= 1 << index % 64;
    }

    public void fillBooleans(boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            this.setIndex(i, booleans[i]);
        }
    }

    public void fillString(String binary) {
        int[] array = this.splitBinary(binary);
        for (int i = 0; i < array.length; i++) {
            this.setIndex(i, array[i] == '1');
        }
    }

    private int setStorageLength(int capacity) {
        return capacity / 64 + capacity % 64 != 0 ? 1 : 0;
    }

    private void verifyRange(int index) {
        if (index >= this.capacity || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
    }

    private int[] splitBinary(String binary) {
        return binary.replaceAll("[^0-1]","").chars().toArray();
    }

    public boolean[] toArray() {
        boolean[] array = new boolean[this.capacity];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.getIndex(i);
        }
        return array;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < this.capacity; i++) {
            string = string.concat(this.getIndex(i) ? "1" : "0");
        }
        return string;
    }

    public long[] getStorage() {
        return this.storage;
    }
}
