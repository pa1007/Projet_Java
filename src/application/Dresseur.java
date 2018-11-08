package application;

/**
 * classe qui modelise un dresseur de Poquemont
 */
public class Dresseur {

    /**
     * Le poquemont dresser par le dresseur.
     */
    private Poquemont poquemontDresse;
    /**
     * Le nom du dresseur de poquemont .
     */
    private String    nom;
    /**
     * La position du dresseur selon l'axe des abssice.
     */
    private int       posX;
    /**
     * La position du dresseur selon l'axe des ordonnée.
     */
    private int       posY;

    /**
     * Le nombre de Point de Vie que le dresseur a.
     */
    private int pv;

    /**
     * L'{@link Arene} ou le dresseur se trouve.
     */
    private Arene arene;

    /**
     * Crée un dresseur par defaut avec les attribut :
     * <ul>
     * <li>Nom = Sacha</li>
     * <li>Position X = 0</li>
     * <li>Position Y = 0</li>
     * <li>Point de Vie = 5</li>
     * <li>{@link Arene} donnée en parametre</li>
     * <li>Pas de poquemont dresser</li>
     * </ul>
     *
     * @param pArene l'arene dans lequel le dresseur se trouve
     */
    public Dresseur(Arene pArene) {
        this.arene = pArene;
        this.nom = "Sacha";
        this.posX = 0;
        this.posY = 0;
        this.pv = 5;
        this.poquemontDresse = null;
    }

    /**
     * Constructeur d'un dresseur avec des valeurs donnée, donne aucun poquemont au dresseur dès le départ
     *
     * @param pNom    le nom du dresseur
     * @param departX le point X de départ du dersseur, si pas accessble {@link Arene#etreAccessible(int, int)} sera mis a 0
     * @param departY le point Y de départ du dresseur, si pas accessble {@link Arene#etreAccessible(int, int)} sera mis a 0
     * @param pArene  l'{@link Arene} ou se trouve le dresseur
     */
    public Dresseur(String pNom, int departX, int departY, Arene pArene) {
        this.nom = pNom;
        if (pArene.etreAccessible(departX, departY)) {
            this.posX = departX;
            this.posY = departY;
        }
        else {
            this.posX = 0;
            this.posY = 0;
        }
        this.arene = pArene;
        this.pv = 5;
        this.poquemontDresse = null;
    }

    /**
     * Getter du nom du dresseur
     *
     * @return Le nom du dresseur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter de la variable <code>nom</code>
     *
     * @param nom Le nom du dresseur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de la position sur l'axe des X
     *
     * @return la position du dresseur sur l'axe des X
     */
    public int getPosX() {
        return posX;
    }


    /**
     * Setter de la variable <code>posX</code>
     *
     * @param posX la position sur l'axe X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Getter de la position sur l'axe des Y
     *
     * @return la position du dresseur sur l'axe des Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Setter de la variable <code>posY</code>
     *
     * @param posY La position sur l'axe Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Getter du {@link Poquemont} dressé,
     *
     * @return Le {@link Poquemont} dressé par le dresseur,<br/> retourne <code>null</code> si le dresseur n'a pas de poquemont dresser
     */
    public Poquemont getPoquemontDresse() {
        return poquemontDresse;
    }

    /**
     * Setter de la variable <code>poquemontDresse</code>
     *
     * @param poquemontDresse le {@link Poquemont} dresser par le dresseur
     */
    public void setPoquemontDresse(Poquemont poquemontDresse) {
        this.poquemontDresse = poquemontDresse;
    }

    /**
     * Getter des Point de Vie du dresseur
     *
     * @return Les PV du dresseur
     */
    public int getPv() {
        return pv;
    }

    /**
     * Setter de la variable <code>pv</code>
     *
     * @param pv Le nombre de Point de Vie du dresseur
     */
    public void setPv(int pv) {
        this.pv = pv;
    }

    /**
     * Getter de l'{@link Arene} où le dreseur est
     *
     * @return L'{@link Arene} du dreseur
     */
    public Arene getArene() {
        return arene;
    }

    /**
     * Setter de la variable <code>arene</code>
     *
     * @param arene l'{@link Arene} ou le dresseur se trouve
     */
    public void setArene(Arene arene) {
        this.arene = arene;
    }

