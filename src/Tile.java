public class Tile {
    public boolean isBomb,isRevealed;
    public int numberOfNeighboringBombs;

    public Tile(){
        this.isBomb = false;
        this.isRevealed = false;
        this.numberOfNeighboringBombs = 0;
    }

    @Override
    public String toString(){
        if (this.isBomb) {
            return "X";
        }
        return String.valueOf(numberOfNeighboringBombs);
    }
}
