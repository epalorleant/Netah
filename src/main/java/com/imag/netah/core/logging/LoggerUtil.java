/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core.logging;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Orleant
 */
public class LoggerUtil {

    private String fileName;
    private FileHandler fileTxt;
    private SimpleFormatter formatterTxt;
    //private String classname;
    private Logger logger;

    public LoggerUtil(String fileName) {
        this.fileName = fileName;
        //this.classname = classname;
        parametrer();
    }

    private void parametrer() {
        try {
            File f = new File("log");
            if(!f.exists()){
                f.mkdir();
            }
            // Create Logger
            logger = Logger.getAnonymousLogger();//getLogger(classname);
            logger.setLevel(Level.ALL);
            fileTxt = new FileHandler("log/"+fileName + ".txt");

            // Create txt Formatter
            formatterTxt = new MyFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (Exception ex) {

        }
    }

    public void log(String msg) {
        logger.info(msg);
    }

}
