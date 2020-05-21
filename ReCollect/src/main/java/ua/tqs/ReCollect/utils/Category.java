package ua.tqs.ReCollect.utils;

import java.util.ArrayList;
import java.util.Objects;

public class Category {

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

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", iconClass='" + iconClass + '\'' +
                '}';
    }

    public static ArrayList<Category> getCategories() {

        ArrayList<Category> ret = new ArrayList<>();

        ret.add(new Category("Books", "fas fa-book-open icon-bg-1"));
        ret.add(new Category("Games", "fas fa-dice-d6  icon-bg-2"));
        ret.add(new Category("Toys", "fas fa-robot icon-bg-3"));
        ret.add(new Category("Technology", "fa fa-camera-retro icon-bg-4"));
        ret.add(new Category("Music", "fas fa-guitar icon-bg-5"));
        ret.add(new Category("Art", "fas fa-image icon-bg-6"));
        ret.add(new Category("Miscellaneous", "fas fa-box-open icon-bg-7"));

        return ret;
    }
}
