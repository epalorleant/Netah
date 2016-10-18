/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.network.routing.Subscriber;
import com.imag.netah.core.event.EventBean;





/**
 *
 * @author epaln
 */
public class TopicReceiver implements Subscriber {

    private EPUnit _epAgent;
    private short flag;


    public TopicReceiver(EPUnit _epAgent, short flag) {
        this(_epAgent, new BatchNWindow(1), flag);
    }

    public TopicReceiver(EPUnit _epAgent) {
        this(_epAgent, (short)0 );
    }
    
    public TopicReceiver(EPUnit _epAgent, WindowHandler handler, short flag) {
        this._epAgent = _epAgent;
        this.flag =  flag;
    }

    public TopicReceiver() {
        this(null);
    }
    
    public EPUnit getEpAgent() {
        return _epAgent;
    }

    @Override
    public void notify(Object event) {
        EventBean[] evts = (EventBean[]) event;
        for (EventBean e : evts) {
            EventBean evt = e.copy();
            evt.getHeader().setReceptionTime(System.currentTimeMillis());
            long ntime = System.nanoTime();
            evt.payload.put("#time#", System.currentTimeMillis()); // start processing this evt at #time#
            evt.payload.put("processTime", ntime);
            evt.payload.put("flag", flag);
            //System.out.println("ProducerID:"+evt.getHeader().getProducerID()+"; type:"+evt.getHeader().getTypeIdentifier()+"; latency: "+ (evt.getHeader().getReceptionTime()-evt.getHeader().getProductionTime()) );
            _epAgent.getSourceStream().onNext(evt);
            //_inputQueue.put(evt); 
        }

        //_epAgent.process(evts);
    }
}
