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

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.Category;
import ua.tqs.ReCollect.utils.Image;
import ua.tqs.ReCollect.utils.ItemForm;
import ua.tqs.ReCollect.utils.PictureListDto;
import ua.tqs.ReCollect.utils.SearchParams;
import org.apache.log4j.Logger;


@Controller
public class FrontendWebController {

    static final Logger logger = Logger.getLogger(FrontendWebController.class);

    private static final String CATEGORYHTML = "category";
    private static final String USERITEMS = "userItems";
    private static final String USERST = "user";
    private static final String SUBMITTED = "submitted";
    private static final String LOGGEDUSER = "loggedUser";
    private static final String ATRIBATUAL = "-------- Atributos atualizados --------";
    private static final String REDIRECTANNOUNCE = "redirect:/announce";

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    ArrayList<Category> categories = Category.getCategories();

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", categories);
        model.addAttribute("searchParams", new SearchParams());
        return "index";
    }

    @GetMapping(value = "/category")
    public String category() {
        return CATEGORYHTML;
    }

    @PostMapping(value = "/category")
    public String categorysearch(@ModelAttribute SearchParams searchparams, BindingResult result, ModelMap model) {
        logger.debug("Selected category: " + searchparams.getCategory());
        model.addAttribute("category", searchparams.getCategory());

        return CATEGORYHTML;
    }

    // @PostMapping(value = "/login")
    // public String login(@ModelAttribute LoginForm loginForm, RedirectAttributes ra) {
    //     String providedEmail = loginForm.getEmail();
    //     String providedPassword = loginForm.getPassword();
    //     logger.debug("providedEmail -> " + providedEmail);
    //     logger.debug("providedPassword -> " + providedPassword);


    //     User userFromDB = userService.getByEmail(providedEmail);

    //     // TODO: só para testes
    //     //--------------------------
    //     userFromDB = userService.getByEmail("alex@email.pt");
    //     providedEmail = "alex@email.pt";
    //     providedPassword = "pass";
    //     //--------------------------

    //     logger.debug("userfromDB -> " + userFromDB);

    //     if(userFromDB!=null){
    //         if(providedPassword.trim().equals(userFromDB.getPassword())){
    //             logger.debug("login success!");
    //             ra.addAttribute("success", true);
    //         }
    //         else{
    //             logger.debug("login error!");
    //             ra.addAttribute("showError", true);
    //             ra.addAttribute("success", false);
    //         }
    //     }
    //     else{
    //         logger.debug("login error!");
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

        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, loggedUser);

        return "dashboard-my-ads";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String deleteItem(Model model, @PathVariable(name = "id") Long id) {

        User loggedUser = this.getLoggedUser();

        logger.debug("ID para delete: " + id.toString());

        // Item deleted = itemService.getItemById(id);

        // loggedUser.removeItemPublicado(deleted);
        // logger.debug("1. loggedUser: " + loggedUser);
        // userService.updateUser(loggedUser);
        // logger.debug("2.-----");
        // itemService.deleteItem(id);
        // logger.debug("3.-----");

        Set<Item> allItems = loggedUser.getPublishedItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, loggedUser);
        logger.debug(USERITEMS+": " + model.getAttribute(USERITEMS));
        logger.debug(USERST+": " + model.getAttribute(LOGGEDUSER));

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/marksold/{id}")
    public String markAsSoldItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        logger.debug("ID para sold: " + id);

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
        // logger.debug("1. loggedUser: " + this.getLoggedUser());
        // userService.updateUser(this.getLoggedUser());

        // logger.debug("2.-----");
        // itemService.updateItem(sold);
        // logger.debug("3.-----");

        Set<Item> allItems = this.getLoggedUser().getPublishedItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, this.getLoggedUser());
        logger.debug(USERITEMS+": " + model.getAttribute(USERITEMS));
        logger.debug(USERST+": " + model.getAttribute(LOGGEDUSER));

        return "redirect:/profile";
    }

    @GetMapping(value = "/profile/deleteSold/{id}")
    public String deleteSoldItem(Model model, @PathVariable(name = "id") Long id) {

        // TODO: verificar se o loggedUser está logged in

        logger.debug("ID para delete: " + id);

        // Item deleted = itemService.getItemById(id);

        // this.getLoggedUser().removeSoldItem(deleted);
        // logger.debug("1. loggedUser: " + this.getLoggedUser());
        // userService.updateUser(this.getLoggedUser());
        // logger.debug("2.-----");
        // itemService.deleteItem(id);
        // logger.debug("3.-----");

        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute("userSoldItems", allItems);
        model.addAttribute(LOGGEDUSER, this.getLoggedUser());
        logger.debug(USERITEMS+": " + model.getAttribute(USERITEMS));
        logger.debug(USERST+": " + model.getAttribute(LOGGEDUSER));

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
        model.addAttribute(SUBMITTED, submitted);
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
            logger.debug("INPUTS INVALIDOS!");
            ra.addAttribute(SUBMITTED, false);
            ra.addAttribute("hasErrors", true);
            return REDIRECTANNOUNCE;
        }

        int emptyEntries = 0;
        for(Image im : imagesList.getImages()){
            if(im.getUrl().trim().equals(""))
                emptyEntries+=1; // count empty entries
        }

        // if all images urls are empty strings
        if(emptyEntries==imagesList.getImages().size()){
            ra.addAttribute("noImages", true);
            ra.addAttribute(SUBMITTED, false);
            return REDIRECTANNOUNCE;
        }

        // setup item with valid provided data
        // Item newItem = new Item();
        // newItem.setNome(itemForm.getNome());
        // newItem.setCategoria(itemForm.getCategoria());
        // newItem.setDescricao(itemForm.getDescricao());
        // newItem.setPreco(itemForm.getPreco());
        // newItem.setQuantidade(itemForm.getQuantidade());

        // logger.debug("1!");
        // for(Image im : imagesList.getImages()){
        //     if(im.getUrl().trim().equals(""))
        //         continue; // skip empty inputs
        //     newItem.addImage(im.getUrl());
        // }
        // newItem.setOwner(this.getLoggedUser().getId());
        // logger.debug("2!");
        // //logger.debug("newItem recebido: "+ newItem.toString());
        // //logger.debug("imagesList: " + imagesList.toString());
        // logger.debug("loggerUser antes do add: " + this.getLoggedUser().toString());
        // itemService.save(newItem);
        // logger.debug("3!");
        // this.getLoggedUser().addItem(newItem);
        // logger.debug("4!");
        // userService.updateUser(this.getLoggedUser());
        // logger.debug("5!");
        // ra.addAttribute(SUBMITTED, true);

        // logger.debug("Item submetido: " + newItem.toString());

        return REDIRECTANNOUNCE;
    }


    @GetMapping(value = "/favourites")
    public String favouritesAds() {
        return "dashboard-favourite-ads";
    }

    @GetMapping(value = "/sold-items")
    public String soldItems(Model model) {
        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        model.addAttribute("userSoldItems", allItems);
        model.addAttribute(LOGGEDUSER, getLoggedUser());

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
        return userService.getByEmail(auth.getName());
    }

}
