/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.gui.animation;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 *
 * @author epaln
 */
public class EdgeAnimation<V, E> implements ActionListener {

    ImageAtEdgeDrawer<V, E> imgDrawer;
    VisualizationViewer vv;
    long prevMillis = 0;
    Timer t;
    int direction;
    int duration;
    double pas, phase=0;
    Object lock;
    public EdgeAnimation(VisualizationViewer vv, int direction, int duration) {
        this.vv = vv;
        this.direction = direction;
        this.duration = duration;
        double ticks = duration / 10;
        pas = 1/ticks;
    }

    public void animate(E edge, BufferedImage img, Object lock){//, int speed) {
        imgDrawer = new ImageAtEdgeDrawer<V, E>(vv, edge, img);
        this.lock = lock;
        //t = new Timer(speed, this);
        t = new Timer(10, this);
        t.start();
        vv.addPostRenderPaintable(imgDrawer);
    }

    public static BufferedImage createDummyImage(int width, int height, Color c, String text) {
        BufferedImage image
                = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, width, height);
        //g.fillOval(0,0,width,height);
        g.setColor(Color.WHITE);
        g.drawString(text, 10, 20);
        g.dispose();
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        if (prevMillis == 0) {
            prevMillis = System.currentTimeMillis();
        }
        long dtMs = System.currentTimeMillis() - prevMillis;
        double dt = dtMs / 1000.0;
        double phase = Math.abs(Math.sin(dt));
                */
        synchronized(lock){
        if (direction == 0) {
            imgDrawer.setImageLocation(phase);
        } else {
            imgDrawer.setImageLocation(1 - phase);
        }
        vv.repaint();
        // System.out.println(phase);
        if (phase >= 0.95) {
            // if we arrived at the destination edge
            t.stop();
            vv.removePostRenderPaintable(imgDrawer);
            vv.repaint();
            lock.notify();
        }
        phase+=(5*pas);
    }
    }
}
