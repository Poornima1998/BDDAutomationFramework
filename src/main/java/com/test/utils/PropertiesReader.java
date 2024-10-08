package com.test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/configfiles/locator.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
