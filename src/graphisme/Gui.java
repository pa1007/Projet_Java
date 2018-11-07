package graphisme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * classe chargee d'afficher la fenetre avec l'afficheur et les
 * boutons.
 * 
 * @author vthomas
 *
 */
public class Gui {

	/**
	 * le modele a afficher
	 */
	private Modele m;

	/**
	 * le constructeur de fenetre
	 * 
	 * @param modele
	 *            le modele a afficher
	 */
	public Gui(Modele modele) {
		this.m = modele;

		// creation de la frame
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// creation du Panel
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// creer observer et fait le lien
		AfficheurDresseur affiche = new AfficheurDresseur(m);
		m.addObserver(affiche);
		panel.add(affiche, BorderLayout.CENTER);

		// ajoute un panel sur la droite
		panel.add(new ControlDresseur(modele, 0), BorderLayout.WEST);
		panel.add(new ControlDresseur(modele, 1), BorderLayout.EAST);

		// ajout du panel
		f.setContentPane(panel);
		f.pack();
		f.setVisible(true);
	}
}
