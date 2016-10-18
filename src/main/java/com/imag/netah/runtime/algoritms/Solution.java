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
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author epaln
 */
public class Solution {

    private HashMap<EPUnit, Device> op2Computer;
    private HashMap<Device, List<EPUnit>> computer2Op;
    private double cost = 0;

    public Solution() {
        op2Computer = new HashMap<EPUnit, Device>();
        computer2Op = new HashMap<>();
    }

    public Solution(HashMap<EPUnit, Device> op2Computer) {
        this();
        this.op2Computer.putAll(op2Computer);
    }

    public HashMap<EPUnit, Device> getOp2Computer() {
        return op2Computer;
    }

    public void setOp2Computer(HashMap<EPUnit, Device> op2Computer) {
        this.op2Computer = op2Computer;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public HashMap<Device, List<EPUnit>> getComputer2Op() {
        return computer2Op;
    }

    private void setComputer2Op(HashMap<EPUnit, Device> op2Comp) {
        computer2Op.clear();
        for (Device c : op2Comp.values()) {
            ArrayList<EPUnit> operators = new ArrayList<>();
            for (EPUnit op : op2Comp.keySet()) {
                if (op2Comp.get(op) == c) {
                    operators.add(op);
                }
            }
            computer2Op.put(c, operators);
        }
    }

    public String comp2OpAsString() {
        setComputer2Op(op2Computer);
        StringBuilder r = new StringBuilder("Solution: ");
        for (Device c : computer2Op.keySet()) {
            r.append(c.toString() + "[");
            for (EPUnit op : computer2Op.get(c)) {
                r.append(op.getName() + ",");
            }
            r.append("];");
        }
        return r.toString();
    }

    public void union(Solution s) {
        if (s != null) {
            for (EPUnit op : s.getOp2Computer().keySet()) {
                if (!op2Computer.containsKey(op)) {
                    op2Computer.put(op, s.getOp2Computer().get(op));
                } else {
                    Device c1 = op2Computer.get(op);
                    if (c1 != s.getOp2Computer().get(op)) {
                        System.out.println("Alert: 2 mappings for operator" + op.getName());
                    }
                }
            }
            setComputer2Op(op2Computer);
        }

    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("Solution: ");
        r.append(op2Computer.toString());
        r.append("\n Solution cost: " + cost);
        return r.toString();

    }

    public void computeCost(UndirectedSparseGraph<Device, ComLink> _topo, DirectedSparseGraph<EPUnit, EventChannel> _epn) {
        cost = 0;
        for (EPUnit ope : op2Computer.keySet()) {
            // compute the cost due to cpu latency in the designated computer
            double op_cpu_time = ope.getExecutionTime();
            double comp_speed_rate = op2Computer.get(ope).getCpuSpeed();
            //int cpu_cost = (int) Math.ceil(new Double(op_cpu_time) / comp_speed_rate);//Math.max(1, op_cpu_time / comp_speed_rate);
            double cpu_cost = op_cpu_time / comp_speed_rate;
            int localLatency_cost = 0, maxLocalLatency_cost = 0;
            // compute the cost due to the latency.
            /*
            for (EventChannel ec : _epn.getOutEdges(ope)) {
                EPUnit next_ope = _epn.getDest(ec);
                //System.out.println(op2Computer.get(ope)+", "+ op2Computer.get(next_ope));
                Transformer<ComLink, Integer> wtTransformer = new Transformer<ComLink, Integer>() {
                    @Override
                    public Integer transform(ComLink link) {
                        return link.getLatency();
                    }
                };

                DijkstraShortestPath<Device, ComLink> alg = new DijkstraShortestPath(_topo, wtTransformer);
                
                List<ComLink> path = alg.getPath(op2Computer.get(ope), op2Computer.get(next_ope));
                if (path != null) {
                    //System.out.println("Path between "+op2Computer.get(ope).getId() + " to" + op2Computer.get(next_ope).getId()+": ");
                    for (ComLink c : path) {
                        //System.out.print(c.getId()+", ");
                        localLatency_cost += c.getLatency();
                    }
                } else {
                    System.out.println(ope.getName() + "-> " + next_ope.getName()
                            + ", no path from " + op2Computer.get(ope) + " to" + op2Computer.get(next_ope));
                    localLatency_cost = Integer.MAX_VALUE;
                }
                if (localLatency_cost > maxLocalLatency_cost) {
                    maxLocalLatency_cost = localLatency_cost;
                }
            }
            */            
            for (EventChannel ec : _epn.getInEdges(ope)) {
                EPUnit prev_ope = _epn.getSource(ec);
                localLatency_cost =0;
                //System.out.println(op2Computer.get(ope)+", "+ op2Computer.get(next_ope));
                Transformer<ComLink, Integer> wtTransformer = new Transformer<ComLink, Integer>() {
                    @Override
                    public Integer transform(ComLink link) {
                        return link.getLatency();
                    }
                };

                DijkstraShortestPath<Device, ComLink> alg = new DijkstraShortestPath(_topo, wtTransformer);
                
                List<ComLink> path = alg.getPath(op2Computer.get(prev_ope), op2Computer.get(ope));
                //localLatency_cost=(int) alg.getDistance(op2Computer.get(prev_ope), op2Computer.get(ope));
                if (path != null) {
                    //System.out.println("Path between "+op2Computer.get(ope).getId() + " to" + op2Computer.get(next_ope).getId()+": ");
                    for (ComLink c : path) {
                        //System.out.print(c.getId()+", ");
                        localLatency_cost += c.getLatency();
                    }
                } else {
                    System.out.println(ope.getName() + "-> " + prev_ope.getName()
                            + ", no path from " + op2Computer.get(ope) + " to" + op2Computer.get(prev_ope));
                    localLatency_cost = Integer.MAX_VALUE;
                }
                if (localLatency_cost > maxLocalLatency_cost) {
                    maxLocalLatency_cost = localLatency_cost;
                }
            }
            cost += (maxLocalLatency_cost + cpu_cost);
        }
    }

}
