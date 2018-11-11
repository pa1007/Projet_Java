package test;

import application.Arene;
import application.Dresseur;
import application.Poquemont;
import libtest.Test;
import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;

/**
 * classe de test de la classe Poquemont
 */
public class TestPoquemont {

    /**
     * test appel methodes
     */
    public void test_appelMethodes() {
        Arene     env       = new Arene();
        Dresseur  dresseur  = new Dresseur("Dresseur1", 0, 0, env);
        Dresseur  dresseur2 = new Dresseur("Dresseur2", 0, 0, env);
        Poquemont o         = new Poquemont(1, 1, 2, 3, env);
        boolean   res       = false;

        // on apelle les methodes poquemont
        res = dresseur.prendrePoquemont(o);
        o.changerPosition();
        res = o.etreDepose();
        res = o.etrePris(dresseur);
        int d = o.getDegats();
        int p = o.getPortee();
        dresseur2 = o.getPorteur();
        int pos = o.getPosX();
    }

    /**
     * test le constructeur vide de poquemont
     */
    @Test
    public void test_constructeurVide() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(arene);
        assertEquals("posX initial", 5, poq.getPosX());
        assertEquals("posY initial", 5, poq.getPosY());
        assertEquals("degats initiaux", 2, poq.getDegats());
        assertEquals("portee initiale", 2, poq.getPortee());
        assertEquals("pas de dresseur", null, poq.getPorteur());
    }

    /**
     * test le constructeur non vide de poquemont, cas normal
     */
    @Test
    public void test_constructeurNonVide_OK() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(0, 1, 2, 3, arene);
        assertEquals("posX initial", 0, poq.getPosX());
        assertEquals("posY initial", 1, poq.getPosY());
        assertEquals("degats initiaux", 2, poq.getDegats());
        assertEquals("portee initiale", 3, poq.getPortee());
        assertEquals("pas de dresseur", null, poq.getPorteur());
    }

    /**
     * quand le poquemont peut etre pris, poquemont libre et dresseur
     * bonne case
     */
    @Test
    public void test_etrePris_OK() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(5, 5, 2, 2, arene);
        Dresseur  d     = new Dresseur("toto", 5, 5, arene);
        boolean   res   = poq.etrePris(d);

        assertEquals("resultat doit etre ok", true, res);
        assertEquals("le poquemont doit etre pris", d, poq.getPorteur());
    }

    /**
     * quand le poquemont ne peut pas etre pris, poquemont occupe et
     * dresseur bonne case
     */
    @Test
    public void test_etrePris_nonLibre() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(5, 5, 2, 2, arene);
        Dresseur  d     = new Dresseur("toto", 5, 5, arene);
        boolean   res   = poq.etrePris(d);
        Dresseur  d2    = new Dresseur("toto", 5, 5, arene);
        res = poq.etrePris(d2);

        assertEquals("poquemont pas pris", false, res);
        assertEquals("ancien porteur garde poquemont", d, poq.getPorteur());
    }

    /**
     * quand le poquemont ne peut pas etre pris, poquemont libre et
     * dresseur ailleurs
     */
    @Test
    public void test_etrePris_ailleurs() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(5, 5, 2, 2, arene);
        Dresseur  d     = new Dresseur("toto", 0, 5, arene);
        boolean   res   = poq.etrePris(d);

        assertEquals("poquemont pas pris", false, res);
        assertEquals("poquemont encore libre", null, poq.getPorteur());

        d = new Dresseur("toto", 0, 0, arene);
        res = poq.etrePris(d);

        assertEquals("poquemont pas pris", false, res);
        assertEquals("poquemont encore libre", null, poq.getPorteur());

        d = new Dresseur("toto", 5, 0, arene);
        res = poq.etrePris(d);

        assertEquals("poquemont pas pris", false, res);
        assertEquals("poquemont encore libre", null, poq.getPorteur());
    }

    /**
     * quand le poquemont ne peut pas etre pris, poquemont libre et
     * dresseur dans une autre arene
     */
    public void test_etrePris_autreArene() {
        Arene     arene  = new Arene();
        Poquemont poq    = new Poquemont(5, 5, 2, 2, arene);
        Arene     arene2 = new Arene();
        Dresseur  d      = new Dresseur("toto", 5, 5, arene2);
        boolean   res    = poq.etrePris(d);
        assertEquals("poquemont pas pris", false, res);
        assertEquals("poquement encore libre", null, poq.getPorteur());
    }

    /**
     * quand le dresseur est null
     */
    @Test
    public void test_etrePris_dresseurNull() {
        Arene     arene = new Arene();
        Poquemont poq   = new Poquemont(5, 5, 2, 2, arene);
        Dresseur  d     = new Dresseur("toto", 0, 0, arene);
        boolean   res   = poq.etrePris(null);

        assertEquals("poquemont pas pris", false, res);
    }

    /**
     * Test de etreDepose avec un fonctionnement normal
     */
    public void test_etreDepose_normal() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        Dresseur  d = new Dresseur("t", 5, 5, a);
        d.prendrePoquemont(p);
        assertEquals("Le poquemont dois etre déposer", true, p.etreDepose());
        assertEquals("Le poquemont doit etre libre", null, p.getPorteur());
    }

    /**
     * Test de etreDepose sans avoir de dresseur
     */
    public void test_etreDepose_sansDres() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        assertEquals("Le poquemont dois etre déposer", false, p.etreDepose());
        assertEquals("Le poquemont doit etre libre", null, p.getPorteur());
    }

    /**
     * On regarde si la position du poquemont change quand il est deposer
     */
    public void test_etreDepose_memePos() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        Dresseur  d = new Dresseur("t", 5, 5, a);
        d.prendrePoquemont(p);
        d.seDeplacer(1, 0);
        assertEquals("Le poquemont dois etre déposer", true, p.etreDepose());
        assertEquals("Le poquemont doit etre libre", null, p.getPorteur());
        assertEquals("Pos differente sur axe des X", d.getPosX(), p.getPosX());
        assertEquals("Pos differente sur axe des Y", d.getPosY(), p.getPosY());
    }

    /**
     * Test de changerPosition qui suit le dresseur grâce à la methode {@link Dresseur#seDeplacer(int, int)}
     */
    public void test_changerPosition_suiviDresseur() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        Dresseur  d = new Dresseur("t", 5, 5, a);
        d.prendrePoquemont(p);
        d.seDeplacer(1, 0);
        assertEquals("Pos differente sur axe des X", d.getPosX(), p.getPosX());
        assertEquals("Pos differente sur axe des Y", d.getPosY(), p.getPosY());
    }

    /**
     * Test changer position du poquemont en forcant la position du dresseur avec un setter ({@link Dresseur#setPosX(int)} et {@link Dresseur#setPosY(int)})
     * car dans {@link Dresseur#seDeplacer(int, int)} il y a le changement de position qui s'aupère
     */
    public void test_changerPosition_avecDresseur() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        Dresseur  d = new Dresseur("t", 5, 5, a);
        d.prendrePoquemont(p);
        d.setPosX(6);
        d.setPosY(6);
        p.changerPosition();
        assertEquals("Pos differente sur axe des X", d.getPosX(), p.getPosX());
        assertEquals("Pos differente sur axe des Y", d.getPosY(), p.getPosY());
    }

    /**
     * Test changerPosition sans dresseur <br> Le dresseur et le poquemont ne sont pas relier donc le poq ne bouge pas
     */
    public void test_changerPosition_sansDresseur() {
        Arene     a = new Arene();
        Poquemont p = new Poquemont(a);
        Dresseur  d = new Dresseur("t", 5, 5, a);
        d.seDeplacer(1, 0);
        p.changerPosition();
        assertEquals("Ne bouge pas sur axe des X", 5, p.getPosX());
        assertEquals("Ne bouge pas sur axe des Y", 5, p.getPosY());
    }


    /**
     * methode de lancement des tests
     *
     * @param args
     */
    public static void main(String[] args) {
        lancer(new TestPoquemont(), args);
    }
}
