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

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository rcRepository;

    @InjectMocks
    private ItemService sutRCService;

    @BeforeEach
    public void setUp() {

        rcRepository.deleteAll();

    }

    @AfterEach
    public void cleanUp() {

        rcRepository.deleteAll();

    }

    @Test
    public void dbInteractions() {

        given(rcRepository.findAll()).willReturn(new ArrayList<>());

        // Testing just to check if the interaction works OK
        // Checking for NullPtrs and other Exceptions
        assertEquals(new ArrayList<>(), sutRCService.getAll());
        sutRCService.save(new Item());
        sutRCService.deleteAll();

    }

    @Test
    public void whenItemDTOIsConverted_theRightDTOIsGenerated() throws MalformedURLException {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        ItemDTO itemDTO = new ItemDTO("Moeda", 3, new BigDecimal(3.0), "Moeda fixe");

        // Testing every branch
        assertEquals(itemDTO, sutRCService.convertItem(item), "Items do not match");

        item.setSeller(new User());
        assertEquals(itemDTO, sutRCService.convertItem(item), "Items do not match");

        item.setOwner(new User());
        assertEquals(itemDTO, sutRCService.convertItem(item), "Items do not match");

        item.addImage(new URL("https://www.google.com"));
        item.addComment(new Comment("Comment", new User(), item));

        assertEquals(itemDTO, sutRCService.convertItem(item), "Items do not match");
        
    }

    @Test
    public void whenItemIsSaved_itemCanBeRetrived() {

        Item item = new Item("Moeda", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
        sutRCService.save(item);

        ArrayList<Item> all = new ArrayList<>();
        all.add(item);

        given(rcRepository.findAll()).willReturn(all);

        assertTrue(sutRCService.getAll().contains(item));

    }

}