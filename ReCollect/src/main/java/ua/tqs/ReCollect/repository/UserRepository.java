package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByEmail(String email);

    boolean existsByEmail(String email);
    
    List<User> findByLocation(Location loc);
    
}
