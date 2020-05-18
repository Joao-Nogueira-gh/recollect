package ua.tqs.ReCollect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tqs.ReCollect.entity.Category;
import ua.tqs.ReCollect.entity.Item;
import ua.tqs.ReCollect.entity.User;
import ua.tqs.ReCollect.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User getUserById(Long id){
        Optional<User> result = userRepository.findById(id);

        return result.orElse(null);
    }

    public User getUserByEmail(String email){
        Optional<User> result = userRepository.findByEmail(email);

        return result.orElse(null);
    }

    public boolean userExists(String email){
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User save(User u){
        return userRepository.save(u);
    }

    public void updateUser(User u){
        System.err.println("update1 | user: " + u.toString());
        Set<Item> itensPub = u.getItensPublicados();
        Set<Item> itensSold = u.getItensVendidos();
        Set<Item> itensFav = u.getItensFavoritos();
        System.err.println("update2 | itensPub: " + itensPub);
        u.setItensPublicados(new HashSet<>());
        u.setItensVendidos(new HashSet<>());
        u.setItensFavoritos(new HashSet<>());
        System.err.println("update3 | user: " + u.toString());
        userRepository.saveAndFlush(u);
        System.err.println("update4");
        u.setItensPublicados(itensPub);
        u.setItensVendidos(itensSold);
        u.setItensFavoritos(itensFav);
        System.err.println("update5 | user: " + u.toString());
        userRepository.saveAndFlush(u);
    }
}
