/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.samples.producer;

import com.imag.netah.runtime.client.CSVFileLoader2EvenBean;
import com.imag.netah.runtime.client.EventProducer;
import com.imag.netah.core.event.EventBean;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class MeterSimulator extends EventProducer {

    CSVFileLoader2EvenBean loader;
    String typeName;
    
    File file;
    ArrayList<EventBean> realValues;
    double SOMME = 0;
    double ECART_TYPE = 0;
    long passage = 0;
    // Class _clazz;
    String attribute;
    boolean sporadic = false;
    volatile boolean stop = false;
    Random random;
    int mean;

    public MeterSimulator(String name, String typeName, InputStream file, String[] propertyOrder,
            String[] dataTypes, long delay, Class clazz) {
        super(name, clazz);
        this.typeName = typeName;
        this.delay = delay;
        this.definedDelay = delay;
        //this.file = file;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        loader = new CSVFileLoader2EvenBean(file, propertyOrder, dataTypes);
        realValues = new ArrayList<>();
        random = new Random();
    }

    @Override
    public void run() {
        boolean isSimulating = false;
        //System.out.println("generating events...");
        int i = 1;
        try {
            Thread.sleep(delay);
            while (!stop) {
                if (!isSimulating) {
                    EventBean evt = loader.getNext(); // get the next event if possible
                    if (evt != null) {
                        //System.out.println(ObjectGraphMeasurer.measure(evt));
                        //EventBean e = producer.createEventWithPayload(evt);
                        //evt.getHeader().setPriority((short) random.nextInt(5));
                        evt.getHeader().setPriority((short)1);
                        evt.getHeader().setDetectionTime(System.currentTimeMillis());
                        publish(evt);
                        System.out.println(" next event generated... (N° " + i + "): " + evt.payload);
                        i++;
                        double value = Double.parseDouble(evt.getValue(attribute).toString());
                        realValues.add(evt);
                        SOMME += value;
                        Thread.sleep(delay);
                    } else {
                        isSimulating = true;
                        double moyenne = SOMME / realValues.size();
                        // calcul de l'ecart type entre les valeurs observées
                        double var = 0;
                        for (EventBean e : realValues) {
                            double val = Double.parseDouble(e.getValue(attribute).toString());
                            var += Math.pow(val - moyenne, 2);
                        }
                        var = var / realValues.size();
                        ECART_TYPE = Math.sqrt(var);
                        System.out.println("sigma = " + ECART_TYPE);
                    }

                } else {
                    passage++;
                    System.out.println("Start Simulation N°" + passage + "...");
                    for (EventBean evt : realValues) {
                        // EventBean evt = e.copy();
                        double value = Double.parseDouble(evt.getValue(attribute).toString());
                        // valeur à ajouter ou retrancher
                        double eps = random.nextGaussian() * (ECART_TYPE / 2);
                        // ajouter ou retrancher?
                        if (random.nextBoolean()) { // ajouter
                            value += eps;
                        } else {  // retrancher
                            value -= eps;
                            if (value < 0) {
                                value += eps;
                            }
                        }
                        evt.payload.put(attribute, value);
//                        try {
//                            long timestamp = Long.parseLong(BeanUtils.getProperty(evt, "timestampUTC"));
//                            timestamp += 86400 * passage;
//                            BeanUtils.setProperty(evt, "timestampUTC", timestamp);
//                        } catch (NoSuchMethodException ex) {
//
//                        }
                        System.out.println(evt.payload);
                        i++;
                        //EventBean e = producer.createEventWithPayload(evt);
                        evt.getHeader().setPriority((short) random.nextInt(5));
                        evt.getHeader().setDetectionTime(System.currentTimeMillis());
                        publish(evt);
                        Thread.sleep(delay);
                    }
                    System.out.println("End Simulation N°" + passage + "...");

                }
            }

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NumberFormatException | InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(MeterSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public MeterSimulator simulate(String attribute) {
        this.attribute = attribute;
        return this;
    }
}
