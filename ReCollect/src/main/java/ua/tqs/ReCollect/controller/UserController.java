package ua.tqs.ReCollect.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String register(Model model) throws IOException {

		// setup for test
		userService.deleteAll();
		// flow starts here
		// create user with input, and fetch location
		User u = new User("user", "email@gmail.com", "coiso", "3467764",
				locationService.getLocation("Aveiro", "Aveiro"));

		model.addAttribute("test", "users");
		System.out.println(userService.getAll());
		return "index";
	}

	// Path for registering users (possibly switch to @ModelAttribute later)
	@PostMapping("/users/register")
	@ResponseBody
	public boolean register(@RequestBody User user) {

		boolean success = userService.register(user);

		if(success) {
			//use register method to save user
			logger.debug("successs");
		} else {
			//email already existed
			logger.debug("nope");
		}

		// TODO: return the post-registered HTML
		return success;

	}
	


	//example path
	@GetMapping(path="/users/login")
	public String login(Model model) throws IOException {
		//placeholder for next feature :)
		return "index";
	}
    
}