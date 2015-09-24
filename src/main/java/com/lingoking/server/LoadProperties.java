package com.lingoking.server;

import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {

    public static final String PROPERTY_FILE = "/config.properties";
    private Properties properties = new Properties();

    public LoadProperties() {

        try {
            InputStream input = null;
            try {
                input = this.getClass().getResourceAsStream(PROPERTY_FILE);
                if (input == null) {
                    throw new NullPointerException("Unable to load " + PROPERTY_FILE + " input stream is null");
                }
                this.properties.load(input);
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (Exception e) {
            String error = "Unable to read " + PROPERTY_FILE + " " + e.getMessage();
            System.err.println(error);
            e.printStackTrace();
            throw new RuntimeException(error, e);
        }

    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }
}
