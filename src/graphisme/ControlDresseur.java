package graphisme;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import application.Dresseur;
import application.Poquemont;

/**
 * permet de controler un dresseur
 * 
 * @author vthomas
 *
 */

public class ControlDresseur extends JPanel implements Observer {

	/**
	 * le modele manipule
	 */
	private Modele modele;

	/**
	 * l'indice du dresseur manipule
	 */
	private int indiceDresseur;

	/**
	 * couleur du dresseur
	 */
	private String textCoul;

	private JTextPane descriptif;

	/**
	 * constructeur de controleur
	 * 
	 * @param i
	 *            indice du dresseur
	 * @param m
	 *            modele correspondant (pour observable)
	 */
	public ControlDresseur(Modele m, int i) {
		// on ajoute l'observeur pour mettre a jour
		m.addObserver(this);

		textCoul = Integer.toHexString(AfficheurDresseur.cols[i].getRGB());
		textCoul = textCoul.substring(2);

		// ajoute un composant pour la place derriere
		JPanel p = new JPanel();
		this.add(p);

		// mise a jour des donnees
		this.indiceDresseur = i;
		this.modele = m;

		// creation du JPanel
		p.setPreferredSize(new Dimension(200, 600));
		p.setLayout(new BorderLayout());

		// creation JLabel au dessus
		String nom = this.modele.dresseurs[i].getNom() + "\n";
		descriptif = new JTextPane();
		descriptif.setContentType("text/html");
		descriptif.setPreferredSize(new Dimension(200, 300));
		descriptif.setEditable(false);
		p.add(descriptif, BorderLayout.NORTH);

		// met ajour contenu
		update(null, null);

		// ajoute les boutons deplacement
		JPanel boutonsDep = new JPanel();
		boutonsDep.setPreferredSize(new Dimension(200, 200));
		boutonsDep.setSize(new Dimension(200, 200));

		// ajoute les boutons des dresseurs
		boutonsDep.setLayout(new GridLayout(3, 3));
		// boutons
		boutonsDep.add(new JPanel());
		boutonsDep.add(creerBoutonHaut());
		boutonsDep.add(new JPanel());
		boutonsDep.add(creerBoutonGauche());
		boutonsDep.add(new JPanel());
		boutonsDep.add(creerBoutonDroite());
		boutonsDep.add(new JPanel());
		boutonsDep.add(creerBoutonBas());
		boutonsDep.add(new JPanel());
		p.add(boutonsDep, BorderLayout.CENTER);

		// ajoute les boutons
		JPanel boutons = new JPanel();
		boutons.setPreferredSize(new Dimension(200, 100));
		// ajoute les boutons des dresseurs
		boutons.setLayout(new GridLayout(4, 1));
		// boutons
		boutons.add(creerBoutonPrendre());
		boutons.add(creerBoutonAttaquer());
		boutons.add(creerBoutonDeposer());
		p.add(boutons, BorderLayout.SOUTH);

	}

	private Component creerBoutonHaut() {
		JButton b = new JButton("Haut");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.deplacerDresseur(indiceDresseur, 0, -1);

			}
		});
		return (b);
	}

	private Component creerBoutonBas() {
		JButton b = new JButton("Bas");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.deplacerDresseur(indiceDresseur, 0, 1);

			}
		});
		return (b);
	}

	/**
	 * methode pour ajouter un bouton aller a droite
	 * 
	 * @return un bouton pour commander le robot
	 */
	private JButton creerBoutonDroite() {
		JButton b = new JButton(">>");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.deplacerDresseur(indiceDresseur, 1, 0);

			}
		});
		return (b);
	}

	/**
	 * methode pour creer un nouton pour prendre une brique
	 * 
	 * @param i
	 *            le numero du robot considere
	 * @return le bouton pour commander robot i
	 */
	private JButton creerBoutonPrendre() {
		JButton b = new JButton("Prendre");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.dresseurPrendre(indiceDresseur);

			}
		});
		return (b);
	}

	/**
	 * methode pour creer un bouton pour prendre une brique
	 * 
	 * @return le bouton pour commander robot i
	 */
	private JButton creerBoutonAttaquer() {
		JButton b = new JButton("Attaquer+Proche");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.dresseurAttaquer(indiceDresseur);

			}
		});
		return (b);
	}

	/**
	 * creer un bouton pour deposer une brique
	 * 
	 * @return le bouton pour commande robot i
	 */
	private JButton creerBoutonDeposer() {
		JButton b = new JButton("Poser");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.dresseurPoser(indiceDresseur);
			}
		});
		return (b);
	}

	/**
	 * creer un bouton pour deplacer a gauche
	 * 
	 * @param i
	 *            le robot a deplacer
	 * @return le bouton pour controler le robot i
	 */
	private JButton creerBoutonGauche() {
		JButton b = new JButton("<<");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.deplacerDresseur(indiceDresseur, -1, 0);

			}
		});
		return (b);
	}

	@Override
	public void update(Observable o, Object arg) {
		// System.out.println("mettre a jour JText");

		// on construit le message
		String message = "";
		Dresseur dresseur = this.modele.dresseurs[indiceDresseur];
		System.out.println(textCoul.substring(0, 6));
		message += "<html><center><b><font color='#" + textCoul + "'> *** " + dresseur.getNom();
		if (dresseur.etreBlesse())
			message += " - (blesse)";
		message += " *** </font></b></center>";

		message += "<ul>";
		message += "<li>Position: (" + dresseur.getPosX() + "," + dresseur.getPosY() + ")";
		message += "<li>Points Vie: " + dresseur.getPv();

		// s'il possede un poquemnt
		Poquemont poquemontDresse = dresseur.getPoquemontDresse();
		if (poquemontDresse == null) {
			message += "<li>Pas de Poquemont";
		} else {
			message += "<li>Poquemont: ";
			message += "<ul>";
			// message += "<li> nom: "+poquemontDresse.getNom();
			message += "<li> degat: " + poquemontDresse.getDegats();
			message += "<li> portee: " + poquemontDresse.getPortee();
			message += "</ul>";
		}
		message += "</ul></html>";

		// mettre a jour
		this.descriptif.setText(message);

	}

}
