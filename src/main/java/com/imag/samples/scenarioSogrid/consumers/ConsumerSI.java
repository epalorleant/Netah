/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.samples.scenarioSogrid.consumers;


import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.runtime.client.AnEventHandler;
import javax.swing.JOptionPane;

/**
 *
 * @author epaln
 */
public class ConsumerSI extends AnEventHandler {

    LoggerUtil logger;
    int count = 1;

    public ConsumerSI() {
        logger = new LoggerUtil("SITR_Notifications");
        logger.log("Info, HTA_Link, BT_Voltage, BT_Location, Notification_Latency, SizeOfEvent");
    }

    @Override
    public void notify(EventBean[] ebs) {
        String depart = "", deviceID="", text = "";
        double voltage=0;
        long latency;
        for (EventBean evt : ebs) {
            //System.out.println("RFailure received: "+evt.payload);

            latency = evt.getHeader().getReceptionTime() - evt.getHeader().getDetectionTime();
            EventBean[] data = (EventBean[]) evt.getValue("data");
            for (EventBean e : data) {
                if (e.payload.containsKey("depart")) { // the HVFailure event
                    depart = (String) data[0].getValue("depart");
                    //deviceID = (String) data[1].getValue("deviceID");
                    //voltage = (double) data[1].getValue("voltage");
                } else {
                    //depart = (String) data[1].getValue("depart");
                    deviceID = (String) e.getValue("deviceID");
                    voltage = (double) e.getValue("voltage");
                    text = text.concat(deviceID + " (" + voltage + " V)\n");
                    logger.log("HVFailure_" + count + ", " + depart + ", " + voltage + ", " + deviceID + ", " + latency + ", " + evt.sizeOf());
                    count++;
                }
                
            }
           // logger.log("HVFailure_" + count + ", " + depart + ", " + voltage + ", " + deviceID + ", " + latency + ", " + evt.sizeOf());            
        }
        JOptionPane.showMessageDialog(null, "Resistive Fault at " + depart + " with voltage imbalance at:\n" + text);
    }

}
