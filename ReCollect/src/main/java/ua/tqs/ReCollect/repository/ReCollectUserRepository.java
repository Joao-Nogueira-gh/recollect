package ua.tqs.ReCollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.tqs.ReCollect.model.User;

public interface ReCollectUserRepository extends JpaRepository<User, Long>{

    @Query("SELECT * FROM user u where u.email = :email") 
    User findUserByEmail(@Param("email") String email);
    
}