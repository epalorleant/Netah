/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.samples.scenarioSogrid.main;

import com.imag.netah.Simulation;
import com.imag.netah.core.ConjunctionAgent;
import com.imag.netah.core.DisjunctionAgent;
import com.imag.netah.core.FilterAgent;
import com.imag.netah.core.GreatherThanFilter;
import com.imag.netah.core.SelectionMode;
import com.imag.netah.core.TimeBatchWindow;
import com.imag.netah.runtime.client.EventConsumer;
import com.imag.samples.scenarioSogrid.consumers.ConsumerSI;
import com.imag.samples.scenarioSogrid.consumers.DCConsumer;
import com.imag.samples.scenarioSogrid.producers.DCProducer;
import com.imag.samples.scenarioSogrid.producers.PalierHTA;
import com.imag.samples.scenarioSogrid.producers.VoltageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author epaln
 */
public class Main {
      /**
     * @param args the command line arguments
     */
    private static long delay = 30000, closeDelay = 20000;
    private static int numPAs = 5;
    private static String folderPa = "voltages";
    private static String folderDefault = "default";

    public static void main(String[] args) throws Exception {

        if (args.length == 3) {
            numPAs = Integer.parseInt(args[0]);
            delay = Long.parseLong(args[1]);
            closeDelay = Long.parseLong(args[2]);
        }
        Simulation simu = new Simulation();

        EventConsumer sitr = new EventConsumer("SIT-R", "ComplexFault", new ConsumerSI());
        File r = new File(folderPa);
        File[] listFiles = r.listFiles();
        String[] topicDCProd = new String[numPAs];
        for (int i = 0; i < listFiles.length; i++) {
            if (i < numPAs) {
                VoltageProducer pa = new VoltageProducer(listFiles[i].getName(), new FileInputStream(listFiles[i]), delay);
                simu.addProducer(pa);                
                FilterAgent f = new FilterAgent("Filter_PA_" + i, pa.getOutputTopic(), "UVoltage" + i);
                    //inputOr[i] = f.getOutputTopic();
                f.setExecutionTime(1);
                f.setUsedMemory(1);
                f.setPriorityFunction("1");
                f.addFilter(new GreatherThanFilter("voltage", 11.5d));
                DCProducer dcproducer = new DCProducer("DCProducer"+i);
                simu.addProducer(dcproducer);
                topicDCProd[i] = dcproducer.getOutputTopic();
                EventConsumer dc = new EventConsumer("DCConsumer_"+i, f.getOutputTopic(), new DCConsumer(dcproducer));
                dc.getEPUList().add(f);
                simu.addConsumer(dc);                
            }
        }
        r = new File(folderDefault);
        File[] listFiles2 = r.listFiles();
        String[] inputOrPa = new String[listFiles2.length];
        for (int i = 0; i < listFiles2.length; i++) {
            if (i < numPAs) {
                PalierHTA pal = new PalierHTA(listFiles2[i].getName(), new FileInputStream(listFiles2[i]), delay, closeDelay);
                inputOrPa[i] = pal.getOutputTopic();
                simu.addProducer(pal);
            }
        }
        DisjunctionAgent or2 = new DisjunctionAgent("Union_ResistiveFault", inputOrPa, "ResistiveFault");
        or2.setExecutionTime(10);
        or2.setUsedMemory(10);
        sitr.getEPUList().add(or2);

        DisjunctionAgent or1 = new DisjunctionAgent("Union_UVoltage", topicDCProd, "UVoltage");
        or1.setUsedMemory(10);
        or1.setExecutionTime(10);
        sitr.getEPUList().add(or1);

        // AND(UVoltage, HVFailure) on a sliging window
        String[] inputAnd = {"UVoltage", "ResistiveFault"};
        ConjunctionAgent and = new ConjunctionAgent("AND[win:sliding(20sec,20sec)]", inputAnd, "ComplexFault");
        and.setExecutionTime(20000);
        and.setUsedMemory(50);
        and.setPriorityFunction("5");
        and.setSelectionMode(SelectionMode.MODE_CUMULATIVE);
        and.setWindowHandler(new TimeBatchWindow(20000, TimeUnit.MILLISECONDS));
        sitr.getEPUList().add(and);

        simu.addConsumer(sitr);      

        simu.run();
    }

}
