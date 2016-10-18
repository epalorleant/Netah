/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;




import com.imag.netah.runtime.base.Func1;
import com.imag.netah.core.event.EventBean;
import com.imag.netah.core.logging.LoggerUtil;
import com.imag.netah.core.qosmonitor.QoSTuner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;



/**
 * Applique un ensemble de filtres sur un événement. Les événements notifiés
 * sont ceux qui passent tous les filtres présents dans la liste de filtres
 *
 * @author epaln
 */
public class FilterAgent extends EPUnit {

    IOTerminal inputTerminal;
    IOTerminal outputTerminal;
    List<Func1<EventBean, Boolean>> _filters = new ArrayList<>();

    public FilterAgent(String info, String IDinputTerminal, String IDoutputTerminal) {
        super(info);
        
        this._info = info;
        this._type = "Filter";
        this._receivers[0] = new TopicReceiver(this);
        inputTerminal = new IOTerminal(IDinputTerminal, "input channel " + _type, _receivers[0], this);
        outputTerminal = new IOTerminal(IDoutputTerminal, "output channel " + _type, this);
        _outputNotifier = new OQNotifier(this, QoSTuner.NOTIFICATION_PRIORITY);
        //Queue<EventBean> selected1 = Queues.newArrayDeque();
        //_selectedEvents[0] = selected1;
        logger = new LoggerUtil("FilterMeasures");
        logger.log("Operator, isProduced, Processing Time, InputQ Size, OutputQ Size ");
        executorService = Executors.newSingleThreadExecutor();
    }

    public FilterAgent(String info) {
        super(info);
        this._info = info;
    }

    public void setInpuTerminal(String input){
        inputTerminal = new IOTerminal(input, "input channel " + _type, _receivers[0], this);
    }
    
    public void setOutputTerminal(String output){
        outputTerminal = new IOTerminal(output, "output channel " + _type, this);
    }
    
    @Override
    public Collection<IOTerminal> getInputTerminals() {
        ArrayList<IOTerminal> inputs = new ArrayList<IOTerminal>();
        inputs.add(inputTerminal);
        return inputs;
    }

    @Override
    public IOTerminal getOutputTerminal() {
        return outputTerminal;
    }

    private void p(String msg) {
        System.out.println(msg);
    }

    @Override
    public void process() {
      //  while (!_selectedEvents[0].isEmpty()) {

            // statistics: #events processed, processing time
            long time ;//= System.currentTimeMillis();     
            long ntime;// = System.nanoTime(); // to compute the processing time for that cycle
            numEventProcessed++;

            EventBean evt = _selectedEvents.poll();
            EventBean[] values;
            if (evt.getHeader().getTypeIdentifier().equals("Window")) {
                values = (EventBean[]) evt.getValue("window");
            } else {
                values = new EventBean[1];
                values[0] = evt;
            }
            //System.out.println("Filtering " + values.length + " values");
            for (EventBean e : values) {
                boolean pass_filters = true;
                time = (long) e.getValue("#time#");                
                
                ntime = (long) e.getValue("processTime");
                
                for (Func1<EventBean, Boolean> _filter : _filters) {
                    if (!_filter.invoke(e)) {
                        pass_filters = false;
                        break;
                    }
                }
                if (pass_filters) {
                    e.getHeader().setProductionTime(System.currentTimeMillis());
                    e.getHeader().setIsComposite(true);
                    e.payload.put("ttl", TTL);
                    _outputQueue.put(e);
                    time = System.currentTimeMillis() - time;
                    numEventProduced++;
                    //logger.log(this.getInfo()+" ,True, "+time+", "+ this.getInputTerminals().iterator().next().getReceiver().getInputQueue().size()+
                    //      ", "+ this.getOutputQueue().size());
                    getExecutorService().execute(getOutputNotifier());
                } else {
                    time = System.currentTimeMillis() - time;

                    logger.log(this.getInfo() + ", False, " + (System.nanoTime() - ntime) + ", " + this.getInputQueue().size()
                            + ", " + this.getOutputQueue().size());
                }
                processingTime += time;
//                if (!_outputQueue.isEmpty()) {
//                   
//                }
            }
  //      }
    }


    public void addFilter(Func1<EventBean, Boolean> filter) {
        this._filters.add(filter);
    }
}
