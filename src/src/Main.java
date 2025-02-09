package src;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue au jeu Sudoku!");

        int taille = 0;
        int difficulte = 0;
        int type = 0;
        int symbole = 0;
        int methode = -1;

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
            while (true) {
                System.out.print("Entrez le niveau de difficulté (1-5): ");
                try {
                    difficulte = Integer.parseInt(scanner.nextLine());
                    if (difficulte < 1 || difficulte > 5) {
                        System.out.println("Erreur: Veuillez entrer un nombre entre 1 et 5.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                }
            }

            while (true) {
                System.out.print("Entrez le type de Sudoku (0: classique, 1: en X, 2: en +): ");
                try {
                    type = Integer.parseInt(scanner.nextLine());
                    if (type < 0 || type > 2) {
                        System.out.println("Erreur: Veuillez entrer 0, 1 ou 2.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                }
            }

            while (true) {
                System.out.print("Entrez le symbole du Sudoku (0: numéros, 1: lettres, 2: emojis): ");
                try {
                    symbole = Integer.parseInt(scanner.nextLine());
                    if (symbole < 0 || symbole > 2) {
                        System.out.println("Erreur: Veuillez entrer 0, 1 ou 2.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                }
            }

            // création de l'objet après avoir collecté toutes les infos
            g = new Grille(taille, difficulte, type, symbole);

            int[][] sudoku = g.genererGrille();
            System.out.println("Sudoku généré :");
            if (symbole == 0) {
                g.afficherGrilleInt(sudoku);
            }
            else if (symbole == 1) {
                char[][] sudokuLettre = g.convertGridToLetters(sudoku);
                g.afficherGrilleChar(sudokuLettre);
            }

            // test solver deduction rules only
            // ask user which method they want to use to solve the sudoku
            while (true) {
                System.out.print("Quelle méthode de résolution souhaitez-vous utiliser ? (0: méthode de déduction, 1: méthode de retour sur trace, 2: les deux): ");
                try {
                    methode = Integer.parseInt(scanner.nextLine());
                    if (methode < 0 || methode > 2) {
                        System.out.println("Erreur: Veuillez entrer 0, 1 ou 2.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                }
            }

            SolveurGeneral solver = new SolveurGeneral(sudoku, g.getTaille(), methode);

            if (solver.solve()) {
                System.out.println("Sudoku résolu :");
                if (symbole == 0) {
                    g.afficherGrilleInt(solver.getGrille());
                }
                else if (symbole == 1) {
                    char[][] sudokuLettreResolu = g.convertGridToLetters(solver.getGrille());
                    g.afficherGrilleChar(sudokuLettreResolu);
                }
            } else {
                System.out.println("Impossible de résoudre le sudoku.");
            }
        }
        // méthode pour demander à l'utilisateur s'il veut rejouer
        replay(scanner);
    }

    private static boolean isValidSudokuSize(int size) {
        int sqrt = (int) Math.sqrt(size);
        return sqrt * sqrt == size;
    }

    private static void replay(Scanner scanner) {
        System.out.print("Est-ce que voulez rejouer? (true/false): ");
        boolean answer = Boolean.parseBoolean(scanner.nextLine());
            if (answer) {
                main(new String[0]);
            }
    }
}
