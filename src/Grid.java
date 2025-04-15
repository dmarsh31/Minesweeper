import java.util.Random;

public class Grid {
    private int width,height;
    private Tile[][] grid;
    private boolean gameStarted;

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];
        this.gameStarted = false;

        //fills grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = new Tile();
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


    //fills the grid with bombs, this method should be called after the first click to ensure the user doesn't immediately loose
    private void fillBombs(){
        //40 bombs is the typical number of bombs for normal mode on a 16x16 grid
        //but this might need to be increased
        int numberOfBombs = 40;
        int tempWidth, tempHeight;
        
        Random r = new Random();

        while (numberOfBombs != 0) {
            tempWidth = r.nextInt(width);
            tempHeight = r.nextInt(height);

            if (!grid[tempWidth][tempHeight].isBomb && !grid[tempWidth][tempHeight].isRevealed) {
                grid[tempWidth][tempHeight].isBomb = true;
                numberOfBombs--;
            }
        }
        calculateNeighborBombCounts();
    }

    //checking the neighboring tiles how many bombs are in the area
    private void calculateNeighborBombCounts() {
             for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx;
                        int ny = j + dy;
                        if (inBounds(nx, ny) && grid[nx][ny].isBomb) {
                            count++;
                        }
                    }
                }
                grid[i][j].numberOfNeighboringBombs = count;
            }
        }
    }
        

    public void pickSquare(int w, int h){
        if (grid[w][h].isBomb) {
            System.out.println("YOU LOSE");
            System.exit(1);
        }
        if (!gameStarted) {
            grid[w][h].isRevealed = true;
            this.fillBombs();
        }
        checkTile(w, h);
    }

    //recursively checks neighboring tiles, setting their numberOfNeighboringBombs field and isRevealed field
    private void checkTile(int w, int h){
        grid[w][h].isRevealed = true;

        //checks all adjacent squares
        for (int i = w-1; i < w+2; i++) {
            for (int j = h-1; j < h+2; j++) {
                if (inBounds(i, j) && !grid[i][j].isRevealed) {
                    if (grid[i][j].isBomb) {
                        grid[w][h].numberOfNeighboringBombs++;
                    }else checkTile(i, j);
                }
            }
        }
    }

    //returns weather the position is in bounds of the grid
    private boolean inBounds(int width, int height){
        return width >= 0 && width < this.width && height >= 0 && height < this.height;
    }


    //shows all the bombs on the board if the player loses
    private void revealAllBombs() {
        for (int i = 0; i < width; i++) 
            for (int j = 0; j < height; j++) 
                if (grid[i][j].isBomb) 
                    grid[i][j].isRevealed = true;
    }


    //checks if the user has won the game, if there's still a bomb hidden, the game isn't done
    private boolean checkWin(){
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (grid[i][j].isBomb && grid[i][j].isRevealed
                    return false;
        return true;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public boolean hasWon() {
        return hasWon;


}
