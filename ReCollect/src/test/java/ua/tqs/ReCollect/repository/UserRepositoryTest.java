package ua.tqs.ReCollect.repository;

import static org.junit.Assert.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.tqs.ReCollect.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void test() {
        userRepo.deleteAll();

        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    void getUserByEmail() {
        User savedUser = entityManager.persistAndFlush(
                new User("User123", "user@email.com", "password", "123123123"));

        User user = userRepo.findByEmail("user@email.com");

        Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
    }

}