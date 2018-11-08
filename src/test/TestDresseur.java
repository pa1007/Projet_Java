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
     * Le poquemont et le dresseur sont a la meme postion mais pas la meme arene
     */
    public void test_prendrePoquemont_difAreneFalse() {
        Arene     env  = new Arene();
        Arene     env2 = new Arene();
        Dresseur  d    = new Dresseur("François", 5, 5, env);
        Poquemont pq   = new Poquemont(env2);
        assertEquals("Le dressuer aurais du prendre un poqemont", false, d.prendrePoquemont(pq));
        assertEquals("La reference du poquemont devrai etre la meme", null, d.getPoquemontDresse());
    }

    /**
     * dresseur qui marche de 1 en 1 normalement
     */
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

    /**
     * Dresseur qui se déplace de 2 case
     */
    public void test_seDeplacer_sup2() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(1, 1);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    /**
     * Dresseur qui se deplace de plusieurs case (Addtion =1 et valeur absolue de l'addtion !=1)
     */
    public void test_seDeplacer_supAsolu() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(-17, 18);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    /**
     * Le dresseur tente de sortir de la carte
     */
    public void test_seDeplacer_horsZone() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(-1, 0);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    /**
     * se deplacer dans un mur (3,0)
     */
    public void test_seDeplacer_dansMur() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.seDeplacer(1, 0);
        d.seDeplacer(1, 0);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur ne devrai pas bouger", 2, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    /**
     * Se deplacer en étant blesser
     */
    public void test_seDeplacer_blesser() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur(env);
        d.subirDegats(5);
        d.seDeplacer(1, 0);
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosX());
        assertEquals("Le dresseur ne devrai pas bouger", 0, d.getPosY());
    }

    /**
     * se deplacer avec un poq qui change c'est coordonée
     */
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

    /**
     * prendre un poq null
     */
    public void test_prendePoquemont_PoqNull() {
        Arene     env = new Arene();
        Poquemont pq  = null;
        Dresseur  d   = new Dresseur(env);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
    }

    /**
     * prendre poq avec des pos differentes
     */
    public void test_prendePoquemont_PoqDiffPos() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur(env);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
    }

    /**
     * Prendre un poqemont en ayant deja un poq
     */
    public void test_prendePoquemont_DejaPoquemont() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Poquemont pq2 = new Poquemont(5, 5, 7, 7, env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(pq2);
        assertEquals("le poquemont ne drevrai paas etre pris", false, d.prendrePoquemont(pq));
        assertEquals("Le poquemont devrait etre le 1er poquemont", pq2, d.getPoquemontDresse());
    }

    /**
     * prendre un poq en etant blesser
     */
    public void test_prendrePoquemont_dBlesser() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.subirDegats(5);
        assertEquals("Le dresseur ne devrai pas prendre le poquemont", false, d.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    /**
     * prendre un poq qui a deja un dresseur
     */
    public void test_prendrePoquemont_dejaDresseur() {
        Arene     env = new Arene();
        Poquemont pq  = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        Dresseur  df  = new Dresseur("t", 5, 5, env);
        assertEquals("Le dresseur devrai prendre le poquemont", true, d.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas prendre le poquemont", false, df.prendrePoquemont(pq));
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, df.getPoquemontDresse());
    }

    /**
     * deposer un poquemont sans poquemont dresser
     */
    public void test_deposerPoquemont_sansPoq() {
        Arene    env = new Arene();
        Dresseur d   = new Dresseur("t", 5, 5, env);
        assertEquals("Le dresseur ne devrai pas déposer le poquemont", false, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    /**
     * deposer un poquemont cas normal
     */
    public void test_deposerPoquemont_normal() {
        Arene     env = new Arene();
        Poquemont p   = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(p);
        assertEquals("Le dresseur devrai déposer le poquemont", true, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", null, d.getPoquemontDresse());
    }

    /**
     * Deposer un poquemont cas normal mais avec test de la position
     */
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

    /**
     * deposer un poquemont en etant blesser
     */
    public void test_deposerPoquemont_blesser() {
        Arene     env = new Arene();
        Poquemont p   = new Poquemont(env);
        Dresseur  d   = new Dresseur("t", 5, 5, env);
        d.prendrePoquemont(p);
        d.subirDegats(5);
        assertEquals("Le dresseur devrai déposer le poquemont", false, d.deposerPoquemont());
        assertEquals("Le dresseur ne devrai pas avoir de poquemont", p, d.getPoquemontDresse());
    }

    /**
     * subir des degats negatif
     */
    public void test_subirDegats_negatif() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(-2);
        assertEquals("Le dresseur devrai ne subir aucun dégat", 5, d.getPv());
    }

    /**
     * subir 4 points de dégat
     */
    public void test_subirDegats_normal() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(4);
        assertEquals("Le dresseur devrai subir 4 point de dégat", 1, d.getPv());
    }

    /**
     * Subir dégats >5
     */
    public void test_subirDegats_supBlesser() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(9);
        assertEquals("Le dresseur devrai subir que 5 point de dégat", 0, d.getPv());
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    /**
     * Subir des degats >5 en 2 temps
     */
    public void test_subirDegats_supBlesserEn2Temps() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(5);
        d.subirDegats(2);
        assertEquals("Le dresseur devrai subir que 5 point de dégat", 0, d.getPv());
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    /**
     * etre blesser a 0 pv
     */
    public void test_etreBlesser_blesser0() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(5);
        assertEquals("Le dresseur devrai etre besser", true, d.etreBlesse());
    }

    /**
     * test etreBlesser sans entre blesser car reste 1pv
     */
    public void test_etreBlesser_pasBlesser() {
        Dresseur d = new Dresseur(new Arene());
        d.subirDegats(4);
        assertEquals("Le dresseur devrai etre besser", false, d.etreBlesse());
    }

    /**
     * Avoir les degat du dresseur (Sans poquemont)
     */
    public void test_getDegats_sansPoq() {
        Dresseur d = new Dresseur(new Arene());
        assertEquals("Le dresseur devrai avoir 1 d'attaque", 1, d.getDegats());
    }

    /**
     * Avoir les degat avec poquemont
     */
    public void test_getDegats_avecPoq() {
        Arene     a = new Arene();
        Dresseur  d = new Dresseur("t", 5, 5, a);
        Poquemont p = new Poquemont(a);
        d.prendrePoquemont(p);
        assertEquals("Le dresseur devrai avoir 1 d'attaque", p.getDegats(), d.getDegats());
    }

    /**
     * Avoir la portee du dresseur (Sans poquemont)
     */
    public void test_getPortee_sansPoq() {
        Arene    a = new Arene();
        Dresseur d = new Dresseur("t", 5, 5, a);
        assertEquals("Le dresseur devrai avoir 1 d'attaque", 1, d.getPortee());
    }

    /**
     * Avoir la portee du pouqemont
     */
    public void test_getPortee_avecPoq() {
        Arene     a = new Arene();
        Dresseur  d = new Dresseur("t", 5, 5, a);
        Poquemont p = new Poquemont(a);
        d.prendrePoquemont(p);
        assertEquals("Le dresseur devrai avoir 1 d'attaque", p.getPortee(), d.getPortee());
    }

    /**
     * Avoir la distance entre 2 dresseurs de la même arene
     */
    public void test_getDistance_Normal() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur("t", 5, 5, a);
        Dresseur d2 = new Dresseur(a);
        assertEquals("La distance est mal calculée", 10, d.getDistance(d2));
    }

    /**
     * Avoir la distance entre 1 dresseur et 1 null
     */
    public void test_getDistance_dresseur2Null() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur("t", 5, 5, a);
        Dresseur d2 = null;
        assertEquals("la dstance devrai etre négatif car 2eme dresseur null", -1, d.getDistance(d2));
    }

    /**
     * Avoir la distance entre 2 dresseur d'arene diferentes
     */
    public void test_getDistance_pasMemeArene() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur("t", 5, 5, a);
        Dresseur d2 = new Dresseur(new Arene());
        assertEquals("la dstance devrai etre négatif car 2eme dresseur pas dans la meme arene", -1, d.getDistance(d2));
    }

    /**
     * Avoir la distance entre le meme dresseur
     */
    public void test_getDistance_memeDresseur() {
        Arene    a = new Arene();
        Dresseur d = new Dresseur("t", 5, 5, a);
        assertEquals("la dstance devrai etre null car même dresseur", 0, d.getDistance(d));
    }

    /**
     * Avoir la distance entre 2 dresseurs a la meme pos
     */
    public void tesst_getDistance_memePos() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur(a);
        Dresseur d2 = new Dresseur(a);
        assertEquals("la dstance devrai etre null car même pos", 0, d.getDistance(d2));
    }

    /**
     * attaquer un dresseur null
     */
    public void test_attaquer_null() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur(a);
        Dresseur d2 = null;
        assertEquals("Pas d'attaque", false, d.attaquer(d2));
    }

    /**
     * attaquer un dresseurs dans une autre arene
     */
    public void test_attaquer_areneDif() {
        Dresseur d  = new Dresseur(new Arene());
        Dresseur d2 = new Dresseur(new Arene());
        assertEquals("Pas d'attaque", false, d.attaquer(d2));
    }

    /**
     * Attaquer un dresseur normal
     */
    public void test_attaquer_normal() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur(a);
        Dresseur d2 = new Dresseur("t", 1, 0, a);
        assertEquals("attaque realiser", true, d.attaquer(d2));
        assertEquals("Pv pas enlever", 4, d2.getPv());
    }

    /**
     * attaquer possible que si avec poquemont porte>1
     */
    public void test_attaquer_porteeDif() {
        Arene     a = new Arene();
        Dresseur  d = new Dresseur("sd", 5, 5, a);
        Poquemont p = new Poquemont(a);
        d.prendrePoquemont(p);
        Dresseur d2 = new Dresseur("t", 5, 7, a);
        assertEquals("attaque realiser", true, d.attaquer(d2));
        assertEquals("Pas d'attaque", false, d2.attaquer(d));
        assertEquals("Pv pas enlever", 3, d2.getPv());
        assertEquals("Portee de 2 pour dresseur avec poq", 2, d.getPortee());
    }

    /**
     * attaquer pour blesser et tentative d'attaque lorsque le dresseur est blesser
     */
    public void test_attaquer_blesser() {
        Arene    a  = new Arene();
        Dresseur d  = new Dresseur("sd", 5, 5, a);
        Dresseur d2 = new Dresseur("t", 5, 6, a);
        d2.subirDegats(4);
        assertEquals("attaque realiser", true, d.attaquer(d2));
        assertEquals("dresseur blesser", true, d2.etreBlesse());
        assertEquals("Pas d'attaque car blesser", false, d2.attaquer(d));
    }

    /**
     * Attaque  avec des dégats supérieur a 5
     */
    public void test_attaquer_degatSup5() {
        Arene     a = new Arene();
        Dresseur  d = new Dresseur(a);
        Poquemont p = new Poquemont(0, 0, 7, 2, a);
        d.prendrePoquemont(p);
        Dresseur d2 = new Dresseur(a);
        assertEquals("attaque realiser", true, d.attaquer(d2));
        assertEquals("pv pas négatif", 0, d2.getPv());
        assertEquals("dresseur blesser", true, d2.etreBlesse());
        assertEquals("Pas d'attaque car blesser", false, d2.attaquer(d));
    }

    /**
     * To string avec poquemont
     */
    public void test_toString_avecPoq() {
        Arene     a = new Arene();
        Dresseur  d = new Dresseur("sd", 5, 5, a);
        Poquemont p = new Poquemont(a);
        d.prendrePoquemont(p);
        assertEquals("Mauvais toString", "dresseur(sd:5)-poquemont(d:2,p:2)", d.toString());
    }

    /**
     * test toString sans poquemont
     */
    public void test_toString_sansPoq() {
        Arene    a = new Arene();
        Dresseur d = new Dresseur("sd", 5, 5, a);
        assertEquals("Mauvais toString", "dresseur(sd:5)", d.toString());
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
