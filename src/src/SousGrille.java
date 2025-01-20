package src;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant un bloc dans une grille de Sudoku.
 */

public class SousGrille {

    private int taille;
    private int[] cellules;

    /**
     * Constructeur pour initialiser un bloc.
     *
     * @param taille la taille du bloc (ex : 3 pour un bloc 3x3).
     */
    public SousGrille(int taille) {
        this.taille = taille;
        this.cellules = new int[taille];
    }

    /**
     * Ajoute une valeur à une cellule donnée du bloc.
     *
     * @param rang   le rang dans le bloc (indexée à partir de 0).
     * @param valeur la valeur à ajouter.
     * @throws IllegalArgumentException si la valeur est invalide ou déjà présente.
     */
    public void setValeur(int rang, int valeur) {
        if (valeur < 1 || valeur > taille * taille) {
            throw new IllegalArgumentException("Valeur invalide pour ce bloc.");
        }
        if (estPresent(valeur)) {
            throw new IllegalArgumentException("Valeur déjà présente dans ce bloc.");
        }
        cellules[rang] = valeur;
    }

    /**
     * Vérifie si une valeur est déjà présente dans le bloc.
     *
     * @param valeur la valeur à vérifier.
     * @return true si la valeur est présente, false sinon.
     */
    public boolean estPresent(int valeur) {
        for (int i = 0; i < taille; i++) {
            if (cellules[i] == valeur) {
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère les valeurs actuelles du bloc.
     *
     * @return un tableau des valeurs du bloc.
     */
    public int[] getValeurs() {
        return cellules;
    }

    /**
     * Vérifie si le bloc respecte les contraintes du Sudoku (valeurs uniques).
     *
     * @return true si le bloc est valide, false sinon.
     */
    public boolean estValide() {
        Set<Integer> valeurs = new HashSet<>();
        for (int i = 0; i < taille; i++) {
            int valeur = cellules[i];
            if (valeur != 0) {
                if (valeurs.contains(valeur)) {
                    return false;
                }
                valeurs.add(valeur);
            }
        }
        return true;
    }
}
