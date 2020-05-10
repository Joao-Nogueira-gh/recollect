package ua.tqs.ReCollect.model;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String county;

    private String district;
    
    public Location(){
    }

    public Location(String county, String district) {
        this.county = county;
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    
}