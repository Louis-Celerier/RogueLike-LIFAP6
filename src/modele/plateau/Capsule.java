package modele.plateau;

public class Capsule extends Pickable {

    public Capsule(int posX, int PosY) {
        signature = 1;
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
