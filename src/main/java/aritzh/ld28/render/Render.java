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
        Arrays.fill(this.pixels, 0xFF000066);
    }

    public void drawSprite(int x, int y, int width, int height, int[] pixels) {

        int maxX = Math.min(width, this.width);
        int maxY = Math.min(y+height, this.height);

        for (int yp = 0; yp < width; yp++) {
            final int screenY = y + yp;
            for (int xp = 0; xp < height; xp++) {
                final int screenX = x + xp;
                if(screenX >= this.width || screenY >= this.height) return;
                this.pixels[screenX + this.width * screenY] = pixels[xp + yp * width];
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
