package ua.tqs.ReCollect.utils;


import javax.validation.constraints.NotNull;

public class SearchParams {

    @NotNull
    private String category;

    private String searchterm;

    public SearchParams() {
        //empty constructor
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
