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
public class ItemService {


    @Autowired
    private ItemRepository itemRepository;


    public Item getItemById(Long id){
        Optional<Item> result = itemRepository.findById(id);

        return result.orElse(null);
    }


    /*private void initSomeItems(){
        Item comic = new Item("Homem Aranha 2", "Descricao 1", "");
        Item coin = new  Item("Moeda", 9.99, 2);
        Item toys = new Item("Toys", "fa-robot");
        Item tech = new Item("Technology", "fa-camera-retro");
        Item music = new Item("Music", "fa-guitar");
        Item art = new Item("Art", "fa-image");
        Item misc = new Item("Miscellaneous", "fa-box-open");

        categoryRepository.save(books);
        categoryRepository.save(games);
        categoryRepository.save(toys);
        categoryRepository.save(tech);
        categoryRepository.save(music);
        categoryRepository.save(art);
        categoryRepository.save(misc);
    }*/





}
