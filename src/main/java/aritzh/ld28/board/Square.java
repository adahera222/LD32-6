package aritzh.ld28.board;

import aritzh.ld28.Game;
import aritzh.ld28.render.Render;
import aritzh.ld28.render.Sprite;
import aritzh.ld28.sound.Sound;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public enum Square {
    NORMAL(0, "/audio/incorrect.wav"), LIT(1, "/audio/correct.wav"), UPGRADE(2, "/audio/upgrade.wav");
    private final Sprite normal, hover, click;
    private final Sound sound;

    Square(int x, String audioPath) {
        this.normal = Game.INSTANCE.sheet.getSprite(x, 0);
        this.hover = Game.INSTANCE.sheet.getSprite(x, 1);
        this.click = Game.INSTANCE.sheet.getSprite(x, 2);
        this.sound = new Sound(this.getClass().getResourceAsStream(audioPath));
    }

    public void render(int x, int y, Render render, boolean hovered, boolean clicked) {
        render.drawSprite(x, y, clicked ? this.click : hovered ? this.hover : this.normal);
    }

    public void playSound() {
        this.sound.play();
    }
}
