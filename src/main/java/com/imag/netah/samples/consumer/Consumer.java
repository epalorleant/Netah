/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.samples.consumer;


import com.imag.netah.runtime.client.AnEventHandler;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.logging.LoggerUtil;
import java.util.Calendar;



/**
 *
 * @author epaln
 */
public class Consumer extends AnEventHandler {

    private boolean isQoS = false;
    LoggerUtil logger;

    public Consumer() {
        logger = new LoggerUtil("latencies");
    }

    @Override
    public void notify(EventBean[] evts) {
        System.out.println("Received(" + evts.length + " evts): ");

        for (EventBean evt : evts) {
            //long timeSinceProd = evt.getHeader().getReceptionTime()- evt.getHeader().getProductionTime();
            //long notificationTime = evt.getHeader().getReceptionTime()- evt.getHeader().getNotificationTime();
            //logger.log(timeSinceProd + ", "+ notificationTime);
            //System.out.println(MemoryMeasurer.measureBytes(evt)+" bytes");
            // System.out.println(evt.getValue("avgPwr")+", " +evt.getValue("meterID"));//+", "+evt.getHeader().getPriority());
            if(evt.payload.contains("avgPwr")){
             System.out.println("Average Pwr: "+ evt.getValue("avgPwr"));//+", " +evt.getValue("meterID"));
            }
            else{
                System.out.println(evt.payload);
            }
        }
    }

private String getHour(long timestamp) {
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(timestamp);
        return d.get(Calendar.HOUR_OF_DAY) + ":" + d.get(Calendar.MINUTE) + ":" + d.get(Calendar.SECOND);
    }
}