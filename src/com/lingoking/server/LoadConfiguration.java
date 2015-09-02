package com.lingoking.server;

import java.io.File;
import java.util.Properties;

public class LoadConfiguration {
    private Properties configuration;

    public LoadConfiguration(File file) {
        configuration = new Properties();

        try {
            configuration.loadFromXML(file.toURI().toURL().openStream());
        }
        catch (Exception e) {
            System.out.println("An error occurred: "+ e);
        }
    }

    public String findSymbol(String symbol) {
        String message = configuration.getProperty(symbol);
        if (message == null) {
            return "";
        }
        return message;
    }
}
