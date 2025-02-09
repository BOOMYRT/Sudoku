package src;

public class SolveurGeneral {
    private int[][] grille; // Référence à la grille courante (entrée/sortie)
    private final int methode; // Méthode de résolution
    private final SolveurBacktrack sudokuSolverBacktrack; // Solveur pour backtracking
    private final SolveurDeduction sudokuSolverDeduction; // Solveur pour déduction

    /**
     * Constructeur pour initialiser le SolveurGeneral.
     *
     * @param grille  La grille de Sudoku à résoudre.
     * @param taille  La taille de la grille (4x4, 9x9, etc.).
     * @param methode 0 = déduction, 1 = backtracking, 2 = mix.
     */
    public SolveurGeneral(int[][] grille, int taille, int methode) {
        this.grille = grille;
        this.methode = methode;
        this.sudokuSolverDeduction = new SolveurDeduction(grille, taille); // Hypothèse : classe existante
        Grille g = new Grille(taille, 0, 0, 0); // Création temporaire pour Solveur (non nécessaire si vous utilisez `grille` directement)
        g.grid = grille; // Adapter l'état de `Grille` pour correspondre à `grille`
        this.sudokuSolverBacktrack = new SolveurBacktrack(g); // Passer `Grille` correctement au solveur backtracking
    }

    /**
     * Retourne la grille actuelle (résolue ou non).
     *
     * @return la grille Sudoku actuelle.
     */
    public int[][] getGrille() {
        return grille;
    }

    /**
     * Méthode pour résoudre le Sudoku selon la méthode choisie.
     *
     * @return true si une solution est trouvée, false sinon.
     */
    public boolean solve() {
        try {
            // Méthode déduction uniquement
            if (methode == 0) {
                if (sudokuSolverDeduction.solve()) {
                    grille = sudokuSolverDeduction.getGrille();
                    return true;
                } else {
                    System.out.println("Impossible de résoudre avec les règles de déduction seules.");
                    return false;
                }
            }
            // Méthode retour sur trace uniquement
            else if (methode == 1) {
                if (sudokuSolverBacktrack.retourTrace()) {
                    grille = sudokuSolverBacktrack.getHypotheses()
                            .get(sudokuSolverBacktrack.getHypotheses().size() - 1); // Dernière grille valide
                    return true;
                } else {
                    System.out.println("Impossible de résoudre avec l'algorithme de retour sur trace.");
                    return false;
                }
            }
            // Méthode mixte : déduction + retour sur trace
            else if (methode == 2) {
                System.out.println("Résolution avec déduction...");
                if (sudokuSolverDeduction.solve()) {
                    grille = sudokuSolverDeduction.getGrille(); // Mise à jour après déduction
                    System.out.println("Déduction terminée.");
                } else {
                    System.out.println("Déduction incomplète, passage au retour sur trace...");
                }

                System.out.println("Résolution avec retour sur trace...");
                if (sudokuSolverBacktrack.retourTrace()) {
                    grille = sudokuSolverBacktrack.getHypotheses()
                            .get(sudokuSolverBacktrack.getHypotheses().size() - 1); // Dernière grille valide
                    return true;
                } else {
                    System.out.println("Impossible de résoudre avec les deux méthodes.");
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Méthode invalide.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
