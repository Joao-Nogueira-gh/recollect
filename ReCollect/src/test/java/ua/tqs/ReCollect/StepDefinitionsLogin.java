package ua.tqs.ReCollect;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.UserService;

public class StepDefinitionsLogin {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    String email;
    String pass;

    @Given("that I am a logged out user")
    public void that_I_am_a_logged_out_user() {
        assertNull(userService.getCurrentUser());
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        //uhhhhhhhhhhhhhh
    }

    @When("I fill in the email and password fields with my credentials")
    public void i_fill_in_the_email_and_password_fields_with_my_credentials() {
        this.email="exist@gmail.com";
        this.pass="pass";

        userService.register(new User("name", email, pass, "1312435", null));
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        //uhhhhhhhhhhhhhh
    }

    @Then("the system signs me in successfully")
    public void the_system_signs_me_in_successfully() {
        assertTrue(userService.login(email, pass));
    }

    @When("I fill in the email and password fields with invalid credentials")
    public void i_fill_in_the_email_and_password_fields_with_invalid_credentials() {
        this.email="other@gmail.com";
        this.pass="sfssf";
    }

    @Then("the system displays an error message")
    public void the_system_displays_an_error_message() {
        assertFalse(userService.login(email, pass));
    }

}
