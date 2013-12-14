package aritzh.ld28.render;

import java.util.Arrays;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Sprite {

    private final int[] pixels;
    private final int width;
    private final int height;

    public Sprite(int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.pixels = new int[this.width * this.height];
        Arrays.fill(this.pixels, color);
    }

    public Sprite(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