    /**
     * Methode pour prendre un poquemont, change aussi les variable
     * <ul>
     * <li>{@link Poquemont#porteur} avec le dresseur (<code>this</code>)</li>
     * <li>{@link Dresseur#poquemontDresse} avec le param <code>poq</code></li>
     * </ul>
     *
     * @param poq Le {@link Poquemont} que le dresseur veut prendre
     *
     * @return True si le poquemont est prenable <br/> False si le dresseur a deja un poquemont ou que le poquemont a deja un dresseur ou que le dresseur n'a pas tout ces Point de vie
     */
    //todo : rework, see if poq is already here or not
    public boolean prendrePoquemont(Poquemont poq) {
        if (!this.etreBlesse()) {
            if (poq != null) {
                if (poq.etrePris(this)) {
                    if (this.poquemontDresse == null) {
                        this.setPoquemontDresse(poq);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Cette méthode permet au dresseur de se déplacer que d'une case a la fois,
     * rien ne se passe si la somme des valeur absolue des deux parametre est supérieur a 1
     *
     * @param dx la distance a se déplacer sur l'axe X
     * @param dy la distance a se déplacer sur l'axe Y
     */
    public void seDeplacer(int dx, int dy) {
        if (!this.etreBlesse()) {
            if (Math.abs(dx + dy) == 1) {
                int tempX = this.posX + dx;
                int tempY = this.posY + dy;
                if (this.arene.etreAccessible(tempX, tempY)) {
                    this.posX = tempX;
                    this.posY = tempY;
                    if (this.poquemontDresse != null) {
                        this.poquemontDresse.changerPosition();
                    }
                }

            }
        }
    }


    /**
     * Méthode pour déposer un {@link Poquemont} si le dresseur en a un
     *
     * @return True si le {@link Poquemont} a été posé <br/> False si le dresseur n'a pas tout ces pv, Si il n'a pas de {@link Poquemont}
     * ou que le poquemont n'a pas été dépose (Cf: {@link Poquemont#etreDepose()})
     */
    public boolean deposerPoquemont() {
        if (this.poquemontDresse != null) {
            if (!this.etreBlesse()) {
                if (this.poquemontDresse.etreDepose()) {
                    this.poquemontDresse = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Permet de calculer la <i>Distance de Manhattan</i>  qui se calcule <code>d(A,B)=|X<sub>B</sub>-X<sub>A</sub>|+|Y<sub>B</sub>-Y<sub>A</sub>| </code>
     *
     * @param dresseur le dresseur avec qui ont veut la distance entre notre dresseur et lui
     *
     * @return La distance, qui est un nombre entier, entre le dresseur en parametre et <code>this</code> ou retourne <strong>-1</strong> si le dresseur est null ou pas dans la même arene
     */
    public int getDistance(Dresseur dresseur) {
        return (dresseur != null && dresseur.arene == this.arene) ? Math.abs(dresseur.posX - this.posX) + Math.abs(
                dresseur.posY - this.posY) : -1;
    }

    /**
     * Savoir la portée du dresseur en fonction de si il a un {@link Poquemont}
     *
     * @return 1 si le dresseur n'a pas de {@link Poquemont} ou retourne {@link Poquemont#portee} du poquemont
     */
    public int getPortee() {
        return (poquemontDresse != null) ? this.poquemontDresse.getPortee() : 1;
    }

    /**
     * Cette méthode permet d'ataquer un dresseur qui est a la portée du dreseur, qui est soit de 1 sil il n'a pas de {@link Poquemont}, ou sera de {@link Poquemont#portee}
     *
     * @param dresseur le dresseur qui sera attaquer
     *
     * @return true si le dresseur a été toucher et lui fera {@link Dresseur#getDegats()} <br/> False si le dresseur est hors de {@link Dresseur#getPortee()} ou que le dressur est bléssé
     */
    public boolean attaquer(Dresseur dresseur) {
        if (dresseur != null &&
            dresseur.arene == this.arene
            && this.getDistance(dresseur) != -1
            && this.getDistance(dresseur) <= this.getPortee()
            && !etreBlesse() && !dresseur.etreBlesse()) {
            if (dresseur.pv - this.getDegats() >= 0) {
                dresseur.setPv(dresseur.pv - this.getDegats());
            }
            else {
                dresseur.pv = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Savoir les dégats du dresseur en fonction de si il a un {@link Poquemont}
     *
     * @return 1 si le dresseur n'a pas de {@link Poquemont} ou retourne {@link Poquemont#degats} du poquemont
     */
    public int getDegats() {
        return (poquemontDresse != null) ? this.poquemontDresse.getDegats() : 1;
    }

    /**
     * Pour savoir si le dresseur est bléssée ou non
     *
     * @return true si le dresseur n'a plus de pv <br/> False si le dresseur a encore des PV
     */
    public boolean etreBlesse() {
        return pv == 0;
    }

    /**
     * Cette méthode permet de faire réduire les pv du dresseur <br/>
     *
     * @param degat est un entier, si négatif, il ne se passera rien, si les dégats sont supérieur au pv , les pv seront de 0
     */
    public void subirDegats(int degat) {
        if ((degat > 0)) {
            int temp = this.pv - degat;
            this.pv = (temp < 0) ? 0 : temp;
        }
    }

    /**
     * To string, qui genere un message en fonction de si le dresseur a un {@link Poquemont} ou pas
     *
     * @return un string qui a pour partern <code>dresseur(nom-dresseur :pv )</code>de base et si il un poquemont <code>-poquemont(d:dégats,p:portée)</code>
     */
    public String toString() {
        return "dresseur(" + this.nom + " :" + this.pv + ")" + (
                (this.poquemontDresse != null)
                        ? this.poquemontDresse.toString()
                        : ""
        );
    }
}
