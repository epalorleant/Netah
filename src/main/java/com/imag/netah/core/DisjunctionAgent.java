/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author epaln
 */
public class DisjunctionAgent extends EPUnit {

    IOTerminal[] inputTerminals;
    IOTerminal outputTerminal;

    public DisjunctionAgent(String info, String[] IDinputTerminals, String IDoutputTerminal) {
        super(info);
        this._info = info;
        this._type = "Disjunction";
        this._receivers = new TopicReceiver[IDinputTerminals.length];
        inputTerminals = new IOTerminal[IDinputTerminals.length];
        short i=0;
        for(String input: IDinputTerminals){
            this._receivers[i] = new TopicReceiver(this, i);
            inputTerminals[i]= new IOTerminal(input, "input channel "+ _type,_receivers[i], this);
            i++;            
        }
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        logger = new LoggerUtil("DisjunctionMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
    }

    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<IOTerminal>();
        for(IOTerminal io: inputTerminals){
            inputs.add(io);
        }        
        return inputs;
    }

    @Override
    public IOTerminal getOutputTerminal() {
        return outputTerminal;
    }

    @Override
    public void process() {
        EventBean[] values = new EventBean[1];
        //ArrayList<EventBean> lValues, rValues;

        // statistics: #events processed, processing time
        long time = System.currentTimeMillis();
        long ntime = System.nanoTime(); // to compute the processing time for that cycle
        EventBean select = _selectedEvents.poll();

        if (select != null) {
            if (select.getHeader().getTypeIdentifier().equals("Window")) {
                values = (EventBean[]) select.getValue("window");
            } else {
                values = new EventBean[1];
                values[0] = select;
            }
        }
        for (EventBean e : values) {
            e.getHeader().setProductionTime(System.currentTimeMillis());
            e.getHeader().setIsComposite(true);
            e.payload.put("ttl", TTL);
            _outputQueue.put(e);
            time = System.currentTimeMillis() - time;
            numEventProduced++;
                    //logger.log(this.getInfo()+" ,True, "+time+", "+ this.getInputTerminals().iterator().next().getReceiver().getInputQueue().size()+
            //      ", "+ this.getOutputQueue().size());
            getExecutorService().execute(getOutputNotifier());
            processingTime += time;
        }
}

}
