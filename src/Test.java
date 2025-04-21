public class Test {
    public static void main(String[] args) {
        //an X represents a mine while the 0's represent a "open cell"
        Grid g = new Grid(16, 16);

        g.pickSquare(0, 0);
        System.out.println(g.toString());
    }
}   
