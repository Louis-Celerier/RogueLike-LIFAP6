package modele.plateau;

public class Cle extends Pickable {

    public Cle(Jeu _jeu) {
        jeu = _jeu;
        signature = 0;
        x = 2;
        y = 2;
    }

    @Override
    public int utilisation() {
        if(!utiliser) {
            utiliser = true;
            return signature;
        }
        else return -1;
    }
}
