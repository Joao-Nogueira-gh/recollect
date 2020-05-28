package ua.tqs.ReCollect.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.CommentService;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;

@Controller
public class CommentController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired 
    private CommentService commentService;
    
    static final Logger logger = Logger.getLogger(ItemController.class);

	@GetMapping(path="/testcc/")
	public String test(Model model) {
        //setup
        itemService.deleteAll();
        commentService.deleteAll();
        User currUser = userService.getCurrentUser();
        Item i1= new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        itemService.addNewProduct(i1, currUser);
        logger.debug(itemService.getAll());

        //actual feature
        //get text box input, logged in user, and current item being viewed
        Comment com1=new Comment("comment text",currUser,itemService.getAll().get(0));
        commentService.addNewComment(com1);
        //get whatever u want with the getters, Item, User or Text
        commentService.getAll().get(0).getItem();
        logger.debug(commentService.getAll());
        //these 2 other lists are updated, the item comments and user comments, even tho you cant
        //see it here, because db updates too slowly
        logger.debug(itemService.getAll().get(0).getComment());
        logger.debug(currUser.getComment());

        //this is also working, it seems (no @Test tests, but it is working)
        commentService.deleteComment(commentService.getAll().get(0));


        return "emptyTest";
    }
    @GetMapping(path="/testccactual/")
	public String testactualstate(Model model) {
        //test actual state of things since loggers are faster than bd updating
        User currUser = userService.getCurrentUser();
        logger.debug(commentService.getAll());
        logger.debug(itemService.getAll().get(0).getComment());
        logger.debug(currUser.getComment());
        logger.debug(itemService.getAll());
        return "emptyTest";
    }
    
    
}