package aritzh.ld28.screen;

import aritzh.ld28.Game;
import aritzh.ld28.screen.elements.Button;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MainMenuScreen extends Screen {

    private final Button start, about, exit;

    public MainMenuScreen(Game game) {
        super(game);
        start = new Button(game.getWidth()/2, game.getHeight()/2 - 100, 200, 50, "Play");
        about = new Button(game.getWidth()/2, game.getHeight()/2, 200, 50, "About");
        exit = new Button(game.getWidth()/2, game.getHeight()/2 + 100, 200, 50, "Exit");
    }

    @Override
    public void renderGraphics(Graphics g) {
        Font f = g.getFont();
        g.setFont(Game.bigFont);

        super.renderGraphics(g);

        this.start.render(g);
        this.about.render(g);
        this.exit.render(g);

        g.setFont(f);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        start.mouseMoved(e);
        about.mouseMoved(e);
        exit.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        start.mousePressed(e);
        about.mousePressed(e);
        exit.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        start.mouseReleased(e);
        about.mouseReleased(e);
        exit.mouseReleased(e);
    }

    @Override
    public void update() {
        super.update();

        if(start.wasActivated()){
            this.game.startGame();
        } else if(about.wasActivated()){
            this.game.openScreen(new AboutScreen(this.game));
        } else if(exit.wasActivated()){
            this.game.stop();
        }
    }
}
