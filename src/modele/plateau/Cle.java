package modele.plateau;

public class Cle extends Pickable {

    public Cle(int posX, int PosY) {
        signature = 0;
        x = posX;
        y = PosY;
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
