package src;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        // Get grid dimensions and difficulty level from the user
        System.out.println("Welcome to Custom Sudoku Generator!");
        int hauteur = 0;
        int largeur = 0;
        int difficulte = 0;

        System.out.print("Enter the block hauteur: ");
        try {
            hauteur = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for hauteur. Please enter a number.");
            return;
        }

        System.out.print("Enter the block largeur: ");
        try {
            largeur = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for largeur. Please enter a number.");
            return;
        }

        System.out.print("Enter the difficulty level (1-5): ");
        try {
            difficulte = Integer.parseInt(scanner.nextLine());
            if (difficulte < 1 || difficulte > 5) {
                System.out.println("Invalid input for difficulty. Please enter a number between 1 and 5.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for difficulty. Please enter a number.");
            return;
        }

        Grille g = new Grille(hauteur, largeur, difficulte, 0, 0);

        // Ensure grid size is at least 4
        if (hauteur * largeur < 4) {
            System.out.println("Invalid grid size! Please ensure hauteur * largeur is at least 4.");
            return;
        }

        // Generate and display one Sudoku puzzle
        int[][] puzzle = g.genererGrille();
        System.out.println("Generated Sudoku Puzzle:");
        printPuzzle(puzzle);
    }

    // Utility method to print the Sudoku puzzle
    private static void printPuzzle(int[][] grid) {
        int hauteur = (int) Math.sqrt(grid.length);
        int largeur = hauteur;

        for (int row = 0; row < grid.length; row++) {
            if (row % hauteur == 0 && row != 0) {
                System.out.println("-".repeat(grid.length * 2 + hauteur - 1));
            }
            for (int col = 0; col < grid[row].length; col++) {
                if (col % largeur == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[row][col] == 0 ? ". " : grid[row][col] + " ");
            }
            System.out.println();
        }

        System.out.print("Do you want to generate another puzzle? (true/false): ");
        boolean answer = Boolean.parseBoolean(scanner.nextLine());
        if (answer) {
            main(new String[0]);
        }
    }
}