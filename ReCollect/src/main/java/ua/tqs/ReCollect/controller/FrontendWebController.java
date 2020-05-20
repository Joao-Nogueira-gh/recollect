package ua.tqs.ReCollect.controller;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.Category;
import ua.tqs.ReCollect.utils.Image;
import ua.tqs.ReCollect.utils.ItemForm;
import ua.tqs.ReCollect.utils.LoginForm;
import ua.tqs.ReCollect.utils.PictureListDto;
import ua.tqs.ReCollect.utils.SearchParams;


@Controller
public class FrontendWebController {

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    ArrayList<Category> categories = Category.getCategories();

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", categories);
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

    // @PostMapping(value = "/login")
    // public String login(@ModelAttribute LoginForm loginForm, RedirectAttributes ra) {
    //     String providedEmail = loginForm.getEmail();
    //     String providedPassword = loginForm.getPassword();
    //     System.err.println("providedEmail -> " + providedEmail);
    //     System.err.println("providedPassword -> " + providedPassword);


    //     User userFromDB = userService.getByEmail(providedEmail);

    //     // TODO: só para testes
    //     //--------------------------
    //     userFromDB = userService.getByEmail("alex@email.pt");
    //     providedEmail = "alex@email.pt";
    //     providedPassword = "pass";
    //     //--------------------------

    //     System.err.println("userfromDB -> " + userFromDB);

    //     if(userFromDB!=null){
    //         if(providedPassword.trim().equals(userFromDB.getPassword())){
    //             System.err.println("login success!");
    //             ra.addAttribute("success", true);
    //         }
    //         else{
    //             System.err.println("login error!");
    //             ra.addAttribute("showError", true);
    //             ra.addAttribute("success", false);
    //         }
    //     }
    //     else{
    //         System.err.println("login error!");
    //         ra.addAttribute("showError", true);
    //         ra.addAttribute("success", false);
    //     }

    //     return "redirect:/login";
    // }


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
    public String userProfile(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getByEmail(auth.getName());

        Set<Item> allItems = loggedUser.getPublishedItems();

        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard-my-ads";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String deleteItem(Model model, @PathVariable(name = "id") Long id) {

        User loggedUser = this.getLoggedUser();

        System.err.println("ID para delete: " + id);

        // Item deleted = itemService.getItemById(id);

        // loggedUser.removeItemPublicado(deleted);
        // System.err.println("1. loggedUser: " + loggedUser);
        // userService.updateUser(loggedUser);
        // System.err.println("2.-----");
        // itemService.deleteItem(id);
        // System.err.println("3.-----");

        Set<Item> allItems = loggedUser.getPublishedItems();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", loggedUser);
        System.err.println("userItems: " + model.getAttribute("userItems"));
        System.err.println("user: " + model.getAttribute("loggedUser"));

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/marksold/{id}")
    public String markAsSoldItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        System.err.println("ID para sold: " + id);

        /*
        * - tirar dos publicados
        * - meter nos vendidos
        * - owner do item a null
        * - seller do item ele próprio
        * */

        // Item sold = itemService.getItemById(id);
        // this.getLoggedUser().removeItemPublicado(sold);
        // sold.setOwner(null);
        // sold.setSeller(this.getLoggedUser().getId());
        // this.getLoggedUser().addSoldItem(sold);
        // System.err.println("1. loggedUser: " + this.getLoggedUser());
        // userService.updateUser(this.getLoggedUser());

        // System.err.println("2.-----");
        // itemService.updateItem(sold);
        // System.err.println("3.-----");

        Set<Item> allItems = this.getLoggedUser().getPublishedItems();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", this.getLoggedUser());
        System.err.println("userItems: " + model.getAttribute("userItems"));
        System.err.println("user: " + model.getAttribute("loggedUser"));

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/deleteSold/{id}")
    public String deleteSoldItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        System.err.println("ID para delete: " + id);

        // Item deleted = itemService.getItemById(id);

        // this.getLoggedUser().removeSoldItem(deleted);
        // System.err.println("1. loggedUser: " + this.getLoggedUser());
        // userService.updateUser(this.getLoggedUser());
        // System.err.println("2.-----");
        // itemService.deleteItem(id);
        // System.err.println("3.-----");

        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userSoldItems", allItems);
        model.addAttribute("loggedUser", this.getLoggedUser());
        System.err.println("userItems: " + model.getAttribute("userItems"));
        System.err.println("user: " + model.getAttribute("loggedUser"));

        return "redirect:/sold-items";
    }


