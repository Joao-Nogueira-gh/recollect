package ua.tqs.ReCollect.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.CommentService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;
import ua.tqs.ReCollect.utils.Category;
import ua.tqs.ReCollect.utils.CommentForm;
import ua.tqs.ReCollect.utils.Image;
import ua.tqs.ReCollect.utils.ItemForm;
import ua.tqs.ReCollect.utils.PictureListDto;
import ua.tqs.ReCollect.utils.SearchParams;


@Controller
public class FrontendWebController {

    static final Logger logger = Logger.getLogger(FrontendWebController.class);


    private static final String USERITEMS = "userItems";
    private static final String USERST = "user";
    private static final String SUBMITTED = "submitted";
    private static final String LOGGEDUSER = "loggedUser";
    private static final String ATRIBATUAL = "-------- Atributos atualizados --------";
    private static final int MAX_ITEM_PICTURES = 5;
    // REDIRECTS AND PAGE NAMES
    private static final String PRODUCT_SEARCH_RESULTS = "product-search-results";
    private static final String PRODUCT_POST = "product-post";
    private static final String REDIRECT_PRODUCT = "redirect:/product";
    private static final String REDIRECTANNOUNCE = "redirect:/announce";
    private static final String REDIRECT_SEARCH_RESULTS = "redirect:/category";
    private static final String REDIRECT_HOME= "redirect:/";
    private static final String ERROR_PAGE = "error";
    private static final String CATEGORIES_MACRO = "categories";
    private static final String CATEGORY = "category";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SEARCH_RESULTS = "searchResults";
    private static final String COMMENT_HAS_ERROR = "commentHasError";

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    List<Category> categories = Category.getCategories();

    @GetMapping(value = "/error")
    public String error(){
        return ERROR_PAGE;
    }

    @GetMapping(value = "/")
    public String home(SearchParams searchParams, Model model, @RequestParam(name = HAS_ERRORS, required = false) boolean hasErrors) {
        model.addAttribute(CATEGORIES_MACRO, categories);
        model.addAttribute(HAS_ERRORS, hasErrors);
        List<Item> recentItems = itemService.get20NewestItems();
        // get only items on sale
        CollectionUtils.filter(recentItems, i -> ((Item) i).getSeller()==null);
        model.addAttribute("recentItems", recentItems);
        logger.debug("loggedUser -> " + this.getLoggedUser());
        return "index";
    }

    @PostMapping(value = "/")
    public String searchProducts(@Valid SearchParams searchParams, BindingResult bindingResult, RedirectAttributes ra) {
        if(bindingResult.hasErrors()){
            // if, for some reason, there's no category, search is invalid
            logger.debug("NÃO FOI FORNECIDA CATEGORIA!");
            ra.addAttribute(HAS_ERRORS, true);
            return REDIRECT_HOME;
        }

        List<Item> searchResults;

        String searchTerm = searchParams.getSearchterm();
        Categories category = Categories.valueOf(searchParams.getCategory());

        if(searchTerm==null || searchTerm.equals("")){
            searchResults = itemService.getItemsByCategory(category);
        }
        else{
            searchResults = itemService.getItemsByCategoryAndSearchTerm(searchTerm, category);
        }

        // get only items on sale
        CollectionUtils.filter(searchResults, i -> ((Item) i).getSeller()==null);

        ra.addAttribute(SEARCH_RESULTS, searchResults);
        ra.addAttribute(CATEGORY, category);

        return REDIRECT_SEARCH_RESULTS;
    }

    @GetMapping(value = "/category") // url for product search results
    public String searchResultsPage(SearchParams searchParams,
                                    Model model,
                                    @RequestParam(name = HAS_ERRORS, required = false) boolean hasErrors,
                                    @RequestParam(name = SEARCH_RESULTS) List<Item> searchResults,
                                    @RequestParam(name = CATEGORY, required = false) Categories category){

        logger.debug("searchResults to recebidos -> " + searchResults.toString());

        model.addAttribute("hasErros", hasErrors);
        model.addAttribute(SEARCH_RESULTS, searchResults);
        model.addAttribute(CATEGORY, category);
        model.addAttribute(CATEGORIES_MACRO, categories);

        return PRODUCT_SEARCH_RESULTS;
    }

