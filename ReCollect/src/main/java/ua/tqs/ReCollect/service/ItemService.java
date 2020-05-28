package ua.tqs.ReCollect.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public Item getItemById(Long id) {
        Optional<Item> result = itemRepo.findById(id);

        return result.orElse(null);
    }

    public List<Item> getAll() {
        return itemRepo.findAll();
    }

    public void save(Item item) {
        itemRepo.save(item);
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    public void deleteAll() {
        itemRepo.deleteAll();
    }

    // Needed it for tests
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ItemDTO convertItem(Item item) {
        ItemDTO dto = new ItemDTO(item.getName(), item.getQuantity(), item.getPrice(), item.getDescription());

        for (URL image : item.getImages()) {
            dto.addImages(image);
        }
        dto.setCreationDate(item.getCreationDate());

        if (item.getOwner() != null) {
            dto.setOwner(item.getOwner().getName());
        } else if (item.getSeller() != null) {
            dto.setOwner(item.getSeller().getName());
        } else {
            dto.setOwner("null");
        }
        for (Comment comment : item.getComment()) {
            dto.addComments(comment.getText() + ";" + comment.getUser().getName());
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
        if (!user.getFavoriteItems().contains(item)) {
            user.addFavItem(item);
        }
    }

    @Transactional
    public void removeFavorite(Item item, User user) {
        if (user.getFavoriteItems().contains(item)) {
            user.remFavItem(item);
        }
    }

    // Return all Items sorted by either Price or Date
    private List<Item> getAll(String orderBy) {

        return itemRepo.findAll(Sort.by(Sort.Direction.ASC, orderBy));

    }

    /**
     * Return all Items from a Category
     * 
     */
    private List<Item> fetchItemsByCategory(String cat) {

        return itemRepo.findByCategory(Categories.valueOf(cat));

    }

    // Sort option
    private List<Item> fetchItemsByCategory(String cat, String orderBy) {

        return itemRepo.findByCategory(Categories.valueOf(cat), Sort.by(Sort.Direction.ASC, orderBy));

    }

    /**
     * Return all Items from a given seller's e-mail
     * 
     */
    private List<Item> fetchItemsBySeller(String email) {

        return itemRepo.findByOwner(userService.getByEmail(email));

    }

    // Sort option
    private List<Item> fetchItemsBySeller(String email, String orderBy) {

        return itemRepo.findByOwner(userService.getByEmail(email), Sort.by(Sort.Direction.ASC, orderBy));

    }

    // Return all Items from a given seller's e-mail and Category
    private List<Item> fetchItemsByCategoryAndSeller(String cat, String email) {

        return itemRepo.findByCategoryAndOwner(Categories.valueOf(cat), userService.getByEmail(email));

    }

    // Return all Items from a given seller's e-mail and Category sorted by either
    // price or date
    private List<Item> fetchItemsByCategoryAndSeller(String cat, String email, String orderBy) {

        return itemRepo.findByCategoryAndOwner(Categories.valueOf(cat), userService.getByEmail(email),
                Sort.by(Sort.Direction.ASC, orderBy));

    }

    public List<Item> fetchItemsApi(String cat, String seller, String orderBy, Integer limit) {

        List<Item> ret;

        // Basic Validation
        if ((seller != null && !userService.userExists(seller))
                || (cat != null && !EnumUtils.isValidEnum(Categories.class, cat))
                || (orderBy != null && !orderByIsValid(orderBy))) {

            return new ArrayList<Item>();
        }


        if (cat == null && seller == null && orderBy == null) {

            ret = this.getAll();

        } else if (cat == null && seller == null && orderBy != null) {

            ret = this.getAll(orderBy);

        } else if (orderBy == null) {

            if (cat == null) {

                // Query based on seller
                ret = this.fetchItemsBySeller(seller);

            } else if (seller == null) {

                // Query based on cat
                ret = this.fetchItemsByCategory(cat);

            } else {

                // Query based on both
                ret = this.fetchItemsByCategoryAndSeller(cat, seller);

            }

        } else {

            if (cat == null) {

                // Query based on seller
                ret = this.fetchItemsBySeller(seller, orderBy);

            } else if (seller == null) {

                // Query based on cat
                ret = this.fetchItemsByCategory(cat, orderBy);

            } else {

                // Query based on both
                ret = this.fetchItemsByCategoryAndSeller(cat, seller, orderBy);

            }

        }

        if (limit == null) {

            return ret;

        }

        return ret.stream().limit(limit).collect(Collectors.toList());

    }

    private boolean orderByIsValid(String orderBy) {
        return orderBy.equals("price") || orderBy.equals("creationDate");
    }

	public Item getSingleItem(Long id) {
        
        Optional<Item> optItem = this.itemRepo.findById(id);

        if(optItem.isPresent()) {

            return optItem.get();
            
        }

        return null;
        
	}

}
