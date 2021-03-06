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
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.RegisterForm;

// User controller for frontend integration

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    static final Logger logger = Logger.getLogger(UserController.class);

    private static final String REGISTER = "register";

    @GetMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(REGISTER);
        modelAndView.addObject("registo", new RegisterForm());
        modelAndView.addObject("cidades", locationService.getCountiesByDistrict());
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public ModelAndView createNewUser(@Valid RegisterForm form, BindingResult bindingResult) {

        User user = new User(form.getName(), form.getEmail(), form.getPassword(), form.getPhone(),
                locationService.getLocation(form.getDistrict(), form.getMunicipality()));

        ModelAndView modelAndView = new ModelAndView();

        if (userService.userExists(user.getEmail())) {
            bindingResult.rejectValue("name", "error.user",
                    "There is already a user registered with the email provided");
            modelAndView.addObject("registerMessage", "That email is already in use!");
            modelAndView.addObject("registerBoolean", "false");
        }
        if (!bindingResult.hasErrors()) {
            userService.register(user);
            modelAndView.addObject("registerMessage", "User registered successfully!");
            modelAndView.addObject("registerBoolean", "true");
        }

        modelAndView.addObject("registo", new RegisterForm());
        modelAndView.addObject("cidades", locationService.getCountiesByDistrict());

        modelAndView.setViewName(REGISTER);
        return modelAndView;
    }

    @GetMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
