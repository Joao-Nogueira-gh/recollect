package ua.tqs.ReCollect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.UserService;


/**
 * 
 * 
 * API Integration test
 * 
 * 0. Clear database
 * 1. Add 2 new users to the database
 * 2. Add a few items associated with these users
 * 3. Make API calls:
 *      * GET all items
 *      * GET all items from a class
 *      * GET all items from an owner
 *      * GET sorted items from a class
 *      * GET sorted items from an owner
 *      * GET all items from a class and owner
 *      * GET sorted items from a class and owner
 * 4. Reset DB
 * 
 * 
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiIntegrationTest {

    @Autowired
    private TestRestTemplate restClient;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;


    private Item i1 = new Item("Moeda", 2, new BigDecimal(3.0), "Escudo", Categories.MISC);
    private Item i2 = new Item("Banda Desenhada", 1, new BigDecimal(15.0), "Homem Aranha LE", Categories.BOOKS);
    private Item i3 = new Item("Torradeira", 1, new BigDecimal(45.0), "Datada do ano 1500", Categories.TECHNOLOGY);
    private Item i4 = new Item("Os Maias", 1, new BigDecimal(150.0), "Edição Original", Categories.BOOKS);
    private Item i5 = new Item("Funko Pop", 1, new BigDecimal(20.0), "Homem Aranha", Categories.MISC);


    @BeforeEach
    public void setUpTest() throws Exception {

        itemRepository.deleteAll();
        userRepository.deleteAll();

        User u1 = new User("USER 1", "user1@email.com", "password", "123456789", locationService.getLocation("Aveiro", "Aveiro"));
        User u2 = new User("USER 2", "user2@email.com", "password", "987654321", locationService.getLocation("Aveiro", "Aveiro"));    

        userService.save(u1);
        userService.save(u2);

        itemService.addNewProduct(i1, u1);
        itemService.addNewProduct(i2, u2);
        itemService.addNewProduct(i3, u1);
        itemService.addNewProduct(i4, u1);
        itemService.addNewProduct(i5, u2);

    }

    @AfterEach
    public void resetDb() {

        itemRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    public void checkIfSetUpSuccessful() {

        assertEquals(2, userRepository.findAll().size());
        assertEquals(5, itemRepository.findAll().size());

    }

    @Test
    public void getAllItems() throws Exception {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items", HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {});
        System.out.println(entity);

        mvc.perform(get("/api/items").contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

    }

     
    
}