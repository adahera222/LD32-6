package aritzh.ld28.screen;

import aritzh.ld28.board.Board;
import aritzh.ld28.screen.elements.Button;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PauseScreen extends Screen {

    private final Button backToGame, toMainMenu;
    private final Board board;

    public PauseScreen(Board board) {
        super(board.game);
        this.board = board;
        this.backToGame = new Button(this.game.getWidth()/2, this.game.getHeight()/2 - 50, 200, 50, "Continue");
        this.toMainMenu = new Button(this.game.getWidth()/2, this.game.getHeight()/2 + 50, 200, 50, "Main Menu");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.game.openScreen(this.board);
        }
    }

    @Override
    public void renderGraphics(Graphics g) {
        super.renderGraphics(g);
        this.backToGame.render(g);
        this.toMainMenu.render(g);
    }

    @Override
    public void update() {
        super.update();
        if(this.backToGame.wasActivated()) {
            this.game.openScreen(this.board);
        } else if (this.toMainMenu.wasActivated()) this.game.openScreen(new MainMenuScreen(this.game));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        this.backToGame.mousePressed(e);
        this.toMainMenu.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.backToGame.mouseReleased(e);
        this.toMainMenu.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        this.backToGame.mouseMoved(e);
        this.toMainMenu.mouseMoved(e);
    }
}
