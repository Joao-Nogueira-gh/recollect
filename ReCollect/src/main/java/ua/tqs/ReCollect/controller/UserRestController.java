package ua.tqs.ReCollect.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

//API controller, WIP

@RestController()
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping(path="/api/users/")
	@ResponseBody
	public List<User> userIndex(@RequestParam(required = false) String district, @RequestParam(required = false) String county) {
		
		return userService.getUsersByLocation(district, county);
		
	}

}