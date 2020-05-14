package ua.tqs.ReCollect.entity;

import java.util.ArrayList;

public class SearchParams {

    private String category;
    private String searchterm;

    public SearchParams() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }
}
