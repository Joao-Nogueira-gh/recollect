package ua.tqs.ReCollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.User;

@Repository
public interface ReCollectUserRepository extends JpaRepository<User, Long>{

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true) 
    User findUserByEmail(@Param("email") String email);
    
}