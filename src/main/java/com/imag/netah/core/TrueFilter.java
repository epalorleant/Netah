/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.runtime.base.Func1;
import com.imag.netah.core.event.EventBean;




/**
 *
 * @author epaln
 */
public class TrueFilter implements Func1<EventBean, Boolean> {

    @Override
    public Boolean invoke(EventBean param1) {
        return true;
    }

}
