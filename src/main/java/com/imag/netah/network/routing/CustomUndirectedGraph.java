/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.routing;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 *
 * @author epaln
 */
public class CustomUndirectedGraph<V,E> extends UndirectedSparseGraph<V, E> {

    @Override
    public EdgeType getDefaultEdgeType() {
        return EdgeType.DIRECTED; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V getSource(E directed_edge) {
       if (!containsEdge(directed_edge))
            return null;
        return edges.get(directed_edge).getFirst();   
    }
    
    @Override
     public V getDest(E directed_edge)
    {
        if (!containsEdge(directed_edge))
            return null;
        return edges.get(directed_edge).getSecond();
    }

    
    
}
