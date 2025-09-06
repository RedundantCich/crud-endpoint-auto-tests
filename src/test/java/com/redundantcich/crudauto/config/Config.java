package com.redundantcich.crudauto.config;

import io.restassured.specification.RequestSpecification;
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

    public static String getBooksEndpoint() {
        return ConfiguredEnvironment
                .getConfiguration()
                .getEnvironmentVariables()
                .getProperty("endpoints.books");
    }

    public static String getBookByIdEndpoint(String id) {
        String template = ConfiguredEnvironment
                .getConfiguration()
                .getEnvironmentVariables()
                .getProperty("endpoints.bookById");
        return template.replace("{id}", id);
    }
}
