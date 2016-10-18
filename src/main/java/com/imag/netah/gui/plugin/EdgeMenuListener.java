/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.plugin;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 *  An interface for menu items that are interested in knowning the currently selected edge and
 *  its visualization component context.  Used with PopupVertexEdgeMenuMousePlugin.
 * @author epaln
 * @param <E>
 */
public interface EdgeMenuListener<E> {
   
    /**
     * Used to set the edge and visulization component.
     * @param e 
     * @param visView 
     */
     void setEdgeAndView(E e, VisualizationViewer visView); 
    
}
