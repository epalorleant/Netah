/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.client;

import com.imag.netah.network.routing.Subscriber;
import com.imag.netah.network.routing.Topic2Device;
import com.imag.netah.core.EPUnit;
import com.imag.netah.core.EventChannel;
import com.imag.netah.core.IOTerminal;
import com.imag.netah.core.event.EventBean;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author epaln
 */
public class EventConsumer extends EPUnit implements Subscriber {

    String _input;
    AnEventHandler _handler;
    private ArrayList<EPUnit> epuList;
    private DirectedSparseGraph<EPUnit, EventChannel> graph;

    public EventConsumer(String _info, String _inputTerm) {
        this(_info);
        this._input = _inputTerm;
    }

    public EventConsumer(String _info) {
        super(_info);
        this.setExecutionTime(0);
        this.setUsedMemory(0);
        _type = "Consumer";
        epuList = new ArrayList<>();
        graph = new DirectedSparseGraph<>();
    }

    public EventConsumer(String info, String IDinputTerminal, AnEventHandler handler) {
        this(info);
        _handler = handler;
        _handler.setConsumer(this);
        _input = IDinputTerminal;

    }

    @Override
    public synchronized void notify(Object event) {
        EventBean[] evts = (EventBean[]) event;
        for (EventBean evt : evts) {
            evt.getHeader().setReceptionTime(System.currentTimeMillis());
            if (evt.payload.contains("#time#")) {
                evt.payload.remove("#time#");
            }
            if (evt.payload.contains("processTime")) {
                evt.payload.remove("processTime");
            }
        }
        _handler.notify(evts);
    }

    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<IOTerminal>();
        inputs.add(new IOTerminal(_input, null, this));
        return inputs;
    }

    @Override
    public IOTerminal getOutputTerminal() {
        return null;
    }

    @Override
    public void process() {
    }

    @Override
    public boolean fetch() {
        return false;
    }

    @Override
    public void run() {
    }

    @Override
    public boolean openIOchannels() {
        Topic2Device.getInstance().AddMapping(_input, getDevice());
        this.getDevice().getPubSubService().subscribe(this, _input);
        return true;
    }

    public void subscribe(String inputTerm) {
        _input = inputTerm;
    }

    public AnEventHandler getHandler() {
        return _handler;
    }

    public void setHandler(AnEventHandler _handler) {
        this._handler = _handler;
    }

    public String getInput() {
        return _input;
    }

    public ArrayList<EPUnit> getEPUList() {
        return epuList;
    }

    public void buildESCNetwork(Graph expr) {

    }

    public DirectedSparseGraph<EPUnit, EventChannel> getGraph() {
        return graph;
    }

    public void resetMapping() {
        for (EPUnit epu : graph.getVertices()) {
            epu.setDevice(null);
            epu.setMapped(false);
        }

    }

    public void buildCompositionGraph(ArrayList<EventProducer> producers) {
        ArrayList<EPUnit> AllOperators = new ArrayList<>();
        AllOperators.addAll(epuList);
        AllOperators.addAll(producers);
        AllOperators.add(this);
        for (EPUnit op : AllOperators) {
            graph.addVertex(op);
        }
        for (EPUnit op : AllOperators) {
            if (!(op instanceof EventConsumer)) {
                for (EPUnit opDest : AllOperators) {
                    if (op == opDest) {
                        continue;
                    }
                    for (IOTerminal inputTerm : opDest.getInputTerminals()) {
                        String inputTopic = inputTerm.getTopic();
                        //System.out.println("ici: " + inputTopic + "$ " + op);
                        if (inputTopic.equals(op.getOutputTopic())) {
                            EventChannel ec = new EventChannel(inputTopic);
                            graph.addEdge(ec, op, opDest);
                        }
                    }
                }
            }

        }
         ArrayList<EPUnit> remove = new ArrayList<>();
        for (EPUnit op : graph.getVertices()) {
            if ((op instanceof EventProducer) && graph.outDegree(op) == 0) {
                remove.add(op);
            }
        }
        for(EPUnit op: remove){
            graph.removeVertex(op);
        }
       
    }
}
