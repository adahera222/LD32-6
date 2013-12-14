package aritzh.ld28.screen.elements;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ProgressBar {

    private int min;
    private int max;
    private int progress;

    public ProgressBar(int start, int min, int max) {
        this.progress = start;
        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if(this.progress>this.max) this.progress = this.max;
    }

    public void render(Graphics g, int x, int y, int width, int height) {

        double percent = progress / (double) (max - min);

        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.fillRect(x + 2, y + 2, (int) ((width-4) * percent), height - 4);
    }
}
