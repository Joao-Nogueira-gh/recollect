package ua.tqs.ReCollect.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.CommentService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;

//controller for entity testing

@Controller
public class TestController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@GetMapping("")
	public String index(Model model) throws IOException {
		itemService.deleteAll();
		Item i=new Item("moeda", 1, BigDecimal.valueOf(5.5), "moeda rara", Categories.MISC);
		itemService.save(i);
		System.out.println(i);

		userService.deleteAll();
		locationService.deleteAll();
		Location loc=new Location("xd", "xd2");
		locationService.save(loc);
		System.out.println(loc);

		userService.deleteAll();
		User u=new User("user", "email@gmail.com", "coiso", "3467764", loc);
		userService.save(u);
		System.out.println(u);

		commentService.deleteAll();
		Comment com=new Comment("comment", u, i);
		commentService.save(com);
		System.out.println(com);

		List<Item> x = itemService.getAll();
		int len = x.size();
		model.addAttribute("test", len);

		return "index";
	}
	@GetMapping("/test")
	public String index2(Model model) throws IOException {

		commentService.deleteAll();
		System.out.println(userService.getAll());

		userService.deleteAll();
		//System.out.println(commentService.getAll());
		Item i=new Item("moeda", 1, BigDecimal.valueOf(5.5), "moeda rara", Categories.MISC);
		itemService.save(i);
		
		User u=new User("user", "t@gmail.com", "coiso", "3467764", null);
		userService.save(u);
		Comment com=new Comment("texto", u, i);
		commentService.save(com);
		System.out.println(commentService.getAll());
		System.out.println(userService.getAll());
		
		model.addAttribute("test", "test");

		return "index";
	}
    
}