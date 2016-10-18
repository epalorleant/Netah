/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.devices;

import org.apache.commons.collections15.Factory;

/**
 *
 * @author epaln
 */
public class ComLinkFactory implements Factory<ComLink> {
    
    private static int linkCount = 0;
    private static ComLinkFactory instance = new ComLinkFactory();
    
    private ComLinkFactory(){
        
    }
    
    public static ComLinkFactory getInstance(){
        return instance;
    }

    @Override
    public ComLink create() {
        return new ComLink(2000, "link"+linkCount++, 10, 100);
    }
    
    public static void updateLinkCount(){
        linkCount++;
    }
    
    public static void resetCounter(){
        linkCount = 0;
    }
    
    
}
