package src;

import java.util.*;

public class SolveurDeduction {
    //0=methodeDeduction, 1=backtrack , 2=mix des deux
    private int methode;
    private int[][] grille;
    private Set<Integer>[][] candidats;
    private int taille;
    private int subgridSize;

    //récupère la grille et la taille de la grille
    public SolveurDeduction(int[][] grille, int taille) {
        this.grille = grille;
        this.taille = taille;
        this.subgridSize = (int) Math.sqrt(taille);
        initialiseCandidats();
    }


    public int[][] getGrille() {
        return grille;
    }

    // Initialise les candidats pour chaque case
    private void initialiseCandidats() {
        candidats = new HashSet[taille][taille];
        System.out.println("Initialisation des candidats pour chaque case ...");
        for (int r = 0; r < taille; r++) {
            for (int c = 0; c < taille; c++) {
                if (grille[r][c] == 0) {
                    candidats[r][c] = new HashSet<>();
                    for (int num = 1; num <= taille; num++) {
                        if (isValid(r, c, num)) {
                            candidats[r][c].add(num);
                        }
                    }
                    System.out.println("Candidats possibles pour (" + r + ", " + c + ") : " + candidats[r][c]);
                } else {
                    candidats[r][c] = new HashSet<>();
                }
            }
        }
    }

    // Vérifie si un nombre est valide dans une position donnée
    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < taille; i++) {
            if (grille[row][i] == num || grille[i][col] == num) {
                return false;
            }
        }
        int startRow = (row / subgridSize) * subgridSize;
        int startCol = (col / subgridSize) * subgridSize;
        for (int r = 0; r < subgridSize; r++) {
            for (int c = 0; c < subgridSize; c++) {
                if (grille[startRow + r][startCol + c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Applique les règles logiques pour déduire les valeurs
    private boolean applyLogicalRules() {
        boolean progress = false;
        System.out.println("Application des regles de déduction...");
        for (int r = 0; r < taille; r++) {
            for (int c = 0; c < taille; c++) {
                if (grille[r][c] == 0 && candidats[r][c].size() == 1) {
                    int value = candidats[r][c].iterator().next();
                    System.out.println("Case (" + r + ", " + c + ") determinee : " + value);
                    grille[r][c] = value;
                    candidats[r][c].clear();
                    updateCandidats(r, c, value);
                    progress = true;
                }
            }
        }
        return progress;
    }

    // Met à jour les candidats après l'attribution d'une valeur
    private void updateCandidats(int row, int col, int value) {
        System.out.println("Mise à jour des candidats après insertion de " + value + " en (" + row + ", " + col + ")...");

        for (int i = 0; i < taille; i++) {
            candidats[row][i].remove(value);
            candidats[i][col].remove(value);
        }
        int startRow = (row / subgridSize) * subgridSize;
        int startCol = (col / subgridSize) * subgridSize;
        for (int r = 0; r < subgridSize; r++) {
            for (int c = 0; c < subgridSize; c++) {
                candidats[startRow + r][startCol + c].remove(value);
            }
        }
    }

    // Fonction principale de résolution
    public boolean solve() {
        System.out.println("Debut de la resolution avec la méthode de déduction...");
        while (applyLogicalRules()) {
        }
        boolean solved = isSolved();
        System.out.println(solved ? "Sudoku résolu avec succes !" : "Impossible de resoudre completement le sudoku avec seulement les regles de déduction.");
        return solved;
    }


    // Vérifie si le Sudoku est résolu
    private boolean isSolved() {
        for (int r = 0; r < taille; r++) {
            for (int c = 0; c < taille; c++) {
                if (grille[r][c] == 0) return false;
            }
        }
        return true;
    }

}




