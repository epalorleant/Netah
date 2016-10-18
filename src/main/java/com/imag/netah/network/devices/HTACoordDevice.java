/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.devices;

import com.imag.netah.gui.plugin.MyLayeredIcon;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.apache.commons.io.IOUtils;

/**
 * un serveur HP ProLiant DL360  Gen 9  (rackable) avec  processeur 1.6GHz 6-core 1P  et  16GB  de RAM.
 * @author epaln
 */
public class HTACoordDevice extends Device {
    
    public HTACoordDevice(String name) {
         super(name);
        this.setCpuSpeed(500);
        this.setTotalMemory(16000);
        this.setDeviceType(DeviceType.HTA_COORD);
        this.setDeviceName(name);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/htaCoord.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HTACoordDevice(String name, double cpuSpeed, int totalMemory) {
        super(name, cpuSpeed, totalMemory, DeviceType.HTA_COORD);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/htaCoord.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
