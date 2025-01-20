package src;

/**
 * Classe représentant une grille de Sudoku.
 */

public class Grille {

	private final int taille;

	private final SousGrille[] blocs;

	/**
	 * Constructeur pour initialiser une grille de Sudoku.
	 *
	 * @param largeur la largeur de la grille (ex : 9 pour une grille 9x9 composée
	 *                de
	 *                blocs 3x3).
	 */
	public Grille(int hauteur, int largeur) {
		taille = hauteur * largeur;

		int tailleBloc = SousGrille.GetTaille();
		if (tailleBloc * tailleBloc != taille) {
			throw new IllegalArgumentException(
					"La taille de la grille doit être le carré de la taille d'une sous-grille.");
		}
		this.blocs = new SousGrille[tailleBloc];
		for (int i = 0; i < tailleBloc; i++) {
			for (int j = 0; j < tailleBloc; j++) {
				this.blocs[i] = new SousGrille(tailleBloc);
			}
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
	public void setValeur(int ligne, int colonne, int valeur) {
		int tailleBloc = (int) Math.sqrt(taille);
		int blocLigne = ligne / tailleBloc;
		int ligneDansBloc = ligne % tailleBloc;
		blocs[blocLigne].setValeur(ligneDansBloc, valeur);
	}

	/**
	 * Récupère la valeur d'une cellule spécifique de la grille.
	 *
	 * @param ligne   la ligne de la cellule dans la grille (indexée à partir de 0).
	 * @param colonne la colonne de la cellule dans la grille (indexée à partir de
	 *                0).
	 * @return la valeur dans la cellule (ou 0 si elle est vide).
	 */
	public int getValeur(int ligne, int colonne) {
		int tailleBloc = (int) Math.sqrt(taille);
		int blocLigne = ligne / tailleBloc;
		int blocColonne = colonne / tailleBloc;
		int ligneDansBloc = ligne % tailleBloc;
		int colonneDansBloc = colonne % tailleBloc;
		return blocs[blocLigne][blocColonne].getValeurs()[ligneDansBloc][colonneDansBloc];
	}

	/**
	 * Vérifie si la grille respecte les contraintes du Sudoku.
	 *
	 * @return true si la grille est valide, false sinon.
	 */
	public boolean estValide() {
		int tailleBloc = (int) Math.sqrt(taille);

		// Vérification des blocs
		for (int i = 0; i < tailleBloc; i++) {
			for (int j = 0; j < tailleBloc; j++) {
				if (!blocs[i][j].estValide()) {
					return false;
				}
			}
		}

		// Vérification des lignes
		for (int i = 0; i < taille; i++) {
			boolean[] presence = new boolean[taille + 1];
			for (int j = 0; j < taille; j++) {
				int valeur = getValeur(i, j);
				if (valeur != 0) {
					if (presence[valeur]) {
						return false;
					}
					presence[valeur] = true;
				}
			}
		}

		// Vérification des colonnes
		for (int j = 0; j < taille; j++) {
			boolean[] presence = new boolean[taille + 1];
			for (int i = 0; i < taille; i++) {
				int valeur = getValeur(i, j);
				if (valeur != 0) {
					if (presence[valeur]) {
						return false;
					}
					presence[valeur] = true;
				}
			}
		}

		return true;
	}

	/**
	 * Affiche la grille dans la console.
	 */
	public void afficher() {
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				System.out.print(getValeur(i, j) + " ");
			}
			System.out.println();
		}
	}
}
