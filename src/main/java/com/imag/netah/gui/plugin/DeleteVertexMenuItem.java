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
 */
class DeleteVertexMenuItem<V> extends JMenuItem implements VertexMenuListener<V>{

    private V vertex;
    private VisualizationViewer visComp;
    
    public DeleteVertexMenuItem() {
        super("Delete Device");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedVertexState().pick(vertex, false);
                visComp.getGraphLayout().getGraph().removeVertex(vertex);
                visComp.repaint();
            }
        });
    }

    @Override
    public void setVertexAndView(V v, VisualizationViewer visView) {
        this.vertex = v;
        this.visComp = visView;
        this.setText("Delete Device " + v.toString());
    }
    
}