    @PostMapping(value = "/category")
    public String categorysearch(@Valid SearchParams searchParams, BindingResult bindingResult, RedirectAttributes ra) {
        if(bindingResult.hasErrors()){
            // if, for some reason, there's no category, search is invalid
            logger.debug("NÃO FOI FORNECIDA CATEGORIA!");
            ra.addAttribute(HAS_ERRORS, true);
            return REDIRECT_SEARCH_RESULTS;
        }

        List<Item> searchResults;

        String searchTerm = searchParams.getSearchterm();
        Categories category = Categories.valueOf(searchParams.getCategory());

        if(searchTerm==null || searchTerm.equals("")){
            searchResults = itemService.getItemsByCategory(category);
        }
        else{
            searchResults = itemService.getItemsByCategoryAndSearchTerm(searchTerm, category);
        }

        CollectionUtils.filter(searchResults, i -> ((Item) i).getSeller()==null);

        logger.debug("searchResults to post -> " + searchResults.toString());
        ra.addAttribute(SEARCH_RESULTS, searchResults);
        ra.addAttribute(CATEGORY, category);

        return REDIRECT_SEARCH_RESULTS;
    }

    @GetMapping(value = "/home_category/{category}")
    public String showCategoryProducts(@PathVariable(name = CATEGORY) String category, RedirectAttributes ra){
        List<Item> searchResults;

        Categories categoryEnum = Categories.valueOf(category);
        searchResults = itemService.getItemsByCategory(categoryEnum);

        // get only items on sale
        CollectionUtils.filter(searchResults, i -> ((Item) i).getSeller()==null);

        ra.addAttribute(SEARCH_RESULTS, searchResults);
        ra.addAttribute(CATEGORY, categoryEnum);

        return REDIRECT_SEARCH_RESULTS;
    }






    @GetMapping(value = "/edit-profile")
    public String editProfile(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getByEmail(auth.getName());

        model.addAttribute(LOGGEDUSER, loggedUser);

        return "edit-profile";
    }

    @GetMapping(value = "/profile")
    public String userProfile(Model model) {

        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getByEmail(auth.getName());

        Set<Item> allItems = loggedUser.getPublishedItems();

        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, loggedUser);

        return "dashboard-my-ads";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String deleteItem(Model model, @PathVariable(name = "id") Long id) {

        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        User loggedUser = this.getLoggedUser();

        Item deleted = itemService.getItemById(id);
        itemService.removeProduct(deleted);

        Set<Item> allItems = loggedUser.getPublishedItems();

        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, loggedUser);

