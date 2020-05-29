package ua.tqs.ReCollect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

@RestController()
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/api/user/{id}")
	@ResponseBody
	public User singleUser(@PathVariable(value = "id") Long id) {

		return userService.getById(id);

	}

	@GetMapping(path = "/api/users/")
	@ResponseBody
	public List<User> userIndex(@RequestParam(required = false) String district,
			@RequestParam(required = false) String county, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) Integer offset) {

		return userService.getUsersByLocation(district, county, limit, offset);

	}

}