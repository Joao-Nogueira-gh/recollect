package ua.tqs.ReCollect.model;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "item")
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    private int quantity;

    private BigDecimal price;

    private String description;

    private URL image;

    @CreationTimestamp
    private Date creationDate;

    @ManyToMany(mappedBy = "favoriteItems")
    private Set<User> favedBy;

    @ManyToOne
    @JoinColumn(name="ownerid")
    private User owner;

    @ManyToOne
    @JoinColumn(name="sellerid")
    private User seller;

    @OneToOne(mappedBy = "item")
    private Comment comment;
    
    public Item(){
        this.favedBy=new HashSet<User>();
    }

    public Item(String name, int quantity, BigDecimal price, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;

        this.favedBy=new HashSet<User>();
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

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<User> getFavedBy() {
        return favedBy;
    }

    public void setFavedBy(Set<User> favedBy) {
        this.favedBy = favedBy;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
  
}