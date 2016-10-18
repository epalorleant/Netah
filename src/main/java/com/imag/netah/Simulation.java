/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah;

import com.imag.netah.gui.plugin.GraphEditor;
import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import com.imag.netah.network.routing.Topology;
import com.imag.netah.runtime.client.EventConsumer;
import com.imag.netah.runtime.client.EventProducer;
import java.util.ArrayList;

/**
 *
 * @author epaln
 */
public class Simulation {
    private ArrayList<EventProducer> producers;
    private ArrayList<EventConsumer> consumers;
    private static GraphEditor g;
    
    public Simulation() {
        producers = new ArrayList<>();
        consumers = new ArrayList<>();
    }
    
    public void addProducer(EventProducer p){
        producers.add(p);
    }
   
    public void addConsumer(EventConsumer c){
        consumers.add(c);
    }
    
    public void removeProducer(EventProducer p){
        producers.remove(p);
    }
   
    public void removeConsumer(EventConsumer c){
        consumers.remove(c);
    }
    
    public void resetMapping(){
        for (EventConsumer c: consumers){
            c.resetMapping();
        }
    }

    public ArrayList<EventProducer> getProducers() {
        return producers;
    }

    public ArrayList<EventConsumer> getConsumers() {
        return consumers;
    }
    
    public void setup(){
        for (EventConsumer c : consumers){
            c.buildCompositionGraph(producers);
        }
    }
    
    public void run(){
        setup();
        g= GraphEditor.getInstance(this); 
        
    }
    public void addDevice(Device d){
        Topology.getInstance().getGraph().addVertex(d);
    }
    public void addComLink(ComLink l, Device d1, Device d2 ){
        Topology.getInstance().getGraph().addEdge(l, d1, d2);
    }
}
