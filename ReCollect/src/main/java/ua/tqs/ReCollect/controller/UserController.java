package ua.tqs.ReCollect.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

@RestController("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(path="/api/users/", method=RequestMethod.GET)
	public List<User> userIndex(Model model) throws IOException {

		return this.userService.getAll();

	}

	@RequestMapping(path="/api/users/register", method=RequestMethod.POST)
	public void registerUser(User user) {

		this.userService.register(user);
		
	}
    
}