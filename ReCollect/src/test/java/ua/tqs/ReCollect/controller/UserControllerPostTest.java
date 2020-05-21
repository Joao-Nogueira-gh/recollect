package ua.tqs.ReCollect.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.RegisterForm;

@ExtendWith(MockitoExtension.class)
public class UserControllerPostTest {

    @Mock
    UserService service;

    @Mock
    static SecurityContextHolder sec;

    @Mock
    LocationService locService;

    @Autowired
    @InjectMocks
    UserController controller;

    @Test
    public void getHome() {

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        given(SecurityContextHolder.getContext().getAuthentication().getName()).willReturn("user@user.com");
        given(service.getByEmail("user@user.com")).willReturn(new User());
        
        controller.home();
    }

    @Test
    public void registerUser() {
        BindingResult result = mock(BindingResult.class);
        RegisterForm registerForm=new RegisterForm();
        given(locService.getLocation("Aveiro", "Aveiro")).willReturn(new Location("Aveiro", "Aveiro"));;

        registerForm.setName("user");
        registerForm.setEmail("user@user");
        registerForm.setPhone("123123123");
        registerForm.setPassword("pwd");

        registerForm.setDistrict("Aveiro");
        registerForm.setMunicipality("Aveiro");

        controller.createNewUser(registerForm, result);
    }

}