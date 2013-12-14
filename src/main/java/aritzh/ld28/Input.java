package aritzh.ld28;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Input extends MouseAdapter implements MouseMotionListener{

    private Game game;

    public Input(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        game.getBoard().mouseReleased();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        game.getBoard().mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        game.getBoard().mouseMoved(e);
    }
}
