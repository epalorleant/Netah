/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.netah.network.routing;

import com.google.common.eventbus.Subscribe;

/**
 *
 * @author epaln
 */
public interface Subscriber<T> {
    @Subscribe
    public void notify(T event);
    
}
