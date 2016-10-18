/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core;


import com.google.common.collect.Lists;
import com.imag.netah.runtime.base.Func1;
import com.imag.netah.core.event.EventBean;
import java.util.List;


/**
 *
 * @author epaln
 */
public class LogicalAndFilter implements Func1<EventBean, Boolean> {

    private List<Func1<EventBean, Boolean>> _predicates;

    public LogicalAndFilter() {
        _predicates = Lists.newArrayList();
    }

    public void addPredicate(Func1<EventBean, Boolean> predicate) {
        _predicates.add(predicate);
    }

    @Override
    public Boolean invoke(EventBean param1) {
        boolean ok = false;
        for (Func1 predicate : _predicates) {
            ok = (Boolean) predicate.invoke(param1);
            if (!ok) {
                return false;
            }
        }
        return true;
    }
}
