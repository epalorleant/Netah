/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.transformers;

import com.imag.netah.network.devices.Device;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author epaln
 */
public class CustomVertexShapeTransformer implements Transformer<Device, Shape>{

    @Override
    public Shape transform(Device device) {
        Image img;
        int w,h;
        w = device.getIcon().getIconWidth();
        h = device.getIcon().getIconHeight();
        return new Rectangle(-w/2, -h/2, w, h);
        /*
        switch(device.getDeviceType()){
            case AMI:
                img = new ImageIcon("icons"+File.separator+"meter.jpeg").getImage();
                w = img.getWidth(null);
                h = img.getHeight(null);
                return new Rectangle(-w/2, -h/2, w, h);
            case DC:
                img = new ImageIcon("icons"+File.separator+"dc.jpeg").getImage();
                w = img.getWidth(null);
                h = img.getHeight(null);
                return new Rectangle(-w/2, -h/2, w, h);
            case HTA_COORD:
                img = new ImageIcon("icons"+File.separator+"htaCoord.jpg").getImage();
                w = img.getWidth(null);
                h = img.getHeight(null);
                return new Rectangle(-w/2, -h/2, w, h);
            case SACOMUT:
                img = new ImageIcon("icons"+File.separator+"sacomut.jpg").getImage();
                w = img.getWidth(null);
                h = img.getHeight(null);
                return new Rectangle(-w/2, -h/2, w, h);
                default: 
                    System.out.println("default");
                    return null;
                
        }
        */
    }
    
}
