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

        boolean fournirGrille = fournirGrille(scanner);

        if (fournirGrille) {
            Grille g = new Grille(taille, difficulte, type, symbole);

            System.out.println("Veuillez remplir votre propre grille :");
            g.fillGridWithLetters();
            int[][] userSudoku = g.fillGridWithNumbers(scanner);
            System.out.println("Votre grille entrée :");
            g.afficherGrilleInt(userSudoku);
        }
        else {
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
            //pour l'instant pas encore implémenté
            type = 0;

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
            Grille g = new Grille(taille, difficulte, type, symbole);

            int[][] sudoku = g.genererGrille();
            System.out.println("Sudoku généré :");
            if (symbole == 0) {
                g.afficherGrilleInt(sudoku);
            }
            else if (symbole == 1) {
                char[][] sudokuLettre = g.convertGridToLetters(sudoku);
                g.afficherGrilleChar(sudokuLettre);
            }
            else if (symbole == 2) {
                g.afficherGrilleEmoji(sudoku);
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
                else if (symbole == 2) {
                    g.afficherGrilleEmoji(solver.getGrille());
                }
            } else {
                System.out.println("Impossible de résoudre le sudoku.");
            }
        }
        // méthode pour demander à l'utilisateur s'il veut rejouer
        replay(scanner);
    }

    private static boolean fournirGrille(Scanner scanner) {
        while (true) {
            System.out.print("Voulez-vous entrer votre propre Sudoku ? (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Erreur: Veuillez entrer 'true' ou 'false'.");
            }
        }
    }

    private static boolean isValidSudokuSize(int size) {
        int sqrt = (int) Math.sqrt(size);
        return sqrt * sqrt == size;
    }

    private static void replay(Scanner scanner) {
        while (true) {
            System.out.print("Est-ce que voulez rejouer? (true/false): ");
            try {
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("true")) {
                    main(new String[0]);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Veuillez entrer 'true' ou 'false'.");
            }
        }
    }
}
