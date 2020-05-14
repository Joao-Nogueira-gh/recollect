package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.ReCollectUserRepository;

@Service
public class ReCollectUserService {
    
    @Autowired
    private ReCollectUserRepository rcRepo;

    public List<User> getAll(){
        return rcRepo.findAll();
    }
    public void save(User item){
        rcRepo.save(item);
    }
    public void deleteAll(){
        rcRepo.deleteAll();
    }

}