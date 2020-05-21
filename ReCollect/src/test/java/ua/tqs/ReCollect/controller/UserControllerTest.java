package ua.tqs.ReCollect.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import ua.tqs.ReCollect.repository.LocationRepository;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

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

	@Test
	public void getLoginPageandGoHome() throws Exception {
        
        String login = this.restTemplate.getForObject("/login", String.class);

        assertTrue(login.contains("Login Page"));
        
    }
    //TODO FIX
    //neither registration or register works
    // @Test
	// public void getRegistrationPage() throws Exception {
        
    //     String login = this.restTemplate.getForObject("/register", String.class);

    //     assertTrue(login.contains("Registration Form"));
        
    // }
    
}