package ua.tqs.ReCollect.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private TestEntityManager entityManager;

    User user = new User("Tiago", "email@ua", "password", "123123123");

    Item i1 = new Item("Moeda", 2, new BigDecimal(1.0), "Moeda fixe", Categories.MISC);
    Item i2 = new Item("BD", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);
    Item i3 = new Item("Cenas", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
    Item i4 = new Item("Robot", 3, new BigDecimal(3.0), "Moeda fixe", Categories.TECHNOLOGY);
    Item i5 = new Item("Livro", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

    @BeforeEach
    public void insert() {

        itemRepo.deleteAll();

        entityManager.persistAndFlush(i1);
        entityManager.persistAndFlush(i2);
        entityManager.persistAndFlush(i3);
        entityManager.persistAndFlush(i4);
        entityManager.persistAndFlush(i5);

    }

    @Test
    public void test() {
        itemRepo.deleteAll();

        assertEquals(0, itemRepo.findAll().size());
    }

    @Test
    public void queries() {


        List<Item> expected = new ArrayList<>();
        List<Item> actual = itemRepo.findAll();

        System.out.println(actual.size());

        expected.add(i1);
        expected.add(i2);
        expected.add(i3);
        expected.add(i4);
        expected.add(i5);

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void getOnlyBooks() {

        List<Item> books = itemRepo.findByCategory(Categories.BOOKS);

        assertEquals(5, itemRepo.findAll().size());

        for (Item item : books) {
            
            assertEquals(Categories.BOOKS, item.getCategory(), "Error: not a book");

        }

    }

    @Test
    public void getCheapestCoin() {

        assertEquals(5, itemRepo.findAll().size());

        assertEquals(i1.getName(), itemRepo.findByCategory(Categories.MISC, Sort.by(Sort.Direction.ASC, "price")).get(0).getName());

    }

    @AfterEach
    public void cleanUp() {
        itemRepo.deleteAll();
    }




    // Infelizmente este teste nao pode ser aqui acho eu
    // @Test
    // public void getItemsByOwner() {

    //     Item i6 = new Item("Carica", 2, new BigDecimal(1.0), "Carica fixe", Categories.MISC);
    //     Item i7 = new Item("BD do Big Wheel", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

    //     i6.setOwner(user);
    //     i7.setOwner(user);

    //     entityManager.persistAndFlush(i6);
    //     entityManager.persistAndFlush(i7);

    //     List<Item> usersItems = itemRepo.findByOwner(user.getId());

    //     for (Item item : usersItems) {
            
    //         assertEquals(user.getName(), item.getOwner().getName(), "Error: wrong owner");

    //     }

    // }


}