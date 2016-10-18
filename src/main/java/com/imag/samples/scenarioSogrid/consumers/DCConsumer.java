/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.samples.scenarioSogrid.consumers;


import com.imag.netah.core.event.EventBean;
import com.imag.netah.runtime.client.AnEventHandler;
import com.imag.samples.scenarioSogrid.producers.DCProducer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author epaln
 */
public class DCConsumer extends AnEventHandler{
    DCProducer producer;
    
    public DCConsumer(DCProducer producer) {
        this.producer = producer;
    }

    @Override
    public void notify(EventBean[] evts) {
        for(EventBean evt: evts){
            try {
                producer.getQueue().put(evt);
            } catch (InterruptedException ex) {
                Logger.getLogger(DCConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
