package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //public Product findByX();
    public List<User> findAll();
}