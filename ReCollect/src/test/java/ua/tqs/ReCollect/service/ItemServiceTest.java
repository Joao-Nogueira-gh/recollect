package ua.tqs.ReCollect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.utils.OffsetBasedPageRequest;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

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
    void dbInteractions() {

        given(itemRepo.findAll()).willReturn(new ArrayList<>());

        // Testing just to check if the interaction works OK
        // Checking for NullPtrs and other Exceptions
        assertEquals(new ArrayList<>(), itemService.getAll());
        itemService.save(new Item());
        itemService.deleteAll();

    }

    @Test
    void whenItemIsSaved_itemCanBeRetrived() {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        itemService.save(item);

        ArrayList<Item> all = new ArrayList<>();
        all.add(item);

        given(itemRepo.findAll()).willReturn(all);

        assertTrue(itemService.getAll().contains(item));

    }

    @Test
    void whenUserAddsItem_itemIsAddedAndOwnedByUser() {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        List<Item> itemList=new ArrayList<>();
        itemList.add(item);

        given(itemRepo.findAll()).willReturn(itemList);

        Item repItem=itemService.getAll().get(0);

        assertEquals("user",repItem.getOwner().getName());

    }

    @Test
    void whenUserRemovesItem_itemIsGone() {

        //setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        //test

        List<Item> empty=new ArrayList<>();

        given(itemRepo.findAll()).willReturn(empty);

        itemService.removeProduct(item);

        assertEquals(0,itemService.getAll().size());

    }
    @Test
    void whenUserMarksItemAsSold_itemChangesList() {

        //setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        //test

        itemService.markAsSold(item);

        assertNull(item.getOwner());

        assertEquals("user", item.getSeller().getName());


    }
    @Test
    void whenUserRevertsSale_itemChangesList() {

        //setup

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        User owner=new User("user", "user@email.com", "x", "123456789");

        itemService.addNewProduct(item,owner);

        //test

        itemService.markAsSold(item);

        itemService.revertSale(item);

        assertNull(item.getSeller());

        assertEquals("user", item.getOwner().getName());

    }

    @Test
    void whenBooksAreFetched_BooksAreReturned() {

        Item item1 = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

        List<Item> itemList=new ArrayList<>();

        itemList.add(item1);

        given(itemRepo.findByCategory(Categories.BOOKS, new OffsetBasedPageRequest(0, 25))).willReturn(itemList);

        assertEquals(itemList, itemService.fetchItemsApi("BOOKS", null, null, null, null, null), "Error: no books found");

    }




}