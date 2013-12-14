package aritzh.ld28;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Input extends MouseAdapter {

    private Game game;

    public Input(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        game.getBoard().mouseMoved(e);
    }
}
