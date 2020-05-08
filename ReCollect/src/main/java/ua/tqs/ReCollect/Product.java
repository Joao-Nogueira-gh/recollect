package ua.tqs.ReCollect;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal price;
    
    public Product(){
        ;
    }
    
}