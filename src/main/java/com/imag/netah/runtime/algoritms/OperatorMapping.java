/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.algoritms;

import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import com.imag.netah.core.EPUnit;
import com.imag.netah.core.EventChannel;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author epaln
 */
public class OperatorMapping {

    // Topology topo;
    // EPNetwork epn;
    GraphUtil<EPUnit, EventChannel> utilEPN = new GraphUtil<>();
    GraphUtil<Device, ComLink> utilTopology = new GraphUtil<>();

    public OperatorMapping() {

    }

    /**
     * compute all the producers (starting nodes) of an event processing
     * network. An operator is considered as a producer id its indegree equals
     * 0.
     *
     * @param epn
     * @return an ArrayList containing the producers
     */
    public List<EPUnit> getAllProducersNodes(DirectedSparseGraph<EPUnit, EventChannel> epn) {
        ArrayList<EPUnit> startingNodes = new ArrayList<>();
        for (EPUnit op : epn.getVertices()) {
            if (epn.inDegree(op) == 0) {
                startingNodes.add(op);
            }
        }
        return startingNodes;
    }

    public EPUnit getConsumer(DirectedSparseGraph<EPUnit, EventChannel> epn) {
        for (EPUnit op : epn.getVertices()) {
            if (epn.outDegree(op) == 0) {
                return op;
            }
        }
        return null;
    }

    public Solution opMapping(DirectedSparseGraph<EPUnit, EventChannel> epn, UndirectedSparseGraph<Device, ComLink> topo, Solution init) {

        EPUnit consumer = getConsumer(epn);
        Solution finalSolution = new Solution(init.getOp2Computer());

        EPUnit endpoint;// = getConsumer(epn);// endpoint = GraphUtil.getFirstMappedOperatorUpStream(producer, subgraphEPN);
        for (EPUnit producer : getAllProducersNodes(epn)) {
            //endpoint = GraphUtil.getFirstMappedOperatorUpStream(producer, epn);
            // first, compute the subgraph of nodes reacheable from producer to consumer
            DirectedSparseGraph<EPUnit, EventChannel> subgraphEPN = utilEPN.subGraph(epn, producer, consumer, EventChannel.class);
            // remove all mapped operators but don't remove the producer and the first mapped operator encountered upstream (endpoint)          
            /*for (Operator op : subgraphEPN.vertexSet()) {
             if (op.isMapped() && op != producer && op != endpoint) {
             subgraphEPN.removeVertex(op);
             }
             }
             */
            Device computerFrom, computerTo;
            computerFrom = producer.getDevice();
            computerTo = consumer.getDevice();
            //System.out.println("compute the subgraph topo between " + computerFrom.getId() + ", " + computerTo.getId());
            UndirectedSparseGraph<Device, ComLink> subgraphTopo = utilTopology.subWeightedGraph(topo, computerFrom, computerTo, ComLink.class);
            List<Device> computersOnTopo = utilTopology.getVerticesList(subgraphTopo);
            Solver solver = new Solver(subgraphEPN, subgraphTopo, computersOnTopo, 0, finalSolution);
            solver.solve(true, false);

            if (solver.getSolution() != null) {
                finalSolution.union(solver.getSolution());
                for (EPUnit op : subgraphEPN.getVertices()) {
                    if (!op.isMapped()) {
                        //System.out.println("mark operator as mapped: " + op.getName());
                        op.setMapped(true);
                        // update the remaining memory on the deployed node...
                        int remainder = finalSolution.getOp2Computer().get(op).getRemainingMemory() - op.getUsedMemory();
                        finalSolution.getOp2Computer().get(op).setRemainingMemory(remainder);
                    }
                }
            }

            System.out.println("The greddy mapping is: " + finalSolution.getOp2Computer());
        }

        // check for non mapped operators...
        boolean allMapped = true;
        for (EPUnit op : epn.getVertices()) {
            if (!op.isMapped()) {
                System.out.println("!! Operator " + op.getName() + " is not mapped after OpMapping algo !!");
                allMapped = false;
                break;
                //return null;
            }
        }
        if (!allMapped) {
            Solver solver = new Solver(epn, topo, utilTopology.getVerticesList(topo), 0, finalSolution);
            solver.solve(true, false);
            if (solver.getSolution() != null) {
                finalSolution.union(solver.getSolution());
            } else {
                return null;
            }
        } else {

            for (EPUnit op : epn.getVertices()) {
                //if (!(op instanceof EventConsumer || op instanceof EventProducer)) {

                op.setDevice(finalSolution.getOp2Computer().get(op));
                if (!op.getDevice().getOperators().contains(op)) {
                    op.getDevice().getOperators().add(op);
                }
                    //op.setMapped(true);

            }
            System.out.println(" All Operator mapped :)");
        }
        finalSolution.computeCost(topo, epn);

        return finalSolution;
    }
}
