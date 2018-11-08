package test;

import application.Arene;
import application.Dresseur;
import application.Poquemont;
import libtest.Test;
import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;

/**
 * classe de test de la classe Dresseur
 */
public class TestDresseur {

    /**
     * test appels methodes
     */
    @Test
    public void test_appelMethodes() {
        Arene     env       = new Arene();
        Dresseur  d         = new Dresseur(env);
        Dresseur  dresseur  = new Dresseur("Dresseur1", 0, 0, env);
        Dresseur  dresseur2 = new Dresseur("Dresseur2", 0, 0, env);
        Poquemont o         = new Poquemont(1, 1, 2, 3, env);
        boolean   res       = false;

        // on appelle les methodes de dresseur
        res = dresseur.prendrePoquemont(o);
        res = dresseur.attaquer(dresseur2);
        res = dresseur.deposerPoquemont();
        res = dresseur.etreBlesse();
        dresseur.seDeplacer(2, 0);
        String    n    = dresseur.getNom();
        Poquemont o2   = dresseur.getPoquemontDresse();
        int       pos  = dresseur.getPosX();
        int       posY = dresseur.getPosY();
        int       pv   = dresseur.getPv();
        assertEquals("devrait avoir 5pv", 5, pv);

        dresseur.subirDegats(1);
        Arene a = dresseur.getArene();
    }

    /**
     * test constructeur vide
     */
    @Test
    public void test_constructeurVide() {
        Arene    arene = new Arene();
        Dresseur d     = new Dresseur(arene);

        assertEquals("nom defaut", "Sacha", d.getNom());
        assertEquals("pv defaut", 5, d.getPv());
        assertEquals("posX defaut", 0, d.getPosX());
        assertEquals("posY defaut", 0, d.getPosY());
        assertEquals("pas de poquemont par defaut", null, d.getPoquemontDresse());
    }

    /**
     * cas normal
     */
    @Test
    public void test_constructeur_normal() {
        Arene    arene = new Arene();
        Dresseur d     = new Dresseur("toto", 1, 2, arene);

        assertEquals("nom defaut", "toto", d.getNom());
        assertEquals("pv defaut", 5, d.getPv());
        assertEquals("posX defaut", 1, d.getPosX());
        assertEquals("posY defaut", 2, d.getPosY());
        assertEquals("pas de poquemont par defaut", null, d.getPoquemontDresse());
    }

    /**
     * Le poquemont et le dresseur sont a la meme postion et meme arene
     */
    public void test_prendrePoquemont_priseOK() {
        Arene     env = new Arene();
        Dresseur  d   = new Dresseur("François", 5, 5, env);
        Poquemont pq  = new Poquemont(env);
        assertEquals("Le dressuer aurais du prendre un poqemont", true, d.prendrePoquemont(pq));
        assertEquals("La reference du poquemont devrai etre la meme", true, d.getPoquemontDresse() == pq);
    }

    /**
     *
     */
    public void test_prendrePoquemont_difAreneFalse() {
        Arene     env  = new Arene();
        Arene     env2 = new Arene();
        Dresseur  d    = new Dresseur("François", 5, 5, env);
        Poquemont pq   = new Poquemont(env);
        assertEquals("Le dressuer aurais du prendre un poqemont", true, d.prendrePoquemont(pq));
        assertEquals("La reference du poquemont devrai etre la meme", true, d.getPoquemontDresse() == pq);
    }

