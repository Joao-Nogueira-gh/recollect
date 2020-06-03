package ua.tqs.ReCollect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Category;
import ua.tqs.ReCollect.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getAllCateogories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name){
        Optional<Category> result = categoryRepository.findById(name);

        return result.orElse(null);
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }



}
