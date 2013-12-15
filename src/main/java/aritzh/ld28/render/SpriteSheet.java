package aritzh.ld28.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class SpriteSheet {

    private final int size;
    private final BufferedImage image;
    private final Sprite[][] loadedSprites;

    public SpriteSheet(InputStream stream, int size) {
        this.size = size;
        try {
            this.image = ImageIO.read(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading spritesheet image", e);
        }
        this.loadedSprites = new Sprite[this.image.getWidth() / this.size][this.image.getHeight() / this.size];
    }

    public Sprite getSprite(int x, int y) {
        if (this.loadedSprites[x][y] != null) return this.loadedSprites[x][y];

        final int[] pixels = this.image.getRGB(x * this.size, y * this.size, this.size, this.size, null, 0, this.size);
        return this.loadedSprites[x][y] = new Sprite(this.size, this.size, pixels);
    }

    public int getSize() {
        return size;
    }
}
