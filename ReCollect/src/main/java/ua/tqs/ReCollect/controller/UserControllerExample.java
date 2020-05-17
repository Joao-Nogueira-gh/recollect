package ua.tqs.ReCollect.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.tqs.ReCollect.exceptions.EmailAlreadyInUseException;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

//example user controller for frontend integration

@Controller("/users")
public class UserControllerExample {

	@Autowired
	private UserService userService;

	@Autowired
	private LocationService locationService;

	//example path
	@GetMapping(path="/users/register")
	public String register(Model model) throws IOException {
		//setup for test
		userService.deleteAll();
		//flow starts here
		//create user with input, and fetch location
		User u=new User("user", "email@gmail.com", "coiso", "3467764", locationService.getLocation("Aveiro", "Aveiro"));
		try {
			//use register method to save user
			userService.register(u);
			System.out.println("successs");
		} catch (EmailAlreadyInUseException e) {
			//email already existed
			System.out.println("nope");
		}

		model.addAttribute("test", "users");
		System.out.println(userService.getAll());
		return "index";
	}
	//example path
	@GetMapping(path="/users/login")
	public String login(Model model) throws IOException {
		//placeholder for next feature :)
		return "index";
	}
    
}