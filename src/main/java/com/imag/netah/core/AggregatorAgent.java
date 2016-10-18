/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.runtime.base.NameValuePair;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author epaln
 */
public class AggregatorAgent extends EPUnit {

    IOTerminal inputTerminal;
    IOTerminal outputTerminal;
    List<Aggregate> aggregators = new ArrayList<>();

    public AggregatorAgent(String info, String IDinputTerminal, String IDoutputTerminal) {
        super(info);
        //this._filter = filter;        
        this._info = info;
        this._type = "Aggregator";
        this._receivers[0] = new TopicReceiver(this);
        inputTerminal = new IOTerminal(IDinputTerminal, "input channel " + _type, _receivers[0], this);
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        logger = new LoggerUtil("AggregatorMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
    }

    public AggregatorAgent(String info) {
        super(info);
        //this._filter = filter;        
        this._info = info;
        this._type = "Aggregator";
        this._receivers[0] = new TopicReceiver(this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        logger = new LoggerUtil("AggregatorMeasures");
        //logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
    }

    public void addAggregator(Aggregate aggregator) {
        this.aggregators.add(aggregator);
    }

    public void setInpuTerminal(String input) {
        inputTerminal = new IOTerminal(input, "input channel " + _type, _receivers[0], this);
    }

    public void setOutputTerminal(String output) {
        outputTerminal = new IOTerminal(output, "output channel " + _type, this);
    }

    public List<Aggregate> getAggregators() {
        return aggregators;
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
        //EventBean evt = aggregator.aggregate(_selectedEvents.toArray(new EventBean[0]));
        EventBean[] operands;
        //start processing time
        long time = System.currentTimeMillis();
        long ntime = System.nanoTime();
        EventBean evt = _selectedEvents.poll();
        if (evt.getHeader().getTypeIdentifier().equals("Window")) {
            operands = (EventBean[]) evt.getValue("window");
        } else {
            operands = new EventBean[1];
            operands[0] = evt;
        }
        // update the number of event processed by this EPU
        numEventProcessed += operands.length;
        short maxPriority = 0;
        short minPriority = Short.MAX_VALUE, sumPriority = 0, avgPriority = 0;
        for (EventBean e : operands) {
            sumPriority += e.getHeader().getPriority();
            if (e.getHeader().getPriority() < minPriority) {
                minPriority = e.getHeader().getPriority();
            }
            if (e.getHeader().getPriority() > maxPriority) {
                maxPriority = e.getHeader().getPriority();
            }
        }
        avgPriority = (short) (sumPriority / operands.length);

        EventBean ec = new EventBean();
        ec.getHeader().setDetectionTime(getDetectionTime(operands));
        ec.getHeader().setIsComposite(true);
        ec.getHeader().setProducerID(this.getName());
        ec.getHeader().setProductionTime(System.currentTimeMillis());
        ec.getHeader().setTypeIdentifier("Aggregate");
        ec.payload.put("processTime", ntime);
        switch (getPriorityFunction()) {
            case EPUnit.MAX:
                ec.getHeader().setPriority(maxPriority);
                break;
            case EPUnit.MIN:
                ec.getHeader().setPriority(minPriority);
                break;
            case EPUnit.SUM:
                ec.getHeader().setPriority(sumPriority);
                break;
            case EPUnit.AVG: //avg
                ec.getHeader().setPriority(avgPriority);
                break;
            default: //constant
                ec.getHeader().setPriority(Short.parseShort(getPriorityFunction()));
                break;
        }
        
        for (Aggregate aggregator : aggregators) {
            NameValuePair res = aggregator.aggregate(operands);
            ec.payload.put(res.getAttribute(), (Serializable) res.getValue());
        }

        ec.payload.put("ttl", TTL);
        _outputQueue.put(ec);
        numEventProduced++;
        // update the processing time of this EPU
        time = System.currentTimeMillis() - time;
        processingTime += time;
        getExecutorService().execute(getOutputNotifier());
    }
}
