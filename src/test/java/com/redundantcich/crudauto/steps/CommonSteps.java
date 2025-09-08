package com.redundantcich.crudauto.steps;

import io.cucumber.java.en.Then;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.equalTo;

public class CommonSteps {

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        lastResponse().then().statusCode(equalTo(expectedStatus));
    }

    @Then("the response doesn't contain an array")
    public void responseContainsNoArray() {
        doesResponseContainArray(false);
    }

    @Then("the response should contain an array")
    public void responseContainsArray() {
        doesResponseContainArray(true);
    }

    private void doesResponseContainArray(boolean isArrayExpected) {
        String body = lastResponse().getBody().asString().trim();
        boolean isArrayActual = body.startsWith("[");

        if (isArrayExpected && !isArrayActual) {
            throw new AssertionError(
                    "Expected an array but the API didn't return it"
            );
        } else if (!isArrayExpected && isArrayActual) {
            throw new AssertionError(
                    "Expected a single Book object but the API returned an array:"
            );
        }
    }
}

