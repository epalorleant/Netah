/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.devices;

import cern.jet.random.Binomial;
import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;
import com.imag.netah.gui.animation.EdgeAnimation;
import com.imag.netah.gui.plugin.GraphEditor;
import com.imag.netah.network.routing.DataPacket;
import com.imag.netah.network.routing.Topology;
import com.imag.netah.core.logging.LoggerUtil;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class ComLink extends Thread {

    private int latency;
    private String ID;
    private int size;
    private int bandwidth;
    private LinkedBlockingQueue<DataPacket> pendingPackets;
    private double lossRate;
    private double dev;
    private boolean down;
    //private int packetlost;

    // for display control
    private int definedlatency;
    private Normal normalDist;
    private LoggerUtil logger;
    private Binomial bernoulliDist;

    //private double var;
    /**
     * ports used to communicate in the default direction and the opposite
     * direction respectively
     */
    //private Sim_port outputPort1, outputPort2;
    public ComLink(String ID) {
        //super(ID);
        this.ID = ID;
        //outputPort1 = new Sim_port("Out1");
        //add_port(outputPort1);
        //outputPort2 = new Sim_port("Out2");
        //add_port(outputPort2);
        pendingPackets = new LinkedBlockingQueue<>();
        logger = new LoggerUtil("Latencies_" + ID);
        down = false;
        //delay = new Sim_normal_obj("Latency", latency, var);
        latency = 10;
        dev = Math.sqrt(latency);
        normalDist = new Normal(latency, dev, RandomEngine.makeDefault());
        lossRate = 0.05;
        bernoulliDist = new Binomial(1, lossRate, RandomEngine.makeDefault());
    }

    public ComLink(int latency, String ID, int size, int bandwidth) {
        this(ID);
        this.latency = latency;
        definedlatency = latency;
        this.size = size;
        this.bandwidth = bandwidth;
        normalDist.setState(latency, Math.sqrt(latency));

    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
        normalDist.setState(latency, dev);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public String toString() {
        return ID;
    }

    public int getDefinedlatency() {
        return definedlatency;
    }

    public void setDefinedlatency(int definedlatency) {
        this.definedlatency = definedlatency;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
        bernoulliDist.setNandP(1, lossRate);
    }

    public double getDev() {
        return dev;
    }

    public void setDev(double dev) {
        this.dev = dev;
        normalDist.setState(latency, dev);
    }

    public LinkedBlockingQueue<DataPacket> getPendingPackets() {
        return pendingPackets;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void putPacket(DataPacket packet) {
        try {
            //System.out.println("sending packet via " + ID);
            pendingPackets.put(packet);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean send(DataPacket packet) {
        if (isDown()) {
            return false;
        }
        packet.setInputLink(this);
        int overhead = 0, tried = 1;
        while (bernoulliDist.nextInt() == 1) {
            overhead += latency;
            tried++;
        }
        int currentLatency = (int) Math.floor(normalDist.nextDouble()) + overhead;
        if (currentLatency <= 0) {
            currentLatency = overhead + latency;
        }
        Object lock = new Object();
        // The communication can be in both direction, compute the right direction of the packet
        int direction = getDirection(packet);
        logger.log(System.currentTimeMillis() + ", " + currentLatency + ", " + tried + ", " + pendingPackets.size());
        try {
            synchronized (lock) {
                if (direction == 0) { // the default edge direction, send the event on the direct direction
                    Device d = Topology.getInstance().getGraph().getDest(this);
                    showMessageAnimation(direction, currentLatency, lock, packet.getColor());
                    lock.wait();
                    d.receiveEventPacket(packet);
                } else { //the opposite direction
                    Device d = Topology.getInstance().getGraph().getSource(this);
                    showMessageAnimation(direction, currentLatency, lock, packet.getColor());
                    lock.wait();
                    d.receiveEventPacket(packet);
                }
            }
        } catch (InterruptedException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        logger.log("Timestamp, Latency, #Try, #PendingPackets");
        while (true) {
            try {
                DataPacket packet = pendingPackets.take();
                send(packet);
            } catch (InterruptedException ex) {

            }
        }
    }

    private void showMessageAnimation(int direction, int duration, Object lock, Color color) {
        // default speed is 20
        final BufferedImage image = EdgeAnimation.createDummyImage(20, 20, color, "e");
        EdgeAnimation<Device, ComLink> edgeAnimation = new EdgeAnimation<>(GraphEditor.getInstance().getVv(), direction, duration);
        edgeAnimation.animate(this, image, lock);
    }

    private int getDirection(DataPacket p) {

        Device d = p.getOrigin();
        Device dest = Topology.getInstance().getGraph().getSource(this);
        if (d == dest) {
            return 0;
        } else {
            return 1;
        }
    }

}
