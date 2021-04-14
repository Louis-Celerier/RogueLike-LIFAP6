package modele.plateau;

public class Porte extends EntiteStatique {
    private boolean ouverture = false;

    public void utilisationCle(boolean cle) {
        if(!ouverture) {
            ouverture = cle;
        }
    }

    public boolean traversable() { return ouverture; }
}
