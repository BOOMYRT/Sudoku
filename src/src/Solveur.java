package src;

public class Solveur {

    private Grille grille;

    public Solveur(Grille grille) {
        this.grille = grille;
    }

    public boolean resoudre() {
        return resoudreBacktracking(0, 0);
    }

    private boolean resoudreBacktracking(int row, int col) {
        // Si la colonne est égale à la largeur de la grille, passez à la ligne suivante
        if (col == grille.GetL()) {
            col = 0;
            row++;
        }

        // Si la ligne est égale à la hauteur de la grille, la grille est résolue
        if (row == grille.GetH()) {
            return true;
        }

        // Si la cellule est déjà remplie, passez à la cellule suivante
        if (grille.getValeur(row, col) != 0) {
            return resoudreBacktracking(row, col + 1);
        }

        // Essayez de placer un chiffre dans la cellule
        for (int num = 1; num <= grille.GetH() * grille.GetL(); num++) {
            if (grille.isSafe(grille.grid, row, col, num)) {
                grille.setValeur(row, col, num);

                // Si la grille peut être résolue avec ce chiffre, retournez true
                if (resoudreBacktracking(row, col + 1)) {
                    return true;
                }

                // Sinon, retirez le chiffre et essayez le suivant
                grille.setValeur(row, col, 0);
            }
        }

        // Si aucun chiffre ne fonctionne, retournez false
        return false;
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        Grille grille = new Grille(3, 3, 1, 0, 0);
        int[][] puzzle = grille.genererGrille();

        Solveur solveur = new Solveur(grille);
        if (solveur.resoudre()) {
            System.out.println("Solution trouvée :");
            Main.printPuzzle(grille.grid);
        } else {
            System.out.println("Pas de solution trouvée.");
        }
    }
}
