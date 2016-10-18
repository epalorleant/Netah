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
 * Le SACOMUT est un boitier modulaire embarquant un processeur applicatif de type ARM9 (450MHz) 
 * @author epaln
 */
public class SacomutDevice extends Device {

    public SacomutDevice(String name) {
        super(name);
        this.setCpuSpeed(350);
        this.setTotalMemory(1024);
        this.setDeviceType(DeviceType.SACOMUT);
        this.setDeviceName(name);
        try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/sacomut.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SacomutDevice(String name, double cpuSpeed, int totalMemory) {
        super(name, cpuSpeed, totalMemory, DeviceType.SACOMUT);
         try {
            byte[] imageInByte;            
            imageInByte = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("image/sacomut.jpg"));            
            icon = new MyLayeredIcon(new ImageIcon(imageInByte).getImage());
        } catch (IOException ex) {
            Logger.getLogger(AMIDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
