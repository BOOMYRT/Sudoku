package src;

public class Solveur {
    private Grille grille; // Référence à l'objet 'Grille'
    private List<int[][]> hypotheses; // Liste pour stocker les hypothèses

    /**
     * Constructeur pour initialiser le solveur.
     *
     * @param grille la grille de Sudoku à résoudre.
     */
    public Solveur(Grille grille) {
        this.grille = grille;
        this.hypotheses = new ArrayList<>(); // Initialisation de la liste des hypothèses
    }

    /**
     * Méthode pour résoudre le Sudoku en utilisant la méthode de retour sur trace.
     */
    public boolean retourTrace() {
        // Ajouter une copie de l'état initial à la liste des hypothèses
        hypotheses.add(copyGrid(grille.grid));

        // Lancer la résolution récursive en commençant par la cellule (0, 0)
        return solveRecursive(grille.grid, 0, 0);
    }

    /**
     * Méthode récursive pour effectuer la résolution par retour sur trace.
     *
     * @param grid la grille actuelle.
     * @param row  la ligne actuelle.
     * @param col  la colonne actuelle.
     * @return true si une solution est trouvée, false sinon.
     */
    private boolean solveRecursive(int[][] grid, int row, int col) {
        // Si nous avons atteint la fin, la résolution est terminée
        if (row == grille.getTaille()) {
            return true;
        }

        // Passer à la ligne suivante lorsque la fin de la ligne actuelle est atteinte
        if (col == grille.getTaille()) {
            return solveRecursive(grid, row + 1, 0);
        }

        // Si la cellule actuelle est déjà remplie, passer à la suivante
        if (grid[row][col] != 0) {
            return solveRecursive(grid, row, col + 1);
        }

        // Essayer tous les nombres possibles pour cette cellule vide
        for (int num = 1; num <= grille.getTaille(); num++) {
            if (grille.isSafe(grid, row, col, num)) {
                // Placer le nombre dans la cellule
                grid[row][col] = num;

                // Ajouter une copie de l'état actuel à la liste des hypothèses
                hypotheses.add(copyGrid(grid));

                // Appel récursif pour traiter la cellule suivante
                if (solveRecursive(grid, row, col + 1)) {
                    return true;
                }

                // Retour en arrière : réinitialiser la cellule
                grid[row][col] = 0;

                // Ajouter une copie de la grille après le retour en arrière
                hypotheses.add(copyGrid(grid));
            }
        }

        // Si aucun nombre ne fonctionne, retour en arrière
        return false;
    }

    /**
     * Méthode pour copier une grille afin de sauvegarder son état.
     *
     * @param grid la grille à copier.
     * @return une copie indépendante de la grille.
     */
    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[grille.getTaille()][grille.getTaille()];
        for (int i = 0; i < grille.getTaille(); i++) {
            newGrid[i] = grid[i].clone(); // Copie chaque ligne indépendamment
        }
        return newGrid;
    }

    /**
     * Méthode pour obtenir les hypothèses générées.
     *
     * @return la liste des hypothèses.
     */
    public List<int[][]> getHypotheses() {
        return hypotheses;
    }
}
