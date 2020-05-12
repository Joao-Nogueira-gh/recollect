package ua.tqs.ReCollect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ua.tqs.ReCollect.entity.Category;
import ua.tqs.ReCollect.repository.CategoryRepository;

import java.util.List;
import java.util.logging.Logger;

@Component
@Order(0)
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = Logger.getLogger("ApplicationListener#onApplicationEvent()");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("ApplicationListener#onApplicationEvent()");
        List<Category> categoriesList = initSomeCategories();
        System.err.println("categorias: " + categoriesList.toString());
        logger.info("categorias: " + categoriesList.toString());
    }



    private List<Category> initSomeCategories(){
        Category books = new Category("Books", "fa-book-open");
        Category games = new Category("Games", "fa-dice-d6");
        Category toys = new Category("Toys", "fa-robot");
        Category tech = new Category("Technology", "fa-camera-retro");
        Category music = new Category("Music", "fa-guitar");
        Category art = new Category("Art", "fa-image");
        Category misc = new Category("Miscellaneous", "fa-box-open");

        categoryRepository.save(books);
        categoryRepository.save(games);
        categoryRepository.save(toys);
        categoryRepository.save(tech);
        categoryRepository.save(music);
        categoryRepository.save(art);
        categoryRepository.save(misc);

        return categoryRepository.findAll();

    }
}
