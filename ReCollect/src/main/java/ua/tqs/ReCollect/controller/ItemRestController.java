package ua.tqs.ReCollect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.ItemService;

//API controller, WIP

@RestController
public class ItemRestController {

	@Autowired
	private ItemService itemService;

	// not working because of nested serializers shit, kinda hard to fix

	@GetMapping("/api/items")
	@ResponseBody
	public List<Item> itemIndex(@RequestParam(required = false) String category,
			@RequestParam(required = false) String owner, @RequestParam(required = false) String orderBy) {
		
		return itemService.fetchItemsApi(category, owner, orderBy);
	}
	

}