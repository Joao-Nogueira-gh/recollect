package ua.tqs.ReCollect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.net.URL;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.tqs.ReCollect.model.*;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.repository.OffsetBasedPageRequest;

@Service
public class ItemService {

    static final Logger logger = Logger.getLogger(ItemService.class);

    private static final int DEFAULT_LIMIT = 25;

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


    /**
     * API Methods
     * 
     */

    public List<Item> getAll(Pageable offset) {

        Page<Item> page = itemRepo.findAll(offset);

        return page.toList();

    }

    /**
     * Return all Items from a Category
     * 
     */
    private List<Item> fetchItemsByCategory(String cat, Pageable offset) {

        return itemRepo.findByCategory(Categories.valueOf(cat), offset);

    }

    /**
     * Return all Items from a given seller's e-mail
     * 
     */
    private List<Item> fetchItemsBySeller(String email, Pageable offset) {

        return itemRepo.findByOwner(userService.getByEmail(email), offset);

    }

    // Cant be tested in the Service Unit test since they need BD integration for
    // the
    // timestamps to be generated. Mocks won't suffice.
    public List<Item> get20NewestItems() {

        return this.itemRepo.findTop20ByOrderByCreationDateAsc();

    }

    public List<Item> get20OldestItems() {

        return this.itemRepo.findTop20ByOrderByCreationDateDesc();

    }

    public List<Item> getItemsByCategoryAndSearchTerm(String searchTerm, Categories category) {
        return itemRepo.findByNameContainingAndCategory(searchTerm, category);
    }

    public List<Item> getItemsByCategory(Categories category) {
        return itemRepo.findByCategory(category);
    }

    // Return all Items from a given seller's e-mail and Category
    private List<Item> fetchItemsByCategoryAndSeller(String cat, String email, Pageable offset) {

        return itemRepo.findByCategoryAndOwner(Categories.valueOf(cat), userService.getByEmail(email), offset);

    }

    public List<Item> fetchItemsApi(String cat, String seller, String orderBy, Integer limit, Integer offset) {

        List<Item> ret;
        Pageable p;

        if (offset == null) {
            offset = 0;
        }

        if (limit == null || limit > DEFAULT_LIMIT) {

            limit = DEFAULT_LIMIT;

        }

        if (orderBy == null) {

            p = new OffsetBasedPageRequest(offset, limit);

        } else {

            p = new OffsetBasedPageRequest(offset, limit, Direction.ASC, orderBy);

        }

        // Basic Validation
        if (basicValidation(cat, seller, orderBy)) {

            return new ArrayList<>();
        }

        if (cat == null && seller == null) {

            ret = this.getAll(p);

        } else if (cat == null) {

            // Query based on seller
            ret = this.fetchItemsBySeller(seller, p);

        } else if (seller == null) {

            // Query based on cat
            ret = this.fetchItemsByCategory(cat, p);

        } else {

            // Query based on both
            ret = this.fetchItemsByCategoryAndSeller(cat, seller, p);

        }

        return ret;

    }

    private boolean orderByIsValid(String orderBy) {
        return orderBy.equals("price") || orderBy.equals("creationDate");
    }

    private boolean basicValidation(String cat, String seller, String orderBy){

        return ((seller != null && !userService.userExists(seller))
        || (cat != null && !EnumUtils.isValidEnum(Categories.class, cat))
        || (orderBy != null && !orderByIsValid(orderBy)));
    }

    public Item getSingleItem(Long id) {

        Optional<Item> optItem = this.itemRepo.findById(id);

        if (optItem.isPresent()) {

            return optItem.get();

        }

        return null;

    }

}
