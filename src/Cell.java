public class Cell {
    public boolean isMine,isRevealed, isFlagged;
    public int numberOfNeighboringMines;

    public Cell(){
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.numberOfNeighboringMines = 0;
    }

    @Override
    public String toString(){
        if (!isRevealed) {
            return isFlagged ? "F" : "_";
        }
        if (isMine) { 
            return "X";
        }
        return numberOfNeighboringMines == 0 ? " " : String.valueOf(numberOfNeighboringMines);
    }
}
