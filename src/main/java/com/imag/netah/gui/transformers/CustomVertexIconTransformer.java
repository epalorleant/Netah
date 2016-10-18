/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.gui.transformers;

import com.imag.netah.network.devices.Device;
import javax.swing.Icon;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author epaln
 */
public class CustomVertexIconTransformer implements Transformer<Device, Icon> {

    @Override
    public Icon transform(Device device) {
        return  device.getIcon();   
    }
    
}
