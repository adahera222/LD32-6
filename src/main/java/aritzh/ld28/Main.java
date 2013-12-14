package aritzh.ld28;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Main {

    public static void main(String[] args) {

        Game game = Game.getGame(800, 800, false);
        game.start();

    }
}
