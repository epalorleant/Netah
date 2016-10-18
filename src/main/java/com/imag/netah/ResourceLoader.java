/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epaln
 */
public class ResourceLoader {

    public InputStream getRessource(String name) throws URISyntaxException {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResourceAsStream(name);
    }

    public String[] listFiles(String folder) {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        ArrayList<String> files = new ArrayList<>();
        if (jarFile.isFile()) {
            try {
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith(folder + "/") && !name.equals(folder+"/")) { //filter according to the path
                       // System.out.println(name);
                        files.add(name);
                    }
                }
                jar.close();
                return files.toArray(new String[0]);
            } catch (IOException ex) {
                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { // Run with IDE
            final URL url = ResourceLoader.class.getResource("/" + folder);
            if (url != null) {
                try {
                    final File apps = new File(url.toURI());
                    for (File app : apps.listFiles()) {
                        //files.add(getRessource(folder+"/"+app.getName()));
                        files.add(folder+"/"+app.getName());
                    }
                    return files.toArray(new String[0]);
                } catch (URISyntaxException ex) {
                    // never happens
                }
            }
        }

        return null;
    }
}
