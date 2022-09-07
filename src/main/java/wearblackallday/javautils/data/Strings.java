package wearblackallday.javautils.data;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Collection;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Strings {
	private Strings() {}

	private static final Random RANDOM = new Random();

	public static void clipboard(String string) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string), null);
	}

	public static String[] splitLines(String string) {
		return string.trim().split("[\r\n]+");
	}

	public static int countLines(String string) {
		return Strings.splitLines(string).length;
	}

	public static String removeEmptyLines(String string) {
		return string.replaceAll("(?m)^[ \\t]*\\r?\\n", "");
	}

	public static int[] splitToInts(String string) {
		return splitToInts(string, "[\r\n]+");
	}

	public static int[] splitToInts(String string, String regex) {
		String[] lines = string.split(regex);
		int[] ints = new int[lines.length];
		for(int i = 0; i < ints.length; i++) {
			ints[i] = Integer.parseInt(lines[i]);
		}
		return ints;
	}

	public static long[] splitToLongs(String string) {
		return splitToLongs(string, "[\r\n]+");
	}

	public static long[] splitToLongs(String string, String regex) {
		String[] lines = string.split(regex);
		long[] longs = new long[lines.length];
		for(int i = 0; i < longs.length; i++) {
			longs[i] = Long.parseLong(lines[i]);
		}
		return longs;
	}

	public static String getFirst(String string, int chars) {
		return string.substring(0, Math.min(string.length(), chars));
	}

	public static String chopFirst(String string, int chars) {
		return string.replaceFirst(getFirst(string, chars), "");
	}

	public static String trimLeadingChars(String str, char c) {
		int i = 0;
		while(str.charAt(i) == c) {
			if(i++ == str.length() - 1) return "";
		}
		return str.substring(i);
	}

	public static String padLeft(String string, int length, char pad) {
		return String.format("%" + length + "s", string).replace(' ', pad);
	}

	public static String padRight(String string, int length, char pad) {
		return String.format("%-" + length + "s", string).replace(' ', pad);
	}

	public static String randomAlphabetic(int length) {
		return RANDOM.ints(97, 123)
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	public static String randomAlphanumeric(int length) {
		return RANDOM.ints(48, 123)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	public static <T> void splitAndRun(String text, String regex, Function<String, T> mapper, Consumer<T> action) {
		for(String s : text.split(regex)) {
			action.accept(mapper.apply(s));
		}
	}

	public static <T, C extends Collection<T>> C splitAndAdd(String text, String regex, Function<String, T> mapper, C collection) {
		splitAndRun(text, regex, mapper, collection::add);
		return collection;
	}
}
