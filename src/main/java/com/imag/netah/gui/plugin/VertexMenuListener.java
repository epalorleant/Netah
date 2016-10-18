/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.plugin;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 *
 * Used to indicate that this class wishes to be told of a selected vertex
 * along with its visualization component context. Note that the VisualizationViewer
 * has full access to the graph and layout.
 * @author Dr. Greg M. Bernstein
 * @param <V>
 */

public interface VertexMenuListener<V> {
     void setVertexAndView(V v, VisualizationViewer visView);    
}
