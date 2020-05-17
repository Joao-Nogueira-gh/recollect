package ua.tqs.ReCollect.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

//API controller, WIP

@RestController()
public class UserRestController {

	@Autowired
	private UserService userService;

	//not working because of nested serializers shit, kinda hard to fix

	@GetMapping(path="/api/users/")
	public List<User> userIndex(Model model) throws IOException {
		System.out.println(userService.getAll());
		return userService.getAll();

	}
    
}