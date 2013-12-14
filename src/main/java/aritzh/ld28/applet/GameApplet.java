package aritzh.ld28.applet;

import aritzh.ld28.Game;

import java.applet.Applet;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GameApplet extends Applet {

    private Game game;

    public GameApplet(){
        this.game = Game.getGame(602, 652, true);
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