        return "redirect:/profile";
    }

    @GetMapping(value = "/sold-items/deleteSold/{id}")
    public String deleteSoldItem(Model model, @PathVariable(name = "id") Long id) {

        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        logger.debug("ID para delete: " + id);

        Item deleted = itemService.getItemById(id);
        itemService.removeProduct(deleted);

        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute("userSoldItems", allItems);
        model.addAttribute(LOGGEDUSER, this.getLoggedUser());
        logger.debug(USERITEMS+": " + model.getAttribute(USERITEMS));
        logger.debug(USERST+": " + model.getAttribute(LOGGEDUSER));

        return "redirect:/sold-items";
    }


    @GetMapping(value = "/profile/marksold/{id}")
    public String markAsSoldItem(Model model, @PathVariable(name = "id") Long id) {

        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        logger.debug("ID para sold: " + id);

        Item sold = itemService.getItemById(id);
        itemService.markAsSold(sold);

        Set<Item> allItems = this.getLoggedUser().getPublishedItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute(USERITEMS, allItems);
        model.addAttribute(LOGGEDUSER, this.getLoggedUser());
        logger.debug(USERITEMS+": " + model.getAttribute(USERITEMS));
        logger.debug(USERST+": " + model.getAttribute(LOGGEDUSER));

        return "redirect:/profile";
    }

    @GetMapping(value = "/sold-items/backOnSale/{id}")
    public String putItemBackOnSale(Model model, @PathVariable(name = "id") Long id) {

        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        logger.debug("ID para sold: " + id);

        Item backOnSale = itemService.getItemById(id);
        itemService.revertSale(backOnSale);

        Set<Item> allItems = this.getLoggedUser().getSoldItems();

        logger.debug(ATRIBATUAL);
        model.addAttribute(USERITEMS, allItems);
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
                          @RequestParam(name = HAS_ERRORS, required = false) boolean hasErrors) {
        if(this.getLoggedUser() == null){
            return "redirect:/login";
        }

        model.addAttribute(HAS_ERRORS, hasErrors);
        model.addAttribute("noImages", noImages);
        model.addAttribute(SUBMITTED, submitted);
        model.addAttribute(CATEGORIES_MACRO, categories);

        PictureListDto pictureListForm = new PictureListDto();

        // create space for MAX_ITEM_PICTURES, maximum
        for (int i = 1; i <= MAX_ITEM_PICTURES; i++) {
            pictureListForm.addImage(new Image());
        }

        model.addAttribute("imagesList",pictureListForm);
        return "add-item";
    }

    @PostMapping(value = "/announce")
    public String postItem(@Valid ItemForm itemForm, BindingResult bindingResult, @ModelAttribute PictureListDto imagesList, RedirectAttributes ra) {

        if(bindingResult.hasErrors()){
            // if the provided data is invalid
            logger.debug("INPUTS INVALIDOS!");
            ra.addAttribute(SUBMITTED, false);
            ra.addAttribute(HAS_ERRORS, true);
            return REDIRECTANNOUNCE;
        }

        // check for missing URLs (to later check if at least one was provided)
        int emptyEntries = 0;
        for(Image im : imagesList.getImages()){
            if(im.getUrl()==null)
                emptyEntries+=1; // count empty entries
        }

        // if all images urls are empty strings
        if(emptyEntries==imagesList.getImages().size()){
            ra.addAttribute("noImages", true);
            ra.addAttribute(SUBMITTED, false);
            return REDIRECTANNOUNCE;
        }

        // setup item with valid provided data
        Item newItem = new Item();
        newItem.setName(itemForm.getNome());
        newItem.setCategory(itemForm.getCategoria());
        newItem.setDescription(itemForm.getDescricao());
        newItem.setPrice(itemForm.getPreco());
        newItem.setQuantity(itemForm.getQuantidade());

        for(Image im : imagesList.getImages()){
            if(im.getUrl()==null){
                continue;
            }
            newItem.addImage(im.getUrl());
        }

        itemService.addNewProduct(newItem, this.getLoggedUser());
        ra.addAttribute(SUBMITTED, true);
        logger.debug("Item submetido: " + newItem.toString());
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

    @GetMapping(value = "/product/{id}")
    public String productPost(Model model, @PathVariable(name = "id") Long id, RedirectAttributes ra) {
        Item item = itemService.getItemById(id);

        ra.addAttribute("item", item.getId());
        return REDIRECT_PRODUCT;
    }

    @GetMapping(value = "/product")
    public String productPage(CommentForm commentForm,
                              Model model,
                              @RequestParam(name = "item", required = false) Long id,
                              @RequestParam(name = COMMENT_HAS_ERROR, required = false) boolean commentHasError) {
        model.addAttribute(LOGGEDUSER, this.getLoggedUser());
        model.addAttribute(CATEGORIES_MACRO, categories);
        model.addAttribute("searchparams", new SearchParams());
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        model.addAttribute(COMMENT_HAS_ERROR, commentHasError);
        return PRODUCT_POST;
    }

    @PostMapping(value = "/product")
    public String productComment(Model model) {
        model.addAttribute(CATEGORIES_MACRO, categories);

        return PRODUCT_POST;

    }

    @GetMapping(value = "/product/comment/delete/{id}")
    public String deleteComment(RedirectAttributes ra, @PathVariable(name = "id") Long id) {

        Comment deleted = commentService.getCommentById(id);
        Long itemId = deleted.getItem().getId();
        commentService.deleteComment(deleted);

        Item commentedItem = itemService.getItemById(itemId);

        ra.addAttribute("item", commentedItem);

        return REDIRECT_PRODUCT;
    }

    @PostMapping(value = "/product/comment/{id}")
    public String addComment(@Valid CommentForm commentForm, BindingResult bindingResult, @PathVariable(name = "id") Long id, RedirectAttributes ra) {

        Item commentedItem = itemService.getItemById(id);

        if(bindingResult.hasErrors()){
            // if the provided data is invalid
            logger.debug("COMENTARIO INVALIDOS!");
            ra.addAttribute("item", commentedItem);
            ra.addAttribute(COMMENT_HAS_ERROR, true);
            return REDIRECT_PRODUCT;
        }

        // just in case
        if(commentForm.getConteudo().trim().equals("")){
            // if the provided data is invalid
            logger.debug("COMENTARIO VAZIO!");
            ra.addAttribute("item", commentedItem);
            ra.addAttribute(COMMENT_HAS_ERROR, true);
            return REDIRECT_PRODUCT;
        }


        Comment comment = new Comment(commentForm.getConteudo(), this.getLoggedUser(), commentedItem);
        commentService.addNewComment(comment);
        // get the most recent snapshop for that item, after the comment was made
        commentedItem = itemService.getItemById(id);
        ra.addAttribute("item", commentedItem);

        return REDIRECT_PRODUCT;

    }


    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByEmail(auth.getName());
    }

}
