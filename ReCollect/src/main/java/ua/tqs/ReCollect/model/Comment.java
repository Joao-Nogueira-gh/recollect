package ua.tqs.ReCollect.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comment")
public class Comment implements Comparable<Comment> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @CreationTimestamp
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item", referencedColumnName = "id")
    private Item item;

    @PreRemove
    private void preRemove() {
        if (user != null) {
           user.remComment(this);
        }
        if (item != null) {
            item.remComment(this);
        }
    }
    
    public Comment(){
    }
    public Comment(String text, User user, Item item) {
        this.text = text;
        this.user = user;
        this.item = item;
    }
    

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", item=" + item.getName() + ", text=" + text + ", timestamp=" + timestamp + ", user=" + user.getName()
                + "]";
    }


    @Override
    public int compareTo(Comment comment) {
        return this.timestamp.compareTo(comment.timestamp);
    }
}