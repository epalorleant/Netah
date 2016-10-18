/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.core.pfunction.PFunction;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.event.EventComparator2;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * this operator implements the logic AND operation between 2 event streams
 *
 * @author epaln
 */
public class ConjunctionAgent extends EPUnit {

    IOTerminal[] inputTerminals;
    //IOTerminal inputTerminalR;
    IOTerminal outputTerminal;

    public ConjunctionAgent(String info, String[] IDinputTerminals, String IDoutputTerminal) {

        super(info);
        this._info = info;
        this._type = "Conjunction";
        this._receivers = new TopicReceiver[IDinputTerminals.length];
        inputTerminals = new IOTerminal[IDinputTerminals.length];
        short i = 0;
        for (String input : IDinputTerminals) {
            this._receivers[i] = new TopicReceiver(this, i);
            inputTerminals[i] = new IOTerminal(input, "input channel " + _type, _receivers[i], this);
            i++;
        }
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        logger = new LoggerUtil("ConjunctionMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
    }

    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<IOTerminal>();
        for (IOTerminal io : inputTerminals) {
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
        EventBean[] Values = new EventBean[1];
        ArrayList<ArrayList<EventBean>> inValues = new ArrayList<>();
        // statistics: #events processed, processing time
        long time = System.currentTimeMillis();
        // to compute the processing time for that cycle
        long ntime = System.nanoTime();
        EventBean select = _selectedEvents.poll();

        if (select != null) {
            if (select.getHeader().getTypeIdentifier().equals("Window")) {
                Values = (EventBean[]) select.getValue("window");
            } else {
                Values = new EventBean[1];
                Values[0] = select;
            }
        }
        for (int i = 0; i < inputTerminals.length; i++) {
            inValues.add(new ArrayList<EventBean>());
        }
        for (EventBean e : Values) {
            short u = (short) e.getValue("flag");
            inValues.get(u).add(e);
            e.payload.remove("flag");
        }
        //EventBean[] lV, rV;
        //   lV = (EventBean[]) lValues.toArray(new EventBean[0]);
        //rV = (EventBean[]) rValues.toArray(new EventBean[0]);
        boolean ok = true;
        for (ArrayList<EventBean> inValue : inValues) {
            if (inValue.isEmpty()) {
                ok = false;
                break;
            }
        }
        // should we produce a composite event?
        if (ok) {
            // many event instances can match... the selection mode should clearly define the event to select
            switch (selectionMode) {
                case SelectionMode.MODE_CONTINUOUS: {
                    EventBean[] evts = new EventBean[inputTerminals.length];
                    int i = 0;
                    for (ArrayList<EventBean> inValue : inValues) {
                        evts[i] = inValue.get(0);
                        i++;
                    }
                    // for (EventBean r : rValues) {
                    EventBean ec = new EventBean();
                    ec.getHeader().setDetectionTime(getDetectionTime(evts));
                    ec.getHeader().setIsComposite(true);
                    ec.getHeader().setProductionTime(System.currentTimeMillis());
                    ec.getHeader().setProducerID(this.getName());
                    ec.getHeader().setTypeIdentifier("Conjunction");
                    ec.payload.put("processTime", ntime);
                    ec.payload.put("ttl", TTL);
                    ec.payload.put("data", evts);
                    switch (getPriorityFunction()) {
                        case EPUnit.MAX:
                            ec.getHeader().setPriority(PFunction.maxPriority(evts));
                            break;
                        case EPUnit.MIN:
                            ec.getHeader().setPriority(PFunction.minPriority(evts));
                            break;
                        case EPUnit.SUM:
                            ec.getHeader().setPriority(PFunction.sumPriority(evts));
                            break;
                        case EPUnit.AVG: //avg
                            ec.getHeader().setPriority(PFunction.avgPriority(evts));
                            break;
                        default: //constant
                            ec.getHeader().setPriority(Short.parseShort(getPriorityFunction()));
                            break;
                    }
                    _outputQueue.put(ec);
                    numEventProduced++;
                    getExecutorService().execute(getOutputNotifier());
                }
                break;
                    case SelectionMode.MODE_CUMULATIVE: {
                    EventBean[] evts = Values;
                    
                    // for (EventBean r : rValues) {
                    EventBean ec = new EventBean();
                    ec.getHeader().setDetectionTime(getDetectionTime(evts));
                    ec.getHeader().setIsComposite(true);
                    ec.getHeader().setProductionTime(System.currentTimeMillis());
                    ec.getHeader().setProducerID(this.getName());
                    ec.getHeader().setTypeIdentifier("Conjunction");
                    ec.payload.put("processTime", ntime);
                    ec.payload.put("ttl", TTL);
                    ec.payload.put("data", evts);
                    switch (getPriorityFunction()) {
                        case EPUnit.MAX:
                            ec.getHeader().setPriority(PFunction.maxPriority(evts));
                            break;
                        case EPUnit.MIN:
                            ec.getHeader().setPriority(PFunction.minPriority(evts));
                            break;
                        case EPUnit.SUM:
                            ec.getHeader().setPriority(PFunction.sumPriority(evts));
                            break;
                        case EPUnit.AVG: //avg
                            ec.getHeader().setPriority(PFunction.avgPriority(evts));
                            break;
                        default: //constant
                            ec.getHeader().setPriority(Short.parseShort(getPriorityFunction()));
                            break;
                    }
                    _outputQueue.put(ec);
                    numEventProduced++;
                    getExecutorService().execute(getOutputNotifier());
                }
                break;
                case SelectionMode.MODE_CHRONOLOGIC: {
                    EventBean[] evts = new EventBean[inputTerminals.length];
                    int i = 0;
                    for (ArrayList<EventBean> inValue : inValues) {
                        Collections.sort(inValue, new EventComparator2());
                        evts[i] = inValue.get(0);
                        i++;
                    }
                    EventBean ec = new EventBean();
                    ec.getHeader().setDetectionTime(getDetectionTime(evts));

                    switch (getPriorityFunction()) {
                        case EPUnit.MAX:
                            ec.getHeader().setPriority(PFunction.maxPriority(evts));
                            break;
                        case EPUnit.MIN:
                            ec.getHeader().setPriority(PFunction.minPriority(evts));
                            break;
                        case EPUnit.SUM:
                            ec.getHeader().setPriority(PFunction.sumPriority(evts));
                            break;
                        default: //avg
                            ec.getHeader().setPriority(PFunction.avgPriority(evts));
                            break;
                    }
                    ec.getHeader().setIsComposite(true);
                    ec.getHeader().setProductionTime(System.currentTimeMillis());
                    ec.getHeader().setProducerID(this.getName());
                    ec.getHeader().setTypeIdentifier("Conjunction");
                    ec.payload.put("data", evts);
                    ec.payload.put("ttl", TTL);
                    ec.payload.put("processTime", ntime);
                    _outputQueue.put(ec);
                    numEventProduced++;
                    getExecutorService().execute(getOutputNotifier());
                }
                break;

                case SelectionMode.MODE_PRIORITY: {
                    EventBean[] evts = new EventBean[inputTerminals.length];
                    int i = 0;
                    for (ArrayList<EventBean> inValue : inValues) {
                        evts[i] = inValue.get(0);
                        i++;
                    }
                    EventBean ec = new EventBean();
                    ec.getHeader().setDetectionTime(getDetectionTime(evts));
                    switch (getPriorityFunction()) {
                        case EPUnit.MAX:
                            ec.getHeader().setPriority(PFunction.maxPriority(evts));
                            break;
                        case EPUnit.MIN:
                            ec.getHeader().setPriority(PFunction.minPriority(evts));
                            break;
                        case EPUnit.SUM:
                            ec.getHeader().setPriority(PFunction.sumPriority(evts));
                            break;
                        default: //avg
                            ec.getHeader().setPriority(PFunction.avgPriority(evts));
                            break;
                    }
                    ec.getHeader().setIsComposite(true);
                    ec.getHeader().setProductionTime(System.currentTimeMillis());
                    ec.getHeader().setProducerID(this.getName());
                    ec.getHeader().setTypeIdentifier("Conjunction");
                    ec.payload.put("data", evts);
                    ec.payload.put("ttl", TTL);
                    ec.payload.put("processTime", ntime);
                    _outputQueue.put(ec);
                    numEventProduced++;
                    getExecutorService().execute(getOutputNotifier());
                }
                break;

                default: { /// mode recent
                    EventBean[] evts = new EventBean[inputTerminals.length];
                    int i = 0;
                    for (ArrayList<EventBean> inValue : inValues) {
                        Collections.sort(inValue, new EventComparator2());
                        evts[i] = inValue.get(inValue.size() - 1);
                        i++;
                    }
                    EventBean ec = new EventBean();
                    ec.getHeader().setDetectionTime(getDetectionTime(evts));

                    switch (getPriorityFunction()) {
                        case EPUnit.MAX:
                            ec.getHeader().setPriority(PFunction.maxPriority(evts));
                            break;
                        case EPUnit.MIN:
                            ec.getHeader().setPriority(PFunction.minPriority(evts));
                            break;
                        case EPUnit.SUM:
                            ec.getHeader().setPriority(PFunction.sumPriority(evts));
                            break;
                        default: //avg
                            ec.getHeader().setPriority(PFunction.avgPriority(evts));
                            break;
                    }
                    // ec.getHeader().setPriority((short) Math.max(lV[lV.length - 1].getHeader().getPriority(), rV[rV.length - 1].getHeader().getPriority()));
                    ec.getHeader().setIsComposite(true);
                    ec.getHeader().setProductionTime(System.currentTimeMillis());
                    ec.getHeader().setProducerID(this.getName());
                    ec.getHeader().setTypeIdentifier("Conjunction");
                    ec.payload.put("data", evts);
                    ec.payload.put("ttl", TTL);
                    ec.payload.put("processTime", ntime);
                    numEventProduced++;
                    _outputQueue.put(ec);
                    getExecutorService().execute(getOutputNotifier());
                }
                break;
            }

            // update statistics: number event processed
            numEventProcessed += Values.length;
        }
        // update statistics: processing time
        time = System.currentTimeMillis() - time;
        processingTime += time;
    }
}
