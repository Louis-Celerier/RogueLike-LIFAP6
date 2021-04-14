package modele.plateau;

public class CaseVide extends EntiteStatique {
    public CaseVide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return false;
    }

}
