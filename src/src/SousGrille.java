package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un bloc dans une grille de Sudoku.
 */
public class SousGrille {

    private int taille;
    private ArrayList<Integer> positions = new ArrayList<>(); // Référence vers les cellules partagées
    private int part; // Taille d'un côté de la sous-grille
    private Grille grille;

    /**
     * Constructeur pour initialiser un bloc.
     *
     * @param taille    la taille entière de la sous-grille (ex : 9 pour un bloc
     *                  3x3).
     * @param part      la taille d'un côté de la sous-grille (ex : 3 pour un bloc
     *                  3x3).
     * @param positions la liste des positions des valeurs de la sous-grille dans la
     *                  grille.
     */
    public SousGrille(Grille grille, ArrayList<Integer> positions, int startIndex, int part) {
        this.grille = grille;
        this.taille = part * part;
        this.positions = positions;
        this.part = part;
    }

    public SousGrille(int startIndex, int part) {
        this.taille = part * part;
        this.part = part;
    }

    public void initialiser(int startIndex, Grille grille) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < part; j++) {

                positions.add(grille.getValeur(startIndex + i * taille + j));
            }

        }
    }

    /**
     * Définit une valeur dans une cellule spécifique de la sous-grille.
     *
     * @param rang   le rang dans la sous-grille (indexé à partir de 0).
     * @param valeur la valeur à placer dans la cellule.
     */
    public void setIndex(int rangGrille, int rangSousGrille) {
        positions.set(rangSousGrille, rangGrille);
    }

    /**
     * Récupère la valeur d'une cellule spécifique de la sous-grille.
     *
     * @param rang le rang dans la sous-grille (indexé à partir de 0).
     * @return la valeur dans la cellule.
     */
    public String getValeur(int rang) {
        int index = startIndex + (rang / part) * part * part + (rang % part);
        return cellulesPartagees.get(index);
    }

    /**
     * Vérifie si la sous-grille respecte les contraintes du Sudoku.
     *
     * @return true si la sous-grille est valide, false sinon.
     */
    public boolean estValide() {
        return cellulesPartagees.stream().distinct().count() == taille;
    }
}