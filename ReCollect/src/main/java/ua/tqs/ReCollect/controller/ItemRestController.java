package ua.tqs.ReCollect.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.ItemService;

//API controller, WIP

@RestController()
public class ItemRestController {

	@Autowired
	private ItemService itemService;

	//not working because of nested serializers shit, kinda hard to fix

	@GetMapping("/api/items/")
	@ResponseBody
	public List<Item> itemIndex(Model model) throws IOException {;
		return this.itemService.getAll();
	}
    
}