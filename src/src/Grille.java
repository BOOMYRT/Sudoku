package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une grille de Sudoku.
 */
public class Grille {

    private int taille;
    private String mode = "Standard";
    private String[] elements = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    private List<String> cellulesPartagees; // Liste des valeurs partagées

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
        this.cellulesPartagees = new ArrayList<>();
        for (int i = 0; i < taille * taille; i++) {
            this.cellulesPartagees.add("."); // Toutes les cellules initialisées à "."
        }

        // Création des sous-grilles (blocs)
        for (int i = 0; i < taille; i++) {
            this.blocs.add(new SousGrille(part, cellulesPartagees, i * part * part, part));
        }
    }

    /**
     * Définit une valeur dans une cellule spécifique de la grille.
     *
     * @param ligne   la ligne de la cellule dans la grille (indexée à partir de 0).
     * @param colonne la colonne de la cellule dans la grille (indexée à partir de
     *                0).
     * @param valeur  la valeur à placer dans la cellule.
     */
    public void setValeur(int ligne, int colonne, String valeur) {
        int index = ligne * taille + colonne; // Calcul de l'index dans la liste partagée
        cellulesPartagees.set(index, valeur);
    }

    /**
     * Récupère la valeur d'une cellule spécifique de la grille.
     *
     * @param ligne   la ligne de la cellule dans la grille (indexée à partir de 0).
     * @param colonne la colonne de la cellule dans la grille (indexée à partir de
     *                0).
     * @return la valeur dans la cellule.
     */
    public String getValeur(int ligne, int colonne) {
        int index = ligne * taille + colonne; // Calcul de l'index dans la liste partagée
        return cellulesPartagees.get(index);
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
}