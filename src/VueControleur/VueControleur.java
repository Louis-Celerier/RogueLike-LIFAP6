package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHeroD;
    private ImageIcon icoHeroB;
    private ImageIcon icoHeroG;
    private ImageIcon icoHeroH;

    private ImageIcon icoCaseNormale;
    private ImageIcon icoCaseUOk;
    private ImageIcon icoCaseUPasOk;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon icoPorte;

    private ImageIcon icoCle;
    private ImageIcon icoEau;
    private ImageIcon icoCoffre;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.getHeros().gauche(); break;
                    case KeyEvent.VK_RIGHT : jeu.getHeros().droite();break;
                    case KeyEvent.VK_DOWN : jeu.getHeros().bas(); break;
                    case KeyEvent.VK_UP : jeu.getHeros().haut(); break;
                    case KeyEvent.VK_A : jeu = new Jeu(); break;
                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHeroD = chargerIcone("Images/PacmanD.png");
        icoHeroG = chargerIcone("Images/PacmanG.png");
        icoHeroB = chargerIcone("Images/PacmanB.png");
        icoHeroH = chargerIcone("Images/PacmanH.png");

        icoCaseNormale = chargerIcone("Images/Vide.png");
        icoCaseUOk = chargerIcone("Images/CaseUOk.png");
        icoCaseUPasOk = chargerIcone("Images/CaseUPasOk.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoPorte = chargerIcone("Images/Porte.png");

        icoCle = chargerIcone("Images/Cle.png");
        icoEau = chargerIcone("Images/Eau.png");
        icoCoffre = chargerIcone("Images/Coffre.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Roguelike - Louis CELERIER");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
				EntiteStatique e = jeu.getEntite(x, y);
                if (e instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (e instanceof Porte) {
                    tabJLabel[x][y].setIcon(icoPorte);
                } else if (e instanceof CaseNormale) {
                            tabJLabel[x][y].setIcon(icoCaseNormale);
                } else if (e instanceof CaseUnique) {
                    if (e.traversable()) {
                        tabJLabel[x][y].setIcon(icoCaseUOk);
                    } else {
                        tabJLabel[x][y].setIcon(icoCaseUPasOk);
                    }
                }
            }
        }


        switch (jeu.getHeros().getOrientation()) {
            case 0:
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroD);
                break;
            case 1:
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroG);
                break;
            case 2:
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroB);
                break;
            case 3:
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroH);
                break;
        }
        for (Pickable element: jeu.getPickables()) {
            if(!element.getUtiliser()) {
                switch (element.getSignature()) {
                    case 0:
                        tabJLabel[element.getX()][element.getY()].setIcon(icoCle);
                        break;
                    case 1:
                        tabJLabel[element.getX()][element.getY()].setIcon(icoEau);
                        break;
                    case 2:
                        tabJLabel[element.getX()][element.getY()].setIcon(icoCoffre);
                        break;
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}
