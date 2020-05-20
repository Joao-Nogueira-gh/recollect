package ua.tqs.ReCollect.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.*;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.Localizacao;
import ua.tqs.ReCollect.entity.User;
import ua.tqs.ReCollect.service.CategoryService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.entity.Comment;

import javax.validation.Valid;
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

        // TODO: só para testes
        //--------------------------
        userFromDB = userService.getUserByEmail("alex@email.pt");
        providedEmail = "alex@email.pt";
        providedPassword = "pass";
        //--------------------------

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
    public String deleteItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        System.err.println("ID para delete: " + id);

        Item deleted = itemService.getItemById(id);

        this.loggedUser.removeItemPublicado(deleted);
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

        Item sold = itemService.getItemById(id);
        this.loggedUser.removeItemPublicado(sold);
        sold.setOwner(null);
        sold.setSeller(this.loggedUser.getId());
        this.loggedUser.addSoldItem(sold);
        System.err.println("1. loggedUser: " + this.loggedUser);
        userService.updateUser(this.loggedUser);

        System.err.println("2.-----");
        itemService.updateItem(sold);
        System.err.println("3.-----");

        Set<Item> allItems = this.loggedUser.getItensPublicados();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userItems", allItems);
        model.addAttribute("loggedUser", this.loggedUser);
        System.err.println("userItems: " + model.getAttribute("userItems"));
        System.err.println("user: " + model.getAttribute("loggedUser"));

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/deleteSold/{id}")
    public String deleteSoldItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        System.err.println("ID para delete: " + id);

        Item deleted = itemService.getItemById(id);

        this.loggedUser.removeSoldItem(deleted);
        System.err.println("1. loggedUser: " + this.loggedUser);
        userService.updateUser(this.loggedUser);
        System.err.println("2.-----");
        itemService.deleteItem(id);
        System.err.println("3.-----");

        Set<Item> allItems = this.loggedUser.getItensVendidos();

        System.err.println("-------- Atributos atualizados --------");
        model.addAttribute("userSoldItems", allItems);
        model.addAttribute("loggedUser", this.loggedUser);
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
        if(!aUserIsLogged()){
            return "redirect:/login";
        }

        model.addAttribute("hasErrors", hasErrors);
        model.addAttribute("noImages", noImages);
        model.addAttribute("submitted", submitted);
        model.addAttribute("categories", categoryService.getAllCategories());

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
        Item newItem = new Item();
        newItem.setNome(itemForm.getNome());
        newItem.setCategoria(itemForm.getCategoria());
        newItem.setDescricao(itemForm.getDescricao());
        newItem.setPreco(itemForm.getPreco());
        newItem.setQuantidade(itemForm.getQuantidade());

        System.err.println("1!");
        for(Image im : imagesList.getImages()){
            if(im.getUrl().trim().equals(""))
                continue; // skip empty inputs
            newItem.addImage(im.getUrl());
        }
        newItem.setOwner(this.loggedUser.getId());
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
    public String soldItems(Model model) {
        Set<Item> allItems = this.loggedUser.getItensVendidos();

        model.addAttribute("userSoldItems", allItems);
        model.addAttribute("loggedUser", this.loggedUser);

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
