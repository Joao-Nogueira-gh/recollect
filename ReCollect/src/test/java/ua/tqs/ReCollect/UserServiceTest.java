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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.UserService;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository rcRepository;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

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
    void registerNewCredentials_UserIsRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        sutRCService.register(user);

        given(rcRepository.existsByEmail("user@email.com")).willReturn(true);
        
        assertTrue(sutRCService.userExists("user@email.com"));

    }

    @Test
    void registerAlreadyExistingCredentials_UserIsNotRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        given(rcRepository.existsByEmail("user@email.com")).willReturn(true);
        
        assertFalse(sutRCService.register(user));

    }

    @Test
    void loginExistingUser(){
        String email="exist@gmail.com";
        String pass="exist";

        given(mockBCryptPasswordEncoder.encode(pass)).willReturn("SHA512(exist)");

        User user = new User("User123", email, pass, "123123123", new Location("Aveiro", "Aveiro"));

        sutRCService.register(user);

        given(rcRepository.findByEmail(email)).willReturn(user);

        assertTrue(sutRCService.checkUserPassword(user, pass));
        assertTrue(sutRCService.login(email, pass));

    }

    @Test
    void loginNonExistingUser(){
        String email="some@gmail.com";
        String pass="sdf";

        User user = new User("User123", "sfa@gmail.com", "safdgf", "123123123", new Location("Aveiro", "Aveiro"));

        sutRCService.register(user);

        given(rcRepository.findByEmail(email)).willReturn(null);

        assertFalse(sutRCService.login(email,pass));

    }

    
}