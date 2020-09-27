import data.packing.BooleanPacker;

import java.util.Arrays;

public class TestLongPack {
    public static void main(String[] args) {
        BooleanPacker testPack = new BooleanPacker(5);
        boolean[] testArray = {false, false, true, true, false};
        testPack.fillBooleans(testArray);
//        testPack.setIndex(101, true);
//        testPack.setIndex(4, true);
//        System.out.println(testPack.getIndex(4));
//        System.out.println(testPack.getIndex(4));
        System.out.println(Arrays.toString(testPack.toArray()));
        System.out.println(Arrays.toString(testPack.getStorage()));
    }
}
