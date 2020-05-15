package ua.tqs.ReCollect.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.ItemService;

@RestController("/api/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/api/items/")
	@ResponseBody
	public List<Item> itemIndex(Model model) throws IOException {;
		return this.itemService.getAll();
	}
    
}