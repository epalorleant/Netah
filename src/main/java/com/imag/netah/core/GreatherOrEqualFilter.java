/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;

import com.imag.netah.runtime.base.Func1;
import com.imag.netah.core.event.EventBean;





/**
 *
 * @author epaln
 */
public class GreatherOrEqualFilter implements Func1<EventBean, Boolean> {

    private String _attributeName;
    private Object _value;

    public GreatherOrEqualFilter(String _attributeName, Object _value) {
        this._attributeName = _attributeName;
        this._value = _value;
    }

    @Override
    public Boolean invoke(EventBean evt) {
        Object val = evt.payload.get(_attributeName);
        //System.out.println("Name: "+val.getClass().getName());

        if (val instanceof Integer) {
            int castedVal = (int) val;
            int castedValue = (int) _value;

            if (castedVal >= castedValue) {
                return true;
            } else {
                return false;
            }
        } else if (val instanceof Float) {
            float castedVal = (float) val;
            float castedValue = (float) _value;

            if (castedVal >= castedValue) {
                return true;
            } else {
                return false;
            }
        } else if (val instanceof Double) {
            double castedVal = (double) val;
            double castedValue = (double) _value;

            if (castedVal >= castedValue) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
