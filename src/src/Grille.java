package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une grille de Sudoku.
 */
public class Grille {

    private int taille;
    private String mode = "Standard";
    public String[] elements = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
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
    public void setValeur(int ligne, int colonne, String valeur) {
        int index = ligne * taille + colonne; // Calcul de l'index dans la liste partagée
        cellulesPartagees.set(index, valeur);
    }

    public void setValeur(int index, String valeur) {
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

    public String getValeur(int index) {
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

    /**
 * Initialise la grille avec des valeurs pour qu'elle soit résolvable
 */
public void initialiser() {
    // Exemple de grille partiellement remplie (résolvable)
    String[][] valeursInitiales = {
        { "5", "3", ".", ".", "7", ".", ".", ".", "." },
        { "6", ".", ".", "1", "9", "5", ".", ".", "." },
        { ".", "9", "8", ".", ".", ".", ".", "6", "." },
        { "8", ".", ".", ".", "6", ".", ".", ".", "3" },
        { "4", ".", ".", "8", ".", "3", ".", ".", "1" },
        { "7", ".", ".", ".", "2", ".", ".", ".", "6" },
        { ".", "6", ".", ".", ".", ".", "2", "8", "." },
        { ".", ".", ".", "4", "1", "9", ".", ".", "5" },
        { ".", ".", ".", ".", "8", ".", ".", "7", "9" }
    };

    // Remplissage de la grille via `setValeur`
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (!valeursInitiales[i][j].equals(".")) {
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
    String[][] valeursInitiales = {
        { "5", "3", "4", "6", "7", "8", "9", "1", "2" },
        { "6", "7", "2", "1", "9", "5", "3", "4", "8" },
        { "1", "9", "8", "3", "4", "2", "5", "6", "7" },
        { "8", "5", "9", "7", "6", "1", "4", "2", "3" },
        { "4", "2", "6", "8", ".", "3", "7", "9", "1" }, // Une cellule vide (ligne 5, colonne 5)
        { "7", "1", "3", "9", "2", "4", "8", "5", "6" },
        { "9", "6", "1", "5", "3", "7", "2", "8", "4" },
        { "2", "8", "7", "4", "1", "9", "6", ".", "5" }, // Une cellule vide (ligne 8, colonne 8)
        { "3", "4", "5", "2", "8", "6", "1", "7", "9" }
    };


    // Remplissage de la grille via `setValeur`
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            if (!valeursInitiales[i][j].equals(".")) {
                setValeur(i, j, valeursInitiales[i][j]);
            }
        }
    }
}


}


