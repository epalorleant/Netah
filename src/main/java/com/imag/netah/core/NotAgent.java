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
import java.util.concurrent.Executors;



/**
 *
 * @author epaln
 */
public class NotAgent extends EPUnit {

    IOTerminal inputTerminal;
    IOTerminal outputTerminal;

    public NotAgent(String info, String IDinputTerminal, String IDoutputTerminal) {
        super(info);        
        this._info = info;
        this._type = "Negation";
        this._receivers[0] = new TopicReceiver(this);
        inputTerminal = new IOTerminal(IDinputTerminal, "input channel " + _type, _receivers[0], this);
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        //Queue<EventBean> selected1 = Queues.newArrayDeque();
        //_selectedEvents[0] = selected1;
        logger = new LoggerUtil("NotMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<IOTerminal>();
        inputs.add(inputTerminal);
        return inputs;
    }

    @Override
    public IOTerminal getOutputTerminal() {
        return outputTerminal;
    }

    @Override
    public void process() {
       // while (!_selectedEvents.isEmpty()) {
            EventBean evt = _selectedEvents.poll();
            evt.getHeader().setProductionTime(System.currentTimeMillis());
            evt.getHeader().setTypeIdentifier("Negation");
            evt.getHeader().setIsComposite(true);
            evt.getHeader().setProducerID(this.getName());
            evt.getHeader().setPriority((short) 1);
            evt.payload.put("ttl", TTL);
            _outputQueue.put(evt);
            getExecutorService().execute(getOutputNotifier());
        //}

    }

}
