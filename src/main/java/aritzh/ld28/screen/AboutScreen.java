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

    private static final String[] message = new String[]
            {
                    "This game was made within 48 hours",
                    "for Ludum Dare 28, a game-making",
                    "competition held every 3 months. In",
                    "it, competitors make a whole game",
                    "from scratch in 48 hours",
                    "",
                    "The topic was \"You Only Get One\"",
                    "",
                    "This game is a fast-paced clicking game",
                    "in which you have to click the ONE",
                    "yellow square within ONE second.",
                    "Clicking the green ONEs gives you ONE",
                    "extra second for each square, but",
                    "be careful, if you waste it, it won't",
                    "come back",
                    "",
                    "The limit per game is ONE minute. So,",
                    "how many points can you get within",
                    "that time?"
            };
    private final Button back;

    public AboutScreen(Game game) {
        super(game);
        back = new Button(this.game.getWidth() / 2, this.game.getHeight() - 50, 200, 50, "Back");
    }

    @Override
    public void update() {
        super.update();
        if (this.back.wasActivated()) this.game.openScreen(new MainMenuScreen(this.game));
    }

    @Override
    public void renderGraphics(Graphics g) {
        super.renderGraphics(g);

        Font f = g.getFont();
        g.setFont(Game.bigFont);

        this.back.render(g);

        g.setColor(Color.BLACK);
        final int leftMargin = 0;
        final int topMargin = 35;

        for(int i = 0; i<message.length; i++){
            String currMessage = message[i];
            int x = leftMargin + 3 + (this.game.getWidth() - g.getFontMetrics().stringWidth(currMessage)) / 2;
            int y = topMargin + 3 + g.getFontMetrics().getHeight() * i;
            g.drawString(currMessage, x, y);
        }

        g.setColor(Color.WHITE);

        for(int i = 0; i<message.length; i++){
            String currMessage = message[i];
            int x = leftMargin + (this.game.getWidth() - g.getFontMetrics().stringWidth(currMessage)) / 2;
            int y = topMargin + g.getFontMetrics().getHeight() * i;
            g.drawString(currMessage, x, y);
        }

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
