/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 correlate events in differents window=> should be fixed in the candidates selection code (fetch method)
 */
package com.imag.netah.core;


//import event.EventComparator2;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.event.EventComparator;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;



/**
 *
 * @author epaln
 */
public class FollowedByAgent extends EPUnit {

    IOTerminal inputTerminalL;
    IOTerminal inputTerminalR;
    IOTerminal outputTerminal;

    public FollowedByAgent(String info, String IDinputTerminalL, String IDinputTerminalR, String IDoutputTerminal) {
        super(info);        
        this._info = info;
        this._type = "FollowedBy";
        this._receivers[0] = new TopicReceiver(this, (short)0);
        this._receivers[1] = new TopicReceiver(this, (short)1);
        inputTerminalL = new IOTerminal(IDinputTerminalL, "input channel " + _type, _receivers[0], this);
        inputTerminalR = new IOTerminal(IDinputTerminalR, "input channel " + _type, _receivers[1], this);
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        
        logger = new LoggerUtil("FollowedByMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
    }

    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<>();
        inputs.add(inputTerminalL);
        inputs.add(inputTerminalR);
        return inputs;
    }

    @Override
    public IOTerminal getOutputTerminal() {
        return outputTerminal;
    }

    @Override
    public void process() {
        EventBean[] Values = new EventBean[1];
        ArrayList<EventBean> lValues, rValues;
        // statistics: #events processed, processing time
        long time = System.currentTimeMillis();
        long ntime = System.nanoTime(); // to compute the processing time for that cycle
        EventBean select = _selectedEvents.poll();
        
        if(select!=null){
           if (select.getHeader().getTypeIdentifier().equals("Window")) {
                Values = (EventBean[]) select.getValue("window");
            } else {
                Values = new EventBean[1];
                Values[0] = select;
            } 
        }
        lValues = new ArrayList<>();
        rValues = new ArrayList<>();
        
        for(EventBean e: Values){
            if(e.getValue("flag").toString().equals("0")){
                lValues.add(e); 
            }
            else{
                rValues.add(e);
            }
            e.payload.remove("flag");
        }        
        
        if (!lValues.isEmpty() && !rValues.isEmpty()) {
            ArrayList<EventBean> produced = new ArrayList<>();
            for (EventBean l : lValues) {
                for (EventBean r : rValues) {
                    if (l.getHeader().getDetectionTime() < r.getHeader().getDetectionTime()) {
                        EventBean ec = new EventBean();
                        ec.getHeader().setDetectionTime(l.getHeader().getDetectionTime());
                        switch (getPriorityFunction()){
                            case EPUnit.MAX :
                                ec.getHeader().setPriority((short) Math.max(l.getHeader().getPriority(), r.getHeader().getPriority()));
                                break;
                            case EPUnit.MIN :
                                ec.getHeader().setPriority((short) Math.min(l.getHeader().getPriority(), r.getHeader().getPriority()));
                                break;
                            case EPUnit.SUM:
                                ec.getHeader().setPriority((short)(l.getHeader().getPriority()+ r.getHeader().getPriority()));
                                break;
                            case EPUnit.AVG:                            
                                ec.getHeader().setPriority((short) ((l.getHeader().getPriority()+ r.getHeader().getPriority())/2));
                                break;
                            default: //constant
                                ec.getHeader().setPriority(Short.parseShort(getPriorityFunction()));
                                break;
                        }
                        
                        ec.getHeader().setIsComposite(true);
                        ec.getHeader().setProductionTime(System.currentTimeMillis());
                        ec.getHeader().setProducerID(this.getName());
                        ec.getHeader().setTypeIdentifier("FollowedBy");
                        ec.payload.put("before", l);
                        ec.payload.put("after", r);
                        ec.payload.put("ttl", TTL);
                        ec.payload.put("processTime", ntime);
                        produced.add(ec);
                    }
                }

            }
            if (produced.size() > 0) {
                switch (selectionMode) {
                    case SelectionMode.MODE_CONTINUOUS: {
                        for (EventBean e : produced) {
                            _outputQueue.put(e);
                            numEventProduced++;
                            getExecutorService().execute(getOutputNotifier());
                        }
                    }
                    break;

                    case SelectionMode.MODE_CHRONOLOGIC: {
                        _outputQueue.put(produced.get(0));
                        numEventProduced++;
                        getExecutorService().execute(getOutputNotifier());
                    }
                    break;
                    case SelectionMode.MODE_PRIORITY: {
                        EventBean[] prod = produced.toArray(new EventBean[0]);
                        Arrays.sort(prod, new EventComparator());
                        _outputQueue.put(prod[0]);
                        numEventProduced++;
                        getExecutorService().execute(getOutputNotifier());
                    }
                    break;
                    default: { // mode recent
                        _outputQueue.put(produced.get(produced.size() - 1));
                        numEventProduced++;
                        getExecutorService().execute(getOutputNotifier());
                    }
                    break;
                }
            }
            // update statistics: number event processed
            numEventProcessed += Values.length;
            // update statistics: processing time
            processingTime += time;
        }
    }
}
