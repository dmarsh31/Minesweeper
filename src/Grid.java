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
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                returnString.append(grid[i][j].toString() + "\t");
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
    }

    public void pickSquare(int w, int h){
        if (grid[w][h].isBomb) {
            System.out.println("YOU LOOSE");
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


}