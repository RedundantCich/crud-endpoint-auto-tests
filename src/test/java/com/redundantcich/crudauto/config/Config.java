package com.redundantcich.crudauto.config;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

    public class Config {

        private static final EnvironmentVariables vars = SystemEnvironmentVariables.createEnvironmentVariables();
        private static final String env = System.getProperty("environment", "dev");

        public static String getBaseUrl() {
            return vars.getProperty("environments." + env + ".base.url");
        }

        public static String getBooksEndpoint() {
            return vars.getProperty("environments." + env + ".endpoints.books");
        }

        public static String getBookByIdEndpoint(String id) {
            return vars.getProperty("environments." + env + ".endpoints.books").replace("{id}", id);
        }

        public static String getApiUser() {
            return System.getenv("API_USER");
        }

        public static String getApiPass() {
            return System.getenv("API_PASS");
        }
    }