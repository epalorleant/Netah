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
public class MBatchWindow extends WindowHandler {

    //WindowAgent _wagent;
    int _size, skip;
    Notifier notifier;

    public MBatchWindow(int _size, int skip) {
        this._size = _size;
        this.skip = skip;
    }

    @Override
    public void register(EPUnit agent) {
        //  _wagent = agent;
        _agent = agent;
        Observable<Observable<EventBean>> windows = _agent.getSourceStream().window(_size, skip);
        //Observable<Observable<EventBean>> windows = Reactive.window(_agent.getSourceStream(), _size, skip);

        windows.subscribe(new Observer<Observable<EventBean>>() {
            @Override
            public void onNext(Observable<EventBean> aWindow) {

                aWindow.subscribe(new Observer<EventBean>() {
                    //PriorityQueue<EventBean> res = new PriorityQueue<>(1000, new EventComparator());
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
//                            evt.getHeader().setIsComposite(true);
//                            evt.getHeader().setProductionTime(System.currentTimeMillis());
//                            evt.getHeader().setDetectionTime(evts[0].getHeader().getDetectionTime());
                            evt.getHeader().setTypeIdentifier("Window");
//                            evt.getHeader().setProducerID(_wagent.getName());
//                            evt.getHeader().setPriority((short)1);
//                            evt.payload.put("ttl", _wagent.TTL);
//                            _wagent.getOutputQueue().put(evt);
//                            _wagent.numEventProduced++;
//                            _wagent.getOutputNotifier().run();
//                            
                            //notifier = new Notifier(evts, _wagent.outputTerminal);
                            //notifier.start();
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
