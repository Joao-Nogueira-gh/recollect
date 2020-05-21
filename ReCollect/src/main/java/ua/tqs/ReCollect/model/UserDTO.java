package ua.tqs.ReCollect.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

public class UserDTO{
    private String name;

    @Email
    private String email;

    private String phoneNumber;

    private String password;

    private String location;

    private List<String> publishedItems;

    private List<String> soldItems;

    private List<String> favoriteItems;

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getPublishedItems() {
        return publishedItems;
    }

    public void setPublishedItems(List<String> publishedItems) {
        this.publishedItems = publishedItems;
    }
    public void addPublishedItems(String item) {
        this.publishedItems.add(item);
    }
    public List<String> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<String> soldItems) {
        this.soldItems = soldItems;
    }
    public void addSoldItems(String item) {
        this.soldItems.add(item);
    }

    public List<String> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(List<String> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }
    public void addFavoriteItems(String item) {
        this.favoriteItems.add(item);
    }

    public UserDTO(String name, @Email String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        this.favoriteItems = new ArrayList<>();
        this.soldItems = new ArrayList<>();
        this.publishedItems = new ArrayList<>();

    }

    
}