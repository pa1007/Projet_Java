package graphisme;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Gere le panneau de controleurs
 * 
 * @author vthomas
 *
 */
public class Controleurs extends JPanel {

	/**
	 * creer un controleur a partir d'un modele
	 */
	public Controleurs(Modele m) {
		this.setPreferredSize(new Dimension(200, 800));
		for (int i = 0; i < m.dresseurs.length; i++) {
			this.add(new ControlDresseur(m, i));
		}
	}

}
