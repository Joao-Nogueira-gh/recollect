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

import ua.tqs.ReCollect.service.ReCollectItemService;
import ua.tqs.ReCollect.service.ReCollectUserService;

@Controller
public class ReCollectUserController {

	@Autowired
	private ReCollectUserService rcService;

	@RequestMapping("")
	public String index(Model model) throws IOException {

		return "index";

	}
    
}