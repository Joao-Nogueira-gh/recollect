package ua.tqs.ReCollect;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.UserService;

public class StepDefinitionsLogin {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Given("that I am a logged out user")
    public void that_I_am_a_logged_out_user() {
        throw new io.cucumber.java.PendingException();
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        throw new io.cucumber.java.PendingException();
    }

    @When("I fill in the email and password fields with my credentials")
    public void i_fill_in_the_email_and_password_fields_with_my_credentials() {
        throw new io.cucumber.java.PendingException();
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system signs me in successfully")
    public void the_system_signs_me_in_successfully() {
        throw new io.cucumber.java.PendingException();
    }

    @When("I fill in the email and password fields with invalid credentials")
    public void i_fill_in_the_email_and_password_fields_with_invalid_credentials() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system displays an error message")
    public void the_system_displays_an_error_message() {
        throw new io.cucumber.java.PendingException();
    }

}
