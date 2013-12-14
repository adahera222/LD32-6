package aritzh.ld28;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends Canvas implements Runnable {

    private final int width;
    private final int height;
    private final boolean applet;
    private final Thread thread;
    private boolean running;
    private final double WANTED_UPS = 60.0;
    private int fps, ups;
    private JFrame frame;

    public Game(int width, int height, boolean applet) {
        this.width = width;
        this.height = height;
        this.applet = applet;
        this.thread = new Thread(this, "Main Game Thread");

        if(!this.applet) this.createWindow();
    }

    private void createWindow() {
        this.frame = new JFrame("LD28 Entry");
        this.frame.setResizable(false);
        final Dimension preferredSize = new Dimension(width, height);
        this.frame.setPreferredSize(preferredSize);
        this.frame.getContentPane().setPreferredSize(preferredSize);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.add(this);
        this.frame.pack();

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Game.this.stop();
            }
        });
    }

    public synchronized void start() {
        this.running = true;
        this.frame.setVisible(true);
        this.thread.start();
    }

    public synchronized void stop() {
        this.running = false;
        try {
            this.thread.join(3000);
        } catch (InterruptedException e) {
            System.err.println("Thread could not be stopped within 3 seconds");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long lastTimeMillis = System.currentTimeMillis();

        final double NSPerTick = 1000000000.0 / WANTED_UPS;
        double delta = 0.0;
        while (this.running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / NSPerTick;
            lastTime = now;

            if (delta >= 1) {
                this.update(delta);
                delta--;
                ups++;
            }

            this.render();
            this.fps++;

            if (System.currentTimeMillis() - lastTimeMillis >= 1000) {
                this.updatePS();
                lastTimeMillis += 1000;
                this.fps = this.ups = 0;
            }
        }
    }

    private void updatePS() {
        System.out.println("FPS: " + fps + "\t|\tUPS: " + this.ups);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);


        g.dispose();
        bs.show();
    }

    private void update(double delta) {

    }
}
