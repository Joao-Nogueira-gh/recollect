package ua.tqs.ReCollect.model;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "images")
    @ElementCollection(targetClass = URL.class)
    private List<URL> images;

    @CreationTimestamp
    @Column(name = "creationDate")
    private Date creationDate;

    @ManyToMany(mappedBy = "favoriteItems")
    private Set<User> favedBy;

    @ManyToOne
    @JoinColumn(name="ownerid")
    private User owner;

    @ManyToOne
    @JoinColumn(name="sellerid")
    private User seller;

    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @Column(name = "category")
    private Categories category;
    
    public Item(){
        this.favedBy=new HashSet<User>();
        this.images=new ArrayList<URL>();
        this.comments= new HashSet<Comment>();
    }

    public Item(String name, int quantity, BigDecimal price, String description, Categories category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.category=category;

        this.favedBy=new HashSet<User>();
        this.images=new ArrayList<URL>();
        this.comments= new HashSet<Comment>();
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<URL> getImages() {
        return images;
    }

    public void addImage(URL image) {
        this.images.add(image);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Set<User> getFavedBy() {
        return favedBy;
    }

    public void addFavedBy(User favedBy) {
        this.favedBy.add(favedBy);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Set<Comment> getComment() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item [category=" + category + ", comments=" + comments + ", creationDate=" + creationDate
                + ", description=" + description + ", favedBy=" + favedBy + ", id=" + id + ", images=" + images
                + ", name=" + name + ", owner=" + owner + ", price=" + price + ", quantity=" + quantity + ", seller="
                + seller + "]";
    }
  
}