package ua.tqs.ReCollect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.ReCollect.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    public List<Location> findAll();
    public Location findByDistrictAndCounty(String district, String county);
    
}