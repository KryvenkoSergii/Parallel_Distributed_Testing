package com.softserve.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProjectProperties {

    private Properties properties;
    private final String PROPERTIES_PATH = "src\\test\\resources\\project.properties";

    public ReadProjectProperties() {
        properties = new Properties();
        try {
            properties.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getImplicitWaitDelay() {
        return Integer.parseInt(properties.getProperty("implicit.wait.delay"));
    }
    
    public int geExplicitWaitDelay() {
        return Integer.parseInt(properties.getProperty("explicit.wait.delay"));
    }

    public String getBaseUrl() {
        return properties.getProperty("url.youtube");
    }

}
