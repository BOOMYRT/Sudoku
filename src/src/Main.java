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

        while (true) {
            System.out.print("Entrez la taille du Sudoku (4, 9, 16...): ");
            try {
                taille = Integer.parseInt(scanner.nextLine());
                if (!isValidSudokuSize(taille)) {
                    System.out.println("Erreur: La taille doit être un carré parfait (4, 9, 16...).");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }

        // création temporaire de l'objet pour vérifier si l'utilisateur veut entrer sa grille
        Grille g = new Grille(taille, difficulte, type, symbole);

        boolean fournirGrille = g.fournirGrille(scanner);

        if (fournirGrille) {
            System.out.println("Veuillez remplir votre propre grille :");
            g.fillGridWithLetters();
            int[][] userSudoku = g.fillGridWithNumbers(scanner);
            System.out.println("Votre grille entrée :");
            g.afficherGrilleInt(userSudoku);
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
            g.afficherGrilleInt(sudoku);

            //test solver deduction rules only
            //ask user which method they want to use to solve the sudoku
            while (true) {
                System.out.print("Quelle méthode de résolution souhaitez-vous utiliser ? (0=méthode de déduction, 1=méthode de retour sur trace ou 2=méthode de retour sur trace et déduction): ");
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
                g.afficherGrilleInt(solver.getGrille());
            } else {
                System.out.println("Impossible de résoudre le sudoku.");
            }
        }
    }

    private static boolean isValidSudokuSize(int size) {
        int sqrt = (int) Math.sqrt(size);
        return sqrt * sqrt == size;
    }
}
