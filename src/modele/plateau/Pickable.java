package modele.plateau;

public abstract class Pickable {
    protected int x, y;
    protected int signature;
    protected boolean utiliser = false;

    public abstract int utilisation();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getUtiliser() {
        return utiliser;
    }

    public int getSignature() {
        return signature;
    }
}