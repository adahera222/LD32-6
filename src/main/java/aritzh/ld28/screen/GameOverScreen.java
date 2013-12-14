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
public class GameOverScreen extends Screen {
    private final Game game;
    private final int score;
    private final Button mainMenu, playAgain;
    private final String str1, str2, str3;
    private int str1X, str2X, str3X, str1Y, str2Y, str3Y;
    private boolean alignmentSet;

    public GameOverScreen(Game game, int score) {
        super(game);
        this.game = game;
        this.score = score;
        this.mainMenu = new Button(this.game.getWidth() / 2 - 150, this.game.getHeight() / 2 + 100, 200, 50, "Main Menu");
        this.playAgain = new Button(this.game.getWidth() / 2 + 150, this.game.getHeight() / 2 + 100, 200, 50, "Play Again");

        this.str1 = "Your score was:";
        this.str2 = "" + this.score;
        this.str3 = "points!";

    }

    @Override
    public void update() {
        super.update();
        if (this.mainMenu.wasActivated()) {
            this.game.showMainMenu();
        } else if (this.playAgain.wasActivated()) {
            this.game.startGame();
        }
    }

    @Override
    public void renderGraphics(Graphics g) {

        Font f = g.getFont();

        g.setFont(Game.bigFont);

        super.renderGraphics(g);
        this.mainMenu.render(g);
        this.playAgain.render(g);

        if (!alignmentSet) {
            this.str1X = this.game.getWidth() / 2 - g.getFontMetrics().stringWidth(this.str1) / 2;
            this.str2X = this.game.getWidth() / 2 - g.getFontMetrics().stringWidth(this.str2) / 2;
            this.str3X = this.game.getWidth() / 2 - g.getFontMetrics().stringWidth(this.str3) / 2;
            this.str1Y = 100;
            this.str2Y = 100 + g.getFontMetrics().getHeight();
            this.str3Y = 100 + g.getFontMetrics().getHeight() * 2;
            alignmentSet = true;
        }

        g.setColor(Color.BLACK);
        g.drawString(this.str1, str1X + 2, str1Y + 2);
        g.drawString(this.str2, str2X + 2, str2Y + 2);
        g.drawString(this.str3, str3X + 2, str3Y + 2);

        g.setColor(Color.WHITE);
        g.drawString(this.str1, str1X, str1Y);
        g.setColor(Color.GREEN);
        g.drawString(this.str2, str2X, str2Y);
        g.setColor(Color.WHITE);
        g.drawString(this.str3, str3X, str3Y);

        g.setFont(f);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        this.mainMenu.mousePressed(e);
        this.playAgain.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.mainMenu.mouseReleased(e);
        this.playAgain.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        this.mainMenu.mouseMoved(e);
        this.playAgain.mouseMoved(e);
    }
}
