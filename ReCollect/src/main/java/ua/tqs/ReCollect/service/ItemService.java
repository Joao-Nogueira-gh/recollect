package ua.tqs.ReCollect.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.tqs.ReCollect.model.Categories;
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

    @Autowired
    private UserService userService;

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
    @Transactional
	public void addFavorite(Item item, User user) {
        if (!user.getFavoriteItems().contains(item)){
            user.addFavItem(item);
        }
	}
    @Transactional
	public void removeFavorite(Item item, User user) {
        if (user.getFavoriteItems().contains(item)){
            user.remFavItem(item);
        }
    }


    public List<Item> fetchItemsByCategory(String cat) {

        return itemRepo.findByCategory(cat);

    }

    public List<Item> fetchItemsBySeller(String seller) {
        Long id = this.getUserId(seller);

        return itemRepo.findBySeller(id);
    }

    public List<Item> fetchItemsOrderBy(String orderBy) {
        
        List<Item> ret = new ArrayList<>();

        // if(orderBy.toUpperCase().equals("PRICE")) {
        //     ret = itemRepo.findAllOrderByPrice();
        // } else if (orderBy.toUpperCase().equals("CREATION_DATE")) {
        //     ret = itemRepo.findAllOrderByCreationDate();
        // }

        return ret;

    }

    public List<Item> fetchItemsByCatAndSeller(String cat, String seller, String orderBy) {
        
        Long id = this.getUserId(seller);

        return itemRepo.findByCategoryAndSeller(cat, id);

    }

    public List<Item> fetchItemsByCatAndOrderBy(String cat, String orderBy) {

        List<Item> ret = new ArrayList<>();

        if(orderBy.toUpperCase().equals("PRICE")) {
            ret = itemRepo.findByCategoryOrderByPrice(cat);
        } else if (orderBy.toUpperCase().equals("CREATION_DATE")) {
            ret = itemRepo.findByCategoryOrderByCreationDate(cat);
        }

        return ret;
    }

    public List<Item> fetchItemsBySellerAndOrderBy(String seller, String orderBy) {

        List<Item> ret = new ArrayList<>();
        Long id = this.getUserId(seller);

        if(orderBy.toUpperCase().equals("PRICE")) {
            ret = itemRepo.findBySellerOrderByPrice(id);
        } else if (orderBy.toUpperCase().equals("CREATION_DATE")) {
            ret = itemRepo.findBySellerOrderByCreationDate(id);
        }

        return ret;
    }
    
    public List<Item> fetchItemsApi(String cat, String seller, String orderBy) {

        List<Item> ret = new ArrayList<>();
        Long id = this.getUserId(seller);

        if(orderBy.toUpperCase().equals("PRICE")) {
            ret = itemRepo.findByCategoryAndSellerOrderByPrice(cat, id);
        } else if (orderBy.toUpperCase().equals("CREATION_DATE")) {
            ret = itemRepo.findByCategoryAndSellerOrderByCreationDate(cat, id);
        }

        return ret;
    }


    private Long getUserId(String email) {
        return userService.getByEmail(email).getId();
    }

}

  







