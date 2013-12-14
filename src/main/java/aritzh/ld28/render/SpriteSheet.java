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

    public static final int SPRITE_SIZE = 64;
    private final BufferedImage image;
    private final Sprite[][] loadedSprites;

    public SpriteSheet(InputStream stream) {
        try {
            this.image = ImageIO.read(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading spritesheet image", e);
        }
        this.loadedSprites = new Sprite[this.image.getWidth() / SPRITE_SIZE][this.image.getHeight() / SPRITE_SIZE];
    }

    public Sprite getSprite(int x, int y) {
        if (this.loadedSprites[x][y] != null) return this.loadedSprites[x][y];

        final int[] pixels = this.image.getRGB(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE, null, 0, SPRITE_SIZE);
        return this.loadedSprites[x][y] = new Sprite(SPRITE_SIZE, SPRITE_SIZE, pixels);
    }
}
