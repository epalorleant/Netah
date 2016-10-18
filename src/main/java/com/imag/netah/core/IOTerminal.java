/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.core.event.EventBean;


/**
 *
 * @author epaln
 */
public class IOTerminal {

    private String _topic;
    private String description;
    private TopicReceiver _receiver = null;
    private EPUnit _agent;

    public IOTerminal(String topic, String description, TopicReceiver receiver, EPUnit epu) {
        this._topic = topic;
        this.description = description;
        _receiver = receiver;
        _agent = epu;
    }

    public IOTerminal(String topic, String description, EPUnit epu) {
        this(topic, description, null, epu);
    }

    public TopicReceiver getReceiver() {
        return _receiver;
    }

    public boolean open() {
        if (_receiver != null) {
            _agent.getDevice().getPubSubService().subscribe(_receiver, _topic);
        }
        return true;
    }

    public String getTopic() {
        return _topic;
    }

    public String getId() {
        return _topic;
    }

    public void send(EventBean[] e) throws Exception {
        for (EventBean evt : e) {
            evt.payload.remove("ttl");
            evt.getHeader().setNotificationTime(System.currentTimeMillis());
        }
        _agent.getDevice().publishEvent(e, _topic);
    }

    public EPUnit getAgent() {
        return _agent;
    }
}
