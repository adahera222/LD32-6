package aritzh.ld28.sound;

import com.sun.media.sound.JavaSoundAudioClip;

import java.applet.AudioClip;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Sound {

    private final AudioClip clip;

    public Sound(InputStream stream) {
        try {
            this.clip = new JavaSoundAudioClip(stream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Sound could not be loaded", e);
        }
    }

    public void play() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Sound.this.clip.play();
            }
        }, "Sound thread");
        t.setDaemon(true);
        t.start();
    }

    public void stop() {
        this.clip.stop();
    }
}
