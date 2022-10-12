public class test {
    public static void main(String[] args) {
        String s = "1,";
        System.out.println(s.split(",").length);
        for(int i=0; i<s.split(",").length; i++) {
            System.out.println(s.split(",")[i]);
        }
    }
}
