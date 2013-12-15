package aritzh.ld28.screen;

import aritzh.ld28.Game;
import aritzh.ld28.board.Stats;
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
    private final Stats stats;
    private final Button mainMenu, playAgain;

    public GameOverScreen(Game game, Stats stats) {
        super(game);
        this.game = game;
        this.stats = stats;
        this.mainMenu = new Button(this.game.getWidth() / 2 - 150, this.game.getHeight() / 2 + 250, 200, 50, "Main Menu");
        this.playAgain = new Button(this.game.getWidth() / 2 + 150, this.game.getHeight() / 2 + 250, 200, 50, "Play Again");
    }

    @Override
    public void update(boolean hasFocus) {
        super.update(hasFocus);
        if (this.mainMenu.wasActivated()) {
            this.game.openScreen(new MainMenuScreen(this.game));
        } else if (this.playAgain.wasActivated()) {
            this.game.startGame();
        }
    }

    @Override
    public void renderGraphics(Graphics g) {
        super.renderGraphics(g);
        Font f = g.getFont();

        g.setFont(Game.bigFont);

        this.mainMenu.render(g);
        this.playAgain.render(g);

        int leftMargin = 125;
        int topMargin = 102;
        int resultsExtraMargin = 250;

        g.setColor(Color.BLACK);
        // x+2, y+2
        g.drawString("Score:", leftMargin, topMargin);
        g.drawString("Success rate:", leftMargin, topMargin + g.getFontMetrics().getHeight());
        g.drawString("Correct / Total:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 2);
        g.drawString("Total game time:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 3);
        g.drawString("Shortest square:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 4);
        g.drawString("Longest square:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 5);
        g.drawString("Upgrades achieved:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 6);
        g.drawString("Upgrades lost:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 7);

        // x+300, y
        g.drawString("" + this.stats.getScore(), leftMargin+resultsExtraMargin, topMargin);
        g.drawString(this.stats.getSuccessPercentage() + "%", leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight());
        g.drawString(this.stats.getCorrectClicks() + " / " + this.stats.getTotalSquaresClicked(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 2);
        g.drawString("" + this.stats.getTotalGameTime(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 3);
        g.drawString("" + this.stats.getShortestSquare(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 4);
        g.drawString("" + this.stats.getLongestSquare(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 5);
        g.drawString("" + this.stats.getUpgradesClicked(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 6);
        g.drawString("" + this.stats.getUpgradesLost(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 7);

        leftMargin-=2;
        topMargin-=2;
        g.setColor(Color.WHITE);
        // x, y
        g.drawString("Score:", leftMargin, topMargin);
        g.drawString("Success rate:", leftMargin, topMargin + g.getFontMetrics().getHeight());
        g.drawString("Correct / Total:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 2);
        g.drawString("Total game time:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 3);
        g.drawString("Shortest square:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 4);
        g.drawString("Longest square:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 5);
        g.drawString("Upgrades achieved:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 6);
        g.drawString("Upgrades lost:", leftMargin, topMargin + g.getFontMetrics().getHeight() * 7);

        g.setColor(Color.GREEN);
        // x+300
        g.drawString("" + this.stats.getScore(), leftMargin+resultsExtraMargin, topMargin);
        g.drawString(this.stats.getSuccessPercentage() + "%", leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight());
        g.drawString(this.stats.getCorrectClicks() + " / " + this.stats.getTotalSquaresClicked(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 2);
        g.drawString("" + this.stats.getTotalGameTime(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 3);
        g.drawString("" + this.stats.getShortestSquare(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 4);
        g.drawString("" + this.stats.getLongestSquare(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 5);
        g.drawString("" + this.stats.getUpgradesClicked(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 6);
        g.drawString("" + this.stats.getUpgradesLost(), leftMargin+resultsExtraMargin, topMargin + g.getFontMetrics().getHeight() * 7);

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
