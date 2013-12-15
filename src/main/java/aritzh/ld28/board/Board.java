package aritzh.ld28.board;

import aritzh.ld28.Game;
import aritzh.ld28.render.Render;
import aritzh.ld28.screen.GameOverScreen;
import aritzh.ld28.screen.PauseScreen;
import aritzh.ld28.screen.Screen;
import aritzh.ld28.screen.elements.ProgressBar;
import aritzh.ld28.sound.Sound;
import aritzh.ld28.util.Vector2i;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Board extends Screen {

    public static final int UPGRADE_EXTRA_TIME = 1000;
    public static final int TOP_MARGIN = 50;
    private static final int BOARD_SIZE = 8;
    private static final int MARGIN = 10;
    private final Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];
    private final Random random;
    private final Sound bgSound;
    private final ProgressBar gameOverBar, currentBar;
    public boolean dead;
    private Vector2i hover = new Vector2i(-1, -1), click = new Vector2i(-1, -1);
    private int score;
    private int gameOverCountdown = 60;
    private int gameOverCountdownMax = 60;
    private boolean paused, isStartPause;
    private long pausedDiff = -1, currentMax = 1000, currentTime, millisLeft;
    private final Stats stats;
    private boolean isPlaying;

    public Board(Game game) {
        super(game);
        this.bgSound = new Sound(this.getClass().getResourceAsStream("/audio/bgMusic.wav"));
        this.random = new Random();
        this.gameOverBar = new ProgressBar(this.gameOverCountdown, 0, this.gameOverCountdownMax);
        this.currentBar = new ProgressBar(1000, 0, 1000);
        this.stats = new Stats();
    }

    public void start() {
        this.clear();
        this.lightAnotherUp();
        if(!this.game.isMuted()) {
            this.bgSound.loop();
            this.isPlaying = true;
        }
        this.currentTime = System.currentTimeMillis() + this.currentMax;
        this.isStartPause = true;
        this.pause();
    }

    public void pause() {
        this.updateTiming();
        this.paused = true;
        this.pausedDiff = millisLeft;
        if (!this.isStartPause) this.game.silentSwitch(new PauseScreen(this));
    }

    private void updateTiming() {
        if (this.paused) return;
        this.millisLeft = this.currentTime - System.currentTimeMillis();

        if (this.millisLeft <= 0) {
            this.millisLeft = 0;
            if (!this.dead) this.gameOver();
        }
        this.currentBar.setSubdivisions((int) (this.currentMax / 1000));
        this.currentBar.setMax((int) this.currentMax);
        this.currentBar.setProgress((int) this.millisLeft);
    }

    public void gameOver() {
        this.dead = true;
        if(!this.game.isMuted()) this.bgSound.stop();
        this.stats.finishBefore(this.gameOverCountdownMax - this.gameOverCountdown);
        this.game.silentSwitch(new GameOverScreen(this.game, this.stats));
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

    private void clear() {
        for (Square[] squareRow : this.squares) Arrays.fill(squareRow, Square.NORMAL);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) this.pause();
    }

    @Override
    public void render(Render render) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int renderX = x * (this.game.sheet.getSize() + MARGIN) + MARGIN;
                int renderY = y * (this.game.sheet.getSize() + MARGIN) + MARGIN + TOP_MARGIN;
                this.squares[x][y].render(renderX, renderY, render, x == hover.x && y == hover.y, x == click.x && y == click.y);
            }
        }
    }

    @Override
    public void renderGraphics(Graphics g) {
        this.updateTiming();
        double inSeconds = millisLeft / 1000.0;
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
        if (this.paused) return;
        this.gameOverCountdown--;
        if (this.gameOverCountdown <= 0) {
            this.gameOverCountdown = 0;
            if (!this.dead) this.gameOver();
        }
        this.gameOverBar.setMax(this.gameOverCountdownMax);
        this.gameOverBar.setProgress(this.gameOverCountdown);
    }

    @Override
    public void closing() {
        this.gameOver();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean ignoreForTiming = false;

        if (this.paused) {
            if (!this.resume()) return;
            ignoreForTiming = true;
        }
        this.updateTiming();
        if (dead) return;

        this.click = this.getSquareCoords(e.getX(), e.getY());
        if (click.x >= BOARD_SIZE || click.y >= BOARD_SIZE) return;

        if(!this.game.isMuted()) this.squares[click.x][click.y].playSound();

        this.updateTiming();
        switch (this.squares[click.x][click.y]) {
            case NORMAL:
                this.stats.unlitSquareClicked();
                if(!ignoreForTiming) this.stats.updateTiming(this.currentMax - this.millisLeft);
                this.lightAnotherUp();
                score -= 10;
                if (score < 0) score = 0;
                break;
            case LIT:
                this.stats.litSquareClicked();
                if(!ignoreForTiming) this.stats.updateTiming(this.currentMax - this.millisLeft);
                this.lightAnotherUp();
                while (currentMax - this.millisLeft > 1000) {
                    currentMax -= 1000;
                    this.stats.upgradeLost();
                }
                currentTime = System.currentTimeMillis() + currentMax;
                this.updateTiming();
                score += 20;
                break;
            case UPGRADE:
                this.stats.upgradeClicked();
                if(!ignoreForTiming) this.stats.updateTiming(this.currentMax - this.millisLeft);
                while (currentMax - this.millisLeft > 1000) {
                    currentMax -= 1000;
                    this.stats.upgradeLost();
                }
                this.currentMax += UPGRADE_EXTRA_TIME;
                this.gameOverCountdownMax++;
                this.gameOverCountdown++;
                this.squares[click.x][click.y] = Square.NORMAL;
                currentTime = System.currentTimeMillis() + currentMax;
                score += 40;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.click = new Vector2i(-1, -1);
        this.mouseMoved(null);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e == null) this.hover = new Vector2i(-1, -1);
        else this.hover = this.getSquareCoords(e.getX(), e.getY());
    }

    @Override
    public void opening() {
        super.opening();
        if (this.paused && !this.isStartPause) this.resume();
    }

    public boolean resume() {
        if (!this.paused) return false;
        this.paused = false;
        boolean ret = this.isStartPause;
        if(!this.game.isMuted() && !this.isPlaying) this.bgSound.loop();
        else if (this.game.isMuted() && this.isPlaying) this.bgSound.stop();
        this.isStartPause = false;
        currentTime = System.currentTimeMillis() + pausedDiff;
        pausedDiff = -1;
        this.updateTiming();

        return ret;
    }

    private Vector2i getSquareCoords(int screenX, int screenY) {
        final int fullSpriteSize = this.game.sheet.getSize() + MARGIN;
        return new Vector2i(screenX / fullSpriteSize, (screenY - TOP_MARGIN) / fullSpriteSize);
    }

    @Override
    public void update(boolean hasFocus) {
        super.update(hasFocus);
        if(!this.paused && !hasFocus) this.pause();
    }
}
