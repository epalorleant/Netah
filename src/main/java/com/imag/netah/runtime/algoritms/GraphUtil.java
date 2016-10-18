/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.algoritms;


import com.imag.netah.network.devices.Device;
import com.imag.netah.network.routing.EPGraph;
import com.imag.netah.core.EPUnit;
import com.imag.netah.core.EventChannel;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;



/**
 *
 * @author epaln
 * @param <V>
 * @param <E>
 */
public class GraphUtil<V, E> {

    //ConnectivityInspector<V, E> connect;
    public GraphUtil() {
    }

    public static EPUnit getFirstMappedOperatorUpStream(EPUnit op, DirectedSparseGraph<EPUnit, EventChannel> epn) {
        boolean next = true;
        EPUnit nextOp = null;
        for (EPUnit o : nextNodes(op, epn)) {
            if (o.isMapped()) {
                return o;
            } else {
                nextOp = getFirstMappedOperatorUpStream(o, epn);
                if (nextOp != null) {
                    return nextOp;
                }
            }
        }
        return null;
    }

    private static List<EPUnit> nextNodes(EPUnit op, DirectedSparseGraph<EPUnit, EventChannel> epn) {
        ArrayList<EPUnit> nextnodes = new ArrayList<>();
        for (EventChannel ec : epn.getOutEdges(op)) {
            EPUnit oo = epn.getDest(ec);
            if (oo != op) {
                nextnodes.add(oo);
            }
        }
        return nextnodes;
    }

    /**
     * Compute the subgraph of the given graph, containing all the nodes that
     * are reacheable from nodeFrom to nodeTo;
     *
     * @param graph the original graph
     * @param nodeFrom the starting node
     * @param nodeTo the destination node
     * @param edgeClass the class of the underlying edge type
     * @return the resulting subgraph, or null if nodeTo is not reacheable from
     * nodeFrom
     */
    public DirectedSparseGraph<V, E> subGraph(DirectedSparseGraph<V, E> graph, V nodeFrom, V nodeTo, Class edgeClass) {
        DirectedSparseGraph<V, E> subgraph;
        subgraph = new DirectedSparseGraph<>();
        subgraph.addVertex(nodeFrom);
        // special case: if nodeFrom directly connected to nodeTo
        E e = graph.findEdge(nodeFrom, nodeTo);
        if (e != null) {
            subgraph.addVertex(nodeTo);
            subgraph.addEdge(e, nodeFrom, nodeTo);
            return subgraph;
        }
        PathFinder<V, E> pathFinder = new PathFinder<>(graph);
        pathFinder.findAllPaths(nodeFrom, nodeTo);

        for (Stack<V> stack : pathFinder.getConnectionPaths()) {
            V vTo = nodeFrom;
            Iterator<V> iter = stack.iterator();
            V vFrom = nodeFrom;
            while (iter.hasNext()) {
                vTo = iter.next();
                if (!subgraph.containsVertex(vTo)) {
                    subgraph.addVertex(vTo);
                }

                E e2 = graph.findEdge(vFrom, vTo);
                if (!subgraph.containsEdge(e2)) {
                    subgraph.addEdge(e2, vFrom, vTo);
                }
                vFrom = vTo;
            }
            // The destination node
            if (!subgraph.containsVertex(nodeTo)) {
                subgraph.addVertex(nodeTo);
            }

            E e3 = graph.findEdge(vTo, nodeTo);
            if (!subgraph.containsEdge(e3)) {
                subgraph.addEdge(e3, vTo, nodeTo);
            }
        }
        return subgraph;
    }

    /**
     * Compute the subgraph of the given graph, containing all the nodes that
     * are reacheable from nodeFrom to nodeTo;
     *
     * @param graph the original graph
     * @param nodeFrom the starting node
     * @param nodeTo the destination node
     * @param edgeClass the class of the underlying edge type
     * @return the resulting subgraph, or null if nodeTo is not reacheable from
     * nodeFrom
     */
    public UndirectedSparseGraph<V, E> subWeightedGraph(UndirectedSparseGraph<V, E> graph, V nodeFrom, V nodeTo, Class edgeClass) {
        UndirectedSparseGraph<V, E> subgraph;
        subgraph = new UndirectedSparseGraph<>();
        subgraph.addVertex(nodeFrom);
        subgraph.addVertex(nodeTo);
        PathFinder<V, E> pathFinder = new PathFinder<>(graph);
        pathFinder.findAllPaths(nodeFrom, nodeTo);        
        // special case: if nodeFrom directly connected to nodeTo
        E e = graph.findEdge(nodeFrom, nodeTo);
        if (e != null) {
            subgraph.addVertex(nodeTo);
            //E e = graph.getEdge(nodeFrom, nodeTo);
            subgraph.addEdge(e, nodeFrom, nodeTo);
            return subgraph;
        }

        //pathFinder.printAllPaths();
        //  if there is no path between them, handle that.
        for (Stack<V> stack : pathFinder.getConnectionPaths()) {
            V vTo = nodeFrom;
            Iterator<V> iter = stack.iterator();
            V vFrom = nodeFrom;
            System.out.println("--> From "+nodeFrom+" to "+nodeTo+": ");
            while (iter.hasNext()) {
                vTo = iter.next();
                System.out.print(vTo+", ");
                if (!subgraph.containsVertex(vTo)) {
                    subgraph.addVertex(vTo);
                }

                E e2 = graph.findEdge(vFrom, vTo);//E e = graph.getEdge(vFrom, vTo);
                if (!subgraph.containsEdge(e2)) {
                    subgraph.addEdge(e2, vFrom, vTo);
                    //subgraph.setEdgeWeight(e2, graph.getEdgeWeight(e));
                }
                vFrom = vTo;
            }
            // The destination node
            if (!subgraph.containsVertex(nodeTo)) {
                subgraph.addVertex(nodeTo);
            }

            E e3 = graph.findEdge(vTo, nodeTo);
            if (!subgraph.containsEdge(e3)) {
                subgraph.addEdge(e3, vTo, nodeTo);
                //subgraph.setEdgeWeight(e, graph.getEdgeWeight(e));
            }
        }
        return subgraph;
    }

    public Solution initialMapping(DirectedSparseGraph<EPUnit, EventChannel> epn){
        HashMap<EPUnit, Device> init = new HashMap<>();
        for(EPUnit epu : epn.getVertices()){
            if(epu.isMapped()){
                init.put(epu, epu.getDevice());
            }
        }
        /*
        EPUnit consumer = getConsumer(epn);
        init.put(consumer, consumer.getDevice());
        for (EPUnit producer : getAllProducersNodes(epn)) {
            init.put(producer, producer.getDevice());
        }
        */
        Solution solution = new Solution(init);
        return solution;
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
    
    public List<V> getVerticesList(Graph<V, E> g) {
        ArrayList<V> vertices = new ArrayList<>();
        for (V v : g.getVertices()) {
            vertices.add(v);
        }
        return vertices;
    }
}
