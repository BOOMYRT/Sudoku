package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Grille {
    private static final char[] LETTRES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final String[] EMOJIS = {".", "☺", "☻", "♥", "♦", "♣", "♠", "•", "◘", "○", "◙", "♂", "♀", "♪", "♫", "☼", "►"};
    private int taille;
    private float difficulte;
    private int type;
    private int symbole;
    int[][] grid;
    char[][] letterGrid;
    Map<Integer, Character> numberToLetterMap;

    public Grille(int taille, int difficulte, int type, int symbole) {
        this.taille = taille;
        this.difficulte = difficulte;
        this.type = type;
        this.symbole = symbole;

        this.numberToLetterMap = new HashMap<>();
        initializeLetterMapping();
    }

    /**
     * Initialise la correspondance des nombres aux lettres.
     */
    private void initializeLetterMapping() {
        for (int i = 1; i <= taille; i++) {
            numberToLetterMap.put(i, LETTRES[i - 1]);
        }
    }

    /**
     * Génère une grille de Sudoku.
     *
     * @return la grille de Sudoku générée.
     */
    public int[][] genererGrille() {
        int[][] grid = new int[taille][taille];
        fillDiagonalBoxes(grid);
        solveSudoku(grid, 0, 0);
        removeNumbers(grid);
        return grid;
    }

    /**
     * Remplit les boîtes diagonales de la grille.
     *
     * @param grid la grille à remplir.
     */
    private void fillDiagonalBoxes(int[][] grid) {
        int boxSize = (int) Math.sqrt(taille);
        for (int i = 0; i < grid.length; i += boxSize) {
            fillBox(grid, i, i);
        }
    }

    /**
     * Remplit une boîte de la grille avec des nombres uniques.
     *
     * @param grid     la grille à remplir.
     * @param startRow la ligne de départ de la boîte.
     * @param startCol la colonne de départ de la boîte.
     */
    private void fillBox(int[][] grid, int startRow, int startCol) {
        Random rand = new Random();
        int boxSize = (int) Math.sqrt(taille);
        int[] numbers = new int[taille];

        // Génère une liste de nombres uniques
        for (int i = 0; i < taille; i++) {
            numbers[i] = i + 1;
        }

        // Mélange les nombres
        for (int i = 0; i < taille; i++) {
            int j = rand.nextInt(taille);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }

        // Remplit la boîte avec les nombres uniques
        int index = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                grid[startRow + i][startCol + j] = numbers[index++];
            }
        }
    }

    /**
     * Résout le Sudoku en utilisant la méthode de retour sur trace.
     *
     * @param grid la grille à résoudre.
     * @param row  la ligne actuelle.
     * @param col  la colonne actuelle.
     * @return true si le Sudoku est résolu, false sinon.
     */
    private boolean solveSudoku(int[][] grid, int row, int col) {
        if (row == taille) {
            return true; // Sudoku résolu
        }
        if (col == taille) {
            return solveSudoku(grid, row + 1, 0); // Passe à la ligne suivante
        }
        if (grid[row][col] != 0) {
            return solveSudoku(grid, row, col + 1); // Continue si la case est déjà remplie
        }

        for (int num = 1; num <= taille; num++) {
            if (isSafe(grid, row, col, num)) { // Vérifie si le placement est valide
                grid[row][col] = num;

                if (solveSudoku(grid, row, col + 1)) {
                    return true; // Retourne vrai si une solution est trouvée
                }

                grid[row][col] = 0; // Retour en arrière
            }
        }
        return false; // Pas de solution trouvée
    }

    /**
     * Supprime des nombres de la grille pour créer le puzzle.
     *
     * @param grid la grille à modifier.
     */
    private void removeNumbers(int[][] grid) {
        Random rand = new Random();
        int cellsToRemove = (int) (grid.length * grid[0].length * difficulte) / 7;

        while (cellsToRemove > 0) {
            int row = rand.nextInt(grid.length);
            int col = rand.nextInt(grid[0].length);
            if (grid[row][col] != 0) {
                int backup = grid[row][col]; // Sauvegarde le nombre
                grid[row][col] = 0; // Enlève le nombre

                if (!hasUniqueSolution(grid)) { // Si la grille a plusieurs solutions alors on remet le nombre
                    grid[row][col] = backup;
                } else {
                    cellsToRemove--; // Prend en compte que les effacements valides
                }
            }
        }
    }

    /**
     * Vérifie si la grille a une solution unique.
     *
     * @param grid la grille à vérifier.
     * @return true si la grille a une solution unique, false sinon.
     */
    private boolean hasUniqueSolution(int[][] grid) {
        int[][] tempGrid = copyGrid(grid); // Copie de la grille pour ne pas modifier l'originale
        return countSolutions(tempGrid, 0, 0) == 1; // Vérifie si une seule solution existe
    }

    /**
     * Compte le nombre de solutions possibles pour la grille.
     *
     * @param grid la grille à vérifier.
     * @param row  la ligne actuelle.
     * @param col  la colonne actuelle.
     * @return le nombre de solutions possibles.
     */
    private int countSolutions(int[][] grid, int row, int col) {
        if (row == taille) { // Si on est à la fin de la grille
            return 1;
        }

        if (col == taille) { // Avancer à la ligne suivante
            return countSolutions(grid, row + 1, 0);
        }

        if (grid[row][col] != 0) { // Sauter si la case est déjà remplie
            return countSolutions(grid, row, col + 1);
        }

        int count = 0;
        for (int num = 1; num <= taille; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;
                count += countSolutions(grid, row, col + 1);
                if (count > 1) { // Arrêter si plus d'une solution est trouvée
                    return count;
                }
                grid[row][col] = 0; // Retour en arrière
            }
        }

        return count;
    }

    /**
     * Copie la grille pour éviter de modifier l'originale.
     *
     * @param grid la grille à copier.
     * @return une copie de la grille.
     */
    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }

    /**
     * Vérifie si un nombre peut être placé en toute sécurité dans une case.
     *
     * @param grid la grille à vérifier.
     * @param row  la ligne de la case.
     * @param col  la colonne de la case.
     * @param num  le nombre à placer.
     * @return true si le nombre peut être placé en toute sécurité, false sinon.
     */
    boolean isSafe(int[][] grid, int row, int col, int num) {
        int boxSize = (int) Math.sqrt(taille);
        return isRowValid(grid, row, num) &&
                isColValid(grid, col, num) &&
                isBoxValid(grid, row - row % boxSize, col - col % boxSize, num);
    }

    /**
     * Vérifie si un nombre est valide dans une ligne.
     *
     * @param grid la grille à vérifier.
     * @param row  la ligne à vérifier.
     * @param num  le nombre à vérifier.
     * @return true si le nombre est valide, false sinon.
     */
    private boolean isRowValid(int[][] grid, int row, int num) {
        for (int col = 0; col < taille; col++) {
            if (grid[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un nombre est valide dans une colonne.
     *
     * @param grid la grille à vérifier.
     * @param col  la colonne à vérifier.
     * @param num  le nombre à vérifier.
     * @return true si le nombre est valide, false sinon.
     */
    private boolean isColValid(int[][] grid, int col, int num) {
        for (int row = 0; row < taille; row++) {
            if (grid[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un nombre est valide dans une boîte.
     *
     * @param grid        la grille à vérifier.
     * @param boxStartRow la ligne de départ de la boîte.
     * @param boxStartCol la colonne de départ de la boîte.
     * @param num         le nombre à vérifier.
     * @return true si le nombre est valide, false sinon.
     */
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

    /**
     * Convertit la grille de nombres en lettres.
     *
     * @param grid la grille de nombres.
     * @return la grille de lettres.
     */
    public char[][] convertGridToLetters(int[][] grid) {
        char[][] letterGrid = new char[taille][taille];

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grid[i][j] == 0) {
                    letterGrid[i][j] = '.'; // Garde les cases vides comme '.'
                } else {
                    letterGrid[i][j] = LETTRES[grid[i][j] - 1]; // Mappe les nombres aux lettres
                }
            }
        }
        return letterGrid;
    }

    /**
     * Affiche la grille de Sudoku avec des nombres.
     *
     * @param grid la grille à afficher.
     */
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
    }

    /**
     * Affiche la grille de Sudoku avec des lettres.
     *
     * @param grid la grille à afficher.
     */
    public void afficherGrilleChar(char[][] grid) {
        int boxSize = (int) Math.sqrt(taille);

        for (int row = 0; row < grid.length; row++) {
            if (row % boxSize == 0 && row != 0) {
                System.out.println("-".repeat(grid.length * 2 + boxSize - 1)); // Dessine un séparateur horizontal
            }
            for (int col = 0; col < grid[row].length; col++) {
                if (col % boxSize == 0 && col != 0) {
                    System.out.print("| "); // Dessine un séparateur vertical
                }
                System.out.print(grid[row][col] + " "); // Affiche la lettre (ou '.')
            }
            System.out.println(); // Nouvelle ligne après chaque ligne
        }
    }

    /**
     * Affiche la grille de Sudoku avec des emojis.
     *
     * @param grid la grille à afficher.
     */
    public void afficherGrilleEmoji(int[][] grid) {
        int boxSize = (int) Math.sqrt(taille);

        for (int row = 0; row < grid.length; row++) {
            if (row % boxSize == 0 && row != 0) {
                System.out.println("-".repeat(grid.length * 3 + boxSize - 1)); // Séparateur horizontal
            }

            for (int col = 0; col < grid[row].length; col++) {
                if (col % boxSize == 0 && col != 0) {
                    System.out.print("| "); // Séparateur vertical
                }

                int num = grid[row][col];
                System.out.print((num == 0 ? "⬜" : EMOJIS[num]) + " "); // Assure que les cases vides sont toujours un carré blanc
            }
            System.out.println(); // Nouvelle ligne après chaque ligne
        }
    }

    /**
     * Remplit la grille avec des lettres.
     */
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

    /**
     * Affiche le Sudoku en lettres.
     */
    public void afficherSudokuEnLettres() {
        char[][] letterGrid = convertGridToLetters(grid);
        afficherGrilleChar(letterGrid);
    }

    /**
     * Remplit la grille avec des nombres fournis par l'utilisateur.
     *
     * @param scanner l'objet Scanner pour lire les entrées de l'utilisateur.
     * @return la grille remplie avec les nombres.
     */
    public int[][] fillGridWithNumbers(Scanner scanner) {
        grid = new int[taille][taille];
        for (int row = 0; row < letterGrid.length; row++) {
            for (int col = 0; col < letterGrid[row].length; col++) {
                while (true) {
                    System.out.print("Entrez un nombre pour (" + row + ", " + col + ") [" + letterGrid[row][col] + "]: ");
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

    /**
     * Obtient la valeur d'une case de la grille.
     *
     * @param i l'indice de la ligne.
     * @param j l'indice de la colonne.
     * @return la valeur de la case.
     */
    public int getValeur(int i, int j) {
        return grid[i][j];
    }

    /**
     * Définit la valeur d'une case de la grille.
     *
     * @param i l'indice de la ligne.
     * @param j l'indice de la colonne.
     * @param v la valeur à définir.
     */
    public void setValeur(int i, int j, int v) {
        grid[i][j] = v;
    }

    /**
     * Obtient la taille de la grille.
     *
     * @return la taille de la grille.
     */
    public int getTaille() {
        return this.taille;
    }
}