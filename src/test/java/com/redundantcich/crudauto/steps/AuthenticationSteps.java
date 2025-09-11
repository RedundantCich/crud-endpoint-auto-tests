package com.redundantcich.crudauto.steps;

import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;

public class AuthenticationSteps {

    @Given("I am an authenticated API user")
    public void iAmAnAuthenticatedApiUser() {
        Serenity.setSessionVariable("auth").to(true);
    }

    @Given("I don't have valid authentication credentials")
    public void iDonTHaveValidAuthenticationCredentials() {
        Serenity.setSessionVariable("auth").to(false);
    }
}
