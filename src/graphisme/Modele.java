package graphisme;

import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import application.Poquemont;
import application.Dresseur;
import application.Arene;

/**
 * classe destinee a modeliser un environnement constitue
 * - de plusieurs dresseurs
 * - de plusieurs poquemont
 * 
 * c'est un observable pour gerer l'affichage
 * 
 */
public class Modele extends Observable {

	/**
	 * l'ensemble des dresseurs du monde
	 */
	Dresseur[] dresseurs;

	/**
	 * l'ensemble des poquemont du monde
	 */
	Poquemont[] poquemonts;

	/**
	 * l'environnement
	 */
	Arene env;

	/**
	 * constructeur de monde
	 * 
	 * @param nbDresseurs
	 *            le nombre de dresseurs
	 * @param nbPoq
	 *            le nombre de poquemonts
	 */
	public Modele(int nbDresseurs, int nbPoq) {
		// creer le labyrinthe
		env = new Arene();

		// modele
		initialiserDresseurs(nbDresseurs);
		initialisePoquemonts(nbPoq);
	}

	/**
	 * appelle la construction d'une interface graphique avec les
	 * boutons
	 */
	public void creerInterface() {
		Gui f = new Gui(this);
	}

	/**
	 * permet d'initialiser les poquemonts dans le monde
	 * 
	 * @param nbPoq
	 *            le nombre de poquemonts a construire
	 */
	private void initialisePoquemonts(int nbPoq) {
		if (nbPoq < 1)
			nbPoq = 1;
		this.poquemonts = new Poquemont[nbPoq];
		for (int i = 0; i < nbPoq; i++) {
			int posX = -1;
			int posY = -1;
			while (!env.etreAccessible(posX, posY)) {
				posX = (int) (Math.random() * env.getTailleX());
				posY = (int) (Math.random() * env.getTailleY());
			}
			this.poquemonts[i] = new Poquemont(posX, posY, 2, 2, env);
		}
	}

	/**
	 * permet d'initialiser les dresseurs du monde
	 * 
	 * @param nbDresseur
	 *            le nombre de dresseurs a construire
	 */
	private void initialiserDresseurs(int nbDresseur) {
		if (nbDresseur < 1)
			nbDresseur = 1;
		this.dresseurs = new Dresseur[nbDresseur];
		for (int i = 0; i < nbDresseur; i++) {
			// on genere sur une case valide au hasard
			int posX = -1;
			int posY = -1;
			while (!env.etreAccessible(posX, posY)) {
				posX = (int) (Math.random() * env.getTailleX());
				posY = (int) (Math.random() * env.getTailleY());
			}
			this.dresseurs[i] = new Dresseur("D_" + i, posX, posY, env);
		}
	}

	/**
	 * methode pour deplacer un dresseur du monde
	 * On passe par le modele pour mettre a jour la vue
	 * 
	 * @param num
	 *            le numero du dresseur a deplacer
	 * @param dx
	 *            le deplacement du dresseur
	 * @param dy
	 *            le deplacement du dresseur
	 */
	public void deplacerDresseur(int num, int dx, int dy) {
		// pour afficher le sens
		String sens = "gauche";
		if (dx > 0)
			sens = "droite";

		// si le dresseur est un dresseur valide
		if ((num >= 0) && (num < this.dresseurs.length)) {
			this.dresseurs[num].seDeplacer(dx, dy);
			System.out.println(dresseurs[num].getNom() + ": deplacement " + "->"
					+ dresseurs[num].getPosX() + "," + dresseurs[num].getPosY());
			this.setChanged();
		}
		this.notifyObservers();
	}

	/**
	 * methode permettant a un dresseur de prendre un poquemont
	 * 
	 * on passe par le modele pour gerer la mise a jour de la vue
	 * 
	 * @param num
	 *            le numero du dresseur qui fait l'action
	 */
	public void dresseurPrendre(int num) {
		boolean vu = false;
		// on cherche s'il y a un poquemont sur la case
		for (Poquemont poq : poquemonts) {
			// si le poquemont est au meme endroit et qu'il est
			// disponible
			if (etreMemeEndroit(num, poq) && (poq.getPorteur() == null)) {
				// un poquemont potentielle
				vu = true;
				boolean res = this.dresseurs[num].prendrePoquemont(poq);
				// affichage des consequences de l'action
				if (res) {
					System.out.println(dresseurs[num].getNom() + ": poquemont dresse");
				} else {
					System.out.println(dresseurs[num].getNom() + ": prise impossible");
				}
				setChanged();
				break;
			}
		}
		if (!vu)
			System.out.println("robot " + num + ": pas de poquemont");

		notifyObservers();

	}

	
	/**
	 * permet de savoir si le poquemont et le dresseur se trouvent au meme endroit
	 * 
	 * @param num le numero du dresseur
	 * @param poq le poquemont concerne
	 * @return true si et seulement si leur x et y sont identiques
	 */
	private boolean etreMemeEndroit(int num, Poquemont poq) {
		return (poq.getPosX() == this.dresseurs[num].getPosX())&&(poq.getPosY() == this.dresseurs[num].getPosY());
	}

	/**
	 * methode permettant a un dresseur de poser un poquemont
	 * on passe par modele pour gerer la vue
	 * 
	 * @param num
	 *            le numero du dresseur qui fait l'action
	 */
	public void dresseurPoser(int num) {
		boolean res = this.dresseurs[num].deposerPoquemont();
		if (res) {
			System.out.println(dresseurs[num].getNom() + ": poquemont pose");
		} else {
			System.out.println(dresseurs[num].getNom() + ": pose impossible");
		}
		setChanged();
		notifyObservers();

	}

	/**
	 * methode permettant a un dresseur d'attaquer le dresseur +
	 * proche
	 * 
	 * @param num
	 *            le numero du dresseur qui fait l'action
	 */
	public void dresseurAttaquer(int num) {

		// distance max est 100
		int distance = 100;
		int sauv = -1;

		for (int i = 0; i < dresseurs.length; i++) {
			if (i != num) {
				int ndist = dresseurs[num].getDistance(dresseurs[i]);
				if (ndist < distance) {
					distance = ndist;
					sauv = i;
				}
			}
		}

		boolean res = dresseurs[num].attaquer(dresseurs[sauv]);
		System.out.println(dresseurs[num].getNom() + " : attaque " + dresseurs[sauv].getNom()
				+ " distance de " + distance + " - resultat " + res);

		setChanged();
		notifyObservers();

	}
}
