package ua.tqs.ReCollect.service;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Location getLocation(String district, String county){
        return locationRepo.findByDistrictAndCounty(district, county);
    }

    public HashMap<String, ArrayList<String>> getCountiesByDistrict() {
        
        List<Location> locs = this.getAll();
        HashMap<String, ArrayList<String>> ret = new HashMap<>();

        for (Location location : locs) {

            if(!ret.containsKey(location.getDistrict())) {

                ret.put(location.getDistrict(), new ArrayList<>());

            } 

            ret.get(location.getDistrict()).add(location.getCounty());
            
        }

        return ret;
    }
}