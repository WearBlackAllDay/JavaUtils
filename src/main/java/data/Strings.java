package data;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Strings {

    public static void clipboard(String input) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(input),null);
    }

    public static String[] splitLines(String input) {
        return input.replaceAll(" ", "").split("[\\r\\n]+");
    }

    public static int[] splitToInts(String input) {
        return Arrays.stream(Strings.splitLines(input)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] splitToInts(String input, String regex) {
        return Arrays.stream(input.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] splitToLongs(String input) {
        return Arrays.stream(Strings.splitLines(input)).mapToLong(Long::parseLong).toArray();
    }

    public static long[] splitToLongs(String input, String regex) {
        return Arrays.stream(input.split(regex)).mapToLong(Long::parseLong).toArray();
    }

    public static int[] readIntArray(String input) {
        return Stream.of(input.replaceAll("[\\[\\]]", "").split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] readLongArray(String input) {
        return Stream.of(input.replaceAll("[\\[\\]]", "").split(",")).mapToLong(Long::parseLong).toArray();
    }

    public static int countLines(String input) {
        return Strings.splitLines(input).length;
    }

    public static String getFirst(String input, int chars) {
        return input.substring(0, Math.min(input.length(), chars));
    }

    public static String chopFirst(String input, int chars) {
        return input.replaceFirst(Strings.getFirst(input, chars), "");
    }

    public static <T> void splitAndRun(String text, String regex, Function<String, T> mapper, Consumer<T> action) {
        for(String s: text.split(regex)) {
            action.accept(mapper.apply(s));
        }
    }

    public static <T, C extends Collection<T>> C splitAndAdd(String text, String regex, Function<String, T> mapper, C collection) {
        Strings.splitAndRun(text, regex, mapper, collection::add);
        return collection;
    }
}
