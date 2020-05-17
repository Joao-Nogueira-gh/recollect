package ua.tqs.ReCollect.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


}
