package ua.tqs.ReCollect.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void queries() {
        System.out.println(itemRepo);
    }
    
}