package aritzh.ld28;

import java.applet.Applet;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Main extends Applet {

    private Game game;
    private static final int WIDTH = 602, HEIGHT = 652;

    public Main() {
        this.game = Game.getGame(WIDTH, HEIGHT, true);
    }

    public static void main(String[] args) {
        Game game = Game.getGame(WIDTH, HEIGHT, false);
        game.start();
    }

    @Override
    public void start() {
        super.start();
        this.add(game);
        this.game.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.game.stop();
    }
}
