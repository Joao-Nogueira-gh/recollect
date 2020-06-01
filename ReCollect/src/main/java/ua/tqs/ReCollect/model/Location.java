package ua.tqs.ReCollect.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "location",uniqueConstraints={
    @UniqueConstraint(columnNames = {"county", "district"})
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="county")
    private String county;

    @Column(name="district")
    private String district;
    
    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<User> usersloc=new HashSet<>();

    @PreRemove
    private void preRemove() {
        for (User u : usersloc) {
            u.setLocation(null);
        }
    }
    
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

    @Override
    public String toString() {
        return "Location [county=" + county + ", district=" + district + ", id=" + id  + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((county == null) ? 0 : county.hashCode());
        result = prime * result + ((district == null) ? 0 : district.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usersloc == null) ? 0 : usersloc.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (county == null) {
            if (other.county != null)
                return false;
        } else if (!county.equals(other.county))
            return false;
        if (district == null) {
            if (other.district != null)
                return false;
        } else if (!district.equals(other.district))
            return false;
        return true;
    }

    public Set<User> getUsersloc() {
        return usersloc;
    }
    public void remUsersloc(User user) {
        usersloc.remove(user);
    }


}