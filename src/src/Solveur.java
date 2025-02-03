package src;

import java.util.ArrayList;
import java.util.List;

public class Solveur {

	private Grille grille;
	private List<String> log;
	private List<Grille> hypotheses;


	public Solveur(Grille grille) {
		this.grille = grille;
	}


	void retourTrace() {
		hypotheses.add(grille);
		for (int i = 0; i < grille.GetTaille() * grille.GetTaille(); i++) {
			if (grille.getValeur(i).equals(0)) {
				for (int j = 0; j < grille.GetTaille(); j++) {
					grille.setValeur(i, grille.elements[j]);
					//insérer ici une boucle for skippant l'action à chaque fois que le chiffre testé est présent dans la ligne/colonne/SousGrille
				}
				hypotheses.add(grille);
			}
		} 

		
		return;
	}

}
