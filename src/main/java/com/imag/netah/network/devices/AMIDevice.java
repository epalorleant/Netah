/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.devices;

import com.imag.netah.gui.plugin.MyLayeredIcon;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author epaln
 */
public class AMIDevice extends Device {
    

    public AMIDevice(String name, double cpuSpeed, int totalMemory) {
        super(name, cpuSpeed, totalMemory, DeviceType.AMI);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/meter.jpeg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AMIDevice(String name) {
        super(name);
        this.setCpuSpeed(10);
        this.setTotalMemory(128);
        this.setDeviceType(DeviceType.AMI);
        this.setDeviceName(name); 
        //String imageURI = getClass().getClassLoader().getResource("image"+File.separator+"meter.jpeg").getFile();
        //icon = new MyLayeredIcon(new ImageIcon("icons"+File.separator+"meter.jpeg").getImage());
       // icon = new MyLayeredIcon(new ImageIcon(imageURI).getImage());
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/meter.jpeg"));           
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
