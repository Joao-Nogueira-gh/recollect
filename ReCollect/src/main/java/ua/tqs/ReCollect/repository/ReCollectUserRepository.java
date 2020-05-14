package ua.tqs.ReCollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.tqs.ReCollect.model.User;

public interface ReCollectUserRepository extends JpaRepository<User, Long>{
    
}