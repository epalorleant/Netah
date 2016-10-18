/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.samples.scenarioSogrid.producers;


import com.imag.netah.core.event.EventBean;
import com.imag.netah.runtime.client.CSVFileLoader2EvenBean;
import com.imag.netah.runtime.client.EventProducer;
import com.imag.samples.scenarioSogrid.events.RVoltage;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class VoltageProducer extends EventProducer {

    static String[] schema = {"voltage"};
    static String[] types = {"double"};
    CSVFileLoader2EvenBean fileLoader;
    //private InputStream fileInputSTream;

    public VoltageProducer(String name, InputStream fileInputStream, long delai) {
        super(name, RVoltage.class);
        //  this.fileInputSTream = fileInputStream;        
        fileLoader = new CSVFileLoader2EvenBean(fileInputStream, schema, types);
        setDelay(delai);
    }

    @Override
    public void run() {
        boolean ok = true;
        while (ok) {
            try {
                EventBean evt = fileLoader.getNext();
                if (evt == null) {
                    ok = false;
                }
                else{
                    evt.payload.put("deviceID", getDevice().getDeviceName());
                    evt.payload.put("id", System.currentTimeMillis());
                    evt.getHeader().setPriority((short)3);
                    evt.getHeader().setProducerID(this.getName());
                    evt.getHeader().setProductionTime(System.currentTimeMillis());
                    evt.getHeader().setDetectionTime(System.currentTimeMillis());
                    publish(evt);
                    System.out.println(evt.payload);
                    Thread.sleep(delay);
                }
            } catch (Exception ex) {
                Logger.getLogger(VoltageProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
