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

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }








}
