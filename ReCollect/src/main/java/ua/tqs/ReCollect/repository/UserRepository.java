package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //public Product findByX();
    public List<User> findAll();

    User findByEmail(String email);
}