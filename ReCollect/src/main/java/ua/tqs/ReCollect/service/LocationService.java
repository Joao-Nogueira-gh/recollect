package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    public List<Location> getAll(){
        return locationRepo.findAll();
    }
    public void save(Location item){
        locationRepo.save(item);
    }
    public void deleteAll(){
        locationRepo.deleteAll();
    }
}