package aritzh.ld28.screen.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Button {

    private final Rectangle bounds;
    private final String text;
    private boolean hovered;
    private boolean pressed;
    private boolean wasActivated;

    public Button(int x, int y, int width, int height, String text) {
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.text = text;
    }

    public void render(Graphics g) {
        if (this.pressed) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8);
        } else if (this.hovered) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(Color.WHITE);
            g.fillRect(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8);
        }


        Rectangle2D r = g.getFontMetrics().getStringBounds(this.text, g);

        g.setColor(Color.BLACK);
        final int xPos = (int) (bounds.x - r.getWidth() / 2 + bounds.width / 2);
        g.drawString(text, xPos, bounds.y + g.getFontMetrics().getHeight() / 4 + bounds.height / 2);
    }

    public void mousePressed(MouseEvent e) {
        this.pressed = this.bounds.contains(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        this.wasActivated = this.getBounds().contains(e.getX(), e.getY());
        this.mouseMoved(e);
        this.pressed = false;
    }

    public void mouseMoved(MouseEvent e) {
        this.hovered = this.bounds.contains(e.getX(), e.getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean wasActivated() {
        return this.wasActivated;
    }
}
