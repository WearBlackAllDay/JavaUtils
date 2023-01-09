package wearblackallday.javautils.io;

import java.io.*;

public class ByteSlice implements Closeable {

	protected final InputStream in;
	protected final OutputStream out;

	public ByteSlice(InputStream in) {
		this(in, null);
	}

	public ByteSlice(OutputStream out) {
		this(null, out);
	}

	public ByteSlice(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
	}

	public static ByteSlice fromResource(String resource) {
		return new ByteSlice(ByteSlice.class.getResourceAsStream(resource));
	}

	public static ByteSlice fromInputFile(String file) throws FileNotFoundException {
		return new ByteSlice(new FileInputStream(file));
	}

	public static ByteSlice withOutputFile(String file) throws FileNotFoundException {
		return new ByteSlice(new FileOutputStream(file));
	}

	public int available() throws IOException {
		return this.in.available();
	}

	public int read() throws IOException {
		return this.in.read();
	}

	public ByteSlice write(int u8) throws IOException {
		this.out.write(u8);
		return this;
	}

	public boolean readBoolean() throws IOException {
		return this.read() != 0;
	}

	public ByteSlice writeBoolean(boolean bool) throws IOException {
		this.out.write(bool ? 1 : 0);
		return this;
	}

	public int readUnsignedShort() throws IOException {
		return this.in.read() << 8 | this.in.read();
	}

	public short readShort() throws IOException {
		return (short)this.readUnsignedShort();
	}

	public ByteSlice writeShort(short i16) throws IOException {
		this.out.write((byte)(i16 >>> 8));
		this.out.write((byte)i16);
		return this;
	}

	public int readInt() throws IOException {
		return this.read() << 24 | this.read() << 16 | this.read() << 8 | this.read();
	}

	public ByteSlice writeInt(int i32) throws IOException {
		this.out.write((byte)(i32 >>> 24));
		this.out.write((byte)(i32 >>> 16));
		this.out.write((byte)(i32 >>> 8));
		this.out.write((byte)i32);
		return this;
	}

	public long readUnsigned48bits() throws IOException {
		return (long)this.read() << 40
			 | (long)this.read() << 32
			 | (long)this.read() << 24
			 | (long)this.read() << 16
			 | (long)this.read() << 8
			 | this.read();
	}

	public ByteSlice write48Bits(long u48) throws IOException {
		this.out.write((byte)(u48 >>> 40));
		this.out.write((byte)(u48 >>> 32));
		this.out.write((byte)(u48 >>> 24));
		this.out.write((byte)(u48 >>> 16));
		this.out.write((byte)(u48 >>> 8));
		this.out.write((byte)u48);
		return this;
	}

	public long readLong() throws IOException {
		return (long)this.read() << 56
			 | (long)this.read() << 48
			 | (long)this.read() << 40
			 | (long)this.read() << 32
			 | (long)this.read() << 24
			 | (long)this.read() << 16
			 | (long)this.read() << 8
			 | this.read();
	}

	public ByteSlice writeLong(long i64) throws IOException {
		this.out.write((byte)(i64 >>> 56));
		this.out.write((byte)(i64 >>> 48));
		this.out.write((byte)(i64 >>> 40));
		this.out.write((byte)(i64 >>> 32));
		this.out.write((byte)(i64 >>> 24));
		this.out.write((byte)(i64 >>> 16));
		this.out.write((byte)(i64 >>> 8));
		this.out.write((byte)i64);
		return this;
	}

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(this.readInt());
	}

	public ByteSlice writeFloat(float f32) throws IOException {
		return this.writeInt(Float.floatToIntBits(f32));
	}

	public double readDouble() throws IOException {
		return Double.longBitsToDouble(this.readLong());
	}

	public ByteSlice writeDouble(double f64) throws IOException {
		return this.writeLong(Double.doubleToLongBits(f64));
	}

	public char readChar(Encoding encoding) throws IOException {
		return switch(encoding) {
			case ASCII -> (char)this.read();
			case UTF_16 -> (char)this.readUnsignedShort();
		};
	}

	public ByteSlice writeChar(char c, Encoding encoding) throws IOException {
		return switch(encoding) {
			case ASCII -> this.write((byte)c);
			case UTF_16 -> this.writeShort((short)c);
		};
	}

	public String readString(Encoding encoding) throws IOException {
		char[] str = new char[this.readUnsignedShort()];

		for(int i = 0; i < str.length; i++) {
			str[i] = this.readChar(encoding);
		}

		return new String(str);
	}

	public ByteSlice writeString(String str, Encoding encoding) throws IOException {
		this.writeShort((short)str.length());

		for(int i = 0; i < str.length(); i++) {
			this.writeChar(str.charAt(i), encoding);
		}

		return this;
	}

	@Override
	public void close() throws IOException {
		if(this.in != null) this.in.close();
		if(this.out != null) this.out.close();
	}

	public enum Encoding {
		ASCII, UTF_16
	}
}
