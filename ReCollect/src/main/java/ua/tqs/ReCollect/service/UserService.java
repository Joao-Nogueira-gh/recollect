package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> getAll(){
        return userRepo.findAll();
    }
    public void save(User item){
        userRepo.save(item);
    }
    public void deleteAll(){
        userRepo.deleteAll();
    }
}