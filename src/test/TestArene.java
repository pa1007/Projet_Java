package test;

import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;
import libtest.*;

import application.Arene;

/**
 * classe de test qui permet de verifier que la classe Arene
 * fonctionne correctement
 */
public class TestArene {

	/**
	 * methode de lancement des tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		lancer(new TestArene(), args);
	}

	/**
	 * test du constructeur
	 */
	@Test
	public void test_constructeurArene() {
		Arene arene = new Arene();
		assertEquals("taille de 10 selon X", 10, arene.getTailleX());
		assertEquals("taille de 10 selon Y", 10, arene.getTailleY());
	}

	/**
	 * test de la methode etreAccessible pour les murs
	 */
	@Test
	public void test_etreAccessibleArene() {
		Arene arene = new Arene();

		// utilise un tableau a deux dimensions
		int[][] testMur = {
				// colonne 0
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 1
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 2
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 3
				{ 1, 1, 1, 0, 0, 0, 0, 1, 1, 1 },
				// colonne 4
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 5
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 6
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 7
				{ 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
				// colonne 8,
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				// colonne 9
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		// on teste en utilisant ce tableau
		// (0 doit etre vide, 1 doit etre mur)
		for (int j = 0; j < testMur.length; j++)
			for (int i = 0; i < testMur[0].length; i++) {
				boolean valeurCase = arene.etreAccessible(i, j);
				// si cela doit etre vide
				if (testMur[i][j] == 0) {
					String message = "case (" + i + "," + j + ") devrait etre vide";
					assertEquals(message, true, valeurCase);
				}
				// si cela doit etre un mur
				else {
					String message = "case (" + i + "," + j + ") devrait etre un mur";
					assertEquals(message, false, valeurCase);
				}
			}
	}

	/**
	 * teste etreAccessible quand on sort de l'arene
	 */
	@Test
	public void test_etreAccessible_dehorsMur() {
		Arene arene = new Arene();
		boolean etreAccessible = arene.etreAccessible(-1, 0);
		String erreur = "(-1,0) devrait etre inaccessible";
		assertEquals(erreur, false, etreAccessible);

		boolean etreAccessible2 = arene.etreAccessible(0, -1);
		String erreur2 = "(0,-1) devrait etre inaccessible";
		assertEquals(erreur2, false, etreAccessible2);

		int tailleY = arene.getTailleY();
		boolean etreAccessible3 = arene.etreAccessible(0, tailleY);
		String erreur3 = "(0,tailleY) devrait etre inaccessible";
		assertEquals(erreur3, false, etreAccessible3);

		int tailleX = arene.getTailleX();
		boolean etreAccessible4 = arene.etreAccessible(tailleX, 0);
		String erreur4 = "(tailleX,0) devrait etre inaccessible";
		assertEquals(erreur4, false, etreAccessible4);
	}

}
