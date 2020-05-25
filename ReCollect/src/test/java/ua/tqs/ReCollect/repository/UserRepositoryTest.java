package ua.tqs.ReCollect.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;

@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto = create-drop"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getUserByEmail() {
        Location l = new Location("Viseu", "SCD");
        entityManager.persistAndFlush(l);

        User savedUser = entityManager.persistAndFlush(
                new User("User123", "user@email.com", "password", "123123123", l));

        User user = userRepo.findByEmail("user@email.com");

        Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
    }
    @Test
    void userExists() {
        Location l = new Location("Viseu", "Viseu");
        String email="user2@email.com";
        entityManager.persistAndFlush(l);

        entityManager.persistAndFlush(
                new User("User123", email, "password", "123123123", l));

        assertTrue(userRepo.existsByEmail(email));

    }
    @Test
    void userDoesNotExist() {
        Location l = new Location("Viseu", "Tondela");
        String email="user3@email.com";
        entityManager.persistAndFlush(l);

        entityManager.persistAndFlush(
                new User("User123", email, "password", "123123123", l));

        assertFalse(userRepo.existsByEmail("random"+email));

    }

}