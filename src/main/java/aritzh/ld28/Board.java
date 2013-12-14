package aritzh.ld28;

import aritzh.ld28.render.Render;
import aritzh.ld28.render.Sprite;
import aritzh.ld28.render.SpriteSheet;

import java.awt.event.MouseEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Board {

    private static final int BOARD_SIZE = 10;
    private static final int MARGIN = 2;
    private final Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];
    private int hoverX, hoverY;

    public Board(Game game) {
    }

    public void render(Render render) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int renderX = x * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN;
                int renderY = y * (SpriteSheet.SPRITE_SIZE + MARGIN) + MARGIN;
                this.squares[x][y].render(renderX, renderY, render, renderX == hoverX && renderY == hoverY);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        this.hoverX = e.getX() / (SpriteSheet.SPRITE_SIZE + MARGIN);
        this.hoverX = e.getY() / (SpriteSheet.SPRITE_SIZE + MARGIN);
    }

    private static enum Square {
        NORMAL(0), LIT(1), UPGRADE(1);

        private final Sprite normal, hover, clicked;

        Square(int x) {
            this.normal = Game.INSTANCE.sheet.getSprite(x, 0);
            this.hover = Game.INSTANCE.sheet.getSprite(x, 1);
            this.clicked = Game.INSTANCE.sheet.getSprite(x, 2);
        }

        public void render(int x, int y, Render render, boolean hovered) {
            render.drawSprite(x, y, this.sprite);
        }
    }
}
