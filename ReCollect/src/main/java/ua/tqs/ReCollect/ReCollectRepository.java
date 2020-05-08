package ua.tqs.ReCollect;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReCollectRepository extends JpaRepository<Product, Long> {

    //public Product findByX();
    public List<Product> findAll();

}