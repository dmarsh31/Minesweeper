import java.util.Random;

public class Grid {
    private int width,height;
    private Cell[][] grid;
    private boolean gameStarted;

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        this.gameStarted = false;

        //fills grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = new Cell();
            }
        }
    }


    //this toString is necessary but just to visualize the board
    @Override
    public String toString(){
        StringBuilder returnString = new StringBuilder();
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                returnString.append(grid[i][j].toString()).append("\t");
            }
            returnString.append("\n");
        }
        return returnString.toString();
    }


    //fills the grid with mines, this method should be called after the first click to ensure the user doesn't immediately lose
    private void fillMines(int initialX, int initialY){
        //40 mines is the typical number of mines for normal mode on a 16x16 grid
        //but this might need to be increased
        int numberOfMines = 40;
        int tempWidth, tempHeight;
        
        Random r = new Random();

        while (numberOfMines != 0) {
            tempWidth = r.nextInt(width);
            tempHeight = r.nextInt(height);

            //prevents placing mine on the first click
            if ((tempWidth == initialX && tempHeight == initialY) || grid[tempWidth][tempHeight].isMine) {
                continue;
            }
            grid[tempWidth][tempHeight].isMine = true;
            numberOfMines--;
        }
        calculateNeighborMineCounts();
    }

    //checks how many mines surround the cell
    //nested loop checks through all 8 neighbors and the cell itself
    private void calculateNeighborMineCounts() {
            for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx;
                        int ny = j + dy;
                        if (inBounds(nx, ny) && grid[nx][ny].isMine) {
                            count++;
                        }
                    }
                }
                grid[i][j].numberOfNeighboringMines = count;
            }
        }
    }
    
        
    //mines will never spawn on the first click
    public void pickSquare(int w, int h) {
    	//prevents flag cells from getting revealed, player has to unflag first to reveal cell
    	if (!inBounds(w, h)) {
            System.out.println("Invalid selection.");
            return;
        }
        pickCell(w, h);
    }

    public void pickCell(int w, int h){
        if (grid[w][h].isMine) {
            System.out.println("YOU LOSE");
            System.exit(1);
        }
        //fills bombs after game has started to ensure first click isn't a bomb
        if (!gameStarted) {
            grid[w][h].isRevealed = true;
            this.fillMines(w, h);
        }
        checkCell(w, h);
    }

    //recursively checks neighboring tiles, setting their numberOfNeighboringBombs field and isRevealed field
    private void checkCell(int w, int h){
        grid[w][h].isRevealed = true;

        //checks all adjacent squares
        for (int i = w-1; i < w+2; i++) {
            for (int j = h-1; j < h+2; j++) {
                if (inBounds(i, j) && !grid[i][j].isRevealed) {
                    if (grid[i][j].isMine) {
                        grid[w][h].numberOfNeighboringMines++;
                    }else checkCell(i, j);
                }
            }
        }
    }

    //returns weather the position is in bounds of the grid
    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    
    //flags cells that are potentially mines
    public void FlagCell(int x, int y) {
        if (!inBounds(x, y)) {
            System.out.println("Invalid coordinates.");
            return;
        }

        Cell cell = grid[x][y];

        if (cell.isRevealed) {
            System.out.println("Can't flag a revealed cell.");
            return;
        }

        cell.isFlagged = !cell.isFlagged;
    }
    

    private void revealAllMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j].isMine) {
                    grid[i][j].isRevealed = true;
                }
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!grid[i][j].isMine && !grid[i][j].isRevealed) {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean hasWon() {
        return false;
    }
}
