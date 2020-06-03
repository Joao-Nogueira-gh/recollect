package ua.tqs.ReCollect.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonIgnore
    private String password;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name="locationid", referencedColumnName = "id")
    private Location location;

    @PreRemove
    private void preRemove() {
        if (location != null) {
            location.remUsersloc(this);
        }
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
    name = "fav_items", 
    joinColumns = @JoinColumn(name = "favuserid", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "favitemid", referencedColumnName = "id"))
    private Set<Item> favoriteItems=new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> publishedItems=new HashSet<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> soldItems=new HashSet<>();

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments=new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    @JsonIgnore
    private Set<Role> roles=new HashSet<>();

    @Column(name = "active")
    private Boolean active;
    
    public User(){
        this.favoriteItems=new HashSet<>();
        this.publishedItems=new HashSet<>();
        this.soldItems=new HashSet<>();
        this.comments=new HashSet<>();
        this.roles=new HashSet<>();
        this.active = false;
    }

    public User(String name, @Email String email, String password, String phone, Location location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.favoriteItems=new HashSet<>();
        this.publishedItems=new HashSet<>();
        this.soldItems=new HashSet<>();
        this.comments=new HashSet<>();
        this.roles=new HashSet<>();
        this.active = false;
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
    public void remComment(Comment comment) {
        this.comments.remove(comment);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRole(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        String st=getName()+"/"+getEmail()+"/"+getPhone();
        return (location==null) ? st : st+"/"+location.getCounty()+"/"+location.getDistrict();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User(String name, @Email String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    public void addFavItem(Item i) {
        this.favoriteItems.add(i);
    }
    public void remFavItem(Item i) {
        this.favoriteItems.remove(i);
    }

    public void addPublishedItem(Item i) {
        this.publishedItems.add(i);        
    }
    public void remPubItem(Item i) {
        this.publishedItems.remove(i);
    }
    public void addSoldItem(Item i) {
        this.soldItems.add(i);        
    }
    public void remSoldItem(Item i) {
        this.soldItems.remove(i);
    }

}