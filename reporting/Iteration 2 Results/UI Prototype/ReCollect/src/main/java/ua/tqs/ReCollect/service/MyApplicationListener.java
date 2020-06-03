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
        //logger.info("categorias: " + categoriesList.toString());
    }



    private List<Category> initSomeCategories(){
        Category books = new Category("Books", "fas fa-book-open icon-bg-1");
        Category games = new Category("Games", "fas fa-dice-d6  icon-bg-2");
        Category toys = new Category("Toys", "fas fa-robot icon-bg-3");
        Category tech = new Category("Technology", "fa fa-camera-retro icon-bg-4");
        Category music = new Category("Music", "fas fa-guitar icon-bg-5");
        Category art = new Category("Art", "fas fa-image icon-bg-6");
        Category misc = new Category("Miscellaneous", "fas fa-box-open icon-bg-7");

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
