package ua.tqs.ReCollect.model.items;

import java.math.BigDecimal;

import javax.persistence.Entity;

import ua.tqs.ReCollect.model.Item;

@Entity
public class Coin extends Item {

    public Coin(String name, int quantity, BigDecimal price, String description) {
        super(name, quantity, price, description);
    }

    public Coin() {
    }
    


}