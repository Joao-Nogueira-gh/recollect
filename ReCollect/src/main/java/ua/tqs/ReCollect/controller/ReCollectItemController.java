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

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.items.Coin;
import ua.tqs.ReCollect.service.ReCollectItemService;

@Controller
public class ReCollectItemController {

	@Autowired
	private ReCollectItemService rcService;

	@RequestMapping("")
	public String index(Model model) throws IOException {
		rcService.deleteAll();
		rcService.save(new Coin("moeda", 1, new BigDecimal(5.5), "moeda rara"));
		List<Item> x = rcService.getAll();
		int len = x.size();
		String l=String.valueOf(len);
		String l2="3";
		model.addAttribute("test", l);

		return "index";
	}
    
}