package ua.tqs.ReCollect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAll();

    public Optional<Item> findById(Long id);

    public List<Item> findByName(String name);

    public void deleteById(Long id);

    public List<Item> findByCategory(Categories category);

    public List<Item> findByNameContaining(String name);

    public List<Item> findByNameContainingAndCategory(String name, Categories category);

}



    

