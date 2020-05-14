package ua.tqs.ReCollect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.exceptions.EmailAlreadyInUseException;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository rcRepo;

    public List<User> getAll(){
        return rcRepo.findAll();
    }
    public void save(User user){
        rcRepo.save(user);
    }
    public void deleteAll(){
        rcRepo.deleteAll();
    }

	public boolean register(User user) throws EmailAlreadyInUseException{

        if(this.emailInUse(user.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        this.save(user);
        return true;

    }
    
    // Returns if the e-mail is already in use
	public boolean emailInUse(String email) {
        return this.rcRepo.findByEmail(email) != null;
	}

}