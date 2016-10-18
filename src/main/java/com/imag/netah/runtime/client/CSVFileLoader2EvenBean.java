/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.runtime.client;

import com.google.common.collect.Lists;
import com.imag.netah.core.event.EventBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author epaln
 */
public class CSVFileLoader2EvenBean {        
    private File file;
    private String[] format;
    private BufferedReader input;
    private ArrayList<EventBean> data;
    private String[] types;

    public CSVFileLoader2EvenBean(InputStream file, String[] format, String[] types) {
        //this.file = file;
        this.format = format;
        this.types = types;
        try {
            input = new BufferedReader(new InputStreamReader(file,"UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CSVFileLoader2EvenBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public void loadData() throws Exception {
        data = Lists.newArrayList();

        String line = input.readLine();
        while (line != null) {
            EventBean bean = new EventBean();
            StringTokenizer tokenizer = new StringTokenizer(line, ",");
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                bean.payload.put(format[i], token);
                set(bean, format[i], types[i], token);
                i++;
            }
            //System.out.println(bean);
            data.add(bean);
            line = input.readLine();
        }
        input.close();

    }

    private void set(EventBean bean, String attribute, String type, String fieldValue) {
        switch (type) {
                    case "long": {
                        long castedValue = Long.parseLong(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    case "int": {
                        int castedValue = Integer.parseInt(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    case "float": {
                        float castedValue = Float.parseFloat(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    case "double": {
                        double castedValue = Double.parseDouble(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    case "short": {
                        short castedValue = Short.parseShort(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    case "boolean": {
                        boolean castedValue = Boolean.parseBoolean(fieldValue);
                        bean.payload.put(attribute, castedValue);
                        return;
                    }
                    default:
                        bean.payload.put(attribute, fieldValue);
                        return;
                }

    }
    
     public EventBean getNext() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String line = input.readLine();        
        if (line != null) {
            EventBean bean = new EventBean();
            StringTokenizer tokenizer = new StringTokenizer(line, ", ");
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                set(bean, format[i], types[i], token);
                i++;
            }

            return bean;
        } else {
            closeInput();
            return null;
        }

    }

    public void closeInput() throws IOException {
        input.close();
    }
}
