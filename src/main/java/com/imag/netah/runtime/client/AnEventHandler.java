/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.client;

import com.imag.netah.core.event.EventBean;

/**
 *
 * @author epaln
 */
public abstract class AnEventHandler {
   EventConsumer consumer;
    public abstract void notify(EventBean[] evts);

    public void setConsumer(EventConsumer c){
    consumer = c;
}
}
