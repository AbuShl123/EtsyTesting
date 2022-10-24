package utilities;

public class TestMethods {
    public static boolean assertEquals(String expected, String actual) {
        return expected.equals(actual);
    }

    public static boolean assertContains(String expected, String actual) {
        return actual.contains(expected);
    }

    public static boolean ignoreStarting(String title, String word) {
        String[] parts = title.substring(0, title.indexOf(" - Etsy")).split(" ");
        int count = 0;
        for (String part : parts) if (word.contains(part)) count++;
        return count > 0;
    }
}

