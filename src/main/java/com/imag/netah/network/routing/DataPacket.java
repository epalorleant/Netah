/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.routing;

import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author epaln
 */
public class DataPacket implements Serializable{
    private Device origin;
    private Device source;
    private ArrayList<Device> destination;
    //private ComLink path;
    private ComLink inputLink;
    private Object event;
    private String topic;
    private Color color = Color.BLUE;
    
    public DataPacket(Object event) {
        this.destination = new ArrayList<Device>();
        this.event = event;
        //path = null;
    }

    public DataPacket(Device origin, Object event) {
        this.destination = new ArrayList<Device>();
        this.origin = origin;
        this.event = event;
        //path = null;
    }

    public DataPacket(Device origin, Object event, String topic) {
        this.destination = new ArrayList<Device>();
        this.origin = origin;
        this.event = event;
        this.topic = topic;
       // path = null;
    }

    public Device getOrigin() {
        return origin;
    }

    public void setOrigin(Device origin) {
        this.origin = origin;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }
/*
    public ComLink getPath() {
        return path;
    }
*/
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ComLink getInputLink() {
        return inputLink;
    }

    public void setInputLink(ComLink inputLink) {
        this.inputLink = inputLink;
    }
   
    /*
    public void setPathToDestination(Device depart, Device dest){
        path = null;
        path= Topology.getInstance().getPath(depart, dest).get(0);
    }
*/
    public ArrayList<Device> getDestination() {
        return destination;
    }

    public Device getSource() {
        return source;
    }

    public void setSource(Device source) {
        this.source = source;
    }

    public void setDestination(Collection<Device> destination) {
        this.destination.clear();
        for(Device d: destination){
            this.destination.add(d);
        } 
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public DataPacket clone(){
        DataPacket ep = new DataPacket(origin, event, topic);
        //ep.path = path;
        ep.color = color;
        //ep.destination = destination;
        return ep;
    }
    
}
