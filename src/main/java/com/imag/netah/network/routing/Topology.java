 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.routing;

import com.imag.netah.network.devices.ComLink;
import com.imag.netah.network.devices.Device;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections15.Transformer;


/**
 *
 * @author epaln
 */
public class Topology {
    private CustomUndirectedGraph<Device, ComLink> graph;
    //private ObservableGraph<Device, ComLink> oGraph;
    private static Topology instance =null;


    private Topology() {
        graph = new CustomUndirectedGraph<>();
        /*oGraph = new ObservableGraph<>(graph);
        oGraph.addGraphEventListener(new GraphEventListener<Device, ComLink>() {

            @Override
            public void handleGraphEvent(GraphEvent<Device, ComLink> evt) {
                GraphEvent.Edge<Device, ComLink> e = (GraphEvent.Edge<Device, ComLink>)evt;
                e.
            }
        });
        */
    }
    
    public static Topology getInstance(){
        if(instance == null){
            instance = new Topology();
        }
        return instance;
    }

    public CustomUndirectedGraph<Device, ComLink> getGraph() {
        return graph;
    }

    public void setGraph(CustomUndirectedGraph<Device, ComLink> graph) {
        this.graph = graph;
    }
/*
    public Map<Device, Icon> getIconMap() {
        return iconMap;
    }
    
  */
    public List<ComLink> getPath(Device from, Device to){
        ArrayList<ComLink> path = new ArrayList<>();
        Transformer<ComLink, Integer> wtTransformer = new Transformer<ComLink,Integer>() {
        @Override
        public Integer transform(ComLink link) {
            if(link.isDown()){
                return Integer.MAX_VALUE;
            }
            return link.getLatency()*(link.getPendingPackets().size()+1);
        }
    };
        DijkstraShortestPath<Device,ComLink> alg = new DijkstraShortestPath(graph, wtTransformer);        
        return alg.getPath(from, to);       
    }
       
}
