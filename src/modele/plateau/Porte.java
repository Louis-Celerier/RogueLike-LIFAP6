package modele.plateau;

public class Porte extends EntiteStatique {
    private boolean ouverture = false;
    private int x, y;

    public Porte(Jeu _jeu, int posX, int posY) {
        super(_jeu);
        x = posX;
        y = posY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void utilisationCle(boolean cle) {
        if(!ouverture) {
            ouverture = cle;
        }
    }

    public boolean traversable() {
        return ouverture;
    }
}