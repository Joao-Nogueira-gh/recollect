package ua.tqs.ReCollect.service;

import java.util.List;
import java.util.function.BooleanSupplier;

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
    public void save(User user){
        rcRepo.save(user);
    }
    public void deleteAll(){
        rcRepo.deleteAll();
    }

	public boolean register(User user) {

        if(this.emailInUse(user.getEmail())) {
            System.out.println("ERROR: E-mail already in use");
            return false;
        }

        this.save(user);
        System.out.println(this.rcRepo.findAll());
        return true;

    }
    
    // Returns if the e-mail is already in use
	public boolean emailInUse(String email) {
        return this.rcRepo.findUserByEmail(email) != null;
	}

}