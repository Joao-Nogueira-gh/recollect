package ua.tqs.ReCollect.Controller;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.tqs.ReCollect.entity.Comment;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.SearchParams;


@Controller
public class WebController {

    @GetMapping(value = "/")
    public String home(Model model) {
        Item i = new Item("Moeda", 9.99, 2);
        model.addAttribute("item", i);

        return "index";
    }

    @GetMapping(value = "/category")
    public String category() {
        return "category";
    }

    @PostMapping(value = "/category")
    public String categorysearch(@ModelAttribute SearchParams searchparams, BindingResult result, ModelMap model) {
        System.out.println("Selected category: " + searchparams.getCategory());

        model.addAttribute("category", searchparams.getCategory());

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
    public String productPost(Model model) {

        Item i = new Item("Hp Dual Core 2gb Ram-Slim Laptop Available In Very Low Price", "Only three of these were made!",2009.99, 1);
        i.addComment(new Comment("André Amarante", "Always wanted one!"));
        i.addComment(new Comment("Joana Silva", "Very high price. Would you be willing to lower it?"));
        model.addAttribute("item", i);
        model.addAttribute("searchparams", new SearchParams());

        return "product-post";
    }

    @PostMapping(value = "/product")
    public String productComment(@ModelAttribute String s, BindingResult result, ModelMap model) {

        Item i = new Item("Hp Dual Core 2gb Ram-Slim Laptop Available In Very Low Price", "Only three of these were made!",2009.99, 1);
        i.addComment(new Comment("André Amarante", "Always wanted one!"));
        i.addComment(new Comment("Joana Silva", "Very high price. Would you be willing to lower it?"));
        i.addComment(new Comment("André Amarante", "Cool product."));
        model.addAttribute("item", i);
        model.addAttribute("searchparams", new SearchParams());

        return "product-post";
    }

    @GetMapping(value = "/terms-conditions")
    public String termsConditions() {
        return "terms-conditions";
    }

}
