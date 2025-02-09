package src;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue au jeu Sudoku!");

        int taille = 0;
        int difficulte = 0;

        System.out.print("Enter the size of the Sudoku (e.g., 9 for a 9x9 grid): ");
        taille = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the difficulty level (1-5): ");
        difficulte = Integer.parseInt(scanner.nextLine());

        if (difficulte < 1 || difficulte > 5) {
            System.out.println("Invalid input for difficulty. Please enter a number between 1 and 5.");
            return;
        }

        Grille g = new Grille(taille, difficulte, 0, 0);

        int[][] puzzle = g.genererGrille();
        System.out.println("Generated Sudoku Puzzle:");
        printPuzzle(puzzle);

        Solveur solveur = new Solveur(g);
        if (solveur.resoudre()) {
            System.out.println("Solution trouvée :");
            printPuzzle(g.getGrid());
        } else {
            System.out.println("Pas de solution trouvée.");
        }
    }

    public static void printPuzzle(int[][] grid) {
        int sqrt = (int) Math.sqrt(grid.length);
        for (int row = 0; row < grid.length; row++) {
            if (row % sqrt == 0 && row != 0) {
                System.out.println("-".repeat(grid.length * 2 + sqrt - 1));
            }
            for (int col = 0; col < grid[row].length; col++) {
                if (col % sqrt == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[row][col] == 0 ? ". " : grid[row][col] + " ");
            }
            System.out.println();
        }
    }
}
