package ua.tqs.ReCollect.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ItemRepository;
import ua.tqs.ReCollect.repository.UserRepository;
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
 * 
 * 4. Reset DB
 * 
 * 
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @Autowired
    private TestRestTemplate restClient;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    private Item i1 = new Item("Moeda", 2, new BigDecimal(3.0), "Escudo", Categories.MISC);
    private Item i2 = new Item("Banda Desenhada", 1, new BigDecimal(15.0), "Homem Aranha LE", Categories.BOOKS);
    private Item i3 = new Item("Torradeira", 1, new BigDecimal(45.0), "Datada do ano 1500", Categories.TECHNOLOGY);
    private Item i4 = new Item("Os Maias", 1, new BigDecimal(150.0), "Edicao Original", Categories.BOOKS);
    private Item i5 = new Item("Funko Pop", 1, new BigDecimal(20.0), "Homem Aranha", Categories.MISC);
    private Item i6 = new Item("Mensagem", 1, new BigDecimal(100.0), "Do F. Pessoa", Categories.BOOKS);

    @BeforeEach
    public void setUpTest() throws Exception {

        itemRepository.deleteAll();
        userRepository.deleteAll();

        User u1 = new User("USER 1", "user1@email.com", "password", "123456789",
                locationService.getLocation("Aveiro", "Aveiro"));
        User u2 = new User("USER 2", "user2@email.com", "password", "987654321",
                locationService.getLocation("Viseu", "Viseu"));

        userService.save(u1);
        userService.save(u2);

        i1.setOwner(u1);
        itemRepository.saveAndFlush(i1);

        i2.setOwner(u2);
        itemRepository.saveAndFlush(i2);

        i3.setOwner(u1);
        itemRepository.saveAndFlush(i3);

        i4.setOwner(u1);
        itemRepository.saveAndFlush(i4);

        i5.setOwner(u2);
        itemRepository.saveAndFlush(i5);

        i6.setOwner(u1);
        itemRepository.saveAndFlush(i6);

    }

    @AfterEach
    public void resetDb() {

        itemRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    public void checkIfSetUpSuccessful() {

        assertEquals(2, userRepository.findAll().size());
        assertEquals(6, itemRepository.findAll().size());

    }

    @Test
    public void getAllItems() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Item>>() {
                });

        assertEquals(6, entity.getBody().size());

    }

    @Test
    public void getJust3Items() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items?limit=3", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Item>>() {
                });

        assertEquals(3, entity.getBody().size());

    }

    @Test
    public void getAllBooks() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items?category=BOOKS", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(3, entity.getBody().size());

        for (Item item : body) {

            assertEquals(Categories.BOOKS, item.getCategory());

        }

    }

    @Test
    public void getAllUser1() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items?owner='user1@email.com'", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(4, entity.getBody().size());

        for (Item item : body) {

            assertEquals("USER 1", item.getOwner().getName());

        }

    }

    @Test
    public void getSortedBooks() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items?category=BOOKS&orderBy=price",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(3, entity.getBody().size());

        for (Item item : body) {

            assertEquals(Categories.BOOKS, item.getCategory());

        }

        assertEquals(i2.getName(), body.get(0).getName(), "ERROR: Not sorted properly");

    }

    @Test
    public void getSortedUser2() {

        ResponseEntity<List<Item>> entity = restClient.exchange("/api/items?owner='user2@email.com'&orderBy=price",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(2, entity.getBody().size());

        for (Item item : body) {

            assertEquals("USER 2", item.getOwner().getName());

        }

        assertEquals(i2.getName(), body.get(0).getName(), "ERROR: Not sorted properly");

    }

    @Test
    public void getAllUser1Books() {

        ResponseEntity<List<Item>> entity = restClient.exchange(
                "/api/items?category=BOOKS&owner='user1@email.com'", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(2, entity.getBody().size());

        for (Item item : body) {

            assertEquals(Categories.BOOKS, item.getCategory());
            assertEquals("USER 1", item.getOwner().getName());

        }

    }

    @Test
    public void getSortedUser1Books() {

        ResponseEntity<List<Item>> entity = restClient.exchange(
                "/api/items?category=BOOKS&owner='user1@email.com'&orderBy=price", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Item>>() {
                });

        List<Item> body = entity.getBody();

        assertEquals(2, entity.getBody().size());

        for (Item item : body) {

            assertEquals(Categories.BOOKS, item.getCategory());
            assertEquals("USER 1", item.getOwner().getName());

        }

        assertEquals(i6.getName(), body.get(0).getName(), "ERROR: Not sorted properly");

    }

    @Test
    public void noPwdsSent() {

        ResponseEntity<List<User>> entity = restClient.exchange(
            "/api/users/", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<User>>() {
            });

        List<User> body = entity.getBody();

        for (User user : body) {
            assertEquals(null, user.getPassword(), "ERROR: Password leaked");
        }

    }

    @Test
    public void getUsersByLocation() {

        ResponseEntity<List<User>> entity = restClient.exchange(
            "/api/users/?district=Viseu&county=Viseu", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<User>>() {
            });

        List<User> users = entity.getBody();

        for (User user : users) {
            
            assertEquals("USER 2", user.getName());

        }

    }

}