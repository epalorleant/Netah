/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.network.devices;

import org.apache.commons.collections15.Factory;

/**
 *
 * @author epaln
 */
public class DeviceFactory implements Factory<Device> {

    private static int amiCount = 0;
    private static int dcCount = 0;
    private static int htaCount = 0;
    private static int sacomutCount = 0;
    private static int utilityCount = 0;
    private static int paCount =0 ;
    private static int jcCount =0 ;
    private static DeviceFactory instance = new DeviceFactory();
    private static DeviceType typeToCreate = DeviceType.AMI;

    private DeviceFactory() {

    }

    public static DeviceFactory getInstance() {
        return instance;
    }

    @Override
    public Device create() {
        //typeToCreate = getRandomType();
        switch (typeToCreate) {
            case SACOMUT:
                return new SacomutDevice("SACOMUT_" + sacomutCount++);
            case HTA_COORD:
                return new HTACoordDevice("HTACOORD_" + htaCount++);
            case DC:
                return new DCDevice("DC_" + dcCount++);
            case UTILITY:
                return new UtilityDevice("Utility" + utilityCount++);
            case PA:
                return new PADevice("PA_"+paCount++);
            case Junction:
                return new Junction("JC"+jcCount++);
            default:
                return new AMIDevice("AMI_" + amiCount++);
        }
    }

    public void setTypeToCreate(DeviceType type) {
        typeToCreate = type;
    }

    public static int getAmiCount() {
        return amiCount;
    }

    public static void setAmiCount(int amiCount) {
        DeviceFactory.amiCount = amiCount;
    }

    public static int getDcCount() {
        return dcCount;
    }

    public static void setDcCount(int dcCount) {
        DeviceFactory.dcCount = dcCount;
    }

    public static int getHtaCount() {
        return htaCount;
    }

    public static void setHtaCount(int htaCount) {
        DeviceFactory.htaCount = htaCount;
    }

    public static int getSacomutCount() {
        return sacomutCount;
    }

    public static void setSacomutCount(int sacomutCount) {
        DeviceFactory.sacomutCount = sacomutCount;
    }

    public static int getUtilityCount() {
        return utilityCount;
    }

    public static void setUtilityCount(int utilityCount) {
        DeviceFactory.utilityCount = utilityCount;
    }

    public static int getPaCount() {
        return paCount;
    }

    public static void setPaCount(int paCount) {
        DeviceFactory.paCount = paCount;
    }

    public static int getJcCount() {
        return jcCount;
    }

    public static void setJcCount(int jcCount) {
        DeviceFactory.jcCount = jcCount;
    }

    
    public static void resetCounters() {
        amiCount = 0;
        dcCount = 0;
        htaCount = 0;
        sacomutCount = 0;
        paCount = 0;
        jcCount=0;
    }
}
