package ua.tqs.ReCollect.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private TestEntityManager entityManager;

    Item i1 = new Item("Moeda", 2, new BigDecimal(1.0), "Moeda fixe", Categories.MISC);
    Item i2 = new Item("BD", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);
    Item i3 = new Item("Cenas", 3, new BigDecimal(3.0), "Moeda fixe", Categories.MISC);
    Item i4 = new Item("Robot", 3, new BigDecimal(3.0), "Moeda fixe", Categories.TECHNOLOGY);
    Item i5 = new Item("Livro", 3, new BigDecimal(3.0), "Moeda fixe", Categories.BOOKS);

    @Test
    public void test() {
        itemRepo.deleteAll();

        assertEquals(0, itemRepo.findAll().size());
    }

    @Test
    public void queries() {

        entityManager.persistAndFlush(i1);
        entityManager.persistAndFlush(i2);
        entityManager.persistAndFlush(i3);
        entityManager.persistAndFlush(i4);
        entityManager.persistAndFlush(i5);

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

        for (Item item : books) {
            
            assertEquals(Categories.BOOKS, item.getCategory(), "Error: not a book");

        }

    }

    @Test
    public void getCheapestCoin() {

        assertEquals(i1.getName(), itemRepo.findByCategory(Categories.MISC, Sort.by(Sort.Direction.ASC, "price")).get(0).getName());
        
    }


}