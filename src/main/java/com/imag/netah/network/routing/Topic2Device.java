/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.routing;

import com.imag.netah.network.devices.Device;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author epaln
 */
public class Topic2Device {

    private HashMap<String, HashSet<Device>> topic2device;
    private static Topic2Device instance = null;

    private Topic2Device() {
        topic2device = new HashMap<>();
    }

    public static Topic2Device getInstance() {
        if (instance == null) {
            instance = new Topic2Device();
        }
        return instance;
    }

    public HashMap<String, HashSet<Device>> getTopic2device() {
        return topic2device;
    }

    public void AddMapping(String topic, Device d) {
        if (!topic2device.containsKey(topic)) {
            HashSet<Device> hs = new HashSet<>();
            hs.add(d);
            topic2device.put(topic, hs);
        } else {
            topic2device.get(topic).add(d);
        }
    }

}
