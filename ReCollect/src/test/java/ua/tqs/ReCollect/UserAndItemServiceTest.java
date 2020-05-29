package ua.tqs.ReCollect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserAndItemServiceTest {

    @Mock
    private ItemRepository itemRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    @Autowired
    private UserService userService;

    @InjectMocks
    @Autowired
    private ItemService itemService;

    private User user =new User("user2", "email@email.com", "xsff", "789456123");

    @Test
    public void whenUserAddsFavoriteItem_ItemIsAddedToTheList() {

        // setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        // test

        itemService.addFavorite(item,user);

        assertTrue(user.getFavoriteItems().contains(item));

    }

    @Test
    public void whenUserRemovesFavoriteItem_ItemIsRemovedFromTheList() {

        // setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        itemService.addFavorite(item,user);

        assertTrue(user.getFavoriteItems().contains(item));

        // test

        itemService.removeFavorite(item,user);

        assertFalse(user.getFavoriteItems().contains(item));


    }

    @Test
    public void whenGetItemsByOwner_onlyOwnerItemsAreReturned() {

        itemService.setUserService(userService);

        String email = "email@email.com";

        Item i6 = new Item("Carica", 2, new BigDecimal(1.0), "Carica fixe", Categories.MISC);
        Item i7 = new Item("BD do Big Wheel", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

        i6.setOwner(user);
        i7.setOwner(user);

        List<Item> userItems = new ArrayList<>();
        userItems.add(i6);
        userItems.add(i7);

        given(userRepo.existsByEmail(email)).willReturn(true);
        given(userRepo.findByEmail(email)).willReturn(user);
        given(itemRepo.findByOwner(user, PageRequest.of(0, 25))).willReturn(userItems);

        List<Item> usersItems = itemService.fetchItemsApi(null, email, null, null, null);
        
        assertEquals(2, usersItems.size());

        for (Item item : usersItems) {

            assertTrue(user.getName().equals(item.getOwner().getName()));

        }

    }
    
}