package ua.tqs.ReCollect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAll();

    public Optional<Item> findById(Long id);

    public List<Item> findByName(String name);

    public void deleteById(Long id);


    // public List<Item> findAllOrderByPrice();

    // public List<Item> findAllOrderByCreationDate();

    public List<Item> findByCategory(String cateogyr);

    public List<Item> findBySeller(Long id);


    public List<Item> findByCategoryAndSeller(String category, Long id);

    public List<Item> findByCategoryOrderByPrice(String category);

    public List<Item> findBySellerOrderByPrice(Long id);

    public List<Item> findByCategoryOrderByCreationDate(String category);

    public List<Item> findBySellerOrderByCreationDate(Long id);


    public List<Item> findByCategoryAndSellerOrderByPrice(String category, Long id);

    public List<Item> findByCategoryAndSellerOrderByCreationDate(String category, Long id);

}



    

