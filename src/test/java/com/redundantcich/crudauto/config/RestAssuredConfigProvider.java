package com.redundantcich.crudauto.config;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

public class RestAssuredConfigProvider {

    private static final int CONNECTION_TIMEOUT_MS = 2000; // 2 sec
    private static final int SOCKET_TIMEOUT_MS = 2000;    // 2 sec

    public static void applyTimeouts() {
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", CONNECTION_TIMEOUT_MS)
                        .setParam("http.socket.timeout", SOCKET_TIMEOUT_MS)
                );
    }
}


