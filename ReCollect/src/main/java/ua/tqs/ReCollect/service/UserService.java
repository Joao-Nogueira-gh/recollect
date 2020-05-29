package ua.tqs.ReCollect.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.tqs.ReCollect.model.Item;
import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.model.UserDTO;
import ua.tqs.ReCollect.repository.OffsetBasedPageRequest;
import ua.tqs.ReCollect.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocationService locationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    static final Logger logger = Logger.getLogger(UserService.class);

    private static final int DEFAULT_LIMIT = 5;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public boolean userExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }

    public boolean register(User user) {

        if (userExists(user.getEmail())) {
            logger.debug("E-mail is already in use");
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        this.save(user);

        logger.debug(user.toString());

        return true;

    }

    public User getByEmail(String email) {

        return userRepo.findByEmail(email);

    }

    public List<User> getAll(OffsetBasedPageRequest offset) {

        return this.userRepo.findAll(offset).toList();

    }

    public List<User> getUsersByLocation(String distrito, String concelho, Integer limit, Integer offset) {

        List<User> ret;

        if (offset == null) {
            offset = 0;
        }

        if (limit == null || limit > DEFAULT_LIMIT) {

            limit = DEFAULT_LIMIT;

        }

        if (!(distrito == null ^ concelho == null)) {

            return new ArrayList<>();

        } else if (distrito == null && concelho == null) {

            ret = this.getAll(new OffsetBasedPageRequest(offset, limit));

        } else {

            ret = userRepo.findByLocation(locationService.getLocation(distrito, concelho), new OffsetBasedPageRequest(offset, limit));

        }


        return ret.stream().limit(limit).collect(Collectors.toList());

    }

    public UserDTO convertToDTO(User user) {

        UserDTO dto = new UserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getPassword());

        if (user.getLocation() != null) {
            dto.setLocation(user.getLocation().getCounty() + "-" + user.getLocation().getDistrict());
        }
        for (Item i : user.getFavoriteItems()) {
            dto.addFavoriteItems(i.getName() + ";" + i.getQuantity() + ";" + i.getPrice() + ";" + i.getDescription()
                    + ";" + i.getCategory());
        }
        for (Item i : user.getSoldItems()) {
            dto.addSoldItems(i.getName() + ";" + i.getQuantity() + ";" + i.getPrice() + ";" + i.getDescription() + ";"
                    + i.getCategory());
        }
        for (Item i : user.getPublishedItems()) {
            dto.addPublishedItems(i.getName() + ";" + i.getQuantity() + ";" + i.getPrice() + ";" + i.getDescription()
                    + ";" + i.getCategory());
        }

        return dto;

    }

    // might need to have several methods, or flags
    // this specific one is because of the register
    public User convertToUser(UserDTO userdto) {

        return new User(userdto.getName(), userdto.getEmail(), userdto.getPassword(), userdto.getPhoneNumber());

    }

    public User getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return userRepo.findByEmail(email);
    }

    public User getById(Long id) {

        Optional<User> optUser = this.userRepo.findById(id);

        if(optUser.isPresent()) {

            return optUser.get();
            
        }

        return null;

    }
    
    public Set<Item> getSoldItems(Integer offset, Integer limit) {

        if(offset == null) {
            offset = 0;
        }

        if(limit == null || limit > DEFAULT_LIMIT) {
            limit = DEFAULT_LIMIT;
        }

        List<User> users = this.userRepo.findAll(new OffsetBasedPageRequest(offset, limit)).toList();
        Set<Item> ret = new HashSet<>();

        for (User user : users) {

            ret.addAll(user.getSoldItems());
            
        }

        return ret;

    }

    public Set<Item> getItemsOnSale(Integer offset, Integer limit) {

        if(offset == null) {
            offset = 0;
        }

        if(limit == null || limit > DEFAULT_LIMIT) {
            limit = DEFAULT_LIMIT;
        }

        List<User> users = this.userRepo.findAll(new OffsetBasedPageRequest(offset, limit)).toList();
        Set<Item> ret = new HashSet<>();

        for (User user : users) {

            ret.addAll(user.getPublishedItems());
            
        }

        return ret;

    }

}