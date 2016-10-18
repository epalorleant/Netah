/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.routing;

import com.imag.netah.core.EPUnit;
import com.imag.netah.core.EventChannel;
import com.imag.netah.core.IOTerminal;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.util.List;


/**
 *
 * @author epaln
 */
public class EPGraph {

    private DirectedSparseGraph<EPUnit, EventChannel> graph;
    private static EPGraph instance = null;

    private EPGraph() {
        graph = new DirectedSparseGraph<>();
    }

    public static EPGraph getInstance() {
        if (instance == null) {
            instance = new EPGraph();
        }
        return instance;
    }

    public DirectedSparseGraph<EPUnit, EventChannel> getGraph() {
        return graph;
    }
    
    public void resetMapping(){
        for(EPUnit epu: graph.getVertices()){
            epu.setDevice(null);
            epu.setMapped(false);
        }
        
    }

    public void AddEPGraphFromList(List<EPUnit> operators) {
        for (EPUnit op : operators) {
            graph.addVertex(op);
        }
        for (EPUnit op : operators) {
            if (op.getOutputTerminal() != null) {
                for (EPUnit opDest : operators) {
                    for (IOTerminal inputTerm : opDest.getInputTerminals()) {
                        String inputTopic = inputTerm.getTopic();
                        //System.out.println(inputTopic);
                        if (inputTopic.equals(op.getOutputTopic())) {
                            EventChannel ec = new EventChannel(inputTopic);
                            graph.addEdge(ec, op, opDest);                              
                        }
                    }
                }
            }

        }
    }
}
