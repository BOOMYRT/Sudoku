package src;

import java.util.Random;
import java.util.Scanner;

public class Grille {
    private static final char[] LETTRES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private int taille;
    private float difficulte;
    private int type;
    private int symbole;
    int[][] grid;
    char[][] letterGrid;

    public Grille(int taille, int difficulte, int type, int symbole) {
        this.taille = taille;
        this.difficulte = difficulte;
        this.type = type;
        this.symbole = symbole;
    }

    public int[][] genererGrille() {
        int[][] grid = new int[taille][taille];
        fillDiagonalBoxes(grid);
        solveSudoku(grid, 0, 0);
        removeNumbers(grid);
        return grid;
    }

    private void fillDiagonalBoxes(int[][] grid) {
        int boxSize = (int) Math.sqrt(taille);
        for (int i = 0; i < grid.length; i += boxSize) {
            fillBox(grid, i, i);
        }
    }

    private void fillBox(int[][] grid, int startRow, int startCol) {
        Random rand = new Random();
        int boxSize = (int) Math.sqrt(taille);
        int[] numbers = new int[taille];

        // générer une liste de nombres uniques
        for (int i = 0; i < taille; i++) {
            numbers[i] = i + 1;
        }

        // mélanger les nombres
        for (int i = 0; i < taille; i++) {
            int j = rand.nextInt(taille);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }

        // remplir la box avec les nombres uniques
        int index = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                grid[startRow + i][startCol + j] = numbers[index++];
            }
        }
    }

    public boolean fournirGrille(Scanner scanner) {
        System.out.print("Voulez-vous entrer votre propre Sudoku ? (true/false): ");
        return Boolean.parseBoolean(scanner.nextLine());
    }

    private boolean solveSudoku(int[][] grid, int row, int col) {
        if (row == taille) {
            return true; // sudoku résolu
        }
        if (col == taille) {
            return solveSudoku(grid, row + 1, 0); // passe à la ligne suivante
        }
        if (grid[row][col] != 0) {
            return solveSudoku(grid, row, col + 1); // continue si la case est déjà remplie
        }

        for (int num = 1; num <= taille; num++) {
            if (isSafe(grid, row, col, num)) { // vérifie si le placement est valide
                grid[row][col] = num;

                if (solveSudoku(grid, row, col + 1)) {
                    return true; // retourne vrai si une solution est trouvée
                }

                grid[row][col] = 0; // retour en arrière
            }
        }
        return false; // Pas de solution trouvée
    }

    private void removeNumbers(int[][] grid) {
        Random rand = new Random();
        int cellsToRemove = (int) (grid.length * grid[0].length * difficulte) / 7;

        while (cellsToRemove > 0) {
            int row = rand.nextInt(grid.length);
            int col = rand.nextInt(grid[0].length);
            if (grid[row][col] != 0) {
                int backup = grid[row][col]; // sauvegarde le nombre
                grid[row][col] = 0; // enleve le nombre

                if (!hasUniqueSolution(grid)) { // si grille a plusieurs solutions alors on remet le nombre
                    grid[row][col] = backup;
                } else {
                    cellsToRemove--; // prend en compte que les effacements valides

                }
            }
        }
    }

    private boolean hasUniqueSolution(int[][] grid) {
        int[][] tempGrid = copyGrid(grid); // copie de la grille pour ne pas modifier l'originale
        return countSolutions(tempGrid, 0, 0) == 1; // verfiex si une seule solution existe
    }

    private int countSolutions(int[][] grid, int row, int col) {
        if (row == taille) { // si on est à la fin de la grille
            return 1;
        }

        if (col == taille) { // avancer à la ligne suivante
            return countSolutions(grid, row + 1, 0);
        }

        if (grid[row][col] != 0) { // sauter si la case est déjà remplie
            return countSolutions(grid, row, col + 1);
        }

        int count = 0;
        for (int num = 1; num <= taille; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;
                count += countSolutions(grid, row, col + 1);
                if (count > 1) { // arreter si plus d'une solution est trouvée
                    return count;
                }
                grid[row][col] = 0; // retour en arrière
            }
        }

        return count;
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }

    boolean isSafe(int[][] grid, int row, int col, int num) {
        int boxSize = (int) Math.sqrt(taille);
        return isRowValid(grid, row, num) &&
                isColValid(grid, col, num) &&
                isBoxValid(grid, row - row % boxSize, col - col % boxSize, num);
    }

    private boolean isRowValid(int[][] grid, int row, int num) {
        for (int col = 0; col < taille; col++) {
            if (grid[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isColValid(int[][] grid, int col, int num) {
        for (int row = 0; row < taille; row++) {
            if (grid[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoxValid(int[][] grid, int boxStartRow, int boxStartCol, int num) {
        int boxSize = (int) Math.sqrt(taille);
        for (int row = 0; row < boxSize; row++) {
            for (int col = 0; col < boxSize; col++) {
                if (grid[boxStartRow + row][boxStartCol + col] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void afficherGrilleInt(int[][] grid) {
        int boxSize = (int) Math.sqrt(taille);

        for (int row = 0; row < grid.length; row++) {
            if (row % boxSize == 0 && row != 0) {
                System.out.println("-".repeat(grid.length * 2 + boxSize - 1));
            }
            for (int col = 0; col < grid[row].length; col++) {
                if (col % boxSize == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[row][col] == 0 ? ". " : grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.print("Est-ce que voulez rejouer? (true/false): ");
        boolean answer = Boolean.parseBoolean(new Scanner(System.in).nextLine());
        if (answer) {
            Main.main(new String[0]);
        }
    }

    public void fillGridWithLetters() {
        letterGrid = new char[taille][taille];
        int letterIndex = 0;
        for (int row = 0; row < letterGrid.length; row++) {
            for (int col = 0; col < letterGrid[row].length; col++) {
                letterGrid[row][col] = LETTRES[letterIndex % LETTRES.length];
                letterIndex++;
            }
        }
        afficherGrilleChar(letterGrid);
    }

    public int[][] fillGridWithNumbers(Scanner scanner) {
        grid = new int[taille][taille];
        for (int row = 0; row < letterGrid.length; row++) {
            for (int col = 0; col < letterGrid[row].length; col++) {
                while (true) {
                    System.out
                            .print("Entrez un nombre pour (" + row + ", " + col + ") [" + letterGrid[row][col] + "]: ");
                    try {
                        int number = Integer.parseInt(scanner.nextLine());
                        if (number >= 0 && number <= taille) {
                            grid[row][col] = number;
                            break;
                        } else {
                            System.out.println("Erreur: Entrez un nombre entre 0 et " + taille);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                    }
                }
            }
        }
        return grid;
    }

    public void afficherGrilleChar(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.print("Est-ce que voulez rejouer? (true/false): ");
        boolean answer = Boolean.parseBoolean(new Scanner(System.in).nextLine());
        if (answer) {
            Main.main(new String[0]);
        }
    }

    public int getValeur(int i, int j) {
        return grid[i][j];
    }

    public int getTaille() {
        return taille;
    }

    public void setValeur(int i, int j, int v) {
        grid[i][j] = v;
    }
}
