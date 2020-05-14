package ua.tqs.ReCollect;

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
import ua.tqs.ReCollect.repository.ReCollectUserRepository;
import ua.tqs.ReCollect.service.ReCollectUserService;

@ExtendWith(MockitoExtension.class)
public class ReCollectUserServiceTest {

    @Mock
    private ReCollectUserRepository rcRepository;

    @InjectMocks
    private ReCollectUserService sutRCService;

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

        given(rcRepository.findUserByEmail("user@email.com")).willReturn(user);
        
        assertTrue(sutRCService.emailInUse("user@email.com"));

    }

    
}