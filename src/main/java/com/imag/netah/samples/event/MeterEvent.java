/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.samples.event;

/**
 *
 * @author epaln
 */
public class MeterEvent {

    private long timestampUTC;
    private double realPowerWatts;
    private String meterID;

    public long getTimestampUTC() {
        return timestampUTC;
    }

    public void setTimestampUTC(long timestamp) {
        this.timestampUTC = timestamp;
    }

    public double getRealPowerWatts() {
        return realPowerWatts; //roundToDecimals(realPowerWatts,2);
    }

    public void setRealPowerWatts(double realPowerWatts) {
        this.realPowerWatts = realPowerWatts;
    }

    @Override
    public String toString() {
        return timestampUTC + ", " + realPowerWatts + ", " + meterID; //To change body of generated methods, choose Tools | Templates.
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

   
}