package ua.tqs.ReCollect.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.ReCollect.entity.Item;

@Repository
public interface UserRepository extends JpaRepository<Item, Long>  {



}
