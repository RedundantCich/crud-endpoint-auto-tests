package com.redundantcich.crudauto.config;

import net.serenitybdd.model.environment.ConfiguredEnvironment;

public class Config {

    public static String getBaseUrl() {
        return ConfiguredEnvironment.getConfiguration().getBaseUrl();
    }

    public static String getApiUser() {
        return System.getenv("API_USER");
    }

    public static String getApiPass() {
        return System.getenv("API_PASS");
    }
}
