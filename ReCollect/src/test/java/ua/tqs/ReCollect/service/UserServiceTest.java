package ua.tqs.ReCollect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.model.UserDTO;
import ua.tqs.ReCollect.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository rcRepository;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @InjectMocks
    private UserService sutRCService;

    // Set ups and cleanups

    @BeforeEach
    public void setUp() {

        rcRepository.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        rcRepository.deleteAll();

    }

    //

    @Test
    void registerNewCredentials_UserIsRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));
        ArrayList<User> all = new ArrayList<>();
        all.add(user);

        assertTrue(sutRCService.register(user));

        given(rcRepository.existsByEmail("user@email.com")).willReturn(true);
        given(rcRepository.findByEmail("user@email.com")).willReturn(user);
        given(rcRepository.findAll()).willReturn(all);
        
        assertTrue(sutRCService.userExists("user@email.com"));
        assertEquals(sutRCService.getByEmail("user@email.com"), user, "Users dont match");
        assertEquals(sutRCService.getAll(), all, "DB Dump doesnt match");

        // Testing DB interaction
        // Checking for NullPtrs and stuff alike
        sutRCService.deleteAll();

    }

    @Test
    void registerAlreadyExistingCredentials_UserIsNotRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        given(rcRepository.existsByEmail("user@email.com")).willReturn(true);
        
        assertFalse(sutRCService.register(user));

    }

    @Test
    void convertToDto(){

        User user = new User("user", "mail@email.com", "pwd", "321321321", new Location("Aveiro", "Aveiro"));

        user.addFavItem(new Item());
        user.addPublishedItem(new Item());
        user.addSoldItem(new Item());

        UserDTO userDTO = sutRCService.convertToDTO(user);

        User convertedBack = sutRCService.convertToUser(userDTO);

        assertEquals(user.getName(), convertedBack.getName());

    }

}