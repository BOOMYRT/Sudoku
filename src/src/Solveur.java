package src;

import java.util.ArrayList;
import java.util.List;

public class Solveur {

	private Grille grille;
	private List<String> log;
	private List<int[][]> hypotheses;

	public Solveur(Grille grille) {
		this.grille = grille;
	}

	void retourTrace() {
		hypotheses.add(grille.grid.clone());
		for (int i = 0; i < grille.GetH(); i++) {
			for (int j = 0; j < grille.GetL(); j++) {

				if (grille.getValeur(i, j) == 0) {
					for (int w = 0; w < grille.GetH() * grille.GetL(); w++) {
						if (grille.isSafe(grille.grid, i, j, w)) {
							grille.setValeur(i, j, w);
							hypotheses.add(grille.grid.clone());
						}
						//insérer ici une boucle for skippant l'action à chaque fois que le chiffre testé est présent dans la ligne/colonne/SousGrille
					}
					hypotheses.add(grille.grid.clone());
				}
			}
		}

		
		return;
	}

}
