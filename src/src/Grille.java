package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une grille de Sudoku.
 */
public class Grille {

    private int taille;
    private String mode = "Standard";
    public int[] elements = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private ArrayList<Integer> cellules; // Liste des valeurs partagées

    private List<SousGrille> blocs = new ArrayList<>();

    /**
     * Constructeur pour initialiser une grille de Sudoku.
     *
     * @param taille la taille de la grille (ex : 9 pour une grille 9x9 composée de
     *               blocs 3x3).
     */
    public Grille(int taille) {
        this.taille = taille;
        int part = (int) Math.sqrt(taille);

        if (part * part != taille) {
            throw new IllegalArgumentException("La taille doit être un carré parfait.");
        }

        // Initialisation des valeurs partagées
        this.cellules = new ArrayList<>();
        for (int i = 0; i < taille * taille; i++) {
            this.cellules.add(0); // Toutes les cellules initialisées à "."
        }

        // Création des sous-grilles (blocs)
        for (int i = 0; i < taille; i++) {
            this.blocs.add(new SousGrille(i * part * part, part));
        }
    }

    public int GetTaille() {
        return this.taille;
    }



    /**
     * Définit une valeur dans une cellule spécifique de la grille.
     *
     * @param ligne   la ligne de la cellule dans la grille (indexée à partir de 0).
     * @param colonne la colonne de la cellule dans la grille (indexée à partir de
     *                0).
     * @param valeur  la valeur à placer dans la cellule.
     */
    public void setValeur(int ligne, int colonne, int valeur) {
        int index = ligne * taille + colonne; // Calcul de l'index dans la liste partagée
        cellules.set(index, valeur);
    }

    public void setValeur(int index, int valeur) {
        cellules.set(index, valeur);
    }

    /**
     * Récupère la valeur d'une cellule spécifique de la grille.
     *
     * @param ligne   la ligne de la cellule dans la grille (indexée à partir de 0).
     * @param colonne la colonne de la cellule dans la grille (indexée à partir de
     *                0).
     * @return la valeur dans la cellule.
     */
    public int getValeur(int ligne, int colonne) {
        int index = ligne * taille + colonne; // Calcul de l'index dans la liste partagée
        return cellules.get(index);
    }

    public int getValeur(int index) {
        return cellules.get(index);
    }

    /**
     * Affiche la grille dans la console.
     */
    public void afficher() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(" | " + getValeur(i, j));
            }
            System.out.println();
        }
    }

    /**
 * Initialise la grille avec des valeurs pour qu'elle soit résolvable
 */
public void initialiser() {
    // Exemple de grille partiellement remplie (résolvable)
    int[][] valeursInitiales = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    // Remplissage de la grille via `setValeur`
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (valeursInitiales[i][j] != 0) {
                setValeur(i, j, valeursInitiales[i][j]);
            }
        }
    }
}




    /**
 * Initialise la grille avec des valeurs pour qu'il reste deux espaces vides.
 */
public void initialiserPartiellement() {
    // Exemple de grille initiale (presque complète, avec deux espaces vides)
    int[][] valeursInitiales = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };


    // Remplissage de la grille via `setValeur`
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (valeursInitiales[i][j] != 0) {
                setValeur(i, j, valeursInitiales[i][j]);
            }
        }
    }
}


}


