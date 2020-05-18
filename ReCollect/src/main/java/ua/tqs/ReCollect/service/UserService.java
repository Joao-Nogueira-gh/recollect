package ua.tqs.ReCollect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.repository.UserRepository;

@Service
public class UserService {

    private User currentUser;

    @Autowired
    private UserRepository userRepo;

    static final Logger logger = Logger.getLogger(UserService.class);

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }

    public boolean register(User user) {

        if (this.emailInUse(user.getEmail())) {
            logger.debug("E-mail is already in use");
            System.out.println("AAAAAAAAAAAAAAAA");
            return false;
        }

        this.save(user);
        return true;

    }

    // Returns if the e-mail is already in use
    public boolean emailInUse(String email) {
        System.out.println(this.userRepo.findByEmail(email));
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

	public boolean login(String email, String pass) {
        if (userRepo.findByEmail(email) != null){
            User u = userRepo.findByEmail(email);
            if (checkUserPassword(u, pass)){
                currentUser=u;
                return true;
            }
        }
		return false;
	}

	public boolean checkUserPassword(User user, String pass) {
		return user.getPassword().equals(pass) ? true : false;
    }
    public void logout(){
        currentUser=null;
    }
    public User getCurrentUser(){
        return currentUser;
    }

}