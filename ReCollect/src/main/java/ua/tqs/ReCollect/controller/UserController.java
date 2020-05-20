package ua.tqs.ReCollect.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.model.UserDTO;
import ua.tqs.ReCollect.service.UserService;

// User controller for frontend integration

@Controller
public class UserController {

	@Autowired
	private UserService userService;

    static final Logger logger = Logger.getLogger(UserController.class);
    
    private static final String registrationPage = "registration";


    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(registrationPage);
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid UserDTO userdto, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
		 
        if (userService.userExists(userdto.getEmail())) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (!bindingResult.hasErrors()) {
            User user=userService.convertToUser(userdto);
            userService.register(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
        }

        modelAndView.setViewName(registrationPage);

        return modelAndView;
    }

    @GetMapping(value="/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    
}
