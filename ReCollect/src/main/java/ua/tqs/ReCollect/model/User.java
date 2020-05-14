package ua.tqs.ReCollect.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    //unique
    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="locationid", referencedColumnName = "id")
    private Location location;

    @ManyToMany
    @JoinTable(
    name = "fav_items", 
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private Set<Item> favoriteItems;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Item> publishedItems;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Item> soldItems;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Comment> comments;
    
    public User(){
        this.favoriteItems=new HashSet<Item>();
        this.publishedItems=new HashSet<Item>();
        this.soldItems=new HashSet<Item>();
        this.comments=new HashSet<Comment>();
    }

    public User(String name, @Email String email, String password, String phone, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.favoriteItems=new HashSet<Item>();
        this.publishedItems=new HashSet<Item>();
        this.soldItems=new HashSet<Item>();
        this.comments=new HashSet<Comment>();
    }
    
    public Long getId() {
        return id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(Set<Item> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public Set<Item> getPublishedItems() {
        return publishedItems;
    }

    public void setPublishedItems(Set<Item> publishedItems) {
        this.publishedItems = publishedItems;
    }

    public Set<Item> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(Set<Item> soldItems) {
        this.soldItems = soldItems;
    }

    public Set<Comment> getComment() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "User [comments=" + comments + ", email=" + email + ", favoriteItems=" + favoriteItems + ", id=" + id
                + ", location=" + location + ", name=" + name + ", password=" + password + ", phone=" + phone
                + ", publishedItems=" + publishedItems + ", soldItems=" + soldItems + "]";
    }
}