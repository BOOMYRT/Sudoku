package src;

import java.util.Random;

public class Grille {
    private int hauteur;
    private int largeur;
    private float difficulte;
    private int type;
    private int symbole;

    public Grille(int hauteur, int largeur, int difficulte, int type, int symbole) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.difficulte = difficulte;
        this.type = type;
        this.symbole = symbole;
    }

    public int[][] genererGrille() {
        int gridSize = hauteur * largeur; // Total grid size (e.g., 9x9 for 3x3 blocks)
        int[][] grid = new int[gridSize][gridSize];
        fillDiagonalBoxes(grid);
        solveSudoku(grid);
        removeNumbers(grid);
        return grid;
    }

    private void fillDiagonalBoxes(int[][] grid) {
        int boxSize = hauteur; // Hauteur of a single block
        for (int i = 0; i < grid.length; i += boxSize) {
            fillBox(grid, i, i);
        }
    }

    private void fillBox(int[][] grid, int startRow, int startCol) {
        Random rand = new Random();
        boolean[] used = new boolean[grid.length + 1];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int num;
                do {
                    num = rand.nextInt(grid.length) + 1;
                } while (used[num]);
                used[num] = true;
                grid[startRow + i][startCol + j] = num;
            }
        }
    }

    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= grid.length; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;

                            if (solveSudoku(grid)) {
                                return true;
                            }

                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeNumbers(int[][] grid) {
        Random rand = new Random();
        //change this into a percentage so the difficulty is more adjustable and works better
        if (difficulte == 1){
            difficulte = 10;
        }

        int cellsToRemove = (int)(grid.length * grid[0].length * difficulte) / 7; // Adjust the number of cells to remove based on difficulty

        while (cellsToRemove > 0) {
            int row = rand.nextInt(grid.length);
            int col = rand.nextInt(grid[0].length);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        return !isInRow(grid, row, num) && !isInCol(grid, col, num) && !isInBox(grid, row, col, num);
    }

    private boolean isInRow(int[][] grid, int row, int num) {
        for (int col = 0; col < grid[row].length; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int[][] grid, int col, int num) {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int[][] grid, int row, int col, int num) {
        int startRow = row - row % hauteur;
        int startCol = col - col % largeur;
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}