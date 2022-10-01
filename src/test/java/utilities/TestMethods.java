package utilities;

public class TestMethods {

    public static boolean assertEquals(String expected, String actual) {
        return expected.equals(actual);
    }

    public static boolean assertContains(String expected, String actual) {
        return actual.contains(expected);
    }
}

