package ua.tqs.ReCollect.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

// User controller for frontend integration

@Controller("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	static final Logger logger = Logger.getLogger(UserController.class);

	// Path for GETting the register page
	@GetMapping(path = "/users/register")
	public String registerForm(Model model) throws IOException {
		// setup for test
		userService.deleteAll();

		// flow starts here
		// create user with input, and fetch location
		User u = new User("user", "email@gmail.com", "coiso", "3467764",
				locationService.getLocation("Aveiro", "Aveiro"));
		//attach user to model
		model.addAttribute("user", u);
		System.out.println(userService.getAll());
		return "index";
	}

	// Path for registering users (or not, if it goes wrong)
	@PostMapping("/users/register")
	public boolean register(@ModelAttribute User user) {
		//user comes from getMapping model
		boolean success = userService.register(user);

		if(success) {
			//success
			logger.debug("success");
		} else {
			//email already existed
			logger.debug("nope");
		}

		// TODO: return the post-registered HTML
		return success;

	}
	// Login page form path
	@GetMapping(path = "/users/login")
	public String loginForm(Model model) throws IOException {
		//setup for test
		userService.deleteAll();
		User u=new User("registereduser", "reg@gmail.com", "coiso", "3467764", locationService.getLocation("Aveiro", "Aveiro"));
		userService.register(u);

		//flow starts here
		//get email and password input
		model.addAttribute("inputemail", "reg@gmail.com");
		model.addAttribute("inputpassword", "coiso");

		return "index";
	}

	// Login user (or not)
	@PostMapping("/users/login")
	public boolean login(@ModelAttribute String email,@ModelAttribute String pass) {
		//input comes from getMapping model

		boolean success=userService.login(email,pass);
		if (success){
			//logged in
			System.out.println("logged");
		}
		else{
			//wrong email/pass
			System.out.println("nope");
		}
		//this variable in the user service holds the current logged in user (or null)
		//access it whenever needed
		System.out.println(userService.getCurrentUser());

		//also, logout feature
		userService.logout();

		// return home page or not if login failed
		return success;

	}
    
}