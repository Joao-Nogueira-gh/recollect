package ua.tqs.ReCollect.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
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

	static final Logger logger = Logger.getLogger(TestController.class);

	private static final String index_page = "index";

	@GetMapping("")
	public String index(Model model) {

		userService.deleteAll();
		User u=new User("user", "email@gmail.com", "pass", "3467764", locationService.getLocation("Aveiro", "Aveiro"));
		userService.save(u);

		itemService.deleteAll();
		Item i=new Item("moeda", 1, BigDecimal.valueOf(5.5), "moeda rara", Categories.MISC);
		i.setOwner(u);
		itemService.save(i);

		commentService.deleteAll();
		Comment com=new Comment("comment", u, i);
		commentService.save(com);

		logger.debug(u);
		logger.debug(i);
		logger.debug(com);

		model.addAttribute("test", "loaded some data");

		return index_page;
	}

	@GetMapping("/test")
	public String index2(Model model) {

		commentService.deleteAll();
		logger.debug(userService.getAll());

		userService.deleteAll();
		//logger.debug(commentService.getAll());
		Item i=new Item("moeda", 1, BigDecimal.valueOf(5.5), "moeda rara", Categories.MISC);
		itemService.save(i);
		
		User u=new User("user", "t@gmail.com", "coiso", "3467764", null);
		userService.save(u);
		Comment com=new Comment("texto", u, i);
		commentService.save(com);
		logger.debug(commentService.getAll());
		logger.debug(userService.getAll());
		
		model.addAttribute("test", "test");
		logger.debug(userService.getCurrentUser());

		return index_page;
	}

    @GetMapping("/testUsers")
	public String index3(Model model) {

		logger.debug(userService.getAll());
		return index_page;
	}
}