package src;

import java.util.List;

/**
 * Classe représentant un bloc dans une grille de Sudoku.
 */
public class SousGrille {

    private int taille;
    private List<String> cellulesPartagees; // Référence vers les cellules partagées
    private int startIndex; // Index de départ dans la liste partagée
    private int part; // Taille d'un côté de la sous-grille

    /**
     * Constructeur pour initialiser un bloc.
     *
     * @param tailleBloc        la taille de la sous-grille (ex : 3 pour un bloc 3x3).
     * @param cellulesPartagees la liste des valeurs partagées.
     * @param startIndex        l'index de départ des cellules dans la liste partagée.
     * @param part              la taille d'un côté de la sous-grille.
     */
    public SousGrille(int tailleBloc, List<String> cellulesPartagees, int startIndex, int part) {
        this.taille = tailleBloc * tailleBloc;
        this.cellulesPartagees = cellulesPartagees;
        this.startIndex = startIndex;
        this.part = part;
    }

    /**
     * Définit une valeur dans une cellule spécifique de la sous-grille.
     *
     * @param rang   le rang dans la sous-grille (indexé à partir de 0).
     * @param valeur la valeur à placer dans la cellule.
     */
    public void setValeur(int rang, String valeur) {
        int index = startIndex + (rang / part) * part * part + (rang % part);
        cellulesPartagees.set(index, valeur);
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