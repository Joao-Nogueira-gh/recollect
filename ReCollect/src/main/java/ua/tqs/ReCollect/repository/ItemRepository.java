package ua.tqs.ReCollect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Categories;
import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAll();

    public Optional<Item> findById(Long id);

    public List<Item> findByName(String name);

    public void deleteById(Long id);

    public List<Item> findAll(Sort sort);

    public List<Item> findByCategory(Categories category);

    public List<Item> findByCategory(Categories category, Sort sort);

    public List<Item> findByOwner(User user);

    public List<Item> findByOwner(User user, Sort sort);

    public List<Item> findByCategoryAndOwner(Categories category, User user);

    public List<Item> findByCategoryAndOwner(Categories category, User user, Sort sort);

}



    

