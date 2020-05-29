package ua.tqs.ReCollect.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;
    
    static final Logger logger = Logger.getLogger(ItemController.class);
    static final String EMPTY_TEST_PAGE = "emptyTest";

	@GetMapping(path="/testic/")
	public String test(Model model) {
        itemService.deleteAll();

        //get logged in user
        User currUser = userService.getCurrentUser();

        logger.debug(currUser.toString());
        //create item from forms
        Item i1= new Item("Moeda", 3, new BigDecimal("3.0"), "Moeda fixe", Categories.MISC);
        //create item for that user
        itemService.addNewProduct(i1, currUser);

        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        //remove a certain item, u can obviously get it by id or something
        itemService.removeProduct(itemService.getAll().get(0));
        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        return EMPTY_TEST_PAGE;
    }
    @GetMapping(path="/testic2/")
	public String test2(Model model) {
        itemService.deleteAll();

        //get logged in user
        User currUser = userService.getCurrentUser();

        logger.debug(currUser.toString());
        //create item from forms
        Item i1= new Item("Moeda", 3, new BigDecimal("3.0"), "Moeda fixe", Categories.MISC);
        //create item for that user
        itemService.addNewProduct(i1, currUser);

        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        logger.debug(currUser.getSoldItems()+"\n");
        //mark item as sold, u can obviously get it by id or something
        itemService.markAsSold(itemService.getAll().get(0));
        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        logger.debug(currUser.getSoldItems()+"\n");
        //revert sale, u can obviously get item by id or something
        itemService.revertSale(itemService.getAll().get(0));
        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        logger.debug(currUser.getSoldItems());
        return EMPTY_TEST_PAGE;
    }
    @GetMapping(path="/testic3/")
	public String test3(Model model) {
        //just for debugging, ignore
        //check actual state of stuff
        User currUser = userService.getCurrentUser();
        
        logger.debug(itemService.getAll());
        //logger.debug(itemService.getAll().get(0).getFavedBy());
        logger.debug(currUser.getFavoriteItems()+"\n");
        return EMPTY_TEST_PAGE;
    }
    @GetMapping(path="/testic4/")
	public String test4(Model model) {
        itemService.deleteAll();
        User currUser = userService.getCurrentUser();

        Item i1= new Item("Moeda", 3, BigDecimal.valueOf(3.0), "Moeda fixe", Categories.MISC);

        itemService.addNewProduct(i1, currUser);
        //add to favorite list
        itemService.addFavorite(itemService.getAll().get(0),currUser);

        logger.debug(itemService.getAll());
        logger.debug(currUser.getPublishedItems());
        logger.debug(currUser.getSoldItems()+"\n");
        return EMPTY_TEST_PAGE;
    }
    
}