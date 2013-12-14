package aritzh.ld28;

import aritzh.ld28.render.Render;
import aritzh.ld28.render.Sprite;
import aritzh.ld28.render.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Board {

    private static final int BOARD_SIZE = 10;
    private static final int MARGIN = 10;
    private final Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];
    private int hoverX, hoverY;
    private int clickedX, clickedY;
    private int litX, litY;
    private long lastTimeClick = 0;
    private final Random random;
    private Game game;

    public Board(Game game) {
        this.game = game;
        this.random = new Random();
    }

    public void renderBoard(Render render) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int renderX = x * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN;
                int renderY = y * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN;
                this.squares[x][y].render(renderX, renderY, render, renderX == hoverX && renderY == hoverY, renderX == clickedX && renderY == clickedY);
            }
        }
    }

    public void start(){
        this.clear();
        this.lightAnotherUp();
        this.lastTimeClick = System.currentTimeMillis();
    }

    private void clear(){
        for(Square[] squareRow : this.squares) Arrays.fill(squareRow, Square.NORMAL);
    }

    public void mouseMoved(MouseEvent e) {
        this.hoverX = e.getX() / (SpriteSheet.SPRITE_SIZE + MARGIN);
        this.hoverY = e.getY() / (SpriteSheet.SPRITE_SIZE + MARGIN);
        System.out.println("Mover to square: (" + hoverX + ", " + hoverY + ")");
        if(hoverX >= BOARD_SIZE || hoverY >= BOARD_SIZE) return;
    }

    public void mouseClicked(MouseEvent e){
        this.clickedX = e.getX() / (SpriteSheet.SPRITE_SIZE + MARGIN);
        this.clickedY = e.getY() / (SpriteSheet.SPRITE_SIZE + MARGIN);
        System.out.println("Mover to square: (" + clickedX + ", " + clickedY + ")");
        if(clickedX >= BOARD_SIZE || clickedY >= BOARD_SIZE) return;
        switch (this.squares[clickedX][clickedY]){
            case NORMAL:
                System.out.println("NOPE!");
                this.lightAnotherUp();
                break;
            case LIT:
                System.out.println("YEP!");
                this.lightAnotherUp();
                this.lastTimeClick = System.currentTimeMillis();
                break;
            case UPGRADE:
                System.out.println("Even better!");
                break;
        }
    }

    private void lightAnotherUp() {
        int lightX = random.nextInt(BOARD_SIZE);
        int lightY = random.nextInt(BOARD_SIZE);

        this.clear();
        this.squares[lightX][lightY] = Square.LIT;
    }

    public void renderStrings(Graphics g) {
        long timeLeft = (this.lastTimeClick + 1000) - System.currentTimeMillis();
        if(timeLeft < 0 ) timeLeft = 0;
        double inSeconds = timeLeft / 1000.0;

        final String str = "Time " + inSeconds + " seconds";
        g.setColor(Color.BLACK);
        g.drawString(str, 52, 52);
        g.setColor(Color.WHITE);
        g.drawString(str, 50, 50);
    }

    private static enum Square {
        NORMAL(0), LIT(1), UPGRADE(1);
        private final Sprite normal, hover, click;

        Square(int x) {
            this.normal = Game.INSTANCE.sheet.getSprite(x, 0);
            this.hover = Game.INSTANCE.sheet.getSprite(x, 1);
            this.click = Game.INSTANCE.sheet.getSprite(x, 2);
        }

        public void render(int x, int y, Render render, boolean hovered, boolean clicked) {
            render.drawSprite(x, y, clicked ? this.click : hovered ? this.hover : this.normal);
        }
    }
}
