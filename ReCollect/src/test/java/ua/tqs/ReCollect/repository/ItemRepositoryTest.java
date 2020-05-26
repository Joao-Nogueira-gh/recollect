package ua.tqs.ReCollect.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TestEntityManager entityManager;


    User user = new User("Tiago", "email@ua", "password", "123123123");

    Item i1 = new Item("Moeda", 2, new BigDecimal(1.0), "Moeda fixe", Categories.MISC);
    Item i2 = new Item("BD", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);
    Item i3 = new Item("Cenas", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
    Item i4 = new Item("Robot", 3, new BigDecimal(3.0), "Moeda fixe", Categories.TECHNOLOGY);
    Item i5 = new Item("Livro", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

    @BeforeEach
    public void insert() throws InterruptedException {

        itemRepo.deleteAll();
        userRepo.deleteAll();

        entityManager.persistAndFlush(i1);
        sleep();

        entityManager.persistAndFlush(i2);
        sleep();

        entityManager.persistAndFlush(i3);
        sleep();

        entityManager.persistAndFlush(i4);
        sleep();

        entityManager.persistAndFlush(i5);
        sleep();

    }

    @AfterEach
    public void cleanUp() {
        itemRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void test() {
        itemRepo.deleteAll();

        assertEquals(0, itemRepo.findAll().size());
    }

    // Smelly test, cant figure out how to delay test execution without sleep
    @Test
    public void get20SortedItems() {

        List<Item> newest = itemRepo.findTop20ByOrderByCreationDateAsc();
        List<Item> oldest = itemRepo.findTop20ByOrderByCreationDateDesc();


        assertEquals(oldest.get(4), newest.get(0));

    }

    private void sleep() {
        long start = new Date().getTime();
        while(new Date().getTime() - start < 1000L){}
    }




}