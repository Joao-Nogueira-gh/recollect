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
    private UserRepository userRepo;

    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    // Set ups and cleanups

    @BeforeEach
    public void setUp() {

        userRepo.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        userRepo.deleteAll();

    }

    //

    @Test
    void registerNewCredentials_UserIsRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));
        ArrayList<User> all = new ArrayList<>();
        all.add(user);

        assertTrue(userService.register(user));

        given(userRepo.existsByEmail("user@email.com")).willReturn(true);
        given(userRepo.findByEmail("user@email.com")).willReturn(user);
        given(userRepo.findAll()).willReturn(all);
        
        assertTrue(userService.userExists("user@email.com"));
        assertEquals(userService.getByEmail("user@email.com"), user, "Users dont match");
        assertEquals(userService.getAll(), all, "DB Dump doesnt match");

        // Testing DB interaction
        // Checking for NullPtrs and stuff alike
        userService.deleteAll();

    }

    @Test
    void registerAlreadyExistingCredentials_UserIsNotRegistered(){

        User user = new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD"));

        given(userRepo.existsByEmail("user@email.com")).willReturn(true);
        
        assertFalse(userService.register(user));

    }

    @Test
    void convertToDto(){

        User user = new User("user", "mail@email.com", "pwd", "321321321", new Location("Aveiro", "Aveiro"));

        user.addFavItem(new Item());
        user.addPublishedItem(new Item());
        user.addSoldItem(new Item());

        UserDTO userDTO = userService.convertToDTO(user);

        User convertedBack = userService.convertToUser(userDTO);

        assertEquals(user.getName(), convertedBack.getName());

    }

}