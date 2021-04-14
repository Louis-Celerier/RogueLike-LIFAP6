/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;
    private int[] inventaire = new int[3];
    private int orientation = 0;

    private Jeu jeu;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrientation() {
        return orientation;
    }

    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
        inventaire[0] = 0;
        inventaire[1] = 1;
        inventaire[2] = 0;
    }

    public void droite() {
        orientation = 0;
        if (traversable(x+1, y)) {
            x ++;
        }
    }

    public void gauche() {
        orientation = 1;
        if (traversable(x-1, y)) {
            x --;
        }
    }

    public void bas() {
        orientation = 2;
        if (traversable(x, y+1)) {
            y ++;
        }
    }

    public void haut() {
        orientation = 3;
        if (traversable(x, y-1)) {
            y --;
        }
    }

    private boolean utiliseCle() {
        if(inventaire[0] > 0) {
            inventaire[0]--;
            return true;
        }
        else
            return false;
    }

    public void prendre(Pickable objet) {
        int obj = objet.utilisation();
        if(obj >= 0) {
            inventaire[obj]++;
        }
    }

    public void stageSuivant(int posX, int posY) {
        inventaire[1] = 1;
        x = posX;
        y = posY;
    }

    public void capsuleUtilisation() {
        int posX, posY;
        if(inventaire[1] > 0) {
            for (CaseUnique element: jeu.getCasesUniques()) {
                if(!element.traversable()) {
                    posX = x;
                    posY = y;
                    switch (orientation) {
                        case 0:
                            posX++;
                            break;
                        case 1:
                            posX--;
                            break;
                        case 2:
                            posY++;
                            break;
                        case 3:
                            posY--;
                            break;
                    }
                    if(element.getX() == posX && element.getY() == posY) {
                        element.mouiller();
                        inventaire[1]--;
                    }
                }
            }
        }
    }

    private boolean traversable(int x, int y) {
        for (Pickable element: jeu.getPickables()) {
            if (x == element.getX() && y == element.getY()) {
                this.prendre(element);
            }
        }
        for (CaseUnique element: jeu.getCasesUniques()) {
            if (x == element.getX() && y == element.getY() && element.traversable()) {
                element.traverse();
                return true;
            }
        }
        if (x == jeu.getPorte().getX() && y == jeu.getPorte().getY()) {
            jeu.getPorte().utilisationCle(utiliseCle());
            return jeu.getEntite(x, y).traversable();
        } else if (x >0 && x < jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }
}
