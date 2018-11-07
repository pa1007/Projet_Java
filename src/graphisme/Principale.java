package graphisme;
import java.util.Scanner;

/**
 * classe principale
 * 
 * permet de lancer une interface graphique
 * 
 * @author vthomas
 *
 */
public class Principale {

	public static void main(String[] args) {
		int nombreDresseurs = 2;
		int nombrePoquemonts = 4;
		Modele m = new Modele(nombreDresseurs, nombrePoquemonts);
		m.creerInterface();
		System.out.println("Systeme operationnel");
	}
}
