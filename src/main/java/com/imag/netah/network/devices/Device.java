/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.devices;

import com.imag.netah.gui.plugin.MyLayeredIcon;
import com.imag.netah.network.routing.DataPacket;
import com.imag.netah.network.routing.PubSubService;
import com.imag.netah.network.routing.Topic2Device;
import com.imag.netah.network.routing.Topology;
import com.imag.netah.core.EPUnit;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author epaln
 */
public class Device extends Thread implements Serializable {

    private String deviceName;
    private double cpuSpeed;
    private int totalMemory;
    private DeviceType deviceType;
    private int remainingMemory;
    private double x = 0, y = 0;

    // input port of the device
    //private Sim_port inputPort;
    // The local Pub/Sub service 
    PubSubService pubSubService;
    private ArrayList<EPUnit> operators;
    private LinkedBlockingQueue<DataPacket> inputQueue;
    private Color packetColor = Color.BLUE;
    protected MyLayeredIcon icon;

    public Color getPacketColor() {
        return packetColor;
    }

    public void setPacketColor(Color packetColor) {
        this.packetColor = packetColor;
    }

    public Device(String name) {
        super(name);
        this.deviceName = name;
        //inputPort = new Sim_port("In");
        //add_port(inputPort);
        pubSubService = new PubSubService(this);
        operators = new ArrayList<>();
        inputQueue = new LinkedBlockingQueue<>();
        Random r = new Random();
        int red = r.nextInt(255);
        int green = r.nextInt(255);
        int blue = r.nextInt(255);
        packetColor= new Color(red, green, blue);
    }

    public Device(String name, double cpuSpeed, int totalMemory, DeviceType type) {
        this(name);
        this.cpuSpeed = cpuSpeed;
        this.totalMemory = totalMemory;
        this.deviceType = type;
        this.remainingMemory = totalMemory;
        Random r = new Random();
        int red = r.nextInt(255);
        int green = r.nextInt(255);
        int blue = r.nextInt(255);
        packetColor= new Color(red, green, blue);
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
        setRemainingMemory(totalMemory);
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return deviceName;
    }

    public LinkedBlockingQueue<DataPacket> getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(LinkedBlockingQueue<DataPacket> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public synchronized void receiveEventPacket(DataPacket packet) {
        inputQueue.add(packet);
    }

    private void forwardEventPacket(DataPacket packet) {
        // for routing ijn the right direction ;)
        packet.setOrigin(this);
        HashMap<ComLink, DataPacket> map = new HashMap<>();
        //EventPacket ep = packet.clone();
        for(Device d: packet.getDestination()){
            ComLink interf = getPathToDestination(this, d);
            // do not send back the packet over its input port/link
            if(interf == packet.getInputLink() || interf == null){
                continue;
            }
            DataPacket ep = map.get(interf);
            if(ep == null){
                ep = packet.clone();
                ep.getDestination().add(d);
                map.put(interf, ep);
            }
            else{
                ep.getDestination().add(d);               
            }
        }
        for(ComLink link: map.keySet()){
            DataPacket ep = map.get(link);
            link.putPacket(ep);
        }
//        packet.setPathToDestination(this, );
//        ComLink link = packet.getPath().remove(0);
        // send the event packet over the link
//        link.putPacket(packet);
    }

    private ComLink getPathToDestination(Device depart, Device dest){ 
        List<ComLink> apath = Topology.getInstance().getPath(depart, dest);
        if(!apath.isEmpty()){
            return Topology.getInstance().getPath(depart, dest).get(0);
        }
        return null;      
    }
    
    @Override
    public void run() {
        while (true) {
            try {                
                DataPacket packet = inputQueue.take();                
                if (/*packet.getPath() == null ||*/ packet.getDestination().contains(this)) { // then, the packet arrived at destination. We can process it here.
                    process(packet);
                    packet.getDestination().remove(this);
                    if(!packet.getDestination().isEmpty()) {
                        forwardEventPacket(packet);
                    }
                } else { // then, the packet is just passing. Forward it!
                    forwardEventPacket(packet);
                }                
            } catch (InterruptedException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void process(DataPacket packet) {
        pubSubService.publish(packet.getEvent(), packet.getTopic());
    }

    public PubSubService getPubSubService() {
        return pubSubService;
    }

    public int getRemainingMemory() {
        return remainingMemory;
    }

    public void setRemainingMemory(int remainingMemory) {
        this.remainingMemory = remainingMemory;
    }

    /**
     * send the event to the corresponding destination
     *
     * @param packet
     * @param dest
     */
    protected void sendPacket(DataPacket packet, Collection<Device> dest) {
        // set the destination of this packet.
        packet.setSource(this);
        packet.setDestination(dest);
        forwardEventPacket(packet);
    }
    
    protected void sendPacket(DataPacket packet, Device dest){
        ArrayList<Device> d = new ArrayList<>();
        d.add(dest);
        sendPacket(packet, d);
    }

    /**
     * publish the event to the corresponding topic
     *
     * @param event
     * @param topic
     */
    public void publishEvent(Object event, String topic) {
        for (Device dest : Topic2Device.getInstance().getTopic2device().get(topic)) {
            if (dest == this) {
                pubSubService.publish(event, topic);
                
            }
            DataPacket packet = new DataPacket(this, event, topic);
            packet.setColor(packetColor);
            sendPacket(packet, Topic2Device.getInstance().getTopic2device().get(topic));
        }
    }

    public ArrayList<EPUnit> getOperators() {
        return operators;
    }

    public void addEPUnit(EPUnit op) {
        operators.add(op);
        op.setDevice(this);
        op.setMapped(true);
        remainingMemory -= op.getUsedMemory();
    }

    public void removeEPUnit(EPUnit op) {
        operators.remove(op);
        op.setDevice(null);
        op.setMapped(false);
        remainingMemory += op.getUsedMemory();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public MyLayeredIcon getIcon() {
        return icon;
    }

    public void setIcon(MyLayeredIcon icon) {
        this.icon = icon;
    }

}
