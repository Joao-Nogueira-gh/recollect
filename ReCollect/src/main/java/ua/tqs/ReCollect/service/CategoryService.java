package ua.tqs.ReCollect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Category;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.repository.CategoryRepository;
import ua.tqs.ReCollect.repository.ItemRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryService {



    public CategoryService(){
        initSomeCategories();
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCateogories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name){
        Optional<Category> result = categoryRepository.findByName(name);

        return result.orElse(null);
    }


    private void initSomeCategories(){
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
    }
}
