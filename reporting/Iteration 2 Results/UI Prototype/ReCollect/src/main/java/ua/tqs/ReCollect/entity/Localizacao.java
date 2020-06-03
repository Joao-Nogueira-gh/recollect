package ua.tqs.ReCollect.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String distrito;
    private String concelho;

    public Localizacao(String distrito, String concelho) {
        this.distrito = distrito;
        this.concelho = concelho;
    }

    public Localizacao() {
    }


    public String getConcelho() {
        return concelho;
    }

    public void setConcelho(String concelho) {
        this.concelho = concelho;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", concelho='" + concelho + '\'' +
                ", distrito='" + distrito + '\'' +
                '}';
    }
}
