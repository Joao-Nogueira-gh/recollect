package ua.tqs.ReCollect;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
// import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.LocationRepository;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

@RunWith(SpringRunner.class)
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

    @Mock
    BCryptPasswordEncoder mockBCryptPwdEncoder;

    @Autowired
    UserRepository userRepo;

    @Autowired
    LocationRepository localRepo;

    @Autowired
    @InjectMocks
    UserService userService;
    
    @Autowired
    LocationService locationService;

    @When("A user submits credentials that don't exist in the DB")
    @Test
    public void a_user_submits_credentials_that_don_t_exist_in_the_DB() throws Exception {

        // Clean up the DB
        // userRepo.deleteAll();
        
        // // Set up the PwdEncryption mock
        // given(mockBCryptPwdEncoder.encode("coiso")).willReturn("SHA512(coiso)");

        // // Instance the user that will be created
        // User newUser = new User("user", "new_user@gmail.com", "coiso", "3467764", localRepo.findByDistrictAndCounty("Aveiro", "Aveiro"));

        // // Make sure the credentials submitted don't exist in the BD
        // assertEquals(null, userService.getByEmail("new_user@gmail.com"), "Error: email in use");

        // // Post the credentials to the registration endpoint

        // // restClient.
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        
        // HttpEntity<byte[]> request = new HttpEntity<>(JsonUtil.toJson(newUser), headers);

        // System.out.println(request);
        // System.out.println(newUser);

        // System.out.println(JsonUtil.toJson(newUser));

        // ResponseEntity<String> entity = restClient.postForEntity("/registration", request, String.class);

        // // Assert everything went OK
        // assertEquals(HttpStatus.OK, entity.getStatusCode());

    }
    
    @Then("A new user should be created with the inserted credentials")
    @Test
    public void a_new_user_should_be_created_with_the_inserted_credentials() {
      
        // User newUser = new User("user", "new_user@gmail.com", "coiso", "3467764", localRepo.findByDistrictAndCounty("Aveiro", "Aveiro"));

        // // Simple validation check
        // assertEquals(newUser.getName(), userService.getByEmail("new_user@gmail.com").getName(), "Users don't match");

        // // Clean up
        // userRepo.deleteAll();

    }
    
}
