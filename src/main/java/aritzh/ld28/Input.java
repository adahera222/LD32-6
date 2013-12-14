package aritzh.ld28;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Input extends MouseAdapter implements MouseMotionListener, KeyListener{

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
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().mouseReleased(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().mouseMoved(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!(this.game.getCurrentScreen() == null))game.getCurrentScreen().keyReleased(e);
    }
}
