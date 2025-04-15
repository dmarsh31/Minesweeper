public class Cell {
    public boolean isBomb,isRevealed;
    public int numberOfNeighboringBombs;

    public Cell(){
        this.isBomb = false;
        this.isRevealed = false;
        this.numberOfNeighboringBombs = 0;
    }

    @Override
    public String toString(){
        if (isBomb) {
            return "X";
        }
        if (!isRevealed) {
            return "_";
        }
        return numberOfNeighboringBombs == 0 ? " " : String.valueOf(numberOfNeighboringBombs);
    }
}
