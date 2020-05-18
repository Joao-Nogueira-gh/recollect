package ua.tqs.ReCollect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.LocationRepository;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitionsRegisterTest {

    /**
     * 
     * Integration test
     * 
     * Focuses on testing the Web and Service layer
     * using the defined behavior as test template (BDD)
     * 
     */

    @Autowired
    TestRestTemplate restClient;

    @Autowired
    UserRepository userRepo;

    @Autowired
    LocationRepository localRepo;

    @Autowired
    UserService userService;
    
    @Autowired
    LocationService locationService;

    @When("A user submits credentials that don't exist in the DB")
    @Test
    public void a_user_submits_credentials_that_don_t_exist_in_the_DB() {

        // Clean up the DB
        userRepo.deleteAll();
        
        // Instance the user that will be created
        User newUser = new User("user", "new_user@gmail.com", "coiso", "3467764", localRepo.findByDistrictAndCounty("Aveiro", "Aveiro"));

        // Make sure the credentials submitted don't exist in the BD
        assertEquals(null, userService.getByEmail("new_user@gmail.com"), "Error: email in use");

        // Post the credentials to the API
        ResponseEntity<Boolean> entity = restClient.postForEntity("/users/register", newUser, Boolean.class);

        // Assert everything went OK
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue(entity.getBody());

    }
    
    @Then("A new user should be created with the inserted credentials")
    @Test
    public void a_new_user_should_be_created_with_the_inserted_credentials() {
      
        User newUser = new User("user", "new_user@gmail.com", "coiso", "3467764", localRepo.findByDistrictAndCounty("Aveiro", "Aveiro"));

        // Simple validation check
        assertEquals(newUser.getName(), userService.getByEmail("new_user@gmail.com").getName(), "Users don't match");

        // Clean up
        userRepo.deleteAll();

    }
    
}
