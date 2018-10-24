package com.zq.youxi.service.utils.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysConfig {

    private static final Logger logger = LoggerFactory.getLogger(SysConfig.class);

    static Properties properties;

    static {
        InputStream inputStream = SysConfig.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("properties is not found", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("load properties failed", e);
            }
        }
    }

    public static String getPro(String key) {
        return getPro(key, "");
    }

    public static String getPro(String key, String defaultvalue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            value = defaultvalue;
        }
        return value;
    }

    public static int getInt(String key, int defaultvalue) {
        String value = properties.getProperty(key);
        boolean useDefault = false;
        if (value == null || value.trim().equals("")) {
            useDefault = true;
        }
        return useDefault ? defaultvalue : Integer.parseInt(value);
    }

    public static int getInt(String key) {
        String value = properties.getProperty(key);
        return Integer.parseInt(value);
    }

    public static void main(String[] args) {
        System.out.println(getPro("addrs"));
    }
}
