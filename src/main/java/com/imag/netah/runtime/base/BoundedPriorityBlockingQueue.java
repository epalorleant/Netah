/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.runtime.base;


import com.imag.netah.core.EPUnit;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.event.EventComparator;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.util.Arrays;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * @author epaln
 */
public class BoundedPriorityBlockingQueue extends PriorityBlockingQueue<EventBean> {

    private int _capacity = 1000;
    volatile private int numberDefaults = 0;

    private short strategy = QoSTuner.QUEUE_REPLACE;
    private EPUnit _agent;

    public BoundedPriorityBlockingQueue(EPUnit agent) {
        this(1000, agent);
    }

    public BoundedPriorityBlockingQueue(int maxCapacity, EPUnit agent) {
        super(maxCapacity, new EventComparator());
        _capacity = maxCapacity;
        _agent = agent;
    }

    @Override
    public boolean offer(EventBean e) {
        if (this.size() == _capacity) {
            numberDefaults++;
            // System.out.println("full queue: evts "+this.size()+" in "+ _capacity);
            if (strategy == QoSTuner.QUEUE_IGNORE) {
                return false;
            } else if (strategy == QoSTuner.QUEUE_NOTIFY) {
                _agent.getOutputNotifier().run();
                return super.offer(e);
            } else { // strategy = REPLACE
                boolean success = super.offer(e);
                if (!success) {
                    return false;
                } else {
                    EventBean[] items = (EventBean[]) this.toArray(new EventBean[0]);
                    Arrays.sort(items, new EventComparator());
                    this.remove(items[items.length - 1]);
                    return true;
                }
            }
        } else {
            return super.offer(e);
        }
    }

    public int getNumberDefaults() {
        return numberDefaults;
    }

    public int getCapacity() {
        return _capacity;
    }

    public void setCapacity(int _capacity) {
        this._capacity = _capacity;
    }

    public short getStrategy() {
        return strategy;
    }

    public void setStrategy(short strategy) {
        this.strategy = strategy;
    }

}
