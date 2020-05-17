package ua.tqs.ReCollect.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Item;

import java.util.Optional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Override
    Optional<Item> findById(Long id);

    Optional<Item> findByNome(String nome);
}
