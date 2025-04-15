public class Test {
    public static void main(String[] args) {
        //an X represents a bomb while the 0's represent a "open tile"
        Grid g = new Grid(16, 16);

        g.pickSquare(0, 0);
        System.out.println(g.toString());
    }
}   
