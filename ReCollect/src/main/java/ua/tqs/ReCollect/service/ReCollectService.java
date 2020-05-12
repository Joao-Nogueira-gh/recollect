package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.repository.ReCollectRepository;

@Service
public class ReCollectService {

    @Autowired
    private ReCollectRepository rcRepo;

    public List<Item> getAll(){
        return rcRepo.findAll();
    }
    public void save(Item item){
        rcRepo.save(item);
    }
    public void deleteAll(){
        rcRepo.deleteAll();
    }
}