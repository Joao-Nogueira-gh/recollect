package ua.tqs.ReCollect.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Category {

    @Id
    private String name;

    private String iconClass;

    public Category() {
    }

    public Category(String name, String iconClass) {
        this.name = name;
        this.iconClass = iconClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(iconClass, category.iconClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iconClass);
    }
}
