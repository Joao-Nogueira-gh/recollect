package ua.tqs.ReCollect.service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.ItemDTO;
import ua.tqs.ReCollect.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    public Item getItemById(Long id){
        Optional<Item> result = itemRepo.findById(id);

        return result.orElse(null);
    }

    public List<Item> getAll(){
        return itemRepo.findAll();
    }
    public void save(Item item){
        itemRepo.save(item);
    }
    public void deleteItem(Long id){
        itemRepo.deleteById(id);
    }
    public void deleteAll(){
        itemRepo.deleteAll();
    }
    public ItemDTO convertItem(Item item){
        ItemDTO dto=new ItemDTO(item.getName(), item.getQuantity(), item.getPrice(),item.getDescription());

        for (URL image : item.getImages()) {
           dto.addImages(image); 
        }
        dto.setCreationDate(item.getCreationDate());

        if (item.getOwner()!=null){
            dto.setOwner(item.getOwner().getName());
        }
        else if (item.getSeller()!=null){
            dto.setOwner(item.getSeller().getName());
        }
        else{
            dto.setOwner("null");
        }
        for (Comment comment : item.getComment()) {
            dto.addComments(comment.getText()+";"+comment.getUser().getName()); 
         }

        dto.setCategory(String.valueOf(item.getCategory()));

        return dto;
    }
}

  







