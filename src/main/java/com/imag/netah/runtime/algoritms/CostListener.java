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
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections15.Transformer;
import org.jacop.core.Var;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSolutionListener;

/**
 *
 * @author epaln
 * @param <T>
 */
public class CostListener<T extends Var> extends SimpleSolutionListener<T> {

    private double CostValue = 0;
    private UndirectedSparseGraph<Device, ComLink> _topo;
    private DirectedSparseGraph<EPUnit, EventChannel> _epn;
    //private int cpu_cost;
    private List<Device> computers;
    private EPUnit[] operators;
    private HashMap<EPUnit, Device> op2Computer;
    private Solution optimalSolution = null;
    /**
     * the solution founded so far
     */
    private Solution global;

    public CostListener() {
        super();
    }

    public CostListener(UndirectedSparseGraph<Device, ComLink> _topo, DirectedSparseGraph<EPUnit, EventChannel> _epn,
            List<Device> computers, Solution globalSolution) {
        this._topo = _topo;
        this._epn = _epn;
        this.computers = computers;
        operators = _epn.getVertices().toArray(new EPUnit[1]);
        op2Computer = new HashMap<EPUnit, Device>();
        global = globalSolution;
    }

    public boolean executeAfterSolution(Search<T> search, SelectChoicePoint<T> select) {
        boolean returnCode = super.executeAfterSolution(search, select);
        //System.out.println("Solution N° " + this.solutionsNo() + ": ");
        double cost = 0;
        op2Computer.clear();
        if (global != null) {
            op2Computer.putAll(global.getOp2Computer());
        } else {
            return false;
        }
        // there are operators that where already mapped, their initial mapping should be considered here

//        for (Var v : this.getVariables()) {
//            System.out.print(v + ", ");
//        }
//        System.out.println();
        if (_topo != null) {
            for (Var v : this.getVariables()) {
                int computer_index = v.dom().valueEnumeration().nextElement();
                for (EPUnit p : operators) {
                    if (p.getName().equals(v.id())) {
                        op2Computer.put(p, computers.get(computer_index));
                        p.setDevice(computers.get(computer_index));                        
                        break;
                    }
                }
            }
            // current solution
            Solution s = new Solution(op2Computer);
            for (EPUnit ope : operators) {
                if (!_topo.containsVertex(op2Computer.get(ope))) {
                    continue;
                }
                // compute the cost due to cpu latency in the designated computer
                double op_cpu_time = ope.getExecutionTime();
                double comp_speed_rate = op2Computer.get(ope).getCpuSpeed();
                //double cpu_cost = (int) Math.ceil(new Double(op_cpu_time) / comp_speed_rate);//Math.max(1, op_cpu_time / comp_speed_rate);
                double cpu_cost = op_cpu_time / comp_speed_rate;
                int localLatency_cost = 0, maxLocalLatency_cost = 0;
                // compute the cost due to the latency.
                for (EventChannel ec : _epn.getInEdges(ope)) {
                    EPUnit prev_ope = _epn.getSource(ec);
                    localLatency_cost = 0;
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
                s.setCost(cost);
            }
            if (optimalSolution == null) {
                optimalSolution = s;
            } else {
                if (s.getCost() < optimalSolution.getCost()) {
                    optimalSolution = s;
                    // System.out.println(s.getCost()+ ", "+System.currentTimeMillis());
                }
            }
            //System.out.println("Cost= " + cost);
            CostValue = cost;
        }
        return returnCode;
    }

    public Solution getOptimalSolution() {
        return optimalSolution;
    }

}
