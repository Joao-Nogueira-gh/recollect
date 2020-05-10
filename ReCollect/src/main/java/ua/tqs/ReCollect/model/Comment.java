package ua.tqs.ReCollect.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @CreationTimestamp
    private Timestamp timestamp;
    
    public Comment(){
    }
    public Comment(String text){
        this.text=text;

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
    
    
}