package ua.tqs.ReCollect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Category;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.User;
import ua.tqs.ReCollect.repository.CategoryRepository;
import ua.tqs.ReCollect.repository.ItemRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ItemService {


    @Autowired
    private ItemRepository itemRepository;


    public Item getItemById(Long id){
        Optional<Item> result = itemRepository.findById(id);

        return result.orElse(null);
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }

    public void updateItem(Item i){
        System.err.println("update1 | item: " + i.toString());
        itemRepository.saveAndFlush(i);
    }






}
