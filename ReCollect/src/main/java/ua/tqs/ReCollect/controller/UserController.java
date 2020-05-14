package ua.tqs.ReCollect.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

@Controller("/api/users")
public class UserController {

	@Autowired
	private UserService rcService;

	@RequestMapping(path="/api/users/index", method=RequestMethod.GET)
	public String userIndex(Model model) throws IOException {

		return "index";

	}

	@RequestMapping(path="/api/users/register", method=RequestMethod.POST)
	public void registerUser(User user) {

		this.rcService.register(user);
		
	}
    
}