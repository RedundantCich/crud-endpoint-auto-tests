package com.redundantcich.crudauto.steps;

import io.cucumber.java.en.Then;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.equalTo;

public class CommonSteps {

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        lastResponse().then().statusCode(equalTo(expectedStatus));
    }
}

