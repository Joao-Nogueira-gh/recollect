package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    public List<Item> getAll(){
        return itemRepo.findAll();
    }
    public void save(Item item){
        itemRepo.save(item);
    }
    public void deleteAll(){
        itemRepo.deleteAll();
    }
}