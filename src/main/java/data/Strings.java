package data;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.stream.Stream;

public class Strings {

    public static void clipboard(String input) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(input),null);
    }

    public static String[] splitLines(String input) {
        return input.replaceAll(" ", "").split("[\\r\\n]+");
    }

    public static int[] splitToInts(String input) {
        return Arrays.stream(splitLines(input)).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] splitToInts(String input, String regex) {
        return Arrays.stream(input.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] splitToLongs(String input) {
        return Arrays.stream(splitLines(input)).mapToLong(Long::parseLong).toArray();
    }

    public static long[] splitToLongs(String input, String regex) {
        return Arrays.stream(input.split(regex)).mapToLong(Long::parseLong).toArray();
    }

    public static int[] readIntArray(String input) {
        return Stream.of(input.replaceAll("[\\[\\]\\ ]", "").split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] readLongArray(String input) {
        return Stream.of(input.replaceAll("[\\[\\]\\ ]", "").split(",")).mapToLong(Long::parseLong).toArray();
    }

    public static int countLines(String input) {
        return splitLines(input).length;
    }
}
