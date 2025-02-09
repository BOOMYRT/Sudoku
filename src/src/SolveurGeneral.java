package src;

public class SolveurGeneral {
    private int[][] grille;
    //0=deduction, 1=backtrack , 2=mix des deux
    private int methode;
    private final SolveurDeduction sudokuSolverDeduction;

    /**
     *
     * @param grille
     * @param taille
     * @param methode
     */
    public SolveurGeneral(int[][] grille, int taille, int methode) {
        this.grille = grille;
        this.methode = methode;
        this.sudokuSolverDeduction = new SolveurDeduction(grille, taille);
    }


    public int[][] getGrille() {
        return grille;
    }

    /**
     *
     * @return
     */
    public boolean solve(){
        try {
            //methode de resolution avec deduction
            if (methode == 0) {
                if (sudokuSolverDeduction.solve()) {
                    //mettre à jour la grille du solveur
                    grille = sudokuSolverDeduction.getGrille();
                    return true;
                } else {
                    System.out.println("Impossible de résoudre avec les règles de déduction seules.");
                    return false;
                }
            }
            //methode de resolution avec retour sur trace
            else if (methode == 1) {

            }
            //methode de resolution avec regle de deduction et retour sur trace
            else if (methode == 2) {

            } else {
                throw new Exception("Erreur sur le choix de la méthode de résolution");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
