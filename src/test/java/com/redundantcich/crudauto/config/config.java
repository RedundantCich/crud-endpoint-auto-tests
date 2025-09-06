package com.redundantcich.crudauto.config;

import net.serenitybdd.core.environment.ConfiguredEnvironment;

public class Config {

    public static String getBaseUrl() {
        return ConfiguredEnvironment.getConfiguration().getProperty("base.url");
    }

    public static String getApiUser() {
        return System.getenv("API_USER");
    }

    public static String getApiPass() {
        return System.getenv("API_PASS");
    }
}
