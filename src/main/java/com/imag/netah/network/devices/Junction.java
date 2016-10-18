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
public class Junction extends Device {

    public Junction(String name) {
        super(name);
        this.setCpuSpeed(1);
        this.setTotalMemory(0);
        this.setDeviceType(DeviceType.Junction);
        this.setDeviceName(name);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/join.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public Junction(String name, double cpuSpeed, int totalMemory) {
        super(name, 1, 0, DeviceType.Junction);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/join.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}
