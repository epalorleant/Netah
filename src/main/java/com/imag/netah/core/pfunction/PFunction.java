/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.core.pfunction;

import com.imag.netah.core.event.EventBean;

/**
 *
 * @author epaln
 */
public class PFunction {
   public static short maxPriority(EventBean[] evts){
        short max = 0, val = 0;
        for (EventBean evt : evts) {
            val = evt.getHeader().getPriority();
            if (val > max) {
                max = val;
            }
        }      
        return max;
    }
    public static short minPriority(EventBean[] evts){
        short min = Short.MAX_VALUE, val = 0;
        for (EventBean evt : evts) {
            val = evt.getHeader().getPriority();
            if (val < min) {
                min = val;
            }
        }      
        return min;
    }
    public static short sumPriority(EventBean[] evts){
        short sum = 0, val = 0;
        for (EventBean evt : evts) {
            val = evt.getHeader().getPriority();
            sum+= val;
        }      
        return sum;
    }
    public static short avgPriority(EventBean[] evts){
        short avg = (short) (sumPriority(evts) / evts.length);       
        return avg;
    }
}
