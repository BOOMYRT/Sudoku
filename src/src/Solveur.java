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
        if (col == grille.getTaille()) {
            col = 0;
            row++;
        }

        if (row == grille.getTaille()) {
            return true;
        }

        if (grille.getValeur(row, col) != 0) {
            return resoudreBacktracking(row, col + 1);
        }

        for (int num = 1; num <= grille.getTaille(); num++) {
            if (grille.isSafe(grille.getGrid(), row, col, num)) {
                grille.setValeur(row, col, num);

                if (resoudreBacktracking(row, col + 1)) {
                    return true;
                }

                grille.setValeur(row, col, 0);
            }
        }

        return false;
    }
}
