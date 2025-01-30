package src;

public class Main {
    public static void main(String[] args) {
        Grille test = new Grille(9);
        test.initialiserPartiellement();
        test.afficher();
    }
}