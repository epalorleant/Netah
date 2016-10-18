/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.routing;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.imag.netah.network.devices.Device;
import java.util.HashMap;

/**
 *
 * @author epaln
 */
public class PubSubService {

    private HashMap<String, EventBus> _topicBus;
    private Device device;

    public PubSubService(Device d) {
        _topicBus = Maps.<String, EventBus>newHashMap();
        device = d;
    }

    /**
     * publish an event to the event bus associated to his topic
     *
     * @param evt the event to publish
     * @param topic the topic to publish to
     * @return
     *
     */
    public long publish(Object evt, String topic) {
        EventBus eBus;
        if (!_topicBus.containsKey(topic)) {
            eBus = new EventBus();
            _topicBus.put(topic, eBus);
        } else {
            eBus = _topicBus.get(topic);
        }
        if (evt != null) {
            eBus.post(evt);
        }
        return System.currentTimeMillis();
    }

    /**
     * register an event to the eventBus associated to the given topic
     *
     * @param subscriber
     * @param topic
     */
    public void subscribe(Subscriber subscriber, String topic) {
        EventBus eBus;
        if (!_topicBus.containsKey(topic)) {
            eBus = new EventBus();
            _topicBus.put(topic, eBus);
        } else {
            eBus = _topicBus.get(topic);
        }
        if (subscriber != null) {
            // internally subscribe for the topic
            eBus.register(subscriber);
            // update the Topic2Device mapping in order to 
            //inform other peers that this node is interested to receive messages from this topic
            Topic2Device.getInstance().AddMapping(topic, device);
        }

    }

}
