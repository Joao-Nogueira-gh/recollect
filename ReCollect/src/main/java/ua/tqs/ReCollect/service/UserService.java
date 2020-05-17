package ua.tqs.ReCollect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.exceptions.EmailAlreadyInUseException;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }

    public boolean register(User user) throws EmailAlreadyInUseException {

        if (this.emailInUse(user.getEmail())) {
            throw new EmailAlreadyInUseException();
        }
        this.save(user);
        return true;

    }

    // Returns if the e-mail is already in use
    public boolean emailInUse(String email) {
        return this.userRepo.findByEmail(email) != null;
    }

    //weird alternate solution but idk
    public List<String> apiGetAll() {
        ListIterator<User> it = this.getAll().listIterator();
        List<String> ul= new ArrayList<>();
        while(it.hasNext()){
            ul.add(it.next().toString());
        }
        return ul;
    }
    public User getByEmail(String email){
        return userRepo.findByEmail(email);

    }

}