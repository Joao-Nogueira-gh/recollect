package ua.tqs.ReCollect.Controller;

import com.example.demo.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("item", new Item("Moeda", 9.99, 2));
        return "index";
    }

    @GetMapping(value = "/category")
    public String category() {
        return "category";
    }
}
