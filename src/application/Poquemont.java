package application;

/**
 * classe qui modelise un poquemont cpaturable par un dresseur
 */
public class Poquemont {


    /**
     * L'{@link Arene} ou le poquemont se trouve
     */
    private Arene arene;

    /**
     * La possiton sur l'axe X, qui sera une case accessible
     */
    private int posY;

    /**
     * La possiton sur l'axe Y, qui sera une case accessible
     */
    private int posX;

    /**
     * Le {@link Dresseur} qui porte le poquemont, si le poquemont est libre, il sera egale a <code>null</code>
     */
    private Dresseur porteur;

    /**
     * Les dégats fait par le poquemont lorsqu'il va attaquer, <br/> <strong>ne peut etre inferieur a 0 </strong>
     */
    private int degats;

    /**
     * La portée du poquemont, br/> <strong>ne peut etre inferieur a 0 </strong>
     */
    private int portee;

    public Poquemont(Arene pArene) {
        this.arene = pArene;
        this.degats = 2;
        this.portee = 2;
        this.posX = 5;
        this.posY = 5;
    }

    /**
     * Constructeur de Poquemont qui permet de set chaque variable
     *
     * @param x       La possiton sur l'axe X, si elle n'ai pas accessible {@link Arene#etreAccessible(int, int)}, ce met a 0.
     * @param y       La possiton sur l'axe Y, si elle n'ai pas accessible {@link Arene#etreAccessible(int, int)}, ce met a 0.
     * @param pDegats Les dégats du poquemont, ce met a 0 si négatif
     * @param pPortee La portée de l'attaque du poquemont, ce met a 0 si négative
     * @param pArene  L'{@link Arene} ou le poquemont doit etre
     */
    public Poquemont(int x, int y, int pDegats, int pPortee, Arene pArene) {
        this.arene = pArene;
        this.degats = (pDegats < 0) ? 0 : pDegats;
        this.portee = (pPortee < 0) ? 0 : pPortee;
        if (pArene.etreAccessible(x, y)) {
            this.posX = x;
            this.posY = y;
        }
        else {
            this.posX = 0;
            this.posY = 0;
        }
    }

    /**
     * Getter des dégats
     *
     * @return les dégats d'un poquemont
     */
    public int getDegats() {
        return degats;
    }

    /**
     * Getter de la portée
     *
     * @return La portée d'un poquemont
     */
    public int getPortee() {
        return portee;
    }

    /**
     * Setter de la variable <code>portee</code>
     *
     * @param portee La portée de l'attaque du poquemont
     */
    public void setPortee(int portee) {
        this.portee = portee;
    }

    /**
     * Getter de l'arene d'un poquemont
     *
     * @return l'arene ou le poquemont est
     */
    public Arene getArene() {
        return arene;
    }

    /**
     * Setter de la variable <code>arene</code>
     *
     * @param arene l'{@link Arene} ou le poquemont est
     */
    public void setArene(Arene arene) {
        this.arene = arene;
    }

    /**
     * Getter de la position en Y
     *
     * @return La position du poquemont sur l'axe des abscise
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Setter de la variable <code>posy</code>
     *
     * @param posY la position du poquemont sur l'axe Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Getter de la position en X
     *
     * @return La position du poquemont sur l'axe des ordonnée
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Setter de la variable <code>posX</code>
     *
     * @param posX La position du pouqemont sur l'axe X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Getter du {@link Dresseur} du poquemont
     *
     * @return Le {@link Dresseur} du poquemont
     */
    public Dresseur getPorteur() {
        return porteur;
    }

    /**
     * Setter de la variable <code>porteur</code>
     *
     * @param porteur le {@link Dresseur} du poquemont
     */
    public void setPorteur(Dresseur porteur) {
        this.porteur = porteur;
    }

    /**
     * Methode pour prendre de le poquemont, a 2 resultat possible
     *
     * @param dresseur le {@link Dresseur} qui veut prendre le poquemont
     *
     * @return <ul>
     * <li>True -> Le poqemont a été pris par le dresseur</li>
     * <li>False -> Si le dresseur a deja un poquemont </li>
     * <li>False -> Si le poquemont a deja un dresseur </li>
     * <li>False -> Si le dresseur n'existe pas </li>
     * <li>False -> Si le dresseur et le poquemont ne sont pas dans la même arene</li>
     * <li>False -> Si le dresseur et le poquemont ne sont pas a la même position </li>
     * </ul>
     */
    public boolean etrePris(Dresseur dresseur) {
        if (dresseur != null) {
            if (porteur == null) {
                if (dresseur.getArene() == this.arene) {
                    if (dresseur.getPosX() != this.posX && dresseur.getPosY() != this.posY) {
                        this.porteur = dresseur;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Change la position du pouqemont si il a un {@link Dresseur}
     */
    public void changerPosition() {
        if (this.porteur != null) {
            this.posX = this.porteur.getPosX();
            this.posY = this.porteur.getPosY();
        }
    }

    /**
     * Pour déoser un poquemont,
     *
     * @return true si le poquemont a été déposer <br/> False si le poquemont n'a pas de dresseur et n'a pas peu etre déposer
     */
    public boolean etreDepose() {
        if (this.porteur != null) {
            this.changerPosition();
            this.porteur = null;
            return true;
        }
        return false;
    }

    /**
     * Pour générer un message en {@link String}
     *
     * @return un {@link String} avec le pattern "-poquemont(d:degats,p:portee )"
     */
    public String toString() {
        return "-poquemont(d:" + this.degats + ",p:" + this.portee + ")";
    }
}
