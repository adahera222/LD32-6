package aritzh.ld28;

import aritzh.ld28.board.Square;
import aritzh.ld28.render.Render;
import aritzh.ld28.render.SpriteSheet;
import aritzh.ld28.screen.Screen;
import aritzh.ld28.screen.elements.ProgressBar;
import aritzh.ld28.sound.Sound;
import aritzh.ld28.util.Vector2i;
import org.apache.commons.math3.util.Precision;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Board extends Screen {

    public static final int UPGRADE_EXTRA_TIME = 2000;
    public static final int TOP_MARGIN = 50;
    private static final int BOARD_SIZE = 8;
    private static final int MARGIN = 10;
    private final Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];
    private final Random random;
    private final Sound bgSound;
    private final ProgressBar gameOverBar, currentBar;
    public boolean dead;
    private Vector2i hover = new Vector2i(-1, -1), click = new Vector2i(-1, -1);
    private long lastTimeClick = 0;
    private long timeLeft;
    private int score;
    private int gameOverCountdown = 60;
    private int currentMax = 1000;
    private boolean paused;
    private long pauseDiff;

    public Board(Game game) {
        super(game);
        this.bgSound = new Sound(this.getClass().getResourceAsStream("/audio/bgMusic.wav"));
        this.random = new Random();
        this.gameOverBar = new ProgressBar(60, 0, 60);
        this.currentBar = new ProgressBar(0, 0, 1);
    }

    @Override
    public void render(Render render) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int renderX = x * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN;
                int renderY = y * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN + TOP_MARGIN;
                this.squares[x][y].render(renderX, renderY, render, x == hover.x && y == hover.y, x == click.x && y == click.y);
            }
        }
    }

    @Override
    public void renderGraphics(Graphics g) {
        this.updateTiming();
        double inSeconds = timeLeft / 1000.0;
        FontMetrics metrics = g.getFontMetrics();

        final String nextSquare = "Death in " + inSeconds + " seconds";
        final int renderY = MARGIN * 2;

        final String gameOver = "Game is over in " + this.gameOverCountdown + " seconds";
        final int gameOverX = (int) (this.game.getWidth() - metrics.getStringBounds(gameOver, g).getWidth());

        final String scoreString = "Score: " + score;
        final int scoreStringX = (int) (this.game.getWidth() - metrics.getStringBounds(scoreString, g).getWidth()) / 2;

        g.setColor(Color.BLACK);
        g.drawString(nextSquare, MARGIN + 1, renderY + 1);
        g.drawString(gameOver, gameOverX - MARGIN, renderY + 1);
        g.setColor(Color.WHITE);
        g.drawString(nextSquare, MARGIN - 1, renderY - 1);
        g.drawString(gameOver, gameOverX - MARGIN, renderY - 1);
        g.setColor(Color.RED);
        g.drawString(scoreString, scoreStringX, renderY);

        this.currentBar.render(g, MARGIN, 23, this.game.getWidth() - 2 * MARGIN, 15);
        this.gameOverBar.render(g, MARGIN, 40, this.game.getWidth() - 2 * MARGIN, 15);
    }

    @Override
    public void updatePS() {
        if(this.paused) return;
        this.gameOverCountdown--;
        if (this.gameOverCountdown <= 0) {
            this.gameOverCountdown = 0;
            if (!this.dead) this.gameOver();
        }
        this.gameOverBar.setProgress(this.gameOverCountdown);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.resume();
        this.updateTiming();
        if (dead) return;

        this.click = this.getSquareCoords(e.getX(), e.getY());

        if (click.x >= BOARD_SIZE || click.y >= BOARD_SIZE) return;

        this.squares[click.x][click.y].playSound();

        long nextThousand = this.roundToNextThousand(this.timeLeft);
        switch (this.squares[click.x][click.y]) {
            case NORMAL:
                this.lightAnotherUp();
                score -= 10;
                if (score < 0) score = 0;
                break;
            case LIT:
                this.lightAnotherUp();
                this.updateTiming();
                this.currentMax = (int) nextThousand;
                this.lastTimeClick += nextThousand - this.timeLeft;
                score += 20;
                break;
            case UPGRADE:
                this.squares[click.x][click.y] = Square.NORMAL;
                this.currentMax = (int) nextThousand + UPGRADE_EXTRA_TIME;
                this.lastTimeClick += nextThousand - this.timeLeft + UPGRADE_EXTRA_TIME;
                score += 40;
                break;
        }
    }

    @Override
    public void closing() {
        this.gameOver();
    }

    private void updateTiming() {
        if(this.paused) return;
        this.timeLeft = (this.lastTimeClick + 1000) - System.currentTimeMillis();
        if (this.timeLeft <= 0) {
            this.timeLeft = 0;
            if (!this.dead) this.gameOver();
        }
        this.currentBar.setMax(this.currentMax);
        this.currentBar.setProgress((int) this.timeLeft);
    }

    private long roundToNextThousand(long number) {
        return (long) Precision.round(number, -3, BigDecimal.ROUND_CEILING);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.hover = this.getSquareCoords(e.getX(), e.getY());
    }

    private Vector2i getSquareCoords(int screenX, int screenY) {
        final int fullSpriteSize = SpriteSheet.SPRITE_SIZE + MARGIN;
        return new Vector2i(screenX / fullSpriteSize, (screenY - TOP_MARGIN) / fullSpriteSize);
    }

    public void gameOver() {
        this.dead = true;
        this.bgSound.stop();
        this.game.showGameOverScreen(this.score);
    }

    public void start() {
        this.clear();
        this.lightAnotherUp();
        this.bgSound.loop();
        this.lastTimeClick = System.currentTimeMillis() + 1000;
        this.pause();
    }

    private void clear() {
        for (Square[] squareRow : this.squares) Arrays.fill(squareRow, Square.NORMAL);
    }

    private void lightAnotherUp() {
        int lightX = random.nextInt(BOARD_SIZE);
        int lightY = random.nextInt(BOARD_SIZE);

        this.clear();
        this.squares[lightX][lightY] = Square.LIT;

        if (random.nextInt(10) == 0) {
            this.newUpgrade();
        }
    }

    private void newUpgrade() {
        int lightX;
        int lightY;
        do {
            lightX = random.nextInt(BOARD_SIZE);
            lightY = random.nextInt(BOARD_SIZE);
        } while (this.squares[lightX][lightY] == Square.LIT);

        this.squares[lightX][lightY] = Square.UPGRADE;
    }

    public void mouseReleased() {
        this.click = new Vector2i(-1, -1);
    }

    public void pause() {
        this.pauseDiff = System.currentTimeMillis() - this.lastTimeClick;
        this.paused = true;
    }

    public void resume() {
        this.lastTimeClick = System.currentTimeMillis()-pauseDiff;
        this.updateTiming();
        this.paused = false;
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) this.pause();
    }
}
