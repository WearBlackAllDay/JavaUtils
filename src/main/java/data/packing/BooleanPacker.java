package data.packing;

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

    public BooleanPacker(boolean... booleans) {
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
        return ((this.storage[index >> 6] >> (index % 64)) & 1) != 0;
    }

    public void setIndex(int index, boolean state) {
        this.verifyRange(index);
        this.storage[index >> 6] = this.storage[index >> 6] & ~(1L << (index &= 63)) | (state ? 1L : 0L) << index;
    }

    public void flipIndex(int index) {
        this.verifyRange(index);
        this.storage[index >> 6] ^= 1 << index % 64;
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
        return (((capacity - 1) >>> 6) + 1) & ((1 << 26) - 1);
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

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < this.capacity; i++) string += (this.getIndex(i) ? "1" : "0");
        return string;
    }

    public long[] getStorage() {
        return this.storage;
    }
}
