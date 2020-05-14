package ua.tqs.ReCollect.model;

import javax.persistence.*;

@Entity
@Table(name = "location",uniqueConstraints={
    @UniqueConstraint(columnNames = {"county", "district"})
}) 
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="county")
    private String county;

    @Column(name="district")
    private String district;

    @OneToOne(mappedBy = "location")
    private User user;
    
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}