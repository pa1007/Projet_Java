package graphisme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import application.Poquemont;
import application.Dresseur;

/**
 * classe qui permet d'afficher un modele constitue de plusieurs
 * dresseurs
 * 
 * @author vthomas
 * 
 */
public class AfficheurDresseur extends JPanel implements Observer {

	/**
	 * constante correspondant la taille des cases
	 */
	public final static int TAILLE = 50;

	/**
	 * constantes correspondant aux couleurs utilisees
	 */
	static final Color cols[] = { Color.green, Color.red, Color.blue, Color.gray };
	/**
	 * le modele a afficher
	 */
	private Modele m = null;

	/**
	 * construit un afficheur de dresseurs a partir du modele
	 * 
	 * @param modele
	 *            modele a afficher
	 */
	public AfficheurDresseur(Modele modele) {
		// recuperation du modele
		this.m = modele;
		// initialisation du Panel
		int tailleX = m.env.getTailleX();
		int tailleY = m.env.getTailleY();
		Dimension taille = new Dimension(TAILLE * tailleX + 1, TAILLE * tailleY + 1);
		this.setPreferredSize(taille);
	}

	/**
	 * mis a jour de l'affichage
	 * - affiche les dresseurs
	 * - affiche les poquemont
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// dessine arene et labyrinthe
		dessinerArene(g);

		// dessin dresseurs
		int i = 0;
		for (Dresseur dresseur : m.dresseurs) {
			System.out.println(i);
			g.setColor(getCouleur(i));
			dessinerDresseur(g, dresseur);
			i++;
		}

		// dessin poquemonts
		for (Poquemont poq : m.poquemonts) {
			dessinerPoquemont(g, poq);
		}
	}

	private void dessinerArene(Graphics g) {
		// dessin arene
		g.setColor(Color.black);
		int tailleY = TAILLE * m.dresseurs.length * 2 + TAILLE;
		g.drawRect(0, 0, m.env.getTailleX() * TAILLE, m.env.getTailleY() * TAILLE);

		// pour chaque case
		for (int i = 0; i < m.env.getTailleX(); i++)
			for (int j = 0; j < m.env.getTailleY(); j++) {
				if (!m.env.etreAccessible(i, j))
					g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
				else
					g.drawRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
			}

	}

	private void dessinerPoquemont(Graphics g, Poquemont b) {
		int t = TAILLE;

		// decalage x et y
		int dx = b.getPosX() * t + t / 4;
		int dy = b.getPosY() * t + t / 4;

		// la couleur du poquemont
		Color coulPoq = Color.yellow;

		// si le poquemont est porte
		if (b.getPorteur() != null) {
			coulPoq = Color.orange;
			dy = dy - t / 4;
		}

		// dessin oreille
		g.setColor(coulPoq);
		g.fillOval(t / 4 + dx, 0 + dy, t / 4, t / 2);
		g.fillOval(t / 2 + dx, 0 + dy, t / 4, t / 2);
		g.setColor(Color.black);
		g.drawOval(t / 4 + dx, 0 + dy, t / 4, t / 2);
		g.drawOval(t / 2 + dx, 0 + dy, t / 4, t / 2);

		// dessine corps
		g.setColor(coulPoq);
		g.fillOval(t / 4 + dx, t / 4 + dy, t / 2, t / 2);
		g.setColor(Color.black);
		g.drawOval(t / 4 + dx, t / 4 + dy, t / 2, t / 2);

		// dessine yeux
		int oeil = 8;
		g.setColor(Color.BLUE);
		g.fillOval(t / 2 + dx - oeil + 1, t / 2 + dy - oeil, oeil, oeil);
		g.fillOval(t / 2 + dx - 1, t / 2 + dy - oeil, oeil, oeil);

		// dessine message si il n'est pas pris
		if (b.getPorteur() == null) {
			g.setColor(Color.BLACK);
			String message = "d:" + b.getDegats() + " p:" + b.getPortee();
			g.drawString(message, dx, b.getPosY() * t + 10);
		}
	}

	private void dessinerDresseur(Graphics g, Dresseur dresseur) {
		int t = TAILLE;
		int decaly = dresseur.getPosY() * TAILLE;
		int decalx = dresseur.getPosX() * TAILLE;

		// dessin du corps
		if (dresseur.etreBlesse())
			g.setColor(Color.LIGHT_GRAY);
		g.fillOval(decalx, decaly, t, t);
		g.setColor(Color.black);
		g.drawOval(decalx, decaly, t, t);

		// dessin de la tete
		g.setColor(Color.PINK);
		if (dresseur.etreBlesse())
			g.setColor(Color.LIGHT_GRAY);
		g.fillOval(decalx + t / 4, decaly - t / 4, t / 2, t / 2);
		g.setColor(Color.black);
		g.drawOval(decalx + t / 4, decaly - t / 4, t / 2, t / 2);

		// yeux
		int oeil = 8;
		g.setColor(Color.white);
		g.fillOval(decalx + t / 2 - oeil, decaly - oeil / 2, oeil, oeil);
		g.fillOval(decalx + t / 2, decaly - oeil / 2, oeil, oeil);
		g.setColor(Color.black);
		g.drawOval(decalx + t / 2 - oeil, decaly - oeil / 2, oeil, oeil);
		g.drawOval(decalx + t / 2, decaly - oeil / 2, oeil, oeil);

		oeil = 4;
		g.fillOval(decalx + t / 2 - oeil, decaly - oeil / 2, oeil, oeil);
		g.fillOval(decalx + t / 2, decaly - oeil / 2, oeil, oeil);

		// texte corps
		g.setColor(Color.black);
		g.drawString(dresseur.getNom(), decalx + 5, decaly + t - 20);
	}

	@Override
	/**
	 * methode de mise a jour quand observer est notifie
	 */
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	/**
	 * methode qui retourne la couleur souhaitee
	 * 
	 * @param i
	 *            indice de la couleur
	 * @return couleur pour afficher
	 */
	public Color getCouleur(int i) {
		if (i < 0)
			i = 0;
		int num = i % cols.length;
		return (cols[num]);
	}

}
