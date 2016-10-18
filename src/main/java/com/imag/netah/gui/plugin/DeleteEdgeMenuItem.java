/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.plugin;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 *
 * @author epaln
 * @param <E>
 */
public class DeleteEdgeMenuItem <E> extends JMenuItem implements EdgeMenuListener<E> {

    private E edge;
    private VisualizationViewer visComp;
    
    /** Creates a new instance of DeleteEdgeMenuItem */
    public DeleteEdgeMenuItem() {
        super("Delete Link");
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedEdgeState().pick(edge, false);
                visComp.getGraphLayout().getGraph().removeEdge(edge);
                visComp.repaint();
                if(edge instanceof Thread){
                    Thread t = (Thread) edge;
                    t.interrupt();
                }
            }
        });
    }
    
    @Override
    public void setEdgeAndView(E e, VisualizationViewer visView) {
        this.edge = e;
        this.visComp = visView;
        this.setText("Delete Link " + e.toString());
    }
    
}
