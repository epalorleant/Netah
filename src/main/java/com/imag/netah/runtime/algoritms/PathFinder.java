/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.algoritms;

import edu.uci.ics.jung.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 * @author epaln
 */
public class PathFinder<V, E> {

    private Graph<V, E> graph;
    Stack<V> connectionPath = new Stack();
    List<Stack> connectionPaths = new ArrayList<>();

    public PathFinder(Graph<V, E> topo) {
        this.graph = topo;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    
    public void init(){
        connectionPath.clear();
        connectionPaths.clear();
    }
    
    public List<Stack> getConnectionPaths() {
        return connectionPaths;
    }
    

// Push to connectionsPath the object that would be passed as the parameter 'node' into the method below
    public void findAllPaths(V node, V targetNode) {
        for (V nextNode : nextNodes(node)) {
            if (nextNode.equals(targetNode)) {
                Stack temp = new Stack();
                for (V node1 : connectionPath) {
                    temp.add(node1);
                }
                connectionPaths.add(temp);
            } else if (!connectionPath.contains(nextNode)) {
                connectionPath.push(nextNode);
                findAllPaths(nextNode, targetNode);
                connectionPath.pop();
            }
        }
    }

    private List<V> nextNodes(V node) {
        ArrayList<V> nextnodes = new ArrayList<>();
        for(E c : graph.getOutEdges(node)){
            V co = graph.getDest(c);
            if(co!= node){
                nextnodes.add(co);
            }
        }
        return nextnodes;
    }

    public void printAllPaths(){
        int i =1;
        for(Stack<V> s : connectionPaths){
            System.out.print("Path "+(i++) +":");
            ListIterator<V> iter = s.listIterator();
            while(iter.hasNext()){
                V c = iter.next();
                System.out.print(c+",");
            }
            System.out.println();
        }
    }
    
}
