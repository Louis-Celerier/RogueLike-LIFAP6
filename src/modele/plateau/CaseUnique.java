package modele.plateau;

public class CaseUnique extends EntiteStatique {
    protected boolean traverser = false;
    protected int x, y;

    public CaseUnique(Jeu _jeu, int posX, int posY) {
        super(_jeu);
        x = posX;
        y = posY;
    }

    public void traverse() {
        traverser = true;
    }

    public void mouiller() {
        traverser = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean traversable() {
        return !traverser;
    }

}
