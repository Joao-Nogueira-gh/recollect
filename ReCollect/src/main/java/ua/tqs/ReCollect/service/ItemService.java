package ua.tqs.ReCollect.service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.ItemDTO;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;

@Service
public class ItemService {

    static final Logger logger = Logger.getLogger(ItemService.class);

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
    @Transactional
	public void addNewProduct(Item item, User owner) {
        save(item);
        item.setOwner(owner);
        save(item);
	}
    @Transactional
	public void removeProduct(Item item) {
        itemRepo.delete(item);
    }
    @Transactional
	public void markAsSold(Item item) {
        item.setSeller(item.getOwner());
        item.setOwner(null);
	}
    @Transactional
	public void revertSale(Item item) {
        item.setOwner(item.getSeller());
        item.setSeller(null);
	}
}

  







