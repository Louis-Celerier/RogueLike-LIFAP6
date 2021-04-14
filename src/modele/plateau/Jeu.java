/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.util.Observable;
import java.util.Random;


public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    private int pause = 200; // période de rafraichissement

    private Heros heros;
    private Porte porte;
    private boolean debut = true;

    public Porte getPorte() {
        return porte;
    }

    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];
    private Pickable[] grillePickables = new Pickable[4];
    private CaseUnique[] grilleCasesUniques = new CaseUnique[3];

    public Jeu() {
        initialisationDesEntites();
    }

    public Heros getHeros() {
        return heros;
    }

    public EntiteStatique[][] getGrille() {
        return grilleEntitesStatiques;
    }

    public Pickable[] getPickables() {
        return grillePickables;
    }

    public CaseUnique[] getCasesUniques() {
        return grilleCasesUniques;
    }

    public EntiteStatique getEntite(int x, int y) {
		if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
			// L'entité demandée est en-dehors de la grille
			return null;
		}
		return grilleEntitesStatiques[x][y];
	}

    private void initialisationDesEntites() {
        if (debut) {
            heros = new Heros(this, 4, 4);
            debut = false;
        }
        else {
            if (heros.getY() == 0 || heros.getY() == 9) {
                heros.stageSuivant(heros.getX(), 9 - heros.getY());
            } else {
                heros.stageSuivant(19 - heros.getX(), heros.getY());
            }
            for (int x = 0; x < 20; x++) {
                for (int y = 1; y < 9; y++) {
                    grilleEntitesStatiques[x][y] = null;
                }
            }
        }
        Random random = new Random();
        int posX, posY, pickChoix;

        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            addEntiteStatique(new Mur(this), x, 0);
            addEntiteStatique(new Mur(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntiteStatique(new Mur(this), 0, y);
            addEntiteStatique(new Mur(this), 19, y);
        }

        addEntiteStatique(new Mur(this), 1+random.nextInt(17-2), 1+random.nextInt(8-2));
        addEntiteStatique(new Mur(this), 1+random.nextInt(17-2), 1+random.nextInt(8-2));
        addEntiteStatique(new Mur(this), 1+random.nextInt(17-2), 1+random.nextInt(8-2));
        addEntiteStatique(new CaseVide(this), 1+random.nextInt(17-2), 1+random.nextInt(8-2));


        if(random.nextBoolean()) {
            posX = (1+random.nextInt(18-1));
            if(random.nextBoolean()) {
                posY = 0;
            }
            else {
                posY = 9;
            }
        }
        else {
            posY = (1+random.nextInt(8-1));
            if(random.nextBoolean()) {
                posX = 0;
            }
            else {
                posX = 19;
            }
        }
        addEntiteStatique(porte = new Porte(this, posX, posY), posX, posY);

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(this);
                }

            }
        }
        for (int i = 0; i < 3; i++) {
            posX = 1+random.nextInt(19-1);
            posY = 1+random.nextInt(8-1);
            grilleEntitesStatiques[posX][posY] = grilleCasesUniques[i] = new CaseUnique(this, posX, posY);
        }

        grillePickables[0] = new Cle((2+random.nextInt(17-2)), (2+random.nextInt(7-2)));

        for (int i = 1; i < 4; i++) {
            pickChoix = 1 + random.nextInt(2);
            if (pickChoix == 1) {
                grillePickables[i] = new Capsule((2+random.nextInt(17-2)), (2+random.nextInt(7-2)));
            } else {
                grillePickables[i] = new Coffre(2+random.nextInt(17-2), (2+random.nextInt(7-2)));
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while(true) {
            setChanged();
            notifyObservers();

            if(heros.getX() == porte.getX() && heros.getY() == porte.getY()) {
                this.initialisationDesEntites();
            }

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    private void addEntiteStatique(EntiteStatique e, int x, int y) {
        grilleEntitesStatiques[x][y] = e;

    }

}
