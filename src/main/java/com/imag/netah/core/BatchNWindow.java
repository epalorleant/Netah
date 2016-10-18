/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.google.common.collect.Queues;
import com.imag.netah.core.event.EventBean;
import java.util.Queue;
import rx.Observable;
import rx.Observer;


/**
 *
 * @author epaln
 */
public class BatchNWindow extends WindowHandler {

    //EPAgent _wagent;
    int _size;
    Notifier notifier;

    public BatchNWindow(int size) {
        _size = size;
    }

    @Override
    public void register(EPUnit agent) {
        //  _wagent = agent;
        _agent = agent;
        Observable<Observable<EventBean>> windows = _agent.getSourceStream().window(_size);        
        windows.subscribe(new Observer<Observable<EventBean>>() {
            @Override
            public void onNext(Observable<EventBean> aWindow) {

                aWindow.subscribe(new Observer<EventBean>() {                   
                    Queue<EventBean> res = Queues.newArrayDeque();
                    @Override
                    public void onNext(EventBean evt) {
                        res.add(evt);
                    }

                    @Override
                    public void onCompleted() {
                        if (!res.isEmpty()) {
                            EventBean[] evts;
                            evts = res.toArray(new EventBean[0]);                            
                            res.clear();
                            EventBean evt = new EventBean();                            
                            evt.payload.put("window", evts);                           
                            evt.getHeader().setTypeIdentifier("Window");                           
                            evt.getHeader().setPriority((short)1);                            
                            _agent.getInputQueue().put(evt);
                        }
                    }

                    @Override
                    public void onError(Throwable thrwbl) {
                        
                    }
                });
            }

            @Override
            public void onCompleted() {
                
            }

            @Override
            public void onError(Throwable thrwbl) {
                
            }
        });
    }
}
