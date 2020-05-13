package ua.tqs.ReCollect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.tqs.ReCollect.Image;
import ua.tqs.ReCollect.LoginForm;
import ua.tqs.ReCollect.PictureListDto;
import ua.tqs.ReCollect.RegisterForm;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.Localizacao;
import ua.tqs.ReCollect.entity.User;
import ua.tqs.ReCollect.service.CategoryService;
import ua.tqs.ReCollect.service.ItemService;


@Controller
public class WebController {

    CategoryService categoryService;
    ItemService itemService;


    private User registeredUser;
    private User loggedUser;

    public WebController(CategoryService categoryService,ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
        //this.registeredUser = null;
        // TODO: hardoced user para não estar sempre a registar
        this.registeredUser = new User("Alex","teste","alex@email.pt", new Localizacao("Leiria", "Ansiao"));
        // TODO: só para testar, para não estar sempre a fazer login
        loggedUser = registeredUser;
        //this.resetLoggedUser();
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
        model.addAttribute("categories", categoryService.getAllCateogories());
        return "index";
    }

    @GetMapping(value = "/category")
    public String category() {
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
            this.loggedUser = this.registeredUser;
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

        if(this.registeredUser!=null){
            if(providedEmail.trim().equals(registeredUser.getEmail()) && providedPassword.trim().equals(registeredUser.getPassword())){
                System.err.println("login success!");
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
    public String userProfile() {
        return "dashboard-my-ads";
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
        model.addAttribute("categories", categoryService.getAllCateogories());

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

        for(Image im : imagesList.getImages()){
            newItem.addImage(im.getUrl());
        }

        //System.err.println("newItem recebido: "+ newItem.toString());
        //System.err.println("imagesList: " + imagesList.toString());

        loggedUser.addItem(newItem);
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
    public String productPost() {
        return "product-post";
    }

    @GetMapping(value = "/terms-conditions")
    public String termsConditions() {
        return "terms-conditions";
    }

}
