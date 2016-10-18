/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.samples.scenarioSogrid.producers;


import com.imag.netah.core.event.EventBean;
import com.imag.netah.runtime.client.EventProducer;
import com.imag.samples.scenarioSogrid.events.UVoltage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class DCProducer extends EventProducer {

    private BlockingQueue<EventBean> queue;
    
    public DCProducer(String name) {
        super(name, UVoltage.class);
        queue = new LinkedBlockingDeque<>();
    }

    public BlockingQueue<EventBean> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<EventBean> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                EventBean evt = queue.take();
                evt.getHeader().setProducerID(this.getDevice().getDeviceName());
                evt.payload.put("deviceID", this.getDevice().getDeviceName());
                publish(evt);
            } catch (Exception ex) {
                Logger.getLogger(DCProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    }
    
    
}
