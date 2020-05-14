package ua.tqs.ReCollect;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ReCollectUserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ReCollectUserRepositoryTest {
    
    @Autowired
    private ReCollectUserRepository rcRepo;

    @Autowired
    private TestEntityManager entityManager;

   @Test
   public void getUserByEmail() {
       User savedUser = entityManager.persistAndFlush(new User("User123", "user@email.com", "password", "123123123", new Location("Viseu", "SCD")));

       User user = rcRepo.findUserByEmail("user@email.com");

       Assertions.assertThat(user.getName()).isEqualTo(savedUser.getName());
    }

}