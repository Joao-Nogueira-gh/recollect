package ua.tqs.ReCollect.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Comment;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.ItemDTO;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepo;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {

        itemRepo.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        itemRepo.deleteAll();

    }

    @Test
    public void dbInteractions() {

        given(itemRepo.findAll()).willReturn(new ArrayList<>());

        // Testing just to check if the interaction works OK
        // Checking for NullPtrs and other Exceptions
        assertEquals(new ArrayList<>(), itemService.getAll());
        itemService.save(new Item());
        itemService.deleteAll();

    }

    @Test
    public void whenItemDTOIsConverted_theRightDTOIsGenerated() throws MalformedURLException {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        ItemDTO itemDTO = new ItemDTO("Moeda", 3, new BigDecimal(3.0), "Moeda fixe");

        // Testing every branch
        assertEquals(itemDTO, itemService.convertItem(item), "Items do not match");

        item.setSeller(new User());
        assertEquals(itemDTO, itemService.convertItem(item), "Items do not match");

        item.setOwner(new User());
        assertEquals(itemDTO, itemService.convertItem(item), "Items do not match");

        item.addImage(new URL("https://www.google.com"));
        item.addComment(new Comment("Comment", new User(), item));

        assertEquals(itemDTO, itemService.convertItem(item), "Items do not match");
        
    }

    @Test
    public void whenItemIsSaved_itemCanBeRetrived() {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        itemService.save(item);

        ArrayList<Item> all = new ArrayList<>();
        all.add(item);

        given(itemRepo.findAll()).willReturn(all);

        assertTrue(itemService.getAll().contains(item));

    }

    @Test
    public void whenUserAddsItem_itemIsAddedAndOwnedByUser() {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        Item repItem=itemService.getAll().get(0);

        given(itemRepo.findAll().get(0)).willReturn(item);

        assertEquals(item.getOwner().getName(), "user");

    }

    @Test
    public void whenUserRemovesItem_itemIsGone() {

        //setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        //test

        itemService.removeProduct(item);

        int l = itemService.getAll().size();

        given(itemRepo.findAll().size()).willReturn(0);

        assertEquals(0, l);


    }

}