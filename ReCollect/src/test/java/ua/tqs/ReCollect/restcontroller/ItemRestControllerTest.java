package ua.tqs.ReCollect.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.service.ItemService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemRestControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ItemService service;

    @BeforeEach
    public void setUp() {
        service.deleteAll();
    }

	@Test
	void apiShouldReturnAccurateListOfItems() throws Exception {

        ArrayList<Item> all = (ArrayList<Item>) service.getAll();
        
        @SuppressWarnings("unchecked")
        ArrayList<Item> apiItems = this.restTemplate.getForObject("/api/items/", ArrayList.class);
        
        assertEquals(all, apiItems);
	}
}