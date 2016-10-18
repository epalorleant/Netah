/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.samples.scenarioSogrid.producers;


import com.imag.netah.core.event.EventBean;
import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import com.imag.netah.network.routing.Topology;
import com.imag.netah.runtime.client.CSVFileLoader2EvenBean;
import com.imag.netah.runtime.client.EventProducer;
import com.imag.samples.scenarioSogrid.events.ResistiveFault;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class PalierHTA extends EventProducer {

    static String[] schema = {"depart", "failure"};
    static String[] types = {"String", "boolean"};
    CSVFileLoader2EvenBean fileLoader;
    private long closeDelay;

    public PalierHTA(String name, InputStream fileInputStream, long delai, long closeDelay) {
        super(name, ResistiveFault.class);
        fileLoader = new CSVFileLoader2EvenBean(fileInputStream, schema, types);
        setDelay(delai);
        this.closeDelay = closeDelay;
    }

    @Override
    public void run() {
        boolean ok = true;
        int i=1;
        while (ok) {
            try {
                EventBean evt = fileLoader.getNext();               
                if (evt == null) {
                    ok = false;
                } else {
                   // System.out.println("Evt Produced Num "+i++ +": "+evt.payload);
                    boolean down = (boolean)evt.getValue("failure");
                    String linkID = (String) evt.getValue("depart");                   
                    if (down) {
                        evt.payload.put("location", getDevice().getDeviceName());
                        evt.getHeader().setPriority((short) 4);
                        evt.getHeader().setProducerID(this.getName());
                        evt.getHeader().setProductionTime(System.currentTimeMillis());
                        evt.getHeader().setDetectionTime(System.currentTimeMillis());                      
                        Device d = getDevice();
                        for (final ComLink link : Topology.getInstance().getGraph().getIncidentEdges(d)) {
                            if (link.getID().equals(linkID)) {
                                publish(evt);   
                                System.out.println(evt.payload);
                                Timer t = new Timer();
                                t.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        System.out.println(link.getID()+" is now in down state");
                                        link.setDown(true);
                                    }
                                }, closeDelay);
                            }
                        }
                    }
                    Thread.sleep(delay);
                }
            } catch (Exception ex) {
                Logger.getLogger(VoltageProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
