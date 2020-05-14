package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Item;

@Repository
public interface ReCollectItemRepository extends JpaRepository<Item, Long> {

    //public Product findByX();
    public List<Item> findAll();
}