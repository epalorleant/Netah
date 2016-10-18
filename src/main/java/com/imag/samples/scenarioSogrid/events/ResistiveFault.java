/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imag.samples.scenarioSogrid.events;

/**
 *
 * @author epaln
 */
public class ResistiveFault {
    private String location;
    private String depart;
    private boolean failure;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
    
    
}
