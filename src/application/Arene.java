package application;

/**
 * classe qui modelise une arene et qui gere les murs de l'environnement
 */
public class Arene {


    /**
     * La taille de l'arène selon l’axe des abscisses.
     */
    private int tailleX;

    /**
     * La taille de l'arène selon l’axe des ordonée.
     */
    private int tailleY;

    /**
     * Createur null qui permet d'initialiser une Arene avec une taile de 10*10 cases soit 100 cases
     */
    public Arene() {
        this.tailleY = 10;
        this.tailleX = 10;
    }

    /**
     * Pour avoir la taille de l'arene en X.
     *
     * @return La taille de l'arène selon l’axe des abscisses.
     */
    public int getTailleX() {
        return this.tailleX;
    }

    /**
     * Pour remplir le l'emplacement <code>tailleX</code>.
     *
     * @param tailleX pour changer La taille de l'arene selon l’axe des abscisses .
     */
    public void setTailleX(int tailleX) {
        this.tailleX = tailleX;
    }

    /**
     * Pour avoir la taille de l'arene en Y.
     *
     * @return La taille de l'arène selon l’axe des abscisses.
     */
    public int getTailleY() {
        return this.tailleY;
    }

    /**
     * Pour remplir le l'emplacement <code>tailleX</code>.
     *
     * @param tailleY pour changer La taille de l'arene selon l’axe des abscisses .
     */
    public void setTailleY(int tailleY) {
        this.tailleY = tailleY;
    }

    /**
     * Pour savoir si la case aux coorodonées donné est un mur ou non <br>
     * <img src="../img/TableauJeu.png" alt="">
     *
     * @param x un int qui donnera la coordonnée x
     * @param y un int qui donnera la coordonée y
     *
     * @return un boolean qui a deux résultat possible
     * <ul>
     * <li><strong>true</strong> -> si la case est accesible</li>
     * <li> false -> si (x,y) est un mur  </li>
     * <li>false -> si x et y sont en dehors de l'arène </li>
     * </ul>
     */
    public boolean etreAccessible(int x, int y) {
        if (x == 0 && y == 0) {
            return true;
        }
        if (x > this.tailleX - 1 || y > this.tailleY - 1 || x < 0 || y < 0) {
            return false;
        }
        return !(x == 3 && !(y == 3 || y == 4 || y == 5 || y == 6) || x == 7 && (y == 3 || y == 4 || y == 5 || y == 6));
    }
}