    public void test_seDeplacer_normal() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(0, 1);
        assertEquals("Le dresseur devrai etre en sur l'axe des X", 0, d.getPosX());
        assertEquals("Le dresseur devrai etre en sur l'axe des Y", 1, d.getPosY());
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur devrai etre en sur l'axe des X", 1, d.getPosX());
        assertEquals("Le dresseur devrai etre en sur l'axe des Y", 1, d.getPosY());
    }

    public void test_seDeplacer_sup2() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(1, 1);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    public void test_seDeplacer_supAsolu() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(-17, 18);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    public void test_seDeplacer_horsZone() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(100, 100);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    public void test_seDeplacer_dansMur() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(1, 0);
        d.seDeplacer(1, 0);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur ne devrai pas bouger", 2, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    public void test_seDeplacer_blesser() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.subirDegats(5);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    public void test_seDeplacer_avecPoq() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur("tot", 5, 5, env);
        d.prendrePoquemont(pq);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur devrai bouger sur l'axe des Y", 6, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger sur l'axe des Y", 5, d.getPosY());
        assertEquals("Le poquemont devrait bouger sur l'axe des X", 6, d.getPoquemontDresse().getPosX());
        assertEquals("Le poquemont ne devrait pas bouger sur l'axe des Y", 5, d.getPoquemontDresse().getPosY());
    }

    public void test_prendePoquemont_PoqNull() {
        Arene     env = new Arene();
        Poquemont pq  = null;
        Dresseur  d   = new Dresseur(env);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
    }

    public void test_prendePoquemont_PoqDiffPos() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur(env);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
    }

    public void test_prendePoquemont_DejaPoquemont() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Poquemont pq2 = new Poquemont(5, 5, 7, 7, env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(pq2);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
        assertEquals("Le poquemont devrait etre le 1er poquemont", pq, d.getPoquemontDresse());
    }

    public void test_prendrePoquemont_dBlesser() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.subirDegats(5);
        assertEquals("Le dresseur ne devrai pas prendre le poquemont", false, d.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    public void test_prendrePoquemont_dejaDresseur() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        Dresseur  df  = new Dresseur("t", 5, 5, env);
        assertEquals("Le dresseur devrai prendre le poquemont", true, d.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas prendre le poquemont", false, df.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, df.getPoquemontDresse());
    }


    public void test_deposerPoquemont_sansPoq() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur("t", 5, 5, env);
        assertEquals("Le dresseur ne devrai pas déposer le poquemont", false, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    public void test_deposerPoquemont_normal() {
        Arene     env = new Arene();
        Poquemont p   = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(p);
        assertEquals("Le dresseur devrai déposer le poquemont", true, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    public void test_deposerPoquemont_memePos() {
        Arene     env = new Arene();
        Poquemont p   = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(p);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur devrai déposer le poquemont", true, d.deposerPoquemont());
        assertEquals("la position du poquemont devrais avoir changer sur l'axe des X", d.getPosX(), p.getPosX());
        assertEquals("la position du poquemont ne devrais pas avoir changer sur l'axe des Y", d.getPosY(), p.getPosY());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    public void test_deposerPoquemont_blesser() {
        Arene     env = new Arene();
        Poquemont p   = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(p);
        d.subirDegats(5);
        assertEquals("Le dresseur devrai déposer le poquemont", false, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", p, d.getPoquemontDresse());
    }

    public void test_subirDegats_negatif() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(-2);
        assertEquals("Le dresseur devrai ne subir aucun dégat", 5, d.getPv());
    }

    public void test_subirDegats_normal() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(4);
        assertEquals("Le dresseur devrai subir 4 point de dégat", 1, d.getPv());
    }

    public void test_subirDegats_supBlesser() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(9);
        assertEquals("Le dresseur devrai subir que 5 point de dégat", 0, d.getPv());
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    public void test_subirDegats_supBlesserEn2Temps() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(5);
        d.subirDegats(2);
        assertEquals("Le dresseur devrai subir que 5 point de dégat", 0, d.getPv());
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    public void test_etreBlesser_blesser0() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(5);
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    public void test_etreBlesser_pasBlesser() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(4);
        assertEquals("Le dresseur devrai etre besser", false, d.etreBlesse());
    }

    public void test_getDegats_sansPoq() {
        Dresseur d = new Dresseur(new Arene());
        assertEquals("Le dresseur devrai avoir 1 d'attaque", 1, d.getDegats());
    }

    /**
     * methode de lancement des tests
     *
     * @param args
     */
    public static void main(String[] args) {
        lancer(new TestDresseur(), args);
    }
}
