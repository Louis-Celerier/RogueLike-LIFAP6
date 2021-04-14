package modele.plateau;

import java.util.Random;

public class Coffre extends Pickable {

    public Coffre(int posX, int PosY) {
        signature = 2;
        x = posX;
        y = PosY;
    }

    @Override
    public int utilisation() {
        if(!utiliser) {
            int obj = (int)(Math.random() * 2);
            utiliser = true;
            if(obj == 0) {
                System.out.println("C'est une clé !");
            } else {
                System.out.println("C'est une capsule !");
            }
            return obj;
        }
        else return -1;
    }
}
