package ua.tqs.ReCollect.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.UserService;

@RestController
public class SalesRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/sold")
    @ResponseBody
    public Set<Item> soldItems(@RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return userService.getSoldItems(offset, limit);
    }

    @GetMapping("/api/on_sale")
    @ResponseBody
    public Set<Item> itemsOnSale(@RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return userService.getItemsOnSale(offset, limit);
    }

}