    @GetMapping(value = "/about")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping(value = "/announce")
    public String addItem(ItemForm itemForm, Model model, @RequestParam(name = "submitted", required = false) boolean submitted,
                          @RequestParam(name = "noImages", required = false) boolean noImages,
                          @RequestParam(name = "hasErrors", required = false) boolean hasErrors) {
        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        model.addAttribute("hasErrors", hasErrors);
        model.addAttribute("noImages", noImages);
        model.addAttribute("submitted", submitted);
        model.addAttribute("categories", categories);

        PictureListDto pictureListForm = new PictureListDto();

        // 5 images, at max
        for (int i = 1; i <= 5; i++) {
            pictureListForm.addImage(new Image());
        }

        model.addAttribute("imagesList",pictureListForm);
        //model.addAttribute("itemForm", new ItemForm());
        return "add-item";
    }

    @PostMapping(value = "/announce")
    public String postItem(@Valid ItemForm itemForm, BindingResult bindingResult, @ModelAttribute PictureListDto imagesList, RedirectAttributes ra) {

        if(bindingResult.hasErrors()){
            // if the provided data is invalid
            System.err.println("INPUTS INVALIDOS!");
            ra.addAttribute("submitted", false);
            ra.addAttribute("hasErrors", true);
            return "redirect:/announce";
        }

        int emptyEntries = 0;
        for(Image im : imagesList.getImages()){
            if(im.getUrl().trim().equals(""))
                emptyEntries+=1; // count empty entries
        }

        // if all images urls are empty strings
        if(emptyEntries==imagesList.getImages().size()){
            ra.addAttribute("noImages", true);
            ra.addAttribute("submitted", false);
            return "redirect:/announce";
        }

        // setup item with valid provided data
        // Item newItem = new Item();
        // newItem.setNome(itemForm.getNome());
        // newItem.setCategoria(itemForm.getCategoria());
        // newItem.setDescricao(itemForm.getDescricao());
        // newItem.setPreco(itemForm.getPreco());
        // newItem.setQuantidade(itemForm.getQuantidade());

        // System.err.println("1!");
        // for(Image im : imagesList.getImages()){
        //     if(im.getUrl().trim().equals(""))
        //         continue; // skip empty inputs
        //     newItem.addImage(im.getUrl());
        // }
        // newItem.setOwner(this.getLoggedUser().getId());
        // System.err.println("2!");
        // //System.err.println("newItem recebido: "+ newItem.toString());
        // //System.err.println("imagesList: " + imagesList.toString());
        // System.err.println("loggerUser antes do add: " + this.getLoggedUser().toString());
        // itemService.save(newItem);
        // System.err.println("3!");
        // this.getLoggedUser().addItem(newItem);
        // System.err.println("4!");
        // userService.updateUser(this.getLoggedUser());
        // System.err.println("5!");
        // ra.addAttribute("submitted", true);

        // System.err.println("Item submetido: " + newItem.toString());

        return "redirect:/announce";
    }


    @GetMapping(value = "/favourites")
    public String favouritesAds() {
        return "dashboard-favourite-ads";
    }

    @GetMapping(value = "/sold-items")
    public String soldItems(Model model) {
        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        model.addAttribute("userSoldItems", allItems);
        model.addAttribute("loggedUser", getLoggedUser());

        return "dashboard-sold-items";
    }

    @GetMapping(value = "/product")
    public String productPost(Model model) {

        // Item i = new Item("Hp Dual Core 2gb Ram-Slim Laptop Available In Very Low Price", "Only three of these were made!",2009.99, 1);
        // i.addComment(new Comment("André Amarante", "Always wanted one!"));
        // i.addComment(new Comment("Joana Silva", "Very high price. Would you be willing to lower it?"));
        // model.addAttribute("item", i);
        model.addAttribute("searchparams", new SearchParams());

        return "product-post";
    }

    @PostMapping(value = "/product")
    public String productComment(@ModelAttribute String s, BindingResult result, ModelMap model) {

        // Item i = new Item("Hp Dual Core 2gb Ram-Slim Laptop Available In Very Low Price", "Only three of these were made!",2009.99, 1);
        // i.addComment(new Comment("André Amarante", "Always wanted one!"));
        // i.addComment(new Comment("Joana Silva", "Very high price. Would you be willing to lower it?"));
        // i.addComment(new Comment("Alexandre Lopes", "Cool product."));
        // model.addAttribute("item", i);
        model.addAttribute("searchparams", new SearchParams());

        return "product-post";
    }

    @GetMapping(value = "/terms-conditions")
    public String termsConditions() {
        return "terms-conditions";
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getByEmail(auth.getName());
        return loggedUser;
    }

}
