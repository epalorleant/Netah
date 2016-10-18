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
public class NotEqualFilter implements Func1<EventBean, Boolean> {
    private String _attributeName;
    private Object _value;
    
    public NotEqualFilter(String _attributeName, Object _value) {
        this._attributeName = _attributeName;
        this._value = _value;
    }
    
    @Override
    public Boolean invoke(EventBean evt) {
       Object val = evt.payload.get(_attributeName);
       boolean ok = false;
        if (val != null) {
            ok= !val.equals(_value);
        }
       return ok;
    }
    
}
