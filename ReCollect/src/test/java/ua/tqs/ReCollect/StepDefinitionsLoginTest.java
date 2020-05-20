package ua.tqs.ReCollect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.UserService;

//not working, nor would it make sense even if it was working

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitionsLoginTest {

    private ResponseEntity<Boolean> entity;

    @Autowired
    TestRestTemplate restClient;
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Given("that I am a logged out user")
    public void that_I_am_a_logged_out_user() {
        //setup
        userService.deleteAll();
        userService.register(new User("name", "exist@gmail.com", "pass", "1312435", null));


        //logged out
        assertNull(userService.getCurrentUser(), "msg");
    }

    @Given("I am on the login page")
    @Test
    public void i_am_on_the_login_page() {

        assertTrue(true);
        //next step combines it
    }

    @When("I fill in the email and password fields with my credentials")
    public void i_fill_in_the_email_and_password_fields_with_my_credentials() {
        String email="exist@gmail.com";
        //send password?

        ResponseEntity<Boolean> entity=restClient.postForEntity("/users/login", email, Boolean.class);
        this.entity=entity;
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        //not tested
    }

    @Then("the system signs me in successfully")
    public void the_system_signs_me_in_successfully() {
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue(entity.getBody());
    }

    @When("I fill in the email and password fields with invalid credentials")
    public void i_fill_in_the_email_and_password_fields_with_invalid_credentials() {
        String email="dontexist@gmail.com";
        //send password?

        ResponseEntity<Boolean> entity=restClient.postForEntity("/users/login", email, Boolean.class);
        this.entity=entity;
    }

    @Then("the system displays an error message")
    public void the_system_displays_an_error_message() {
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertFalse(entity.getBody());
    }

}
