package aritzh.ld28.screen;

import aritzh.ld28.Game;
import aritzh.ld28.screen.elements.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class AboutScreen extends Screen {

    private static final String TEXT1 = "This game was made within 48 hours \n";
    private static final String TEXT2 = "for Ludum Dare 28, a game-making \n";
    private static final String TEXT3 = "competition held every 3 months in \n";
    private static final String TEXT4 = "which competitors make a whole game \n";
    private static final String TEXT5 = "in 48 hours";
    private final Button back;

    public AboutScreen(Game game) {
        super(game);
        back = new Button(this.game.getWidth() / 2, this.game.getHeight() - 50, 200, 50, "Back");
    }

    @Override
    public void update() {
        super.update();
        if (this.back.wasActivated()) this.game.showMainMenu();
    }

    @Override
    public void renderGraphics(Graphics g) {
        super.renderGraphics(g);

        Font f = g.getFont();
        g.setFont(Game.bigFont);

        this.back.render(g);

        g.setColor(Color.BLACK);
        g.drawString(AboutScreen.TEXT1, 50+3, 50+3);
        g.drawString(AboutScreen.TEXT2, 50+3, 50+3 + g.getFontMetrics().getHeight());
        g.drawString(AboutScreen.TEXT3, 50+3, 50+3 + g.getFontMetrics().getHeight()*2);
        g.drawString(AboutScreen.TEXT4, 50+3, 50+3 + g.getFontMetrics().getHeight()*3);
        g.drawString(AboutScreen.TEXT5, 50+3, 50+3 + g.getFontMetrics().getHeight()*4);

        g.setColor(Color.WHITE);
        g.drawString(AboutScreen.TEXT1, 50, 50);
        g.drawString(AboutScreen.TEXT2, 50, 50 + g.getFontMetrics().getHeight());
        g.drawString(AboutScreen.TEXT3, 50, 50 + g.getFontMetrics().getHeight()*2);
        g.drawString(AboutScreen.TEXT4, 50, 50 + g.getFontMetrics().getHeight()*3);
        g.drawString(AboutScreen.TEXT5, 50, 50 + g.getFontMetrics().getHeight()*4);

        g.setFont(f);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        this.back.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.back.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        this.back.mouseMoved(e);
    }
}
