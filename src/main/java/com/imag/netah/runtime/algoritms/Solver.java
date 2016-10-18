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
import java.util.Iterator;
import java.util.List;
import org.jacop.constraints.Constraint;
import org.jacop.constraints.binpacking.Binpacking;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;


/**
 *
 * @author epaln
 */
public class Solver {

    private final UndirectedSparseGraph<Device, ComLink> _topo;
    private final DirectedSparseGraph<EPUnit, EventChannel> _epn;
    private final int[] sizes;
    private final Store store;
    private final IntVar[] items, loads;
    private IntVar cost;
    private final int timeout;
    private static int count = 0;
    CostListener listener;
    private List<Device> _computers;
    Solution _init;

    public Solver(DirectedSparseGraph<EPUnit, EventChannel> epn, UndirectedSparseGraph<Device, ComLink> topo, List<Device> computers, int timeoutSecond, Solution init) {
        store = new Store();
        _topo = topo;
        _epn = epn;
        sizes = new int[numberNonMappedOperators(epn)];
        items = new IntVar[sizes.length];
        _computers = getPossibleComputers(computers);
        loads = new IntVar[_computers.size()];
        this.timeout = timeoutSecond;
        _init = init;

        //cost = new IntVar(store, "cost", 0, IntDomain.MaxInt);
        System.out.println("--> Calling Solver on (epn, topo, computers, init) \n" + epn + "\n\t" + topo + "\n\t" + computers+ "\n\t" + init.comp2OpAsString());
    }

    /**
     *
     * @return the founded solution, and null if no solution was founded
     */
    public Solution getSolution() {
        return listener.getOptimalSolution();
    }

    private boolean buildBinPacking() {
        boolean ok = false;
        Iterator<EPUnit> iter = _epn.getVertices().iterator();
        int i = 0;
        while (iter.hasNext()) {
            EPUnit op = iter.next();
            if (!op.isMapped()) {
                // an operator can be assing a value between 0 and the number of servers (minus 1)
                items[i] = new IntVar(store, op.getName(), 0, loads.length - 1);
                sizes[i] = op.getUsedMemory();
                ok = true;
                // if the operator is mapped, then find its mapped location and set to the corresponding variable
                // if (op.isMapped()) {
                //    int location = _computers.indexOf(op.getMappedComputer());
                //    items[i].setDomain(location, location);
                // }
                /*if (op.getLocation() != -1) {
                 //IntVar p = new IntVar(store, op.getName(), op.getLocation(),op.getLocation()); 
                 //store.impose(new XeqY(items[i], p));
                 items[i].setDomain(op.getLocation(), op.getLocation());
                 }
                 */
                i++;
            }
        }
        i = 0;
        for (Device c : _computers) {
            //computers[i] = c;
            loads[i] = new IntVar(store, c.getDeviceName(), 0, c.getRemainingMemory());
            i++;
        }
        if (ok) {
            Constraint binPacking = new Binpacking(items, loads, sizes);
            store.impose(binPacking);
            boolean Result = store.consistency();
            System.out.println(binPacking);
        }
        return ok;
    }

    public void solve(boolean searchAll, boolean printSolution) {
        listener = new CostListener<IntVar>(_topo, _epn, _computers, _init);

        if (buildBinPacking()) {

            count++;
            if (sizes.length == 0) {
                return;
            }
        //System.out.println(store.size() + " variables");
            //System.out.println(store.numberConstraints() + " constraint");
            long T1, T2;

            // search for a solution and print results
            Search<IntVar> search = new DepthFirstSearch<IntVar>();
            SelectChoicePoint<IntVar> select;
            //if (count % 2 == 0) {
            select = new SimpleSelect<IntVar>(items, null, new IndomainMin<IntVar>());
            /*} else {
             select = new SimpleSelect<IntVar>(items, null, new IndomainMax<IntVar>());
             }*/
            //search.setAssignSolution(true);

            search.setPrintInfo(false);
            if (timeout > 0) {
                search.setTimeOut(timeout);
            }

            search.setSolutionListener(listener);
            search.getSolutionListener().searchAll(searchAll);
            //search.getSolutionListener().recordSolutions(true);
            T1 = System.currentTimeMillis();
            boolean result = search.labeling(store, select);

            //search.printAllSolutions();
            T2 = System.currentTimeMillis();

            if (result) {
                //System.out.println("Search time: " + (T2 - T1) + " ms");
                System.out.println(T2 - T1);
                //System.out.println("\n Essaie n° "+ count+": cost=" +listener.getOptimalSolution().getCost());
               // System.out.println(", " + listener.getOptimalSolution().getCost());
                if (printSolution) {
                    System.out.println(listener.getOptimalSolution().comp2OpAsString());
                }
            } else {
                System.out.println("*** No Solution ***");
            }
        }
    }

    private int numberNonMappedOperators(DirectedSparseGraph<EPUnit, EventChannel> epn) {
        Iterator<EPUnit> iter = _epn.getVertices().iterator();
        int i = 0;
        while (iter.hasNext()) {
            EPUnit op = iter.next();
            if (!op.isMapped()) {
                i++;
            }
        }
        return i;
    }
    /**
     * remove from the given list of computers, the computer that have 0 available memory
     * @return 
     */
    private List<Device> getPossibleComputers(List<Device> computers){
        ArrayList<Device> comp= new ArrayList<>();
        for (Device c: computers){
            if (c.getRemainingMemory() != 0){
                comp.add(c);
            }
        }
        return comp;
    }

}
