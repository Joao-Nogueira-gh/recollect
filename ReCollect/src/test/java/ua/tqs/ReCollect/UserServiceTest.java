package ua.tqs.ReCollect;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository rcRepository;

    @InjectMocks
    private UserService sutRCService;

    @BeforeEach
    public void setUp() {

        rcRepository.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        rcRepository.deleteAll();

    }
    

    @Test
    public void registerNewCredentials_UserIsRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        sutRCService.register(user);

        given(rcRepository.findByEmail("user@email.com")).willReturn(user);
        
        assertTrue(sutRCService.emailInUse("user@email.com"));

    }

    @Test
    public void registerAlreadyExistingCredentials_UserIsNotRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        given(rcRepository.findByEmail("user@email.com")).willReturn(user);
        
        assertFalse(sutRCService.register(user));

    }

    @Test
    public void loginExistingUser(){
        String email="exist@gmail.com";
        String pass="exist";

        User user = new User("User123", email, pass, "123123123", new Location("Aveiro", "Aveiro"));

        sutRCService.register(user);

        given(rcRepository.findByEmail(email)).willReturn(user);

        assertTrue(sutRCService.checkUserPassword(user,pass));

        assertTrue(sutRCService.login(email,pass));

    }
    
    @Test
    public void loginNonExistingUser(){
        String email="some@gmail.com";
        String pass="sdf";

        User user = new User("User123", "sfa@gmail.com", "safdgf", "123123123", new Location("Aveiro", "Aveiro"));

        sutRCService.register(user);

        given(rcRepository.findByEmail(email)).willReturn(null);

        assertFalse(sutRCService.login(email,pass));

    }

    
}