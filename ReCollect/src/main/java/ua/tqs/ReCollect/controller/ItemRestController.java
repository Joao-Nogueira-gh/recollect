package ua.tqs.ReCollect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.ItemService;

@RestController
public class ItemRestController {

	@Autowired
	private ItemService itemService;

	@GetMapping("/api/item/{id}")
	@ResponseBody
	public Item singleItem(@PathVariable Long id) {

		return itemService.getSingleItem(id);

	}

	@GetMapping("/api/items")
	@ResponseBody
	public List<Item> itemIndex(@RequestParam(required = false) String category,
			@RequestParam(required = false) String owner, @RequestParam(required = false) String orderBy,
			@RequestParam(required = false) Integer limit) {

		String processedOwner;

		if (owner != null) {
			processedOwner = owner.replace("'", "");
		} else {
			processedOwner = owner;
		}

		System.out.println(limit);

		return itemService.fetchItemsApi(category, processedOwner, orderBy, limit);
	}

}