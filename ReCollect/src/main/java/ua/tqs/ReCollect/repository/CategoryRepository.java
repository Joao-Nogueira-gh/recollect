package ua.tqs.ReCollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, String> {


    Optional<Category> findByName(String s);

    @Override
    List<Category> findAll();
}
