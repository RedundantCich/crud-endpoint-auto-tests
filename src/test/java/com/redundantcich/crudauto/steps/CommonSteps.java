package com.redundantcich.crudauto.steps;

import com.redundantcich.crudauto.config.RestAssuredConfigProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.*;

public class CommonSteps {

    @Given("I configure API timeouts")
    public void configureApiTimeouts() {
        RestAssuredConfigProvider.applyTimeouts();
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        lastResponse().then().statusCode(equalTo(expectedStatus));
    }

    @Then("the response status should be 2xx")
    public void theResponseStatusShouldBe2xx() {
        lastResponse().then().statusCode(lessThan(300)).and().statusCode(greaterThanOrEqualTo(200));
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
                    "Expected a single Book object but the API returned an array"
            );
        }
    }
}

