package aritzh.ld28.render;

import aritzh.ld28.Game;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Render {

    private final int[] pixels;
    private Game game;
    private BufferedImage image;
    private final int width, height;

    public Render(Game game) {
        this.game = game;
        this.width = this.game.getWidth();
        this.height = this.game.getHeight();
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(this.pixels, 0xFF000000);
    }

    public void drawSprite(int x, int y, int width, int height, int[] pixels) {
        int maxX = Math.min(width, this.width);
        int maxY = Math.min(height, this.height);

        for (int yp = 0; yp < maxY; yp++) {
            final int beforeY = y + yp;
            for (int xp = 0; xp < maxX; xp++) {
                final int beforeX = x + xp;
                this.pixels[beforeX + this.width * beforeY] = pixels[xp + yp * width];
            }
        }
    }

    public void drawSprite(int x, int y, Sprite sprite) {
        this.drawSprite(x, y, sprite.getWidth(), sprite.getHeight(), sprite.getPixels());
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
