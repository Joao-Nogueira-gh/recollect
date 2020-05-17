package ua.tqs.ReCollect.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.Image;
import ua.tqs.ReCollect.utils.LoginForm;
import ua.tqs.ReCollect.utils.PictureListDto;
import ua.tqs.ReCollect.utils.RegisterForm;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.Localizacao;
import ua.tqs.ReCollect.entity.User;
import ua.tqs.ReCollect.service.CategoryService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.entity.Comment;
import ua.tqs.ReCollect.utils.SearchParams;

import java.util.List;
import java.util.Set;


@Controller
public class WebController {


    CategoryService categoryService;
    ItemService itemService;
    UserService userService;


    private User registeredUser;
    private User loggedUser;

    private List<User> allUsers;

    public WebController(CategoryService categoryService,ItemService itemService, UserService userService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.userService = userService;
        this.registeredUser = null;
        this.allUsers = userService.getAllUsers();
        // TODO: hardoced user para não estar sempre a registar
        //this.registeredUser = new User("Alex","teste","alex@email.pt", new Localizacao("Leiria", "Ansiao"));
        // TODO: só para testar, para não estar sempre a fazer login
       // loggedUser = registeredUser;
        this.resetLoggedUser();
    }

    public void resetLoggedUser(){
        this.loggedUser = null;
    }

    public void saveLoggedUserChanges(){
        this.registeredUser = loggedUser;
    }

    public boolean aUserIsLogged(){
        return !(this.loggedUser == null);
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("item", new Item("Moeda", 9.99, 2));
        model.addAttribute("categories", categoryService.getAllCategories());
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
    public String login(Model model, @RequestParam(name = "success", required = false) boolean success, @RequestParam(name = "showError", required = false, defaultValue = "false") boolean showError) {
        if(aUserIsLogged()){
            return "redirect:/profile";
        }

        System.err.println("success na entrada: " + success);
        System.err.println("showError na entrada: " + showError);

        model.addAttribute("success", success);
        model.addAttribute("showError", showError);
        model.addAttribute("loginForm", new LoginForm());
        if(success){
            return "redirect:/profile";
        }
        else{
            return "login";
        }

    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute LoginForm loginForm, RedirectAttributes ra) {
        String providedEmail = loginForm.getEmail();
        String providedPassword = loginForm.getPassword();
        System.err.println("providedEmail -> " + providedEmail);
        System.err.println("providedPassword -> " + providedPassword);

        User userFromDB = userService.getUserByEmail(providedEmail);
        System.err.println("userfromDB -> " + userFromDB);

        if(userFromDB!=null){
            if(providedPassword.trim().equals(userFromDB.getPassword())){
                System.err.println("login success!");
                this.loggedUser = userFromDB;
                ra.addAttribute("success", true);
            }
            else{
                System.err.println("login error!");
                ra.addAttribute("showError", true);
                ra.addAttribute("success", false);
            }
        }
        else{
            System.err.println("login error!");
            ra.addAttribute("showError", true);
            ra.addAttribute("success", false);
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        saveLoggedUserChanges();
        this.resetLoggedUser();
        return "index";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("registo", new RegisterForm());
        return "register";
    }

    @PostMapping(value = "/register")
    public String submitRegistation(@ModelAttribute RegisterForm registerForm) {
        Localizacao localizacao = new Localizacao(registerForm.getDistrict(), registerForm.getMunicipality());
        this.registeredUser = new User(registerForm.getName(),registerForm.getPassword(),registerForm.getEmail(), localizacao);
        System.err.println("user inserido: " + registeredUser.toString());
        return "redirect:/login";
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
    public String userProfile(Model model) {

        Set<Item> allItems = this.loggedUser.getItensPublicados();

        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", this.loggedUser);

        return "dashboard-my-ads";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String postItem(Model model, @PathVariable(name = "id") Long id, RedirectAttributes ra) {

        // TODO: verificar se o loggedUser está logged in



        System.err.println("ID para delete: " + id);

        this.loggedUser.removeItemPublicado(id);
        System.err.println("1. loggedUser: " + this.loggedUser);
        userService.updateUser(this.loggedUser);
        System.err.println("2.-----");
        itemService.deleteItem(id);
        System.err.println("3.-----");

        Set<Item> allItems = this.loggedUser.getItensPublicados();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", this.loggedUser);
        System.err.println("userItems: " + model.getAttribute("userItems"));
        System.err.println("user: " + model.getAttribute("loggedUser"));

        return "redirect:/profile";
    }

    @GetMapping(value = "/about")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping(value = "/announce")
    public String addItem(Model model, @RequestParam(name = "submitted", required = false) boolean submitted) {
        if(!aUserIsLogged()){
            return "redirect:/login";
        }


        model.addAttribute("submitted", submitted);
        model.addAttribute("categories", categoryService.getAllCategories());

        PictureListDto pictureListForm = new PictureListDto();

        // 5 images, at max
        for (int i = 1; i <= 5; i++) {
            pictureListForm.addImage(new Image());
        }

        model.addAttribute("imagesList",pictureListForm);
        model.addAttribute("newItem", new Item());
        return "add-item";
    }

    @PostMapping(value = "/announce")
    public String postItem(@ModelAttribute Item newItem, @ModelAttribute PictureListDto imagesList, RedirectAttributes ra) {

        System.err.println("1!");
        for(Image im : imagesList.getImages()){
            newItem.addImage(im.getUrl());
        }
        System.err.println("2!");
        //System.err.println("newItem recebido: "+ newItem.toString());
        //System.err.println("imagesList: " + imagesList.toString());
        System.err.println("loggerUser antes do add: " + this.loggedUser.toString());
        itemService.save(newItem);
        System.err.println("3!");
        this.loggedUser.addItem(newItem);
        System.err.println("4!");
        userService.updateUser(this.loggedUser);
        System.err.println("5!");
        ra.addAttribute("submitted", true);

        System.err.println("Item submetido: " + newItem.toString());

        return "redirect:/announce";
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
        i.addComment(new Comment("Alexandre Lopes", "Cool product."));
        model.addAttribute("item", i);
        model.addAttribute("searchparams", new SearchParams());

        return "product-post";
    }

    @GetMapping(value = "/terms-conditions")
    public String termsConditions() {
        return "terms-conditions";
    }

}
