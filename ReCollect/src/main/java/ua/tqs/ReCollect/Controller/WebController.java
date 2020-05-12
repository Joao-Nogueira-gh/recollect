package ua.tqs.ReCollect.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.tqs.ReCollect.entity.Item;

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

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/ad-listing")
    public String adListing() {
        return "Ad-listing";
    }

    @GetMapping(value = "/ad-list-view")
    public String adListView() {
        return "ad-list-view";
    }

    @GetMapping(value = "/edit-profile")
    public String editProfile() {
        return "edit-profile";
    }

    @GetMapping(value = "/profile")
    public String userProfile() {
        return "dashboard-my-ads";
    }

    @GetMapping(value = "/about")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping(value = "/announce")
    public String addItem() {
        return "add-item";
    }


    @GetMapping(value = "/favourites")
    public String favouritesAds() {
        return "dashboard-favourite-ads";
    }

    @GetMapping(value = "/sold-items")
    public String soldItems() {
        return "dashboard-sold-items";
    }

    @GetMapping(value = "/product")
    public String productPost() {
        return "product-post";
    }

    @GetMapping(value = "/terms-conditions")
    public String termsConditions() {
        return "terms-conditions";
    }

}
