public class Interview {

    public Interview(int m) {

    }

    public Interview() {
        this(23);
    }



    public static void main(String... $$$) {

        System.out.println();

        String b = "qwe";
        int num = Integer.parseInt(b);
    }

    public static void consecutive(int n) {
        for (int i = 0; i < n; i++) {
            String res = "";
            if (i % 2 == 0)
                res += "Codility";
            if (i%3 == 0)
                res += "Test";
            if (i%5 == 0)
                res += "Coders";
            System.out.println(res.length() > 0 ? res : "invalid number");
        }
    }
}

